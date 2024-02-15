import React, {Component} from 'react';
import Header from "../Header/Header";
import {Box, Stack} from "@mui/material";
import {Outlet} from "react-router-dom";

class RootLayout extends Component {
    render() {
        return (
            <Box>
                <Header/>
                <Stack direction="row" spacing={2} justifyContent="space-evenly">
                    <Outlet/>
                </Stack>
            </Box>
        );
    }
}

export default RootLayout;