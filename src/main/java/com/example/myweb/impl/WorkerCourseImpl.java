package com.example.myweb.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.myweb.model.WorkerCourse;
import com.example.myweb.repository.WorkerRepo;
import com.example.myweb.service.WorkerService;

@Service
public class WorkerCourseImpl implements WorkerService {

	private static final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "static" + File.separator + "images";
	@Autowired
	private WorkerRepo repo;

	@Override
	public String addCourse(WorkerCourse course, MultipartFile file) throws IOException {

		// Create the upload folder if it doesn't exist
		File uploadFolder = new File(UPLOAD_DIR);
		if (!uploadFolder.exists()) {
			uploadFolder.mkdirs();
		}

		if (file != null && !file.isEmpty()) {
			// Get the original file name
			String originalName = file.getOriginalFilename();

			// Generate a unique file name to avoid conflicts
			String uniqueName = System.currentTimeMillis() + "_" + originalName;

			// File path
			String filepath = UPLOAD_DIR + File.separator + uniqueName;

			// Copy and save the file
			Files.copy(file.getInputStream(), Paths.get(filepath));

			// Set the unique file name in the course
			course.setFileName(uniqueName);
		} else {
			// No file provided, set file name as null
			course.setFileName(null);
		}

		// Save the course to the repository
		repo.save(course);

		return "Course uploaded successfully.";
	}

	@Override
	public List<WorkerCourse> getCoursesByEmail(String email) {
		// remove the UnsupportedOperationException line!
		return repo.findByEmail(email.trim().toLowerCase());  // or however you store e-mails
	}


	@Override
	public WorkerCourse updateCourse(int courseId, WorkerCourse updatedCourse) {
		// Check if the course exists
		WorkerCourse existingCourse = repo.findById(courseId)
				.orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " not found"));

		// Update the fields of the existing course with the new values
		existingCourse.setTitle(updatedCourse.getTitle());
		existingCourse.setDescription(updatedCourse.getDescription());
		existingCourse.setCategory(updatedCourse.getCategory());
		existingCourse.setPrice(updatedCourse.getPrice());
		existingCourse.setFileName(updatedCourse.getFileName()); // Optional: Update file name if needed

		// Save the updated course to the repository
		return repo.save(existingCourse);
	}

	@Override
	public void deleteCourse(int courseId) {
		// Check if the course exists
		WorkerCourse course = repo.findById(courseId)
				.orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " not found"));

		// Delete the course
		repo.delete(course);
	}
	public List<WorkerCourse> getTopCourses(int limit) {
		return repo.findTopCourses(PageRequest.of(0, limit));
	}

}
