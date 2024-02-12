package org.isa.garage.controller;

import org.isa.garage.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("/schedule")
public class GarageScheduleController {

    private final ScheduleService scheduleService;

    public GarageScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }
    @GetMapping("/get-schedules")
    public ResponseEntity<?> getAllSchedules(@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date){

        return new ResponseEntity<>(scheduleService.getAllSchedules(date), HttpStatus.OK);
    }
}
