package org.isa.garage.repository;

import org.isa.garage.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot,Long> {

    List<TimeSlot> findByStartTimeAfterAndEndTimeBefore(Time starttime, Time endtime);
}
