package com.example.myweb.controller;

import com.example.myweb.model.WorkerCourse;
import com.example.myweb.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private WorkerService workerService;

    @GetMapping("/category/{category}")
    public String getAllCoursesByCategory(@PathVariable String category, Model model) {
        List<WorkerCourse> courses = workerService.getCoursesByCategory(category);
        model.addAttribute("courses", courses);
        model.addAttribute("category", category);
        return "courses-by-category";
    }

}
