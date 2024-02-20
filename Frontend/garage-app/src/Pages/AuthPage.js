import React, {Component, useState} from 'react';
import AuthForm from "../Components/Authentication/AuthForm";
import {redirect} from "react-router-dom";
class AuthPage extends Component {
    render() {
        return (<AuthForm/> );
    }
}

export default AuthPage;