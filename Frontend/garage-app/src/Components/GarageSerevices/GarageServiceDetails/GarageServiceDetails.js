import React, {useEffect, useState} from 'react';
import {Box, Button, createTheme, IconButton, Paper, ThemeProvider, Typography} from "@mui/material";
import {Close} from "@mui/icons-material";


const darkTheme = createTheme({
    palette: {
        mode: 'dark',
    },
});


const GarageServiceDetails = ({service,onClose,onEdit}) => {



    return (
        <Box flex={6} p={2}>
            <ThemeProvider theme={darkTheme}>
                <Box component={Paper} p={2} position="relative">
                    <IconButton
                        aria-label="close"
                        onClick={onClose}
                        sx={{ position: 'absolute', right: 8, top: 8 }}
                    >
                        <Close/>
                    </IconButton>

                    <Typography variant="h4" gutterBottom>
                        {service.serviceName}
                    </Typography>
                    <Typography variant="h6" gutterBottom>
                        Duration: {service.duration}
                    </Typography>
                    <Button variant="contained" color="primary" onClick={onEdit}>
                        Edit
                    </Button>
                    <Button variant="contained" color="secondary" >
                        Delete
                    </Button>
                </Box>
            </ThemeProvider>
        </Box>
    );
};

export default GarageServiceDetails;