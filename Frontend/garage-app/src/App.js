import './App.css';

import {createBrowserRouter, Navigate, RouterProvider, useNavigate} from "react-router-dom"
import RootLayout from "./Components/Layout/RootLayout";
import AuthPage from "./Pages/AuthPage";
import ErrorPage from "./Pages/ErrorPage";
import HomeLayout from "./Components/Layout/HomeLayout";
import Schedules, {loader as schedulesLoader} from "./Components/Schedules/Schedules";
import GarageServices from "./Components/GarageSerevices/GarageServices";
import {checkAuthLoader} from "./util/auth";
import {useEffect} from "react";
import {useDispatch} from "react-redux";
import {authenticateActions} from "./Store";
import Users from "./Components/User/Users";
import LandingPage from "./Components/LandingPage";
import UserProfile from "./Components/Profile/UserProfile";


const router = createBrowserRouter([{
    path: '/',
    element: <RootLayout/>,
    errorElement: <ErrorPage/>,

    children: [
        {
            path: '/auth/:mode', element: <AuthPage/>
        },
        {
            path: '',
            element: <HomeLayout/>,
            children: [
                {index:true,element:<LandingPage/>},
                {path: 'schedules', element: <Schedules/>,loader:checkAuthLoader},
                {path: 'services',element:<GarageServices/>,loader:checkAuthLoader},
                {path: 'profile',element:<UserProfile/>}
            ]
        }]
},

]);

function App() {
    const dispatch = useDispatch();

    useEffect(() => {
        const setLoggedIn = async ()=> {
            const isLoggedIn = localStorage.getItem('isLoggedIn');
            const role = localStorage.getItem("role");

            if (!isLoggedIn){
                dispatch(authenticateActions.clearAuthenticate());
            }
            else{
                dispatch(authenticateActions.setAuthenticate(role));
            }

        }
        setLoggedIn();
    }, [dispatch]);
    return (<RouterProvider router={router}/>);
}

export default App;
