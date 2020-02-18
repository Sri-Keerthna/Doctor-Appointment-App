package com.spiralforge.cureme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.cureme.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByMobileNumber(Long mobileNumber);

}
