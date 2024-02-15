import React, {Component} from 'react';
import Feed from "../Components/Feed/Feed";
import SideBar from "../Components/SideBar/SideBar";

class LandingPage extends Component {
    render() {
        return (
            <>
                <SideBar/>
                <Feed/>
            </>
        );
    }
}

export default LandingPage;