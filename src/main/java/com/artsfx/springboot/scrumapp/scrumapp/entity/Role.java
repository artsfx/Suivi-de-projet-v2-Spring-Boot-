package com.artsfx.springboot.scrumapp.scrumapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="role_id")
	private Integer id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@ManyToMany(mappedBy="roles")
	private List<User> users;

	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(Integer id, String name, List<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
	}

	/**
	 * @return the roleId
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	
	public void addUser(User user) {
		if (users == null) {
			users = new ArrayList<>();
		}
		users.add(user);
	}
}
