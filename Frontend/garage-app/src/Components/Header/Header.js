import {
    AppBar,
    Avatar,
    Box,
    Button, Container,
    CssBaseline,
    IconButton,
    Menu,
    MenuItem,
    Slide,
    Toolbar,
    Tooltip,
    Typography,
    useScrollTrigger
} from "@mui/material";
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import PropTypes from "prop-types";
import * as React from "react";
import {useState} from "react";



const pages = ['TestNav1','TestNav2','TestNav2'];
const settings = ['Profile', 'Logout'];

function HideOnScroll(props) {
    const trigger = useScrollTrigger();

    const {children} = props;

    return (<Slide appear={false} direction="down" in={!trigger}>
            {children}
        </Slide>);
}

HideOnScroll.propTypes = {
    children: PropTypes.element.isRequired,
};

function Logout(props) {

    return null;
}

Logout.propTypes = {fontSize: PropTypes.string};
let Header = (props) => {
    const [anchorElNav, setAnchorElNav] = useState(null);
    const [anchorElUser, setAnchorElUser] = useState(null);
    const [isLogged,setIsLogged] = useState(true);
    function handleOpenNavMenu() {

    }


    function handleCloseNavMenu() {

    }

    function handleOpenUserMenu(event) {
        setAnchorElUser(event.currentTarget);
    }

    function handleCloseUserMenu() {
        setAnchorElUser(null);
    }

    function handleLogOut(){
        setIsLogged(false);
    }
    return (
        <>

                <HideOnScroll {...props}>
                    <AppBar>
                        <Toolbar disableGutters sx={{
                            display: 'flex',
                            justifyContent: 'space-between',
                            width: 'calc(100% -20px)',
                            height:'100%',
                            margin: '0 20px'
                        }}>
                            <Typography
                                variant="h6"
                                noWrap
                                component="a"
                                href="#app-bar-with-responsive-menu"
                                sx={{
                                    mr: 2,
                                    display: {xs: 'none', md: 'flex'},
                                    fontFamily: 'monospace',
                                    fontWeight: 700,
                                    letterSpacing: '.3rem',
                                    color: 'inherit',
                                    textDecoration: 'none',
                                }}
                            >
                                LOGO
                            </Typography>

                            <Box sx={{flexGrow: 1, display: {xs: 'flex', md: 'none'}}}>
                                <IconButton
                                    size="large"
                                    aria-label="account of current user"
                                    aria-controls="menu-appbar"
                                    aria-haspopup="true"
                                    onClick={handleOpenNavMenu}
                                    color="inherit"
                                >

                                </IconButton>
                                <Menu
                                    id="menu-appbar"
                                    anchorEl={anchorElNav}
                                    anchorOrigin={{
                                        vertical: 'bottom', horizontal: 'left',
                                    }}
                                    keepMounted
                                    transformOrigin={{
                                        vertical: 'top', horizontal: 'left',
                                    }}
                                    open={Boolean(anchorElNav)}
                                    onClose={handleCloseNavMenu}
                                    sx={{
                                        display: {xs: 'block', md: 'none'},
                                    }}
                                >
                                    {pages.map((page) => (<MenuItem key={page} onClick={handleCloseNavMenu}>
                                        <Typography textAlign="center">{page}</Typography>
                                    </MenuItem>))}
                                </Menu>
                            </Box>

                            <Typography
                                variant="h5"
                                noWrap
                                component="a"
                                href="#app-bar-with-responsive-menu"
                                sx={{
                                    mr: 2,
                                    display: {xs: 'flex', md: 'none'},
                                    flexGrow: 1,
                                    fontFamily: 'monospace',
                                    fontWeight: 700,
                                    letterSpacing: '.3rem',
                                    color: 'inherit',
                                    textDecoration: 'none',
                                }}
                            >
                                LOGO
                            </Typography>
                            <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
                                {pages.map((page) => (<Button
                                    key={page}
                                    onClick={handleCloseNavMenu}
                                    sx={{my: 2, color: 'white', display: 'block'}}
                                >
                                    {page}
                                </Button>))}
                            </Box>

                            {isLogged && <Box sx={{flexGrow: 0}}>
                                <Tooltip title="Profile">
                                    <IconButton onClick={handleOpenUserMenu} sx={{p: 0}}>
                                        <Avatar alt="Remy Sharp"/>
                                    </IconButton>
                                </Tooltip>
                                <Menu
                                    sx={{mt: '45px'}}
                                    id="menu-appbar"
                                    anchorEl={anchorElUser}
                                    anchorOrigin={{
                                        vertical: 'top', horizontal: 'right',
                                    }}
                                    keepMounted
                                    transformOrigin={{
                                        vertical: 'top', horizontal: 'right',
                                    }}
                                    open={Boolean(anchorElUser)}
                                    onClose={handleCloseUserMenu}
                                >

                                    <MenuItem key={'logout'} onClick={handleLogOut}>
                                        <ExitToAppIcon sx={{ mr: 1 }} /> {/* Logout icon */}
                                        Logout
                                    </MenuItem>
                                </Menu>
                            </Box>}
                        </Toolbar>
                    </AppBar>
                </HideOnScroll>


        </>);
}

export default Header;