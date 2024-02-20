import React, {Component} from 'react';
import Header from "../Header/Header";
import {Box, Stack} from "@mui/material";
import {Outlet} from "react-router-dom";
import ErrorAlert from "../ErrorAlert/ErrorAlert";
import SideBar from "../SideBar/SideBar";

class RootLayout extends Component {
    render() {
        return (
            <Box>
                <ErrorAlert/>
                <Header/>
                <Outlet/>
            </Box>
        );
    }
}

export default RootLayout;