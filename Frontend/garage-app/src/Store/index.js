import {configureStore, createSlice} from "@reduxjs/toolkit";

const initialAlertState = {alertDetails:null};

const alertSlice = createSlice({
    name:'generalAlert',
    initialState:initialAlertState,
    reducers:{
        setAlert(state, action) {
            state.alertDetails = action.payload;
        },
        clearAlert(state) {
            state.alertDetails = null;
        }
    }
});


const initialAuthenticatedStatus = {isLoggedIn:true};

const authenticateSlice = createSlice({
    name:'authenticate',
    initialState:initialAuthenticatedStatus,
    reducers:{
        setAuthenticate(state){
            state.isLoggedIn = true;
        },

        clearAuthenticate(state){
            state.isLoggedIn = false;
        }
    }
});

const store = configureStore({
    reducer:{generalAlert:alertSlice.reducer, authenticate:authenticateSlice.reducer}
});

export const alertActions = alertSlice.actions;
export const authenticateActions = authenticateSlice.actions;
export default store;