package com.spiralforge.cureme.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.cureme.entity.DoctorAvailability;
import com.spiralforge.cureme.entity.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {

	Optional<Slot> findBySlotId(Long slotId);

	List<Slot> getSlotsByDoctorAvailability(DoctorAvailability availableSlot);

}
