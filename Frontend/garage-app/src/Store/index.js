import {configureStore, createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {deleteWithAuth, getWithAuth, postWithAuth, putWithAuth} from "../APIService/api";

const initialAlertState = {alertDetails: null};

const alertSlice = createSlice({
    name: 'generalAlert', initialState: initialAlertState, reducers: {
        setAlert(state, action) {
            state.alertDetails = action.payload;
        }, clearAlert(state) {
            state.alertDetails = null;
        }
    }
});


const initialAuthenticatedStatus = {isLoggedIn: false,role:null};

const authenticateSlice = createSlice({
    name: 'authenticate', initialState: initialAuthenticatedStatus, reducers: {
        setAuthenticate(state,action) {
            state.isLoggedIn = true;
            state.role =action.payload;
        },

        clearAuthenticate(state) {
            state.isLoggedIn = false;
            localStorage.removeItem("isLoggedIn");
            localStorage.removeItem("role");
            state.role = null;
        },

        setRole(state,action){
            state.role = action.payload;
        }
    }
});


export const fetchGarageServices = createAsyncThunk('garageService/fetchServices', async (arg, thunkAPI) => {
    try {
        const services = await getWithAuth('service/get-services');
        thunkAPI.dispatch(alertActions.setAlert({message: 'Fetched with latest services', severity: 'success'}))
        return services;
    } catch (e) {
        if (e.response.status === 401){
            const error = new Error();
            error.message = e.response.data.message;

            throw error;
        }

        thunkAPI.dispatch(alertActions.setAlert({message: e.message, severity: 'error'}))
    }
});

export const editGarageService = createAsyncThunk('garageService/editGarageService', async (updatedService, thunkAPI) => {
    try {
        let result = {}
        const editedService = await putWithAuth(updatedService, 'service/edit-service');
        const services = await getWithAuth('service/get-services');

        if (editedService.id === updatedService.id) {
            thunkAPI.dispatch(alertActions.setAlert({message: 'Service Edited Successfully', severity: 'success'}));
            result = {services, editedService};
        } else {
            thunkAPI.dispatch(alertActions.setAlert({
                message: 'Can not edit service. Please try again!', severity: 'error'
            }));
            result = {services, editedService: null};
        }
        return result;
    }
    catch (e) {
        if (e.response.data){
            if (e.response.status === 401){

                return thunkAPI.rejectWithValue({message:e.response.data.message, status:401});
            }
            else if (e.response.data.status){
                return thunkAPI.rejectWithValue({message:e.response.data.message, status:e.response.data.status});
            }
            else{
                thunkAPI.dispatch(alertActions.setAlert({message:"Error occurred!",severity:'error'}));
            }
        }
        thunkAPI.dispatch(alertActions.setAlert({message:"Error occurred!",severity:'error'}))
    }

})


export const deleteGarageService = createAsyncThunk('garageService/deleteGarageService', async (id, thunkAPI) => {
    try {
        await deleteWithAuth('service/delete-service/' + id);
        thunkAPI.dispatch(alertActions.setAlert({message: 'Service deleted Successfully', severity: 'success'}));
        return await getWithAuth('service/get-services');
    } catch (e) {
        if (e.response.data){

            if (e.response.status === 401){

                return thunkAPI.rejectWithValue({message:e.response.data.message, status:401});
            }
            else if (e.response.data.status){
                return thunkAPI.rejectWithValue({message:e.response.data.message, status:e.response.data.status});
            }
        }
        thunkAPI.dispatch(alertActions.setAlert({message:"Error occurred!",severity:'error'}))
    }
})


export const createGarageService = createAsyncThunk('garageService/createGarageService', async (newService, thunkAPI) => {
    try {
        const createdService = await postWithAuth(newService, 'service/create-service');
        if (createdService.id) {
            thunkAPI.dispatch(alertActions.setAlert({message: 'Service created Successfully', severity: 'success'}));
        } else {
            thunkAPI.dispatch(alertActions.setAlert({
                message: 'Can not edit service. Please try again!', severity: 'error'
            }));
        }
        return await getWithAuth('service/get-services');
    } catch (e) {
        if (e.response.data){

            if (e.response.status === 401){

                return thunkAPI.rejectWithValue({message:e.response.data.message, status:401});
            }
            else if (e.response.data.status){
                return thunkAPI.rejectWithValue({message:e.response.data.message, status:e.response.data.status});
            }
        }
        thunkAPI.dispatch(alertActions.setAlert({message:"Error occurred!",severity:'error'}))
    }
})
const initialState = {
    garageServices: [], editedService: null
};

// Create slice
const garageServicesSlice = createSlice({
    name: 'garageServices', initialState, reducers: {}, extraReducers: (builder) => {
        builder
            .addCase(fetchGarageServices.fulfilled, (state, action) => {
                state.garageServices = action.payload || [];
            })
            .addCase(editGarageService.fulfilled, (state, action) => {
                state.editedService = action.payload.editedService;
                state.garageServices = action.payload.services || state.garageServices;
            }).addCase(createGarageService.fulfilled, (state, action) => {
            state.garageServices = action.payload || state.garageServices;
        }).addCase(deleteGarageService.fulfilled, (state, action) => {
            state.garageServices = action.payload ;
        }).addCase(deleteGarageService.rejected,(state,action)=>{

        })
    },
});


const store = configureStore({
    reducer: {
        generalAlert: alertSlice.reducer,
        authenticate: authenticateSlice.reducer,
        garageServices: garageServicesSlice.reducer
    }
});


export const alertActions = alertSlice.actions;
export const authenticateActions = authenticateSlice.actions;
export default store;