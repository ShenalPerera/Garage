import React, {useEffect, useState} from 'react';
import {
    Avatar, Box, Button, Checkbox, Container, CssBaseline, FormControlLabel, Grid, TextField, Typography
} from "@mui/material";
import {LockOutlined} from "@mui/icons-material";
import {Link, useNavigate, useParams} from "react-router-dom";

import {
    confirmPasswordValidator, emailValidator, passwordValidator, requiredFieldValidator
} from "../../util/validators";
import {useDispatch, useSelector} from "react-redux";
import {alertActions, authenticateActions} from "../../Store";
import {postData} from "../../APIService/api";

const InputField = ({error, ...props}) => {
    return (<TextField
            error={error.hasError}
            helperText={error.hasError ? error.message : ''}
            {...props}
        />

    );
}
const AuthForm = () => {
    const params = useParams();
    const isLogin = params.mode === 'login';
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const resetFormData = {
        firstname: '', lastname: '', email: '', password: '', passwordConfirm: ''
    };
    const resetFormErrors = {
        firstname: {hasError: false, message: ''},
        lastname: {hasError: false, message: ''},
        email: {hasError: false, message: ''},
        password: {hasError: false, message: ''},
        passwordConfirm: {hasError: false, message: ''}
    }
    const [formData, setFormData] = useState(resetFormData);
    const [formErrors, setFormErrors] = useState(resetFormErrors);
    const {isLoggedIn} = useSelector(state => state.authenticate);

    useEffect(() => {
        setFormData(resetFormData);
        setFormErrors(resetFormErrors);
        if (isLoggedIn)
            navigate("/");
    }, [isLoggedIn, navigate, params]);

    const handleChange = (event) => {
        const {name, value} = event.target;
        setFormData({
            ...formData, [name]: value
        });

        // Validate input on change
        validateField(name, value);
    };

    const validateField = (fieldName, value) => {
        let error = {hasError: false, message: ''};

        switch (fieldName) {
            case 'firstname':
            case 'lastname':
                error = requiredFieldValidator(value, fieldName);
                break;
            case 'email':
                error = emailValidator(value, 'Email');
                break;
            case 'password':
                error = passwordValidator(value, 'Password');
                break;
            case 'passwordConfirm':
                error = confirmPasswordValidator(formData.password, value);
                break;
            default:
                break;
        }

        setFormErrors(prevErrors => ({
            ...prevErrors, [fieldName]: error
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Define the fields to check based on the current form
        const fieldsToCheck = isLogin ? ['email', 'password'] : ['firstname', 'lastname', 'email', 'password', 'passwordConfirm'];

        // Check if there are any empty required fields
        const emptyFields = fieldsToCheck.filter(key => {
            return formData[key].trim() === '';
        });

        if (emptyFields.length > 0) {
            emptyFields.forEach(field => {
                validateField(field, formData[field]);
            });
        } else {
            // Check if there are no errors
            if (!Object.values(formErrors).some(error => error.hasError)) {
                try {
                    const data = await postData(formData, isLogin ? 'login' : 'signup');
                    if (!isLogin) {
                        dispatch(alertActions.setAlert({message: data.message, severity: 'success'}));
                        navigate("/auth/login");
                    } else {

                        dispatch(alertActions.setAlert({message: 'Successfully logged in', severity: 'success'}));
                        localStorage.setItem('token', data.token);
                        localStorage.setItem('isLoggedIn',"true");
                        dispatch(authenticateActions.setAuthenticate());
                        navigate('/');
                    }
                } catch (error) {
                    console.log(error)
                    dispatch(alertActions.setAlert({message: error.message, severity: 'error'}));
                }
                // console.log('Form submitted:', formData);
            } else {
                console.log('Form has errors');
            }
        }
    };
    const {firstname, lastname, email, password, passwordConfirm} = formData;
    const {
        firstname: firstnameError,
        lastname: lastnameError,
        email: emailError,
        password: passwordError,
        passwordConfirm: passwordConfirmError
    } = formErrors;


    return (<Container component="main" maxWidth="xs">


            <CssBaseline/>
            <Box
                sx={{
                    marginTop: 3, display: 'flex', flexDirection: 'column', alignItems: 'center',
                }}
            >
                <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                    <LockOutlined/>
                </Avatar>
                <Typography component="h1" variant="h5">
                    {isLogin ? "Login " : "Sign up"}
                </Typography>
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                    {!isLogin && <>
                        <InputField
                            margin="normal"
                            required
                            fullWidth
                            id="firstname"
                            label="First Name"
                            name="firstname"
                            autoFocus
                            value={firstname}
                            error={firstnameError}
                            onChange={handleChange}

                        />

                        <InputField
                            margin="normal"
                            required
                            fullWidth
                            id="lastname"
                            label="Last Name"
                            name="lastname"
                            value={lastname}
                            error={lastnameError}
                            onChange={handleChange}

                        />
                    </>}

                    <InputField
                        margin="normal"
                        required
                        fullWidth
                        error={emailError}
                        id="email"
                        label="Email"
                        autoComplete="email"
                        name='email'
                        value={email}
                        onChange={handleChange}

                    />

                    <InputField
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        value={password}
                        error={passwordError}
                        onChange={handleChange}
                    />

                    {!isLogin && <InputField
                        margin="normal"
                        required
                        fullWidth
                        name="passwordConfirm"
                        label="Confirm Password"
                        type="password"
                        id="passwordConfirm"
                        value={passwordConfirm}
                        error={passwordConfirmError}
                        onChange={handleChange}
                    />}

                    <FormControlLabel
                        control={<Checkbox value="remember" color="primary"/>}
                        label="Remember me"
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{mt: 3, mb: 2}}
                    >
                        Sign In
                    </Button>
                    <Grid container>
                        <Grid item xs>
                            <Link to='/' variant="body2">
                                Forgot password?
                            </Link>
                        </Grid>
                        <Grid item>
                            <Link to={`../${isLogin ? 'signup' : 'login'}`} relative='path'>
                                {isLogin ? "Don't have an account? Sign Up" : "Already have an account? Login"}
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
            {/*<Copyright sx={{ mt: 8, mb: 4 }} />*/}
        </Container>);
};

export default AuthForm;