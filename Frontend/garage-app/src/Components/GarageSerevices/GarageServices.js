import React, {useEffect, useState} from 'react';
import {
    Alert,
    Box, Button,
    createTheme, Dialog, DialogActions, DialogContent, DialogTitle, Fab,
    Paper,
    Table,
    TableBody, TableCell,
    TableContainer,
    TableHead,
    TableRow, TextField,
    ThemeProvider
} from "@mui/material";
import {useDispatch, useSelector} from "react-redux";
import {alertActions, createGarageService, editGarageService, fetchGarageServices} from "../../Store";
import GarageServiceDetails from "./GarageServiceDetails/GarageServiceDetails";
import {Add} from "@mui/icons-material";


const darkTheme = createTheme({
    palette: {
        mode: 'dark',
    },
});

const resetFormData = {id:'',serviceName:'',duration:''}

const GarageServices = () => {
    const dispatch = useDispatch();
    const services = useSelector(state => state.garageServices.garageServices);
    const editedService = useSelector(state => state.garageServices.editedService);
    const [selectedService, setSelectedService] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [open, setOpen] = useState(false);
    const [formData, setFormData] = useState(resetFormData);

    useEffect(() => {
        dispatch(fetchGarageServices());
    }, [dispatch]);

    useEffect(() => {
        if (isEditing && editedService) {
            setSelectedService(editedService);
        }
    }, [isEditing, editedService]);

    const handleServiceOnClick = (service)=>{
        setSelectedService(service);
    }

    const handleCloseSelectedService = ()=>{
        setSelectedService(null);
    }

    const handleOnClickEdit = ()=>{
        setIsEditing(true);
        setOpen(true);
        setFormData({...selectedService});
    }

    const handleClickAddService = ()=>{
        setOpen(true);
        setFormData(resetFormData);
        setIsEditing(false);
    }
    const handleSubmit = async (event)=>{
        event.preventDefault();

        let hasError = !formData.serviceName.trim() || !formData.serviceName.trim();
        if (isEditing){
            hasError = hasError || !formData.id;
        }

        if (hasError){
            dispatch(alertActions.setAlert({message:'Missing required fields',severity:'error'}))
        }
        else{
            if (isEditing)
                await dispatch(editGarageService(formData));
            else
                await dispatch(createGarageService(formData));

            setIsEditing(false);
            setFormData(resetFormData);
        }
    }

    return (
        <Box flex={6} p={2}>
            <ThemeProvider theme={darkTheme}>
                {selectedService !== null &&
                    (
                        <GarageServiceDetails service={selectedService} onClose={handleCloseSelectedService} onEdit={handleOnClickEdit}/>
                )}
                <TableContainer component={Paper}>
                    {services.length > 0 ? (
                        <Table sx={{minWidth: 650}} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>No.</TableCell>
                                    <TableCell>Service Name</TableCell>
                                    <TableCell>Duration</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {services.map((service, index) => (
                                    <TableRow
                                        key={service.id}
                                        hover
                                        onClick={()=>handleServiceOnClick(service)}
                                        style={{cursor: 'pointer'}}
                                    >
                                        <TableCell>{index + 1}</TableCell>
                                        <TableCell>{service.serviceName}</TableCell>
                                        <TableCell>{service.duration}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    ) : (
                        <Alert severity="info">No services available</Alert>
                    )}
                </TableContainer>

                <Dialog open={open} onClose={() => setOpen(false)}>
                    <DialogTitle>{isEditing? 'Edit Service' : 'Create Service'}</DialogTitle>
                    <DialogContent>
                        <form>
                            <TextField
                                name="serviceName"
                                label="Service Name"
                                value={formData.serviceName}
                                required
                                onChange={(event)=>setFormData({...formData,serviceName: event.target.value})}
                            />
                            <TextField
                                name="duration"
                                label="Duration"
                                type='number'
                                required
                                value={formData.duration}
                                onChange={(event)=>setFormData({...formData,duration: event.target.value})}
                            />

                        </form>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={() => setOpen(false)}>Cancel</Button>
                        <Button type="submit" variant="contained" color="primary" onClick={handleSubmit}>
                            {isEditing? 'Save': 'Create'}
                        </Button>
                    </DialogActions>
                </Dialog>
                <Fab color="primary" aria-label="add" sx={{position: 'fixed', bottom: 16, right: 16}}
                     onClick={handleClickAddService}>
                    <Add/>
                </Fab>
            </ThemeProvider>
        </Box>
    );
};

export default GarageServices;