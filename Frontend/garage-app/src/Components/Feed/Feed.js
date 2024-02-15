import React from 'react';
import {Box} from "@mui/material";
import Schedules from "../Schedules/Schedules";
const scheduleData = [
    { title: 'Oil Change', time: '9:00 AM', type: 'maintenance' },
    { title: 'Tire Rotation', time: '11:00 AM', type: 'maintenance' },
    { title: 'Car Wash', time: '2:00 PM', type: 'routine' },
    // Add more tasks as needed
    { title: 'Oil Change', time: '9:00 AM' },
    { title: 'Tire Rotation', time: '11:00 AM' },
    { title: 'Car Wash', time: '2:00 PM' },
    { title: 'Brake Inspection', time: '4:00 PM' },
    { title: 'Alignment Check', time: '10:00 AM' },
    { title: 'Fluid Top-up', time: '3:30 PM' },
    { title: 'Engine Tune-up', time: '1:00 PM' },
    { title: 'Battery Replacement', time: '12:00 PM' },
    { title: 'Wheel Alignment', time: '11:30 AM' },
    { title: 'Air Filter Replacement', time: '3:00 PM' },
    { title: 'Spark Plug Replacement', time: '5:30 PM' },
    { title: 'Headlight Adjustment', time: '6:30 PM' },
    { title: 'Transmission Fluid Change', time: '8:00 AM' },
    { title: 'Coolant Flush', time: '12:30 PM' },
    { title: 'Tire Pressure Check', time: '2:30 PM'}
];
const Feed = () => {
    return (
        <Box bgcolor="skyblue" flex={6} p={2}>
          <Schedules schedule={scheduleData}/>
        </Box>
    );
};

export default Feed;