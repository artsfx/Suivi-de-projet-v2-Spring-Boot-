package com.artsfx.springboot.scrumapp.scrumapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artsfx.springboot.scrumapp.scrumapp.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
