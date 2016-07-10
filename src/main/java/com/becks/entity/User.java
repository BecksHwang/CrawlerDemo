package com.becks.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", catalog = "ggzzjc")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 6980093847795726310L;

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 2)
	private int id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "age", nullable = false)
	private int age;

	public User() {
	}

	public User(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
