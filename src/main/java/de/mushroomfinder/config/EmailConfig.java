/*
 * package de.mushroomfinder.config;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.mail.javamail.JavaMailSender; import
 * org.springframework.mail.javamail.JavaMailSenderImpl;
 * 
 * @Configuration public class EmailConfig {
 * 
 * @Bean public JavaMailSender getJavaMailSender() { JavaMailSender mailSender =
 * new JavaMailSenderImpl(); mailSender.setHost("smtp.gmail.com");
 * mailSender.setPort(25);
 * 
 * mailSender.setUsername("admin@gmail.com");
 * mailSender.setPassword("password");
 * 
 * Properties props = mailSender.getJavaMailProperties();
 * props.put("mail.transport.protocol", "smtp"); props.put("mail.smtp.auth",
 * "true"); props.put("mail.smtp.starttls.enable", "true");
 * props.put("mail.debug", "true");
 * 
 * return mailSender; } }
 */
