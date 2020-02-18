package com.spiralforge.cureme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.cureme.entity.Doctor;
import com.spiralforge.cureme.entity.Rating;

/**
 * @author Sri Keerthna.
 * @since 2020-02-12.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-12.In this method doctor details are fetched from database.
	 * @param doctorId taken from doctor availability table.
	 * @return list of ratings to that doctors.
	 */
	List<Rating> findByDoctor(Doctor doctorId);

}
