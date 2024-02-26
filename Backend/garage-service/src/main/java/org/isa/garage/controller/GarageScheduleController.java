package org.isa.garage.controller;

import jakarta.validation.Valid;
import org.isa.garage.dto.MultiServiceScheduleCreateDTO;
import org.isa.garage.dto.ScheduleEditDTO;
import org.isa.garage.dto.SingleServiceScheduleCreateDTO;
import org.isa.garage.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("/schedule")
@CrossOrigin
public class GarageScheduleController {
    private static final Logger logger = LoggerFactory.getLogger(GarageScheduleController.class);
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

    @PostMapping("/create-multi-service-schedule")
    public ResponseEntity<?> createMultiServiceSchedule(@Valid @RequestBody MultiServiceScheduleCreateDTO multiServiceScheduleCreateDTO){
        logger.info(multiServiceScheduleCreateDTO.toString());
        return new ResponseEntity<>("{\"count\":" + scheduleService.createMultiServiceSchedule(multiServiceScheduleCreateDTO)+"}",HttpStatus.CREATED);
    }

    @PutMapping("/edit-schedule")
    public ResponseEntity<?> editSchedule(@Valid @RequestBody ScheduleEditDTO scheduleEditDTO){
        return new ResponseEntity<>(scheduleService.editSchedule(scheduleEditDTO),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-schedule/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer id){
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
