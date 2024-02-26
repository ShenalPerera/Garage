import React, {useState} from 'react';
import {Link, useLocation, useNavigate} from 'react-router-dom';


import {AppBar, Avatar, IconButton, Menu, MenuItem, Stack, Tab, Tabs, Toolbar, Typography} from "@mui/material";
import {AccountCircle, CarRepair, Login} from "@mui/icons-material";
import {useDispatch, useSelector} from "react-redux";
import {authenticateActions} from "../../Store";
import {blue, deepOrange} from "@mui/material/colors";


const Header = () => {
    const {isLoggedIn} = useSelector(state => state.authenticate);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);

    const location = useLocation();
    // Set the initial value based on the current location
    const [value, setValue] = React.useState(location.pathname);

    // Update the value when the location changes
    React.useEffect(() => {
        setValue(location.pathname);
    }, [location]);

    const handleChange = (event, newValue) => {
        navigate(newValue);
        setValue(newValue);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };
    const handleProfileClick = (event) => {
        setAnchorEl(event.currentTarget);
    }

    const logOutHandler = () => {
        dispatch(authenticateActions.clearAuthenticate());
        localStorage.removeItem('token');
        localStorage.removeItem('isLoggedIn');
        setAnchorEl(null);
        navigate('/');
    }


    return (

        <AppBar position="sticky">
            <Toolbar sx={{display: 'flex', justifyContent: 'space-between', backgroundColor: 'black'}}>
                <Stack direction="row" spacing={2} justifyContent="space-evenly" alignItems='center'>
                    <Typography onClick={() => navigate("/")} variant='h6' sx={{display: {xs: 'none', sm: 'block'}}}>
                        Garage App
                    </Typography>
                    <CarRepair direction='column' sx={{display: {xs: 'block', sm: 'none'}}}/>

                    <Tabs value={value} onChange={handleChange} aria-label="lab API tabs example">
                        <Tab sx={{color: 'white'}} label="Schedules" value="/schedules"/>
                        <Tab sx={{color: 'white'}} label="Services" value="/services"/>

                    </Tabs>
                </Stack>

                {isLoggedIn && <Avatar
                    sx={{
                        width: '40px', height: '40px', backgroundColor: blue[1000], // Change the background color
                        cursor: 'pointer', // Change the cursor to a pointer when hovering over the avatar
                    }}
                    onClick={handleProfileClick}
                >
                    <AccountCircle/>
                </Avatar>}
                {!isLoggedIn &&
                    <Link to="/auth/login">

                        <IconButton
                        sx={{
                            color: deepOrange[500], // Change the color of the icon
                            backgroundColor: 'white', // Change the background color of the button
                            '&:hover': {
                                backgroundColor: blue[400], // Change the background color to blue when hovering over the button
                            },
                        }}

                        >

                        <Login/>

                    </IconButton>
                </Link>}
            </Toolbar>

            <Menu
                id="demo-positioned-menu"
                aria-labelledby="demo-positioned-button"
                open={open}
                anchorEl={anchorEl}
                onClose={(e) => handleClose()}
                anchorOrigin={{
                    vertical: 'top', horizontal: 'right',
                }}
                transformOrigin={{
                    vertical: 'top', horizontal: 'right',
                }}
            >
                <MenuItem onClick={logOutHandler}>Logout</MenuItem>
            </Menu>
        </AppBar>);
};

export default Header;