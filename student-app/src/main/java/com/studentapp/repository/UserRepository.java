package com.studentapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.studentapp.entity.UserEntity;;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
    UserEntity findByUsername(String username);
    Optional<UserEntity> findByEmailId(String emailId);
}
