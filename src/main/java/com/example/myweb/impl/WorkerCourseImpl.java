package com.example.myweb.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

		// folder nhi hoga to folder nhi banega

		File uploadFFolder = new File(UPLOAD_DIR);
		if (!uploadFFolder.exists()) {
			uploadFFolder.mkdir();
		}

		// file name get
		String name = file.getOriginalFilename();

//			file path 
		String filepath = UPLOAD_DIR + File.separator + name;

		// copy and savve file

		if (Files.exists(Paths.get(filepath))) {
			throw new FileAlreadyExistsException(name);
		}

		Files.copy(file.getInputStream(), Paths.get(filepath));

		course.setFileName(file.getOriginalFilename());

		repo.save(course);
		return "course uploaded succes";

	}

	@Override
    public List<WorkerCourse> getCoursesByEmail(String email) {
        // remove the UnsupportedOperationException line!
        return repo.findByEmail(email.trim().toLowerCase());  // or however you store e-mails
    }


    @Override
    public WorkerCourse updateCourse(int courseId, WorkerCourse updatedCourse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteCourse(int courseId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
