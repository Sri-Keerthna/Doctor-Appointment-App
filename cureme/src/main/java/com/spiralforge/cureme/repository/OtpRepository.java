package com.spiralforge.cureme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.cureme.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

	Optional<Otp> findByMobileNumberAndOtpNumberAndOtpStatus(Long mobileNumber, Integer otp, String otpActiveMessage);

}
