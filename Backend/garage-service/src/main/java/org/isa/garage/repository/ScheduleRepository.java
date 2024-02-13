package org.isa.garage.repository;

import org.isa.garage.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {

    List<Schedule> findALLByDateEquals(Date date);

    @Query("SELECT COUNT(s) FROM Schedule s " +
            "WHERE s.date >= :date " +
            "AND ((s.startTime >= :startTime AND s.startTime < :endTime) OR " +
            "     (s.endTime > :startTime AND s.endTime <= :endTime))")
    Integer findConflictingSchedules(Date date, Time startTime, Time endTime);
}
