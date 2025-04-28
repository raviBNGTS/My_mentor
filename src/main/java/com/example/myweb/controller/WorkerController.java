package com.example.myweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myweb.model.WorkerCourse;
import com.example.myweb.service.WorkerService;

@Controller
public class WorkerController {

	@Autowired
	private WorkerService service;

	@GetMapping("/workerdashboard")
	public String workerDashboard(Model model) {
		model.addAttribute("pageTitle", "Upload Your Course - My Mentor");
		model.addAttribute("WorkerCourse", new WorkerCourse());
		return "workerdashboard";
	}

	@PostMapping("/workerdashboard")
	public String addCourse(@RequestParam("file") MultipartFile file, WorkerCourse course,
			RedirectAttributes attributes) {
		try {
			String upload = service.addCourse(course, file);
			if (upload != null) {
				System.out.println(course);
				attributes.addFlashAttribute("success", "upload successfull");
				return "redirect:/workerdashboard";
			}
		} catch (Exception e) {
			attributes.addFlashAttribute("error", "file already exists ");
		}
		return "redirect:/workerdashboard";

	}
	// 🔥 API 1: Get all courses by email
	@GetMapping("/getAllcourses")
	@ResponseBody
	public List<WorkerCourse> getCoursesByEmail(@RequestParam String email) {
		String cleanEmail = email.trim().toLowerCase();   // optional toLowerCase
		return service.getCoursesByEmail(cleanEmail);
	}
	

	// 🔥 API 2: Edit course by courseId
	@PutMapping("/course/{id}")
	@ResponseBody
	public WorkerCourse updateCourse(@PathVariable("id") int id, @RequestBody WorkerCourse updatedCourse) {
		return service.updateCourse(id, updatedCourse);
	}

	// 🔥 API 3: Delete course by courseId
	@DeleteMapping("/course/{id}")
@ResponseBody
public String deleteCourse(@PathVariable int id) {
    service.deleteCourse(id);
    return "Course deleted successfully.";
}

}
