package com.example.myweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myweb.model.UserD;
import com.example.myweb.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserService service;

	@ModelAttribute
	public void addFullNameToModel(Model model, HttpSession session) {
		String fullName = (String) session.getAttribute("fullName");
		model.addAttribute("fullName", fullName);
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("pageTitle", "Home - My Mentor");
		return "index"; // Renders index.html
	}

	@GetMapping("login")
	public String logIn(Model model, HttpSession session) {
		String error = (String) session.getAttribute("error");

		if (error != null) {
			model.addAttribute("error", error);
			session.removeAttribute("error");
		}
		model.addAttribute("user", new UserD()); // Add an empty UserD object to the model
		model.addAttribute("pageTitle", "Login - My Mentor");
		return "login";
	}

	@PostMapping("/login")
	public String showLogIn(@RequestParam("role") String role, @RequestParam("email") String email,
			@RequestParam("password") String password, HttpSession session) {
		UserD logIn = service.logIn(role, email, password);

		if (logIn != null) {
			System.out.println(logIn);
			session.setAttribute("fullName", logIn.getFullName());
			session.setAttribute("role", logIn.getRole());
			session.setAttribute("email", logIn.getEmail());
			session.setAttribute("id", logIn.getId());
			return "redirect:/main"; // Redirect to the main page if login is successful
		} else {
			session.setAttribute("error", "Invalid email or password!"); // error session me daal
			return "redirect:/login";// Return to login page with error message
		}
	}

	@PostMapping("/logout")
	public String logUot(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("d", new UserD());
		return "signup";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute UserD d, Model model) {

		boolean checkEmail = service.checkEmail(d.getEmail());

		if (checkEmail == true) {
			model.addAttribute("error", "User already registered with this email!");
			return "redirect:/login";
		} else {
			UserD user = service.createUser(d);
			model.addAttribute("success", "New user registered successfully!");
			System.out.println(user);
			return "redirect:/main"; // Renders signup .html
		}
	}

	@GetMapping("/main")
	public String mainPage(Model model, HttpSession session) {

		String fullName = (String) session.getAttribute("fullName");
		String role = (String) session.getAttribute("role");

		if (fullName == null || role == null) {
			return "redirect:/login";
		}
		model.addAttribute("fullName", fullName);
		model.addAttribute("role", role);

		return "main";
	}

	@GetMapping("/careerguidance")
	public String careerGuidance(Model model, HttpSession session) {

		String fullName = (String) session.getAttribute("fullName");
		if (fullName == null) {
			return "redirect:/login"; // Session expire hua, direct login page }
		}
		model.addAttribute("fullName", fullName); // navbar me show hoga
		model.addAttribute("pageTitle", "Career Guidance");
		return "careerguidance"; // Renders career-guidance.html
	}

	@GetMapping("/personal-doubt")
	public String personalDoubt(Model model) {
		model.addAttribute("pageTitle", "Personal Doubt");
		return "personal-doubt"; // Renders personal-doubt.html
	}

	@GetMapping("/psychologist-consultation")
	public String psychologistConsultation(Model model) {
		model.addAttribute("pageTitle", "Psychologist Consultation");
		return "psychologist-consultation"; // Renders psychologist-consultation.html
	}

	@GetMapping("/motivation-tips")
	public String motivationTips(Model model) {
		model.addAttribute("pageTitle", "Motivation Tips");
		return "motivation-tips"; // Renders motivation-tips.html
	}

	@GetMapping("/get-started")
	public String getStarted(Model model) {
		model.addAttribute("pageTitle", "Get Started");
		return "get-started"; // Renders get-started.html
	}

	@GetMapping("/career-guidance-course")
	public String careerGuidanceCourse(Model model) {
		model.addAttribute("pageTitle", "Career Guidance Course");
		return "career-guidance-course"; // Renders career-guidance-course.html
	}
}
