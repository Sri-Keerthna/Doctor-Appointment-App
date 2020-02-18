package com.spiralforge.cureme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.cureme.entity.Doctor;
import com.spiralforge.cureme.entity.DoctorAvailability;

/**
 * @author Sri Keerthna.
 * @since 2020-02-12.
 */
@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-12. In this method list of locations will be fetched from
	 *        database.
	 * @param location given by user.
	 * @return list of locations.
	 */
	List<DoctorAvailability> findByLocation(String location);

	List<DoctorAvailability> findByDoctor(Doctor doctor);

}
