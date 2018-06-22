package com.codingdojo.events.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.events.models.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);
	
}
