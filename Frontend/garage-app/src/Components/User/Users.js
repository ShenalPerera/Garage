import React, {useEffect, useState} from 'react';
import {
    Alert,
    Box, Button, createTheme, Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    ThemeProvider
} from "@mui/material";
import * as PropTypes from "prop-types";
import {getWithAuth} from "../../APIService/api";
import {alertActions, authenticateActions} from "../../Store";
import {useDispatch} from "react-redux";



TableCell.propTypes = {children: PropTypes.node};

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




const Users = () => {
    const [users, setUsers] = useState([]);
    const dispatch = useDispatch();
    const navigate = useDispatch();

    useEffect(() => {
        const fetchUserData = async ()=>{
            try {
                const users = await getWithAuth("admin/get-users");
                setUsers(users);
            }
            catch (e) {
                if (e.response && e.response.status === 401){
                    dispatch(alertActions.setAlert({message:"You are not authorized",severity:'error'}));
                    dispatch(authenticateActions.clearAuthenticate());
                    navigate("/auth/login");
                }
                dispatch(alertActions.setAlert({message:"Cannot get the users",severity:'error'}));
            }
        }
       fetchUserData();
    }, [dispatch]);
    return (
        <Box flex={6} p={2}>
            <ThemeProvider theme={darkTheme}>
                <TableContainer component={Paper}>
                    {users.length > 0 ? (
                        <Table sx={{minWidth: 650}} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>No.</TableCell>
                                    <TableCell>User Name</TableCell>
                                    <TableCell>Email</TableCell>
                                    <TableCell>Role</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {users.map((user, index) => (
                                    <TableRow
                                        key={user.id}
                                        hover
                                        // onClick={()=>handleUserOnClick(user)}
                                        style={{cursor: 'pointer'}}
                                    >
                                        <TableCell>{index + 1}</TableCell>
                                        <TableCell>{user.firstname}</TableCell>
                                        <TableCell>{user.email}</TableCell>
                                        <TableCell>{user.role}</TableCell>
                                        <TableCell>
                                            <Button
                                                variant="contained"
                                                color="primary"
                                                size="small"
                                                style={{margin: '5px'}}
                                                disabled={user.role === 'ADMIN'}
                                                // onClick={()=>handleRoleChange(user.id, 'UPPER')}
                                            >
                                                Promote
                                            </Button>
                                            <Button
                                                variant="contained"
                                                color="secondary"
                                                size="small"
                                                style={{margin: '5px'}}
                                                disabled={user.role === 'USER'}
                                                // onClick={()=>handleRoleChange(user.id, 'LOWER')}
                                            >
                                                Remove
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    ) : (
                        <Alert severity="info">No users available</Alert>
                    )}
                </TableContainer>
            </ThemeProvider>
        </Box>

    );
};

export default Users;