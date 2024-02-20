import React, {useState} from 'react';
import {
    Avatar,
    Backdrop,
    Box,
    Button,
    Checkbox,
    Chip,
    Container,
    Fab,
    FormControl,
    FormControlLabel,
    Grid,
    InputLabel,
    MenuItem,
    Paper,
    Select,
    TextField
} from '@mui/material';
import ScheduleItem from "./ScheduleItem/ScheduleItem";
import {getWithAuth, postWithAuth, putWithAuth} from "../../APIService/api";
import {useDispatch} from "react-redux";
import {useLoaderData, useNavigate} from "react-router-dom";
import {Add, Create} from "@mui/icons-material";


import {DemoContainer} from '@mui/x-date-pickers/internals/demo';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
import {LocalizationProvider} from '@mui/x-date-pickers/LocalizationProvider';
import {DatePicker, TimePicker} from '@mui/x-date-pickers';
import {alertActions} from "../../Store";
import dayjs from "dayjs";
import customParseFormat from 'dayjs/plugin/customParseFormat'

// Assuming services is an array of service objects
const servicesData = [{id: 1, serviceName: 'Service 1', duration: 40}, {
    id: 2,
    serviceName: 'Service 2',
    duration: '2 hours'
}, {id: 3, serviceName: 'Service 3', duration: 30}, {
    id: 4,
    serviceName: 'Service 4',
    duration: '3 hours'
}, {id: 5, serviceName: 'Service 5', duration: 120},];
const scheduleResetData = {id: '', date: null, startTime: null, endTime: null, repeat: false, services: []}

function Schedules() {
    const [formData, setFormData] = useState(scheduleResetData);
    const [open, setOpen] = useState(false);
    const [isEditMode, setIsEditMode] = useState(false);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const scheduleData = useLoaderData();
    const handleClose = () => {
        setOpen(false);
        setIsEditMode(false);
        setFormData(scheduleResetData);
    };
    const handleOpen = () => {
        setOpen(true);
    };

    const handleClickEditSchedule = (schedule)=>{
        dayjs.extend(customParseFormat);

        const date = dayjs(schedule.date,'YYYY-MM-DD');
        const startTime = dayjs(schedule.startTime,'HH:mm:ss');
        const endTime = dayjs(schedule.endTime,'HH:mm:ss');


        setFormData({
            id: schedule.id,
            date: date,
            startTime: startTime,
            endTime: endTime,
            repeat: false,
            services: [...schedule.garageServices]
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

        // If there are no errors, return null
        if (Object.keys(errors).length === 0) {

            return null;
        }

        return errors;
    };


    const handleOnSubmit = async (event) => {
        event.preventDefault();
        if (validateForm(formData) === null) {

            formData["date"] = dayjs(formData["date"]).format('YYYY-MM-DD');
            formData["startTime"] = dayjs(formData["startTime"]).format('HH:MM:00');
            formData["endTime"] = dayjs(formData["endTime"]).format('HH:MM:00');
            formData['serviceId'] = []
            formData['services'].forEach(service => {
                formData['serviceId'].push(service.id);
            });

            try {
                if (!isEditMode){
                    const data = await postWithAuth(formData, 'schedule/create-multi-service-schedule');
                    dispatch(alertActions.setAlert({
                        message: data.count + ' Schedules Created Successfully',
                        severity: 'success'
                    }))
                    navigate('/schedules');
                }
                else{
                    await putWithAuth(formData, 'schedule/edit-schedule');
                    dispatch(alertActions.setAlert({
                        message: ' Schedules edited Successfully',
                        severity: 'success'
                    }))
                    navigate(`/schedules/`)
                }
                setFormData(scheduleResetData);
                setOpen(false);

            }
            catch (e) {
                if (e.message)
                    dispatch(alertActions.setAlert({message:e.message,severity:'error'}))
                else
                    dispatch(alertActions.setAlert({message:'Error occurred',severity:'error'}))
            }


        } else {
            dispatch(alertActions.setAlert({message: 'Missing required fields', severity: 'error'}));
        }

    }


    const handleDiscard = () => {
        setFormData(scheduleResetData);
    }


    return (<>
            <Box flex={6} p={2}>

                {scheduleData && scheduleData.length > 0 ? (<Grid container spacing={2}>

                    {scheduleData.map((schedule, index) => (<Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                            <ScheduleItem schedule={schedule} handleEdit={handleClickEditSchedule}/>
                        </Grid>))}
                </Grid>) : (<Paper>No schedules find</Paper>)

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
                            bgcolor='ghostwhite'
                            boxShadow={5}

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
                                        onChange={handleOnChange}
                                        renderValue={(selected) => (<Box sx={{
                                                display: 'flex',
                                                flexDirection: "column",
                                                flexWrap: 'wrap',
                                                gap: 0.5
                                            }}>
                                                {selected.map((value) => (<Chip key={value.id}
                                                                                label={servicesData.find(service => service.id === value.id).serviceName}/>))}
                                            </Box>)}
                                    >
                                        {servicesData.map((service) => (<MenuItem key={service.id} value={service}>
                                                {service.serviceName}
                                            </MenuItem>))}
                                    </Select>
                                </FormControl>

                                <Box sx={{mt: 1}}>
                                    <FormControlLabel
                                        control={<Checkbox
                                            checked={formData.repeat}
                                            onChange={handleOnChange}
                                            name="repeat"
                                            color="primary"
                                        />}
                                        label="Do you want to repeat this schedule?"
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
                                        {isEditMode? 'Save':'Create'}
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

        </>

    );
}

export default Schedules;

export const loader = async () => {
    try {
        return await getWithAuth('schedule/get-schedules', {date: '2024-02-21'});
    } catch (e) {
        if (e.response.data.message) {
            return {hasError: true, message: e.response.data.message};
        }
        throw e;
    }

}