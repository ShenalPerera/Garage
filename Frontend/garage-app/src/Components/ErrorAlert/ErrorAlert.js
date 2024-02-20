import React from 'react';
import {Alert, Snackbar} from "@mui/material";
import {useDispatch, useSelector} from "react-redux";
import {alertActions} from "../../Store";

const ErrorAlert = () => {
    const {alertDetails} = useSelector(state => state.generalAlert);
    const dispatch = useDispatch();
    const handleClose = () =>{
        dispatch(alertActions.clearAlert());
    }
    return (
        <Snackbar open={!!alertDetails} autoHideDuration={6000} onClose={handleClose} anchorOrigin={{vertical:'top',horizontal:'right'}}>
            <Alert
                severity={!!alertDetails ? alertDetails.severity:"info"}
                variant="filled"
                sx={{ width: '100%' }}
                onClose={handleClose}
            >
                {!!alertDetails && alertDetails.message}
            </Alert>
        </Snackbar>
    );
};

export default ErrorAlert;