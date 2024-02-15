import React from 'react';
import {
    Badge, Box,
    Card,
    CardContent,
    IconButton,
    Stack, styled,
    Typography
} from "@mui/material";


const CustomBadge = styled(Badge)(({ theme }) => ({
    '& .MuiBadge-dot': {
        width: 8,
        height: 8,
        borderRadius: '50%', // Ensure the dot is round
    },
    '&.red .MuiBadge-dot': {
        backgroundColor: 'red', // Customize with your preferred color
    },
    '&.green .MuiBadge-dot': {
        backgroundColor: 'green', // Customize with your preferred color
    },
    '&.yellow .MuiBadge-dot': {
        backgroundColor: 'yellow', // Customize with your preferred color
    },
}));

const ScheduleItem = (props) => {

    return (
            <Card>

                <Stack direction='column' spacing={1}>
                    <Box sx={{justifyContent:'right', display:'flex'}}>
                        <IconButton >
                            <CustomBadge className='yellow' badgeContent=" " variant="dot"
                                   anchorOrigin={{
                                       vertical: 'top',
                                       horizontal: 'right',
                                   }}/>
                        </IconButton>
                    </Box>
                    <Card>
                        <CardContent>
                            <Typography variant="h6" component="div">
                                {props.title}
                            </Typography>
                            <Typography variant="body2" color="text.secondary">
                                {props.time}
                            </Typography>
                        </CardContent>
                    </Card>
                </Stack>


            </Card>
    );
};

export default ScheduleItem;