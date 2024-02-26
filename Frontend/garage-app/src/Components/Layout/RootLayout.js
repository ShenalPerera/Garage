import React from 'react';
import Header from "../Header/Header";
import {Box} from "@mui/material";
import {Outlet} from "react-router-dom";
import ErrorAlert from "../ErrorAlert/ErrorAlert";

const RootLayout = () => {

    return (<Box>
            <ErrorAlert/>
            <Header/>
            <Outlet/>
        </Box>);

}

export default RootLayout;