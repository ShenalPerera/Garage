export const emailValidator = (email,field)=>{
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const error = {...requiredFieldValidator(email,field)};
    if (!error.hasError && !emailRegex.test(email)) {
        error.hasError = true;
        error.message = 'Invalid email address';
    }
    return error;
}

export const requiredFieldValidator = (inputValue,field) =>{
    const error = {hasError:false, message:''}
    if (!inputValue)
        error.hasError = true
        error.message = field + ' is required';
    return error;
}


export const passwordValidator = (password,field) =>{
    const error = requiredFieldValidator(password, field);

    if (!error.hasError) {
        // Minimum length check
        if (password.length < 6) {
            error.hasError = true;
            error.message = `${field} must be at least 6 characters long`;
        }
        // Complexity rules check
        else if (!/[a-z]/.test(password) || !/[A-Z]/.test(password) || !/[0-9]/.test(password) || !/[^a-zA-Z0-9]/.test(password)) {
            error.hasError = true;
            error.message = `${field} must contain at least one uppercase letter, one lowercase letter, one digit, and one special character`;
        }
    }
    return error;
}

export const confirmPasswordValidator = (password, confirmPassword) => {
    if (password !== confirmPassword) {
        return { hasError: true, message: "Passwords do not match" };
    } else {
        return { hasError: false, message: "" };
    }
};
