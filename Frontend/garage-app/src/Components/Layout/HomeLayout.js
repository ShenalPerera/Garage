import React from 'react';
import {Stack} from "@mui/material";
import {Outlet} from "react-router-dom";
import SideBar from "../SideBar/SideBar";

const HomeLayout = () => {
    return (

            <Stack direction='row' spacing={2} justifyContent='space-beyween'>
                <SideBar/>
                <Outlet/>
            </Stack>

    );
};

export default HomeLayout;