package com.spiralforge.cureme.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spiralforge.cureme.entity.Booking;
import com.spiralforge.cureme.entity.Slot;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	/**
	 * @author Sujal
	 * 
	 *         This method id used to fetch the bookings for a doctor by the doctor
	 *         id
	 * @param doctorId
	 * @return List<Booking> is list of bookings
	 */
	@Query("select b from Booking b inner join b.slot.doctorAvailability d where d.doctor.doctorId=:doctorId order by d.availableDate")
	List<Booking> getBookingByDoctorId(@Param("doctorId") Long doctorId);

	Optional<Booking> findBySlot(Slot slot);

}
