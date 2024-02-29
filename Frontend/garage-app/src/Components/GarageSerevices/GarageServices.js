import React, {useEffect, useState} from 'react';
import {
    Alert,
    Box, Button,
    createTheme, Dialog, DialogActions, DialogContent, DialogTitle, Fab, makeStyles,
    Paper,
    Table,
    TableBody, TableCell,
    TableContainer,
    TableHead,
    TableRow, TextField,
    ThemeProvider
} from "@mui/material";
import {useDispatch, useSelector} from "react-redux";
import {
    alertActions, authenticateActions,
    createGarageService,
    deleteGarageService,
    editGarageService,
    fetchGarageServices
} from "../../Store";
import GarageServiceDetails from "./GarageServiceDetails/GarageServiceDetails";
import {Add} from "@mui/icons-material";
import {useNavigate} from "react-router-dom";


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



const resetFormData = {id:'',serviceName:'',duration:''}

const GarageServices = () => {
    const dispatch = useDispatch();
    const services = useSelector(state => state.garageServices.garageServices);
    const editedService = useSelector(state => state.garageServices.editedService);
    const [selectedService, setSelectedService] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [open, setOpen] = useState(false);
    const [formData, setFormData] = useState(resetFormData);
    const navigate = useNavigate();

    useEffect(() => {
       const fetchData = async ()=>{
           const actionResults = await dispatch(fetchGarageServices());
           if (fetchGarageServices.rejected.match(actionResults)){
               dispatch(alertActions.setAlert({message:actionResults.error.message,severity:'error' }))
               dispatch(authenticateActions.clearAuthenticate());
               localStorage.removeItem('isLoggedIn');
               navigate("/auth/login");
           }

       }
       fetchData();
    }, [dispatch, navigate]);

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

    const handleOnDelete = async ()=>{
        const actionResults = await dispatch(deleteGarageService(selectedService.id));
        setSelectedService(null);
        if (deleteGarageService.rejected.match(actionResults)){
            const customError = actionResults.payload;
            if (customError.status === 401 || customError.status === 422) {
                dispatch(alertActions.setAlert({message: customError.message, severity: 'error'}));
                if (customError.status === 401){
                    localStorage.removeItem('isLoggedIn');
                    dispatch(authenticateActions.clearAuthenticate());
                    navigate("/auth/login");
                }
            }
            else{
                dispatch(alertActions.setAlert({message: "Error occurred!", severity: 'error'}));
            }
        }



    }
    const handleSubmit = async (event)=>{
        event.preventDefault();
        let actionResults;

        let hasError = !formData.serviceName.trim() || !formData.serviceName.trim();
        if (isEditing){
            hasError = hasError || !formData.id;
        }

        if (hasError){
            dispatch(alertActions.setAlert({message:'Missing required fields',severity:'error'}))
        }
        else{
            try{
                if (isEditing)
                    actionResults  = await dispatch(editGarageService(formData));
                else
                    actionResults = await dispatch(createGarageService(formData));


                if (editGarageService.rejected.match(actionResults) || createGarageService.rejected.match(actionResults)){
                    const customError = actionResults.payload;
                    dispatch(alertActions.setAlert({message: customError.message, severity: 'error'}));
                    if (customError.status === 401){
                        localStorage.removeItem('isLoggedIn');
                        dispatch(authenticateActions.clearAuthenticate());
                        navigate("/auth/login");
                    }
                }

                setIsEditing(false);
                setOpen(false);
                setFormData(resetFormData);
            }
            catch (e) {

                dispatch(alertActions.setAlert({message:"Couldn't fetch the data!",severity:'error'}));

            }
        }
    }

    return (
        <Box flex={6} p={2}>
            <ThemeProvider theme={darkTheme}>
                {selectedService !== null &&
                    (
                        <GarageServiceDetails service={selectedService} onClose={handleCloseSelectedService} onEdit={handleOnClickEdit} onDelete={handleOnDelete}/>
                )}
                <TableContainer component={Paper}>
                    {services.length > 0 ? (
                        <Table sx={{minWidth: 650}} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>No.</TableCell>
                                    <TableCell>Service Name</TableCell>
                                    <TableCell>Duration (min)</TableCell>
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
                        <Box display='flex' flexDirection='column'>
                            <TextField
                                style={{margin:'16px'}}
                                name="serviceName"
                                label="Service Name"
                                value={formData.serviceName}
                                required
                                onChange={(event)=>setFormData({...formData,serviceName: event.target.value})}
                            />
                            <TextField
                                style={{margin:'16px'}}
                                name="duration"
                                label="Duration"
                                type='number'
                                required
                                value={formData.duration}
                                onChange={(event)=>setFormData({...formData,duration: event.target.value})}
                            />

                        </Box>
                    </DialogContent>
                    <DialogActions>
                        <Button  variant="contained" color="error"   onClick={() => setOpen(false)}>Cancel</Button>
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