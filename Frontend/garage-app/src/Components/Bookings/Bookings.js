import React, {useEffect, useState} from 'react';
import GarageServiceDetails from "../GarageSerevices/GarageServiceDetails/GarageServiceDetails";
import {
    Alert, Box, Button, createTheme,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    ThemeProvider
} from "@mui/material";
import {getWithAuth} from "../../APIService/api";
import {useDispatch} from "react-redux";
import {alertActions, authenticateActions} from "../../Store";
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

const Bookings = ({scheduleId,refreshBit}) => {
    const [bookings, setBookings] = useState([]);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect( () => {
        const fetchBookings = async ()=>{
            try {
                const bookings = await getWithAuth(`bookings/get-bookings?scheduleId=${scheduleId}`);
                setBookings(bookings);
            } catch (e) {
                if (e.response.data.message){
                    dispatch(alertActions.setAlert({message:e.response.data.message,severity:'error'}));
                    if (e.response.status){
                        dispatch(authenticateActions.clearAuthenticate());
                        localStorage.removeItem('isLoggedIn');
                        navigate('/auth/login');
                    }
                }
                else{
                    dispatch(alertActions.setAlert({message:"Couldn't fetch the data!",severity:'error'}));
                }
            }
        }
        fetchBookings();
    }, [dispatch, scheduleId, refreshBit, navigate]);

    const handleBookingsClick = async (action,bookingId)=>{
        try {
            const bookings = await getWithAuth(`bookings/set-status?action=${action}&bookingId=${bookingId}&scheduleId=${scheduleId}`);
            setBookings(bookings);
        }
        catch (e) {
            if (e.response.data.message){
                dispatch(alertActions.setAlert({message:e.response.data.message,severity:'error'}));
                if (e.response.status){
                    dispatch(authenticateActions.clearAuthenticate());
                    localStorage.removeItem('isLoggedIn');
                    navigate('/auth/login');
                }
            }
            else{
                dispatch(alertActions.setAlert({message:"Couldn't fetch the data!",severity:'error'}));
            }
        }
    }

    return (
        <Box flex={6} p={2}>
            <ThemeProvider theme={darkTheme}>
                <TableContainer component={Paper}>
                    {bookings.length > 0 ? (
                        <Table sx={{minWidth: 650}} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>No.</TableCell>
                                    <TableCell>Service Name</TableCell>
                                    <TableCell>Vehicle Type</TableCell>
                                    <TableCell>Date</TableCell>
                                    <TableCell>Time</TableCell>
                                    <TableCell>Status</TableCell>
                                    <TableCell>Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {bookings.map((booking, index) => (
                                    <TableRow
                                        key={index}
                                        hover={booking.status !== 'CANCELED'}
                                        style={{cursor: 'pointer'}}
                                    >
                                        <TableCell>{index + 1}</TableCell>
                                        <TableCell>{booking.customerName}</TableCell>
                                        <TableCell>{booking.vehicleType}</TableCell>
                                        <TableCell>{booking.bookingDate}</TableCell>
                                        <TableCell>{booking.bookingTime}</TableCell>
                                        <TableCell style={{
                                            color: booking.status === 'PENDING' ? 'yellow' :
                                                booking.status === 'CANCELED' ? 'red' :
                                                    booking.status === 'CONFIRMED' ? 'lawngreen' :
                                                        booking.status === 'COMPLETED' ? 'skyblue' : 'white'
                                        }}>
                                            {booking.status}
                                        </TableCell>
                                        {booking.status !== 'CANCELED' && <TableCell>
                                            <Button
                                                variant="contained"
                                                color="success"
                                                size="small"
                                                style={{margin: '5px'}}
                                                disabled={booking.status !== 'PENDING'}
                                                onClick={()=>handleBookingsClick('CONFIRMED',booking.bookingId)}
                                            >
                                                Confirm
                                            </Button>
                                            <Button
                                                variant="contained"
                                                color="error"
                                                size="small"
                                                style={{margin: '5px'}}
                                                disabled={booking.status === 'CANCELED' || booking.status === 'COMPLETED'}
                                                onClick={()=>handleBookingsClick('CANCELED',booking.bookingId)}
                                            >
                                                Cancel
                                            </Button>
                                            <Button
                                                variant="contained"
                                                size="small"
                                                style={{margin: '5px'}}
                                                disabled={booking.status !== 'CONFIRMED'}
                                                onClick={()=>handleBookingsClick('COMPLETED',booking.bookingId)}
                                            >
                                                Complete
                                            </Button>


                                        </TableCell>}
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    ) : (
                        <Alert severity="info">No bookings Available</Alert>
                    )}
                </TableContainer>
            </ThemeProvider>
        </Box>
    );
};

export default Bookings;