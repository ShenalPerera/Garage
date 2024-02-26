import {
    Badge,
    Box,
    Button,
    Card,
    CardActions,
    CardContent,
    Chip, createTheme,
    Divider,
    IconButton,
    styled, ThemeProvider,
    Typography
} from "@mui/material";
import {useNavigate} from "react-router-dom";
import moment from 'moment';
import {Delete, Edit, Save} from "@mui/icons-material";
import {LoadingButton} from "@mui/lab";

const CustomBadge = styled(Badge)(({theme}) => ({
    '& .MuiBadge-dot': {
        width: 8, height: 8, borderRadius: '50%', // Ensure the dot is round
    }, '&.red .MuiBadge-dot': {
        backgroundColor: 'red', // Customize with your preferred color
    }, '&.green .MuiBadge-dot': {
        backgroundColor: 'green', // Customize with your preferred color
    }, '&.yellow .MuiBadge-dot': {
        backgroundColor: 'yellow', // Customize with your preferred color
    },
}));


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

const ScheduleItem = ({schedule,handleEdit,handleBookingsClick,handleDelete}) => { // Destructure schedule from props
    const navigate = useNavigate();


    const handleServiceClick = (serviceId) => {
        navigate(`../service/${serviceId}`)
    };


    const getBadgeColor = (scheduleDate, scheduleTime) => {
        const now = moment();
        const scheduleMoment = moment(`${scheduleDate} ${scheduleTime}`, 'YYYY-MM-DD HH:mm:ss'); // Combine date and time

        if (now.isAfter(scheduleMoment)) {
            return 'red';
        } else if (now.isBefore(scheduleMoment) && now.add(2, 'hours').isAfter(scheduleMoment)) {
            return 'yellow';
        } else {
            return 'green';
        }
    };

    return (
        <ThemeProvider theme={darkTheme}>
            <Card sx={{minWidth: 275, marginBottom: 2, backgroundColor: '#282c34', color: 'white'}} elevation={3}>
                <Box sx={{display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: 2}}>
                    <Typography variant="h5" component="div">
                        {moment(schedule.date).format('MMMM Do YYYY')}
                    </Typography>
                    <IconButton>
                        <CustomBadge className={getBadgeColor(schedule.date, schedule.startTime)} badgeContent=" "
                                     variant="dot"
                                     anchorOrigin={{
                                         vertical: 'top', horizontal: 'right',
                                     }}/>
                    </IconButton>
                </Box>
                <CardContent>
                    <Typography variant="h6" component="div">
                        {`${moment(schedule.startTime, 'HH:mm:ss').format('hh:mm A')} - ${moment(schedule.endTime, 'HH:mm:ss').format('hh:mm A')}`}
                    </Typography>
                    <Divider sx={{margin: '10px 0', backgroundColor: 'white'}}/>
                    <Typography sx={{margin: 1}}>Services</Typography>
                    <Divider/>
                    <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 2}}>

                        {schedule.garageServices.map(service => (
                            <Chip key={service.id}
                                  label={service.serviceName} color="primary"/>))}
                    </Box>
                    <Divider sx={{margin: '10px 0', backgroundColor: 'white'}}/>


                </CardContent>

                <CardActions sx={{display: 'flex', justifyContent: 'space-between'}}>
                    <Box sx={{display:'flex',justifyContent:'flex-end'}}>
                        <Typography><Button variant='contained' size='small' onClick={handleBookingsClick}>Bookings</Button></Typography>
                    </Box>
                    <Box sx={{display:'flex',justifyContent:'flex-end'}}>
                        <IconButton onClick={()=>handleEdit(schedule)}>
                            <Edit sx={{color: 'blue'}} />
                        </IconButton>
                        <IconButton onClick={()=>handleDelete(schedule)}>
                            <Delete sx={{color: 'red'}}/>
                        </IconButton>
                    </Box>

                </CardActions>
            </Card>
        </ThemeProvider>
    );
};

export default ScheduleItem;