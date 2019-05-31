package com.artsfx.springboot.scrumapp.scrumapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artsfx.springboot.scrumapp.scrumapp.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
