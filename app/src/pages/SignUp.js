// Code source reference https://github.com/mui-org/material-ui/tree/master/docs/src/pages/getting-started/templates/sign-up
import React, {useState} from 'react';
import {useHistory} from "react-router-dom";
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import {makeStyles} from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Copyright from '../components/Copyright'
import axios from "axios";
import logoTransparentBackground from '../assets/logo_transparent_background.png'

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

export default function SignUp() {
    let history = useHistory();
    const classes = useStyles();

    const [name, setName] = useState("");
    const [nameError, setNameError] = useState("");
    const [password, setPassword] = useState("");
    const [passwordError, setPasswordError] = useState("");
    const [repassword, setRePassword] = useState("");
    const [repasswordError, setRepasswordError] = useState("");

    const updateUsername = (username) => {
        if ("" === username) {
            setNameError("username can't be empty");
            return;
        } else if (username.indexOf(' ') >= 0) {
            setNameError("username can't contain space");
            return;
        } else {
            setNameError(null);
        }
        // duplicate username
        axios.get('/api/user/name/' + username)
            .then(res => {
                if (204 !== res.status) {
                    setNameError("username already exist. ");
                    return;
                } else {
                    setNameError(null);
                }
            });
        setName(username);
    };

    const updatePassword = (password) => {
        if (password.length < 4) {
            setPasswordError("Password is too short. Password must be more than 4 characters. ")
            return;
        } else {
            setPasswordError(null);
        }
        setPassword(password);
    }
    const updateRepassword = (repassword) => {
        if (password !== repassword) {
            setRepasswordError("Re-Password doesn't match password ");
            return;
        } else {
            setRepasswordError(null);
        }
        setRePassword(repassword);
    }
    const handleSignUp = (e) => {
        e.preventDefault();
        if (nameError || passwordError || repasswordError) {
            return;
        }
        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({name: name, password: password})
        };
        fetch('/api/user/', requestOptions)
            .then(response => {
                return response.json()
            })
            .then(data => {
                sessionStorage.setItem('currentUser', name);
                sessionStorage.setItem('currentUserID', data.id);
                history.push('/home');
            });
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <div className={classes.paper}>
                <img src={logoTransparentBackground} alt={""} width="150"/>
                <Typography component="h1" variant="h5">
                    Sign up
                </Typography>
                <form className={classes.form} noValidate onSubmit={handleSignUp}>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="username"
                        label="Username"
                        name="username"
                        autoComplete="username"
                        autoFocus
                        onBlur={e => updateUsername(e.target.value)}
                        {...(nameError && {error: true, helperText: nameError})}
                    />
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="new-password"
                        onBlur={e => updatePassword(e.target.value)}
                        {...(passwordError && {error: true, helperText: passwordError})}
                    />
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        name="repassword"
                        label="Re-Password"
                        type="password"
                        id="repassword"
                        autoComplete="new-password"
                        onBlur={e => updateRepassword(e.target.value)}
                        {...(repasswordError && {error: true, helperText: repasswordError})}
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                    >
                        Sign Up
                    </Button>
                </form>
            </div>
            <Box mt={8}>
                <Copyright/>
            </Box>
        </Container>
    );
}
