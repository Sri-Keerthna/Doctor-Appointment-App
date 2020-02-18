package com.spiralforge.cureme.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spiralforge.cureme.entity.Doctor;
import com.spiralforge.cureme.entity.User;

/**
 * @author Sri Keerthna.
 * @since 2020-02-12.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-12. In this method list of available doctors are fetched from
	 *        databse.
	 * @param doctorId  taken from doctors availability.
	 * @param searchKey it can be doctor's specialization, qualification,
	 *                  diseaseCure and doctor name.
	 * @return list of doctors who are available.
	 */
	@Query("Select d from Doctor d where (d.doctorName like %:searchKey% or d.specialization like %:searchKey% or d.diseaseCure like %:searchKey%) and d.doctorId=:doctorId")
	List<Doctor> findByDoctorId(@Param("doctorId") Long doctorId, @Param("searchKey") String searchKey);

	Optional<Doctor> findByDoctorId(Long doctorId);

	Doctor findByUser(User user);

}
