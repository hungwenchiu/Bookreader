import React from 'react';
import {Button, Toolbar, AppBar, Typography, Link, InputBase} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
// import MenuIcon from '@material-ui/icons/Menu';
import PropTypes from 'prop-types';
import logo from './logo.png'
import SearchBar from './SearchBar.js'

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
  appBar:{
    background: "#282f44"
  },
  buttons: {
    textTransform: "none"
  }
}))

const Layout = ({ children }) => {
  const classes = StyleSheet()
  
  return(
    <div>
      <AppBar position="fixed" className={classes.appBar}>
        <Toolbar>
          <img src={logo} width="70" className={classes.logo}/>

          <div className={classes.navs}>
            <Typography className={classes.root}>
              <Link href="/home" color="inherit" underline="none">Recommendations</Link>
              <Link href="/bookshelves" color="inherit" underline="none">BookShelves</Link>
              <Link href="/friends" color="inherit" underline="none">Friends</Link>
              <Link href="/searchPage" color="inherit" underline="none">Search</Link>
            </Typography>

          </div>
          <SearchBar />

          <Button color="inherit" className={classes.buttons}>Sign Out</Button>
        </Toolbar>
      </AppBar>
      <Toolbar />
      <div>
        {children}
      </div>
    </div>
  )
}


Layout.porpTypes = {
  children: PropTypes.node.isRequired,
}

export default Layout;