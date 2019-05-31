package com.artsfx.springboot.scrumapp.scrumapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artsfx.springboot.scrumapp.scrumapp.entity.Role;

public interface RolesRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(String name);
}
