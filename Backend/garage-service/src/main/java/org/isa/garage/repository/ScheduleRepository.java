package org.isa.garage.repository;

import org.isa.garage.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> findALLByDateEquals(Date date);
}
