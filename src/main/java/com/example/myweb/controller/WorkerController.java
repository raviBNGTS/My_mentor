package com.example.myweb.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myweb.model.WorkerCourse;
import com.example.myweb.service.WorkerService;

@Controller
public class WorkerController {

	@Autowired
	private WorkerService service;

	@GetMapping("/workerdashboard")
	public String workerDashboard(Model model) {
		if (model.containsAttribute("success")) {
			System.out.println("Flash attribute success: " + model.getAttribute("success"));
		}
		model.addAttribute("pageTitle", "Upload Your Course - My Mentor");
		model.addAttribute("WorkerCourse", new WorkerCourse());
		return "workerdashboard";
	}

	@PostMapping("/addCourse")
	public String createCourse(@RequestParam("file") MultipartFile file,
							   WorkerCourse course,
							   RedirectAttributes attrs) throws IOException {


		// Save the file to static/images directory
		String filename = file.getOriginalFilename();
		String filePath = "/images/" + filename;  // Save the file with a relative path

		// Save the file on disk (inside static/images)
		Path path = Paths.get("src/main/resources/static/images/" + filename);
		Files.write(path, file.getBytes());

		// Set the file path in course object
		course.setFileName(filePath);

		service.addCourse(course, file); // persist

		// Add success message
		attrs.addFlashAttribute("success", "Upload successful ✅");
		return "redirect:/workerdashboard"; // Redirect to the dashboard
	}

	// 🔥 API 1: Get all courses by email
	@GetMapping("/getAllcourses")
	@ResponseBody
	public List<WorkerCourse> getCoursesByEmail(@RequestParam String email) {
		String cleanEmail = email.trim().toLowerCase(); // optional toLowerCase
		return service.getCoursesByEmail(cleanEmail);
	}

	// 🔥 API 2: Edit course by courseId
	@PutMapping("/course/{id}")
	@ResponseBody
	public WorkerCourse updateCourse(@PathVariable("id") int id, @RequestBody WorkerCourse updatedCourse) {
		try {
			return service.updateCourse(id, updatedCourse);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	// 🔥 API 3: Delete course by courseId
	@DeleteMapping("/course/{id}")
	@ResponseBody
	public String deleteCourse(@PathVariable int id) {
		try {
			service.deleteCourse(id);
			return "Course deleted successfully.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
	}

	@GetMapping("/featured-courses")
	@ResponseBody
	public List<WorkerCourse> getFeaturedCourses() {
		return service.getTopCourses(10); // return top 10 courses
	}

}
