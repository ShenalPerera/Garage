import React, {useEffect, useState} from 'react';
import CountUp from "./CountUp/CountUp";
import {Box, Typography} from "@mui/material";
import {getData} from "../APIService/api";

const TTL = 60 * 1000;
const LandingPage = () => {
    const [counts, setCounts] = useState({service:undefined, schedules:undefined, bookings:undefined});

    useEffect(() => {
        const updateCounts = async ()=>{
            const cachedCounts = localStorage.getItem('counts');
            const cachedTime = localStorage.getItem('countsTime');

            // Fetch data from cache if it's still valid
            if (cachedCounts && cachedTime && Date.now() - cachedTime < TTL) {
                setCounts(JSON.parse(cachedCounts));
            } else
            {
                try {
                    const {serviceCount,bookingsCount,scheduleCounts} = await getData("get-counts")
                    const newCounts = {service: serviceCount,schedules: scheduleCounts,bookings: bookingsCount};
                    setCounts(newCounts);
                    localStorage.setItem('counts', JSON.stringify(newCounts));
                    localStorage.setItem('countsTime', Date.now().toString());

                }
                catch (e) {
                    
                }
                
            }
        }
        updateCounts();
    }, []);

    return (
        <Box display='flex' flexDirection='column' alignContent='center' height={'80vh'} justifyContent='center'>
            <Typography variant="h2" align="center" gutterBottom>
                Real-Time Statistics
            </Typography>
            <Box display="flex" flexDirection="row" justifyContent="center">
                <CountUp finalValue={counts.service} label="Services" />
                <CountUp finalValue={counts.bookings} label="Bookings" />
                <CountUp finalValue={counts.schedules} label="Schedules" />
            </Box>
        </Box>

    );
};

export default LandingPage;