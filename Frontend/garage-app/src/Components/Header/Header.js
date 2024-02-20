import React, {useState} from 'react';
import {Link, NavLink} from 'react-router-dom';


import {AppBar, Avatar, Box, makeStyles, Menu, MenuItem, Stack, Toolbar, Typography} from "@mui/material";
import {CarRepair} from "@mui/icons-material";
import {useDispatch, useSelector} from "react-redux";
import {authenticateActions} from "../../Store";



const Header = () => {
    const [open, setOpen] = useState(false);
    const {isLoggedIn} = useSelector(state=>state.authenticate);
    const dispatch = useDispatch();
    const handleProfileClick = ()=>{
        setOpen(true);
    }

    const logOutHandler = ()=>{
        dispatch(authenticateActions.clearAuthenticate());
    }

    return (
        <AppBar position="sticky">
            <Toolbar sx={{display:"flex",justifyContent:"space-between"}} >
                <Stack direction="row" spacing={2} justifyContent="space-evenly" alignItems='center'>
                    <Typography  variant='h6' sx={{display:{xs:'none',sm:'block'}}}>
                        Garage App
                    </Typography>
                    <CarRepair direction='column'  sx={{display:{xs:'block',sm:'none'}}}/>

                    <NavLink to='/schedules' > Schedules</NavLink>

                    <NavLink to='/services'> Services</NavLink>
                </Stack>

                {isLoggedIn && <Avatar
                    sx={{width: '40px', height: '40px'}}
                    onClick={handleProfileClick}
                >
                    M
                </Avatar>}
                {!isLoggedIn &&
                    <Link to="/auth/login">
                        <Typography variant='a' > Log in</Typography>
                    </Link>
                }
            </Toolbar>

            <Menu
                id="demo-positioned-menu"
                aria-labelledby="demo-positioned-button"
                open={open}
                onClose={(e) => setOpen(false)}
                anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
            >
                <MenuItem>Profile</MenuItem>
                <MenuItem>My account</MenuItem>
                <MenuItem onClick={logOutHandler}>Logout</MenuItem>
            </Menu>
        </AppBar>
    );
};

export default Header;