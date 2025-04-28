package com.example.myweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myweb.model.WorkerCourse;

public interface WorkerRepo extends JpaRepository<WorkerCourse, Integer>{

    public List<WorkerCourse> findByEmail(String toLowerCase);
	

}
