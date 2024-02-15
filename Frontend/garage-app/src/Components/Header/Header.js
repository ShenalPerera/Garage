import React, {useState} from 'react';
import {Link} from 'react-router-dom';


import {AppBar, Avatar, Menu, MenuItem, Toolbar, Typography} from "@mui/material";
import {CarRepair} from "@mui/icons-material";

const Header = () => {
    const [open, setOpen] = useState(false);

    const handleProfileClick = ()=>{
        setOpen(true);
    }

    return (
        <AppBar position="sticky">
            <Toolbar sx={{display:"flex",justifyContent:"space-between"}}>
                <Typography variant='h6' sx={{display:{xs:'none',sm:'block'}}}>
                    Garage App
                </Typography>
                <CarRepair sx={{display:{xs:'block',sm:'none'}}}/>
                <Avatar
                    sx={{width:'40px',height:'40px'}}
                    onClick={handleProfileClick}
                >
                    M
                </Avatar>
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
                <MenuItem>Logout</MenuItem>
            </Menu>
        </AppBar>
    );
};

export default Header;