import React from 'react';
import {Box, Card, CardContent, CardHeader, CardMedia, Paper, Typography} from "@mui/material";
import Header from "../Components/Header/Header";

const ErrorPage = () => {
    return (
        <>
            <Header/>
            <Box sx={{display:'flex',justifyContent:'center'} }>
                <Card sx={{width:'100%',marginX:'25%',textAlign:'center'}}>
                    <CardHeader component='h2' title='404'/>

                    <CardMedia component='img' image='https://cdn.pixabay.com/photo/2013/04/01/09/02/important-98442_1280.png'/>


                    <CardContent>


                            <Typography variant='h5'>
                                Oops... Page Not Found
                            </Typography>

                    </CardContent>

                </Card>

            </Box>
        </>

    );
};

export default ErrorPage;