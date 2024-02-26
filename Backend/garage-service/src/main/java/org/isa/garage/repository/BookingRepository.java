package org.isa.garage.repository;

import org.isa.garage.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {

    List<Booking> findAllByScheduleId(Integer scheduleId);
}
