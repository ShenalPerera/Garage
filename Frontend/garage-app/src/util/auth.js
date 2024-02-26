import {redirect} from "react-router-dom";

export const getAuthToken = ()=>{
    return localStorage.getItem('token');
}

export const checkAuthLoader = ()=>{
    const token = getAuthToken();
    if (!token)
        return redirect('/auth/login');

    return true;
}