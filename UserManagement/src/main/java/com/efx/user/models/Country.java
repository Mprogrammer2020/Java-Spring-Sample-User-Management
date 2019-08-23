package com.efx.user.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="country")
public class Country {

	@Id
	@GeneratedValue(
	    strategy= GenerationType.AUTO, 
	    generator="native"
	)
	@GenericGenerator(
	    name = "native", 
	    strategy = "native"
	)
	private Integer id;
	
	private String name;

	@OneToMany(mappedBy = "country", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	Set<User> users = new HashSet<>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {                                                                                      
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
}
