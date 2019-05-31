package com.artsfx.springboot.scrumapp.scrumapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "teams")
public class Team {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="team_id")
	private int id;
	
	@Column(name = "team_name")
	@NotEmpty(message="Team name should not be empty")
	private String teamName;
//	
	@ManyToMany(mappedBy="teams")
	private List<User> users;
	
	
	@OneToMany(mappedBy="team",
			   cascade= {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	private List<Project> projects;
	
	
	// getters and setters

	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public Team() {
		// TODO Auto-generated constructor stub
	}

	public Team(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the groupName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * @param teamName the groupName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void addProject(Project project) {
		if (projects == null) {
			projects = new ArrayList<>();
		}
		projects.add(project);
		project.setTeam(this);
	}
	
	public void removeProjects() {
		projects = null;
	}

	public void removeProject(Project project) {
		// TODO Auto-generated method stub
		projects.remove(project);
		
	}

}
