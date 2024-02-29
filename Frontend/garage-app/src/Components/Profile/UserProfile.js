import React, {useEffect, useState} from 'react';
import {
    Box,
    Button,
    Card,
    CardContent,
    createTheme, Divider,
    Grid,
    Stack,
    TextField,
    ThemeProvider,
    Typography
} from "@mui/material";
import {getAuthToken} from "../../util/auth";
import {getWithAuth, postWithAuth, putWithAuth} from "../../APIService/api";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {alertActions, authenticateActions} from "../../Store";


const darkTheme = createTheme({
    palette: {
        mode: 'dark',
        primary: {
            main: '#0000ff', // blue
        },
        secondary: {
            main: '#ff0000', // red
        },
        success: {
            main: '#008000', // green
        },
        error: {
            main: '#ff0000', // red for danger
        },
    },

});

const UserProfile = () => {

    const [editingDetails, setEditingDetails] = useState(false);
    const [editingPassword, setEditingPassword] = useState(false);
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const [email, setEmail] = useState("");
    const [currentPassword, setCurrentPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const isAdmin = useSelector(state => state.authenticate.role);
    const [user, setUser] = useState(undefined);
    const [userId, setUserId] = useState(undefined);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleEditDetailsClick = () => {
        setEditingDetails(true);
        setEditingPassword(false);
    };

    const handleEditPasswordClick = () => {
        setEditingPassword(true);
        setEditingDetails(false);
    };

    const handleSaveClick = async () => {
        // Save changes (e.g., update user data in the backend)
        // You can add your logic here.
        try {
            let result;
            if (editingDetails){
                result =  await putWithAuth({id:userId,firstname,lastname,email},"change-user-details");
            }
            if (editingPassword){
                result = await  putWithAuth({id:userId,currentPassword, confirmPassword,newPassword},"admin/change-password");
            }

            if (result){

                dispatch(alertActions.setAlert({message:"Successfully edited details. Please log in again!",severity:"success"}));
                dispatch(authenticateActions.clearAuthenticate());
                localStorage.removeItem('token');
                localStorage.removeItem('isLoggedIn');
                navigate('/auth/login');
            }


        }
        catch (e) {
            if (e.response && e.response.status === 401){
                dispatch(alertActions.setAlert({message:"Please try to login and check again!",severity:"error"}));
                localStorage.removeItem('token');
                localStorage.removeItem('isLoggedIn');
                dispatch(authenticateActions.clearAuthenticate());
                navigate("/auth/login");
            }
            else if (e.response && e.response.data && e.response.data.message){
                dispatch(alertActions.setAlert({message:e.response.data.message,severity: "error"}));
            }
            else{
                dispatch(alertActions.setAlert({message:"Please try again!",severity:"error"}));
            }
        }

        // For demonstration purposes, let's just toggle back to display mode.
        setEditingDetails(false);
        setEditingPassword(false);
    };


    useEffect(() => {
        const fetchUser = async ()=>{
            try {
                const user = await getWithAuth("get-user");
                setUser(user);
                setFirstname(user.firstname);
                setLastname(user.lastname);
                setEmail(user.email);
                setUserId(user.id);
            }
            catch (e) {
                if (e.response && e.response.status === 401){
                    dispatch(alertActions.setAlert({message:"Please try to login and check again!",severity:"error"}));
                    dispatch(authenticateActions.clearAuthenticate());
                    localStorage.removeItem('token');
                    localStorage.removeItem('isLoggedIn');
                    navigate('/auth/login');
                }
                else {
                    dispatch(alertActions.setAlert({message:"Error occurred",severity:"error"}));
                    navigate("/");
                }

            }

        }
        fetchUser();
    }, [dispatch, navigate]);




    return (
        <ThemeProvider theme={darkTheme}>
            <Grid  justify="center">
                    <Card sx={{justifyContent:'center'}}>
                        <CardContent sx={{justifyContent:'center',marginLeft:'25%',marginRight:"25%"}}>

                                    {editingDetails && (
                                        <Box>
                                            <TextField
                                                label="firstname"
                                                value={firstname}
                                                onChange={(e) => setFirstname(e.target.value)}
                                                fullWidth
                                                margin="normal"
                                            />
                                            <TextField
                                                label="lastname"
                                                value={lastname}
                                                onChange={(e) => setLastname(e.target.value)}
                                                fullWidth
                                                margin="normal"
                                            />
                                            <TextField
                                                label="Email"
                                                value={email}
                                                onChange={(e) => setEmail(e.target.value)}
                                                fullWidth
                                                margin="normal"
                                            />
                                            <Button variant="contained" size='small' style={{margin: '5px'}} onClick={handleSaveClick}>
                                                Save
                                            </Button>
                                            <Button variant='contained' color='secondary' size='small' onClick={()=>{setEditingDetails(false)}}>Close</Button>
                                        </Box>
                                    ) }
                                    {user && !editingDetails && (
                                        <>
                                            <Typography variant="h6">First name -  {user.firstname}</Typography>
                                            <Typography variant="h6">Last name  - {user.lastname}</Typography>
                                            <Typography variant="h6">Email      - {user.email}</Typography>

                                            <Button variant="contained"
                                                    style={{margin: '5px'}}  onClick={handleEditDetailsClick}>
                                                Edit Details
                                            </Button>
                                        </>
                                    )}
                                    {isAdmin==="ROLE_ADMIN" && (
                                        <>
                                            {editingPassword ? (
                                                <>
                                                    <TextField
                                                        label="Current Password"
                                                        type="password"
                                                        value={currentPassword}
                                                        onChange={(e) => setCurrentPassword(e.target.value)}
                                                        fullWidth
                                                        margin="normal"
                                                    />
                                                    <TextField
                                                        label="New Password"
                                                        type="password"
                                                        value={newPassword}
                                                        onChange={(e) => setNewPassword(e.target.value)}
                                                        fullWidth
                                                        margin="normal"
                                                    />
                                                    <TextField
                                                        label="Confirm Password"
                                                        type="password"
                                                        value={confirmPassword}
                                                        onChange={(e) => setConfirmPassword(e.target.value)}
                                                        fullWidth
                                                        margin="normal"
                                                    />
                                                    <Button variant="contained" style={{margin: '5px'}} onClick={handleSaveClick}>
                                                        Save
                                                    </Button>
                                                    <Button variant='contained' color='secondary' onClick={()=>{setEditingPassword(false)}}>Close</Button>
                                                </>
                                            ) : (
                                                <Button variant="contained"  style={{margin: '5px'}} onClick={handleEditPasswordClick}>
                                                    Change Password
                                                </Button>
                                            )}
                                        </>
                                    )}

                        </CardContent>
                    </Card>
            </Grid>

        </ThemeProvider>
    );
};

export default UserProfile;