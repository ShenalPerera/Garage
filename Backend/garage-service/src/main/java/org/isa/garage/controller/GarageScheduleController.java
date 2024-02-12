package org.isa.garage.controller;

import jakarta.validation.Valid;
import org.isa.garage.dto.SingleServiceScheduleCreateDTO;
import org.isa.garage.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create-schedule")
    public ResponseEntity<?> createSingleServiceSchedule(@Valid @RequestBody SingleServiceScheduleCreateDTO singleServiceScheduleCreateDTO){
        return new ResponseEntity<>(scheduleService.createSingleServiceSchedule(singleServiceScheduleCreateDTO),HttpStatus.CREATED);
    }
}
