package com.example.myweb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.myweb.model.WorkerCourse;
import com.example.myweb.service.WorkerService;

@Controller
public class WorkerController {

	@Autowired
	private WorkerService workerService;

	@GetMapping("/workerdashboard")
	public String workerDashboard(Model model) {
		model.addAttribute("pageTitle", "Upload Your Course - My Mentor");
		model.addAttribute("WorkerCourse", new WorkerCourse());
		return "workerdashboard";
	}

	// Upload new course
	@PostMapping("/workerdashboard")
	public String addCourse(@ModelAttribute WorkerCourse course,
							@RequestParam("file") MultipartFile file,
							Model model) {
		try {
			String msg = workerService.addCourse(course, file);
			model.addAttribute("success", msg);
		} catch (IOException e) {
			model.addAttribute("error", "File upload failed: " + e.getMessage());
		}
		return "redirect:/workerdashboard";
	}
	// 🔥 API 1: Get all courses by email
	@GetMapping("/getAllcourses")
	@ResponseBody
	public List<WorkerCourse> getCoursesByEmail(@RequestParam String email) {
		String cleanEmail = email.trim().toLowerCase();   // optional toLowerCase
		return workerService.getCoursesByEmail(cleanEmail);
	}
	

	// 🔥 API 2: Edit course by courseId
	@PutMapping("/course/{id}")
	@ResponseBody
	public WorkerCourse updateCourse(@PathVariable("id") int id, @RequestBody WorkerCourse updatedCourse) {
		return workerService.updateCourse(id, updatedCourse);
	}

	// 🔥 API 3: Delete course by courseId
	@DeleteMapping("/course/{id}")
@ResponseBody
public String deleteCourse(@PathVariable int id) {
    workerService.deleteCourse(id);
    return "Course deleted successfully.";
}

}
