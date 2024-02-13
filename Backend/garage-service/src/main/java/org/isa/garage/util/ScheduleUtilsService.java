package org.isa.garage.util;

import org.isa.garage.dto.MultiServiceScheduleCreateDTO;
import org.isa.garage.entity.GarageService;
import org.isa.garage.entity.Schedule;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleUtilsService {

    public static List<Schedule> createRecurrentSchedules(MultiServiceScheduleCreateDTO multiServiceScheduleCreateDTO, List<GarageService> services){
        List<Schedule> recurrenceSchedule = new ArrayList<>();

        Date startDate = multiServiceScheduleCreateDTO.getDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        for (int i = 0; i < multiServiceScheduleCreateDTO.getPeriod(); i++) {
            calendar.add(Calendar.DAY_OF_MONTH,1);
            Date newDate = new Date(calendar.getTimeInMillis());
            multiServiceScheduleCreateDTO.setDate(newDate);

            Schedule schedule = MappingUtils.mapMultiServiceScheduleDTOToSchedule(multiServiceScheduleCreateDTO,services);
            recurrenceSchedule.add(schedule);
        }
        return recurrenceSchedule;
    }

    public static Schedule createNoneRecurrentSchedule(MultiServiceScheduleCreateDTO multiServiceScheduleCreateDTO, List<GarageService> services){
        return MappingUtils.mapMultiServiceScheduleDTOToSchedule(multiServiceScheduleCreateDTO,services);

    }


}
