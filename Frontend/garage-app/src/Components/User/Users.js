import React from 'react';
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

const users = [
    {
        id: 1,
        userName: 'John Doe',
        email: 'john.doe@example.com',
        role: 'Admin'
    },
    {
        id: 2,
        userName: 'Jane Smith',
        email: 'jane.smith@example.com',
        role: 'User'
    },
    {
        id: 3,
        userName: 'Bob Johnson',
        email: 'bob.johnson@example.com',
        role: 'Admin'
    },
    {
        id: 4,
        userName: 'Alice Williams',
        email: 'alice.williams@example.com',
        role: 'User'
    },
    // Add more users as needed
];


const Users = () => {
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
                                        <TableCell>{user.userName}</TableCell>
                                        <TableCell>{user.email}</TableCell>
                                        <TableCell>{user.role}</TableCell>
                                        <TableCell>
                                            <Button
                                                variant="contained"
                                                color="primary"
                                                size="small"
                                                style={{margin: '5px'}}
                                                disabled={user.role === 'Admin'}
                                                // onClick={()=>handleRoleChange(user.id, 'UPPER')}
                                            >
                                                Promote
                                            </Button>
                                            <Button
                                                variant="contained"
                                                color="secondary"
                                                size="small"
                                                style={{margin: '5px'}}
                                                disabled={user.role === 'User'}
                                                // onClick={()=>handleRoleChange(user.id, 'LOWER')}
                                            >
                                                Demote
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