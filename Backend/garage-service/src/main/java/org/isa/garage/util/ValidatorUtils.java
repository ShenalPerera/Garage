package org.isa.garage.util;

import org.isa.garage.exception.InvalidScheduleTimesException;
import org.springframework.stereotype.Service;

import java.sql.Time;

@Service
public class ValidatorUtils {

    public void validateScheduleStartTimeAndEndTime(Time startTime, Time endTime){
        long currentTimeMillis = System.currentTimeMillis();
        Time currentTime = new Time(currentTimeMillis);

        if (startTime.after(currentTime)) {
            throw new InvalidScheduleTimesException("Start time is invalid");
        }

        if (endTime.before(startTime)) {
            throw new InvalidScheduleTimesException("The end time cannot be before the start time");
        }

        long differenceInMillis = endTime.getTime() - startTime.getTime();
        long differenceInMinutes = differenceInMillis / (60 * 1000);

        if (differenceInMinutes < 30)
            throw new InvalidScheduleTimesException("The duration between the start and end times must be at least 30 minutes.");

    }
}
