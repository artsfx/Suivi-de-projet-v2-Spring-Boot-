package com.artsfx.springboot.scrumapp.scrumapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.artsfx.springboot.scrumapp.scrumapp.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	@Transactional
	List<Project> deleteAllByTeamId(int teamId);
}
