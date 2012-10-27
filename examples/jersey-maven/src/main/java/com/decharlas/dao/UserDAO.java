package com.decharlas.dao;

import java.util.ArrayList;
import java.util.List;

import com.decharlas.model.User;

public class UserDAO {
	private List<User> users;

	public UserDAO() {
		this.users = new ArrayList<User>();
		this.users.add(new User(Integer.valueOf(1), "Pedro Perez Pardo"));
		this.users
				.add(new User(Integer.valueOf(2), "Juan Gutierrez Rodriguez"));
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void removeUser(int id) {
		for (User user : this.users) {
			if (user.getId().intValue() == id) {
				this.users.remove(user);
			}
		}
	}

	public User addUser(User user) {
		this.users.add(user);
		return user;
	}

	public User updateUser(User user) {
		for (User currentUser : this.users) {
			if (currentUser.getId() == user.getId()) {
				this.users.remove(currentUser);
				this.users.add(user);
			}
		}

		return user;
	}
}