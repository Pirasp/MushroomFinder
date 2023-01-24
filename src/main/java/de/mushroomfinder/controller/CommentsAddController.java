package de.mushroomfinder.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.mushroomfinder.entities.Comment;
import de.mushroomfinder.entities.CommentVote;
import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.entities.User;
import de.mushroomfinder.repository.CommentRepository;
import de.mushroomfinder.repository.CommentVoteRepository;
import de.mushroomfinder.repository.SpotRepository;
import de.mushroomfinder.repository.UserRepository;

@Controller
public class CommentsAddController {

	@Autowired 
	CommentRepository commentRepository;
	
	@Autowired
	SpotRepository spotRepository;
	
	@Autowired
	CommentVoteRepository commentVoteRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("comments/add/{id}")
	public ModelAndView showCommentForm(@PathVariable("id") Long id, Principal principal) {
		ModelAndView mv = new ModelAndView();
		Comment comment = new Comment();
		Optional<Spot> spotOpt = spotRepository.findById(id);
		if(spotOpt.isPresent()==false) {
			mv.setViewName("map");
			mv.addObject("error", "The spot to be commented does not exist");
			return mv;
		}

		mv.setViewName("comment/addComment");
		comment.setSpot(spotOpt.get());
		mv.addObject("commentForm", comment);
		return mv;
		
	}
	
	@PostMapping("comments/add/save")
	public String saveComment(@ModelAttribute("commentForm") Comment commentForm, Principal principal, Model model) {
		Optional<Spot> spotOpt = spotRepository.findById(commentForm.getSpot().getId());
		commentForm.setSpot(spotOpt.get());
		commentForm.setDate(LocalDate.now());
		
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		if(oLoggedUser.isPresent() == false) {
			System.out.println("Kein User eingeloggt");
		}
		if(commentForm.getMessage().isBlank()) {
			model.addAttribute("error", "Comment Message must not be empty!");
			return "comment/addComment";
		}
		commentForm.setUser(oLoggedUser.get());
		Comment comment = commentRepository.save(commentForm);
		System.out.println("Kommentar mit der ID: " + comment.getId() + " gespeichert. Comment Spot: " + comment.getSpot());
		return("redirect:/comments/" + comment.getSpot().getId());
	}
	
	@RequestMapping("/comments/{id}")
	public ModelAndView showComments(@PathVariable("id") Long id, Principal principal) {
		ModelAndView mv = new ModelAndView();
		Optional<Spot> spotOpt = spotRepository.findById(id);
		if(spotOpt.isPresent() == false) {
			System.out.println("Spot " + id + " existiert nicht!");
			mv.setViewName("map");
			mv.addObject("error", "This spot does not exist!");
			return mv;
		}
		Spot spot = spotOpt.get();
		List<Comment> comments = spot.getComments();

		mv.setViewName("comment/showComments");
		mv.addObject("comments", comments);
		mv.addObject("userName", principal.getName());
		mv.addObject("spotId", spot.getId());
		
		return mv;
	}
	
	@GetMapping("/comments/edit/{id}")
	public ModelAndView commentEditForm(@PathVariable("id") Long id, Principal principal) {
		Optional<Comment> optComment = commentRepository.findById(id);
		ModelAndView mv = new ModelAndView();
		if(optComment.isPresent()==false) {
			mv.setViewName("map");
			mv.addObject("error", "The comment to be edited does not exist!");
			return mv;
		}
		Comment comment = optComment.get();		
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		
		if(comment.getUser().getId() != oLoggedUser.get().getId()) {
			mv.setViewName("map");
			return mv;
		}
		
		mv.setViewName("comment/editComment");
		mv.addObject("comment", comment);
		return mv;
	}
	
	@PostMapping("/comments/edit/save/{id}")
	public String saveCommentEdit(@PathVariable("id") Long id, @ModelAttribute("comment") Comment comment) {
		Optional<Comment> optComment = commentRepository.findById(id);
		Comment c = optComment.get();
		c.setMessage(comment.getMessage());
		commentRepository.save(c);
		return "redirect:/comments/"+c.getSpot().getId();
	}
	
	@GetMapping("/comments/delete/{id}")
	public String deleteComment(@PathVariable("id") Long id, Model model) {
		Optional<Comment> optComment = commentRepository.findById(id);
		if(optComment.isPresent()==false) {
			model.addAttribute("error", "The comment to be deleted does not exist!");
			return "map";
		}
		Comment comment = optComment.get();
		Long spotId = comment.getSpot().getId();
		commentRepository.delete(comment);
		return "redirect:/comments/"+spotId;
	}
	
	@GetMapping("/comments/upvote/{id}")
	public String upvoteComment(@PathVariable("id") Long cId, Principal principal) {
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		
		Optional<Comment> optComment = commentRepository.findById(cId);
		Comment comment = optComment.get();
		Long spotId = comment.getSpot().getId();
		
		Optional<CommentVote> optCommentVote = commentVoteRepository.findVotesByUserAndCommentId(oLoggedUser.get().getId(), cId);
	
		if(optCommentVote.isPresent()) {
			if(optCommentVote.get().getVote() == 1) {
				optCommentVote.get().setVote(0);
			}else {
				optCommentVote.get().setVote(optCommentVote.get().getVote()+1);
			}
			
		}else {
			CommentVote newCommentVote = new CommentVote();
			newCommentVote.setUser(oLoggedUser.get());
			newCommentVote.setVote(1);
			newCommentVote.setComment(comment);
			List<CommentVote> newCommentVotes = new ArrayList<>();
			newCommentVotes.add(newCommentVote);
			comment.setCommentVotes(newCommentVotes);
		}
		commentRepository.save(comment);

		return "redirect:/comments/"+spotId;
	}
	
	@GetMapping("/comments/downvote/{id}")
	public String downvoteComment(@PathVariable("id") Long cId, Principal principal) {
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		
		Optional<Comment> optComment = commentRepository.findById(cId);
		Comment comment = optComment.get();
		Long spotId = comment.getSpot().getId();
		
		Optional<CommentVote> optCommentVote = commentVoteRepository.findVotesByUserAndCommentId(oLoggedUser.get().getId(), cId);
	
		if(optCommentVote.isPresent()) {
			if(optCommentVote.get().getVote() == -1) {
				optCommentVote.get().setVote(0);
			}else {
				optCommentVote.get().setVote(optCommentVote.get().getVote()-1);
			}
			
		}else {
			CommentVote newCommentVote = new CommentVote();
			newCommentVote.setUser(oLoggedUser.get());
			newCommentVote.setVote(-1);
			newCommentVote.setComment(comment);
			List<CommentVote> newCommentVotes = new ArrayList<>();
			newCommentVotes.add(newCommentVote);
			comment.setCommentVotes(newCommentVotes);
		}
		commentRepository.save(comment);

		return "redirect:/comments/"+spotId;
	}
	
}
