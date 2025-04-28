package com.example.myweb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.myweb.model.WorkerCourse;
public interface WorkerService {
	
	public String addCourse(WorkerCourse course , MultipartFile file) throws IOException;

    public List<WorkerCourse> getCoursesByEmail(String email);

    public WorkerCourse updateCourse(int courseId, WorkerCourse updatedCourse);

    public void deleteCourse(int courseId);

    //get all course by category
   public List<WorkerCourse> getCoursesByCategory(String category);


}
