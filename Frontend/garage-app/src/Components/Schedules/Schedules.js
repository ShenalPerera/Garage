import React, {useEffect, useState} from 'react';
import {
    Alert,
    Avatar,
    Backdrop,
    Box,
    Button,
    Checkbox,
    Chip,
    Container, createTheme,
    Fab,
    FormControl,
    FormControlLabel,
    Grid,
    InputLabel,
    MenuItem,
    OutlinedInput,
    Select,
    Stack,
    TextField, ThemeProvider, Typography
} from '@mui/material';
import ScheduleItem from "./ScheduleItem/ScheduleItem";
import {deleteWithAuth, getWithAuth, postWithAuth, putWithAuth} from "../../APIService/api";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {Add, Create} from "@mui/icons-material";


import {DemoContainer, DemoItem} from '@mui/x-date-pickers/internals/demo';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
import {LocalizationProvider} from '@mui/x-date-pickers/LocalizationProvider';
import {DatePicker, TimePicker} from '@mui/x-date-pickers';
import {alertActions, authenticateActions, fetchGarageServices} from "../../Store";
import dayjs from "dayjs";
import customParseFormat from 'dayjs/plugin/customParseFormat'
import Bookings from "../Bookings/Bookings";
import {StaticDatePicker} from '@mui/x-date-pickers/StaticDatePicker';
import CountUp from "../CountUp/CountUp";


const scheduleResetData = {id: '', date: null, startTime: null, endTime: null, repeat: false, services: []}


const darkTheme = createTheme({
    palette: {
        mode: 'dark',
        primary: {
            main: '#0000ff', // blue
        },
        secondary: {
            main: '#ff0000', // red
        },
        success: {
            main: '#008000', // green
        },
        error: {
            main: '#ff0000', // red for danger
        },
    },

});

function Schedules() {
    const [formData, setFormData] = useState(scheduleResetData);
    const [open, setOpen] = useState(false);
    const [isEditMode, setIsEditMode] = useState(false);
    const [scheduleId, setScheduleId] = useState(null);
    const [refreshBit, setRefreshBit] = useState(false);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const [scheduleData, setScheduleData] = useState([]);
    const [selectedDate, setSelectedDate] = useState(dayjs());

    const servicesData = useSelector(state => state.garageServices.garageServices);

    useEffect(() => {
        dispatch(fetchGarageServices());
    }, [dispatch]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                let schedules = await loader(selectedDate.format("YYYY-MM-DD"));
                setScheduleData(schedules);
            } catch (e) {
                if (e.response.data.message) {
                    dispatch(alertActions.setAlert({message: e.response.data.message, severity: 'error'}));
                    if (e.response.status === 401) {
                        dispatch(authenticateActions.clearAuthenticate());
                        localStorage.removeItem('isLoggedIn');
                        navigate("/auth/login");

                    }
                } else {
                    dispatch(alertActions.setAlert({message: "Couldn't fetch the data!", severity: 'error'}));
                }
            }

        }
        fetchData();
    }, [dispatch, selectedDate]);
    const handleClose = () => {
        setOpen(false);
        setIsEditMode(false);
        setFormData(scheduleResetData);
    };
    const handleOpen = () => {
        setOpen(true);
    };

    const handleClickEditSchedule = (schedule) => {
        dayjs.extend(customParseFormat);

        const date = dayjs(schedule.date, 'YYYY-MM-DD');
        const startTime = dayjs(schedule.startTime, 'HH:mm:ss');
        const endTime = dayjs(schedule.endTime, 'HH:mm:ss');


        setFormData({
            id: schedule.id,
            date: date,
            startTime: startTime,
            endTime: endTime,
            repeat: false,
            services: schedule.garageServices.map(service => service.id)

        });
        setOpen(true);
        setIsEditMode(true);
    }

    const handleOnChange = (event, newValue) => {
        if (typeof event === 'string') {
            const name = event;
            setFormData(prevState => ({
                ...prevState, [name]: newValue
            }));
        } else {
            // Normal input handling
            const {name, value} = event.target;
            if (name === 'services') {
                setFormData(prevState => ({
                    ...prevState, services: value
                }));
            } else if (name === 'repeat') {
                setFormData(prevState => ({
                    ...prevState, repeat: event.target.checked
                }));
            } else {
                setFormData(prevState => ({
                    ...prevState, [name]: value
                }));
            }
        }


    };

    const validateForm = (formData) => {
        // List of errors
        let errors = {};
        console.log(formData);
        // Check if all fields are filled
        for (let field in formData) {
            if (formData[field] === '' || formData[field] === null) {
                if (field === 'id' && !isEditMode) {
                    continue;
                }
                errors[field] = 'This field is required';
            }
        }

        // If 'repeat' is true, then 'period' is required
        if (formData.repeat && (formData.period === '' || formData.period === null)) {
            errors.period = 'Period is required when repeat is enabled';
        }
    console.log(errors);
        // If there are no errors, return null
        if (Object.keys(errors).length === 0) {

            return null;
        }

        return errors;
    };


    const handleOnSubmit = async (event) => {
        event.preventDefault();
        if (validateForm(formData) === null) {
            let date = dayjs(formData["date"]).format('YYYY-MM-DD');

            let startTime = dayjs(formData["startTime"]).format('HH:mm');
            startTime = startTime.length === 5 ? startTime + ':00' : startTime;

            let endTime = dayjs(formData["endTime"]).format('HH:mm');
            endTime = endTime.length === 5 ? endTime + ':00' : endTime;

            let serviceId = formData.services;
            let isRecurrent = formData.repeat ? formData.repeat : null;
            let period = formData.repeatPeriod ? formData.repeatPeriod : null;

            let submittingData = {...formData,date,startTime,endTime,serviceId,isRecurrent, period}
            try {
                if (!isEditMode) {
                    const data = await postWithAuth(submittingData, 'schedule/create-multi-service-schedule');
                    dispatch(alertActions.setAlert({
                        message: data.count + ' Schedules Created Successfully', severity: 'success'
                    }))
                    navigate('/schedules');
                } else {
                    await putWithAuth(submittingData, 'schedule/edit-schedule');
                    dispatch(alertActions.setAlert({
                        message: ' Schedules edited Successfully', severity: 'success'
                    }))

                    setSelectedDate(dayjs(formData.date, 'YYYY-MM-DD'));
                    navigate(`/schedules`)
                }
                setFormData(scheduleResetData);
                setOpen(false);

            } catch (e) {
                if (e.response.data.message) {
                    dispatch(alertActions.setAlert({message: e.response.data.message, severity: 'error'}));
                    if (e.response.status === 401) {
                        dispatch(authenticateActions.clearAuthenticate());
                        localStorage.clear();
                        navigate("/auth/login");
                    } else {
                        dispatch(alertActions.setAlert({message: e.response.data.message, severity: 'error'}));
                    }
                }

            }


        } else {
            dispatch(alertActions.setAlert({message: 'Missing required fields', severity: 'error'}));
        }

    }

    const handleDeleteSchedule = async (schedule) => {

        try {
            await deleteWithAuth(`schedule/delete-schedule/${schedule.id}`)
            dispatch(alertActions.setAlert({
                message: ' Schedules deleted Successfully', severity: 'success'
            }))
            setSelectedDate(dayjs(schedule.date, "YYYY-MM-DD"));

        } catch (e) {
            if (e.response.data.message) {
                dispatch(alertActions.setAlert({message: e.response.data.message, severity: 'error'}));

                if (e.response.status === 401) {
                    dispatch(authenticateActions.clearAuthenticate());
                    localStorage.removeItem("'isLoggedIn");
                    navigate("/auth/login");
                }

            }
            else{
                dispatch(alertActions.setAlert({message: "Error occurred!", severity: 'error'}));
            }


        }
    }


    const handleDiscard = () => {
        setFormData(scheduleResetData);
    }

    const handleClickBookings = (scheduleId) => {
        setScheduleId(scheduleId);
        setRefreshBit(s => !s);
    }

    const handleOnDateChange = (newValue) => {
        setScheduleId(sId => {
            if (sId) {
                setScheduleId(null);
            }
        });
        setSelectedDate(newValue);
    }

    return (

        <Stack direction='row' spacing={2} justifyContent='space-beyween'>
            <Box flex={1} p={2} sx={{display: {xs: 'none', sm: 'block'}}}>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DemoContainer
                        components={['StaticDatePicker',]}
                    >
                        <DemoItem>
                            <StaticDatePicker
                                defaultValue={dayjs()}
                                value={selectedDate}
                                onChange={(newValue) => {
                                    handleOnDateChange(newValue);
                                }}

                            />
                        </DemoItem>
                    </DemoContainer>
                </LocalizationProvider>

            </Box>
            <ThemeProvider theme={darkTheme}>
                <Box flex={6} p={2}>
                {scheduleId && <Bookings refreshBit={refreshBit} scheduleId={scheduleId}/>}
                {scheduleData && scheduleData.length > 0 ? (<Grid container spacing={2}>

                    {scheduleData.map((schedule, index) => (<Grid item key={index}>
                        <ScheduleItem schedule={schedule} handleEdit={handleClickEditSchedule}
                                      handleDelete={handleDeleteSchedule}
                                      handleBookingsClick={() => handleClickBookings(schedule.id)}/>
                    </Grid>))}
                </Grid>) : (<Alert sx={{backgroundColor: 'black', color: 'skyblue'}} severity="info">No bookings
                    Available</Alert>)

                }


                <Fab color="primary" aria-label="add" sx={{position: 'fixed', bottom: 16, right: 16}}
                     onClick={handleOpen}>
                    <Add/>
                </Fab>


                <Backdrop
                    sx={{
                        zIndex: (theme) => theme.zIndex.drawer + 1,
                        width: '100%',
                        marginLeft: 0,
                        backdropFilter: 'blur(4px)',
                        alignItems: 'flex-start', // Align items along the vertical axis to the start
                    }}
                    open={open}
                    onClick={handleClose}
                    aria-hidden='true'
                    invisible={true}


                >

                        <Container component="main" maxWidth="xs" sx={{marginTop: '20%'}}>
                            <Box
                                sx={{
                                    display: 'flex', flexDirection: 'column', alignItems: 'center',
                                }}
                                onClick={(event) => event.stopPropagation()}
                                boxShadow={5}
                                bgcolor='black'

                            >
                                <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                                    <Create/>
                                </Avatar>

                                <Box component="form" noValidate sx={{mt: 1}}>

                                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                                        <DemoContainer components={['DatePicker']}>
                                            <DatePicker
                                                label='Date'
                                                name='date'
                                                value={formData.date}
                                                onChange={(newValue) => handleOnChange('date', newValue)}
                                                disablePast={true}
                                            />
                                        </DemoContainer>

                                        <DemoContainer components={['TimePicker']}>
                                            <TimePicker
                                                label="Start Time"
                                                name='startTime'
                                                value={formData.startTime}
                                                onChange={(newValue) => handleOnChange('startTime', newValue)}
                                                disablePast={true}
                                            />
                                        </DemoContainer>
                                        <DemoContainer components={['TimePicker']}>
                                            <TimePicker
                                                label="End Time"
                                                name='endTime'
                                                value={formData.endTime}
                                                onChange={(newValue) => handleOnChange('endTime', newValue)}
                                                disablePast={true}
                                            />
                                        </DemoContainer>
                                    </LocalizationProvider>

                                    <FormControl variant="outlined" sx={{mt: 1, minWidth: 120}}>
                                        <InputLabel id="service-label">Services</InputLabel>
                                        <Select
                                            labelId="service-label"
                                            id="service-select"
                                            label="Service"
                                            name='services'
                                            multiple
                                            value={formData.services}
                                            input={<OutlinedInput id="select-multiple-chip" label="Chip"/>}
                                            onChange={handleOnChange}
                                            renderValue={(selected) => (<Box sx={{
                                                display: 'flex', flexDirection:'column',flexWrap: 'wrap', gap: 0.5
                                            }}
                                            >
                                                {selected.map((value) => (<Chip
                                                    key={value}
                                                    label={servicesData.find(service => service.id === value).serviceName}/>))}
                                            </Box>)}

                                        >
                                            {servicesData.map((service) => (<MenuItem
                                                key={service.id}
                                                value={service.id}>
                                                {service.serviceName}
                                            </MenuItem>))}
                                        </Select>
                                    </FormControl>

                                    <Box sx={{mt: 1}} >
                                        <FormControlLabel
                                            color='white'
                                            control={<Checkbox
                                                checked={formData.repeat}
                                                onChange={handleOnChange}
                                                name="repeat"
                                            />}
                                            label={
                                                <Typography color={'white'}>Do you want to repeat this schedule?</Typography>
                                            }
                                        />
                                    </Box>
                                    {formData.repeat === true && (<Box sx={{mt: 1}}>
                                        <TextField
                                            label="Period"
                                            name="repeatPeriod"
                                            type='number'
                                            inputProps={{min: '1', max: '30', step: '1'}}
                                            onChange={handleOnChange}
                                            variant="filled"
                                            fullWidth
                                        />
                                    </Box>)}

                                    <Box display='flex' flexDirection='row' justifyContent='space-between'>
                                        <Button
                                            type="submit"
                                            fullWidth
                                            variant="contained"
                                            sx={{mt: 3, mb: 2, marginRight: 1}}
                                            onClick={handleOnSubmit}
                                        >
                                            {isEditMode ? 'Save' : 'Create'}
                                        </Button>
                                        <Button
                                            type="button"
                                            fullWidth
                                            variant="contained"
                                            sx={{mt: 3, mb: 2, bgcolor: 'red'}}
                                            onClick={handleDiscard}
                                        >
                                            Discard
                                        </Button>
                                    </Box>

                                </Box>

                            </Box>
                        </Container>


                </Backdrop>
            </Box>
            </ThemeProvider>
        </Stack>);
}

export default Schedules;

export const loader = async (formattedDate) => {
    try {
        return await getWithAuth('schedule/get-schedules', {date: formattedDate});
    } catch (e) {
        throw e;
    }

}