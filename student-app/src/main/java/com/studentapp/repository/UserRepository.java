package com.studentapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.studentapp.entity.UserEntity;;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
    UserEntity findByUsername(String username);
}
