import React from 'react';
import {Button, Toolbar, AppBar, Typography, Link} from '@material-ui/core';
import {makeStyles} from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import logo from '../assets/logo.png'
import SearchBar from './SearchBar.js'
import {useHistory} from "react-router-dom";
import TimelineMenu from "./TimeLine";

const StyleSheet = makeStyles((theme) => ({
    root: {
        '& > * + *': {
            marginLeft: theme.spacing(2),
        },
    },
    logo: {
        marginRight: theme.spacing(3),
    },
    navs: {
        flexGrow: 1,
    },
    appBar: {
        background: "#282f44"
    },
    buttons: {
        textTransform: "none"
    }
}))

export default function Layout(props) {
    const {children} = props
    let history = useHistory();
    const classes = StyleSheet()
    const logout = () => {
        sessionStorage.removeItem('currentUser');
        sessionStorage.removeItem('currentUserID');
        history.push('/');
    }

    return (
        <div>
            <AppBar position="fixed" className={classes.appBar}>
                <Toolbar>
                    <img src={logo} alt={""} width="70" className={classes.logo}/>

                    <div className={classes.navs}>
                        <Typography className={classes.root}>
                            <Link href="/home" color="inherit" underline="none">Recommendation</Link>
                            <Link href="/bookshelves" color="inherit" underline="none">Bookshelves</Link>
                            <Link href="/friends" color="inherit" underline="none">Friends</Link>
                        </Typography>

                    </div>
                    <SearchBar/>
                    <TimelineMenu/> {/* For timeline*/}
                    <Button onClick={logout} color="inherit" className={classes.buttons}>Sign Out</Button>
                </Toolbar>
            </AppBar>
            <Toolbar/>
            <div>
                {children}
            </div>
        </div>

    )
}

Layout.propTypes = {
    children: PropTypes.node.isRequired,
}