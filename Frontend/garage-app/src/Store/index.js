import {configureStore, createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {getWithAuth, postWithAuth, putWithAuth} from "../APIService/api";

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


export const fetchGarageServices = createAsyncThunk('garageService/fetchServices',
        async (arg, thunkAPI)=> {
            try {
                const services = await getWithAuth('service/get-services');
                thunkAPI.dispatch(alertActions.setAlert({message:'Fetched with latest services',severity:'success'}))
                return services;
            }
            catch (e){
                thunkAPI.dispatch(alertActions.setAlert({message: e.message, severity: 'error'}))
            }
        }
    );

export const editGarageService = createAsyncThunk('garageService/editGarageService',
        async (updatedService, thunkAPI) =>{
            try {
                let result = {}
                const editedService = await putWithAuth(updatedService,'service/edit-service');
                const services = await getWithAuth('service/get-services');

                if (editedService.id === updatedService.id){
                    thunkAPI.dispatch(alertActions.setAlert({message:'Service Edited Successfully',severity:'success'}));
                    result = {services,editedService};
                }
                else{
                    thunkAPI.dispatch(alertActions.setAlert({message:'Can not edit service. Please try again!',severity:'error'}));
                    result = {services,editedService:null};
                }
                return result;
            }
            catch (e){
                thunkAPI.dispatch(alertActions.setAlert({message: e.message, severity: 'error'}))
            }

        }
    )



export const createGarageService = createAsyncThunk('garageService/createGarageService',
        async (newService, thunkAPI)=>{
            try {
                const createdService = await postWithAuth(newService,'service/create-service');
                if (!createdService.id){
                    thunkAPI.dispatch(alertActions.setAlert({message:'Service created Successfully',severity:'success'}));
                }
                else{
                    thunkAPI.dispatch(alertActions.setAlert({message:'Can not edit service. Please try again!',severity:'error'}));
                }
                return await getWithAuth('service/get-services');
            }
            catch (e){
                thunkAPI.dispatch(alertActions.setAlert({message: e.message, severity: 'error'}))
            }
        }
    )
const initialState = {
    garageServices: [],
    editedService:null
};

// Create slice
const garageServicesSlice = createSlice({
    name: 'garageServices',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchGarageServices.fulfilled, (state, action) => {
                state.garageServices = action.payload||[];
            })
            .addCase(editGarageService.fulfilled,(state,action) =>{
                state.editedService = action.payload.editedService;
                state.garageServices = action.payload.services;
            }).addCase(createGarageService.fulfilled,(state,action)=>{
                state.garageServices = action.payload;
            })
    },
});


const store = configureStore({
    reducer:{generalAlert:alertSlice.reducer, authenticate:authenticateSlice.reducer,garageServices:garageServicesSlice.reducer}
});






export const alertActions = alertSlice.actions;
export const authenticateActions = authenticateSlice.actions;
export default store;