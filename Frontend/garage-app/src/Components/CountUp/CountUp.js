import React from 'react';

import { useSpring, animated } from 'react-spring';
import {Box, Typography, useTheme} from "@mui/material";

const CountUp = ({ finalValue, label }) => {
    const theme = useTheme();
    const props = useSpring({ number: finalValue, from: { number: 0 } });

    return (
        <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center" m={1} p={2} bgcolor='black' color={theme.palette.primary.contrastText} borderRadius={16} width="200px" height="200px">
            <Typography variant='h4'>
                <animated.span>
                    {props.number.interpolate(number => Math.floor(number))}
                </animated.span>
            </Typography>
            <Typography variant='h6'>
                {label}
            </Typography>
        </Box>
    );
};

export default CountUp;
