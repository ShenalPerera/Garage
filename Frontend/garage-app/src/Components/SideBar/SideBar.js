import React from 'react';
import {Box, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Paper} from "@mui/material";
import {Home} from "@mui/icons-material";
import {Link} from "react-router-dom";
import CountUp from "../CountUp/CountUp";

const SideBar = () => {
    return (
        <Box  flex={1} p={2} sx={{display:{xs:'none',sm:'block'}}}>

            <Paper>
                <List>
                    <ListItem disablePadding>
                        <ListItemButton component='a' href='#home'>
                            <ListItemIcon>
                                <Home/>
                            </ListItemIcon>
                            <ListItemText primary="Home Page" />
                        </ListItemButton>
                    </ListItem>
                    <ListItem disablePadding>
                        <Link to={'/feed'}>
                            <ListItemButton >
                                <ListItemIcon>
                                    <Home/>
                                </ListItemIcon>
                                <ListItemText primary="Home Page" />
                            </ListItemButton>
                        </Link>
                    </ListItem>
                </List>
            </Paper>

        </Box>
    );
};

export default SideBar;