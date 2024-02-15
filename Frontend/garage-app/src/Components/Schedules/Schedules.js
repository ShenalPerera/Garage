import React from 'react';
import {Grid} from '@mui/material';
import ScheduleItem from "./ScheduleItem/ScheduleItem";

function Schedules({ schedule }) {
    return (
        <Grid container spacing={2}>
            {schedule.map((schedule, index) => (
                <Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                    <ScheduleItem title={schedule.title} time={schedule.time}/>
                </Grid>
            ))}
        </Grid>
    );
}

export default Schedules;
