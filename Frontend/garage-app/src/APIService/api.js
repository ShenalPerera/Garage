import axios from 'axios';
import {getAuthToken} from "../util/auth";


const BASE_URL = 'http://localhost:8080';


export const postData = async (data,path)=>{
    try {
        const response = await axios.post(`${BASE_URL}/`+path, data);
        return response.data;
    } catch (error) {
        if (error.response.data)
            throw error.response.data; // Re-throwing the error after logging
        else
            throw error;
    }
}



const instance = axios.create();


instance.interceptors.request.use(async (config) => {

    const token = getAuthToken();
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
}, (error) => {
    return Promise.reject(error);
});


export const postWithAuth = async (data,path) =>{
    try {
        const response =await instance.post(`${BASE_URL}/` + path,data);
        return response.data;
    }
    catch (error){
        if (error.response.data)
            throw error.response.data;
        else
            throw error;
    }
}

export const putWithAuth  = async (data,path) =>{
    try {
        const response =await instance.put(`${BASE_URL}/` + path,data);
        return response.data;
    }
    catch (error){
        if (error.response.data)
            throw error.response.data;
        else
            throw error;
    }
}


export const getWithAuth = async (path,params= {}) =>{
    try {
        const response = await  instance.get(`${BASE_URL}/` + path, {params});
        return response.data;
    }
    catch (e) {
        throw e;
    }
}

export const deleteWithAuth = async (path,params={}) => {
  try {
      const response = await instance.delete(`${BASE_URL}/`+path,{params});
      return response.data;
  }
  catch (e) {
      throw e;
  }
}