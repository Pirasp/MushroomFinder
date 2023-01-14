package de.mushroomfinder.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.mushroomfinder.entities.Comment;
import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.repository.CommentRepository;
import de.mushroomfinder.repository.SpotRepository;

@Controller
public class CommentsAddController {

	@Autowired 
	CommentRepository commentRepository;
	
	@Autowired
	SpotRepository spotRepository;
	
	@GetMapping("comments/add/{id}")
	public ModelAndView showCommentForm(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView();
		Comment comment = new Comment();

		Optional<Spot> spotOpt = spotRepository.findById(id);
		comment.setSpot(spotOpt.get());
		mv.setViewName("addComment");
		mv.addObject("commentForm", comment);
		return mv;
		
	}
	
	@PostMapping("comments/add/save")
	public String saveComment(@ModelAttribute("commentForm") Comment commentForm) {
		Optional<Spot> spotOpt = spotRepository.findById(commentForm.getSpot().getId());
		commentForm.setSpot(spotOpt.get());
		commentForm.setDate(LocalDate.now());
		Comment comment = commentRepository.save(commentForm);
		System.out.println("Kommentar mit der ID: " + comment.getId() + " gespeichert. Comment Spot: " + comment.getSpot());
		return("map");
	}
	
	@RequestMapping("/comments/{id}")
	public ModelAndView showComments(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView();
		Optional<Spot> spotOpt = spotRepository.findById(id);
		Spot spot = spotOpt.get();
		List<Comment> comments = spot.getComments();
		
		mv.setViewName("showComments");
		mv.addObject("comments", comments);
		return mv;
	}
	
}
