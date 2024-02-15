import './App.css';

import {createBrowserRouter, RouterProvider} from "react-router-dom"
import LandingPage from "./Pages/LandingPage";
import RootLayout from "./Components/RootLayout/RootLayout";
import Feed from "./Components/Feed/Feed";

const router = createBrowserRouter([
    {
        path:'/',
        element:<RootLayout/>,
        children:[
            {path:'/home', element:<LandingPage/>},
            {path:'/feed', element:<Feed/>}
        ]
    }

    ]
);

function App() {
  return (<RouterProvider router={router}/>);
}

export default App;
