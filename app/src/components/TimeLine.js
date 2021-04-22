import React from 'react';
import Button from '@material-ui/core/Button';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import AccountBoxIcon from "@material-ui/icons/AccountBox";
import {classes} from "istanbul-lib-coverage";
import {makeStyles} from "@material-ui/core";
import Link from "@material-ui/core/Link";
import * as PropTypes from "prop-types";

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
    },
    paper: {
        marginRight: theme.spacing(2),
        marginTop: '50px'
    },
}));

export default function TimelineMenu() {

    const classes = useStyles();

    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };


    return (
        <div className={classes.root}>
            <Button color="inherit" aria-controls="simple-menu" aria-haspopup="true"  onClick={handleClick}>
                <AccountBoxIcon fontSize={"large"}/>
            </Button>
            <Menu
                className={classes.paper}
                id="simple-menu"
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={handleClose}
            >
                <MenuItem onClick={handleClose}><Link href="/personalTimeline" color="inherit" underline="none">Personal Timeline</Link></MenuItem>
                <MenuItem onClick={handleClose}>Public Timeline</MenuItem>
            </Menu>
        </div>
    );
}
