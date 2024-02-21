import './App.css';

import {createBrowserRouter, RouterProvider} from "react-router-dom"
import RootLayout from "./Components/Layout/RootLayout";
import AuthPage from "./Pages/AuthPage";
import ErrorPage from "./Pages/ErrorPage";
import HomeLayout from "./Components/Layout/HomeLayout";
import Schedules, {loader as schedulesLoader} from "./Components/Schedules/Schedules";
import GarageServices from "./Components/GarageSerevices/GarageServices";
import GarageServiceDetails from "./Components/GarageSerevices/GarageServiceDetails/GarageServiceDetails";

const router = createBrowserRouter([{
    path: '/',
    element: <RootLayout/>,
    errorElement: <ErrorPage/>,
    children: [{path: '/auth/:mode', element: <AuthPage/>},

        {
            path: '',
            element: <HomeLayout/>,
            children: [
                {path: 'schedules', element: <Schedules/>, loader: schedulesLoader},
                {path: 'services',element:<GarageServices/>},
                {path: 'services/:id',element:<GarageServiceDetails/>}
            ]
        }]
}

]);

function App() {
    return (<RouterProvider router={router}/>);
}

export default App;
