import React, { useRef, useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Layout from '../components/Layout'
import axios from 'axios';
import { useParams } from "react-router";
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import Grow from '@material-ui/core/Grow';
import Popper from '@material-ui/core/Popper';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import parse from 'html-react-parser'

const options = ['Add to Favorate', 'Add to Want to Read', 'Add to Reading', 'Add to Read'];

const StyleSheet = makeStyles({
  container: {
    marginLeft: '15%',
    marginRight: '15%',
    marginTop: '2%'
  },
  buttonGroup: {
    marginTop: '30px'
  },
})

export default function BookPage() {
  const classes = StyleSheet()
  const altSrc = "http://books.google.com/books/content?id=ka2VUBqHiWkC&printsec=frontcover&img=1&zoom=3&edge=curl&imgtk=AFLRE71XOCtVTXTJUp_t11pB2FYbAZEcqe3SuSAnacpG4MD_1_LNl36pkNMfYj8vLPquitV_ECZ7UmhIG90TL6hdGLKvVSQ1iCi9j0oHFIViNzfWFpkiln4Zazh5urR5NKG9clTCoGD6&source=gbs_api"
  const [book, setBook] = useState({})
  const [description, setDescription] = useState("")
  const [open, setOpen] = useState(false)
  const anchorRef = useRef(null);

  const [selectedIndex, setSelectedIndex] = React.useState(0);

  const apiKey = "AIzaSyAu1E-pEKMYEw14bjqcdQDsEybKHIaZfaY";
  const {id}  = useParams();

  useEffect(() => {
    axios.get(`https://www.googleapis.com/books/v1/volumes/` + id + `?key=` + apiKey)
    .then(res => {
        setBook(res.data)
        setDescription(res.data.volumeInfo.description)
    });
  }, [])

  const addToBookshelf = () => {

  }
  
  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = (event) => {
    if (anchorRef.current && anchorRef.current.contains(event.target)) {
      return;
    }

    setOpen(false);
  }

  const handleMenuItemClick = (event, index) => {
    setSelectedIndex(index);
    setOpen(false);
  }

  return(
    <Layout>
      <div className={classes.container}>
        <Grid container spacing={3} >
          <Grid item xs={3}>
            <img src={book.volumeInfo?.imageLinks.thumbnail} alt={altSrc} height="300" />
            <ButtonGroup variant="contained" color="primary" ref={anchorRef} aria-label="split button" className={classes.buttonGroup}>
              <Button onClick={addToBookshelf}>{options[selectedIndex]}</Button>
              <Button
                color="primary"
                size="small"
                aria-controls={open ? 'split-button-menu' : undefined}
                aria-expanded={open ? 'true' : undefined}
                aria-haspopup="menu"
                onClick={handleToggle}
              >
                <ArrowDropDownIcon />
              </Button>
            </ButtonGroup>

            <Popper open={open} anchorEl={anchorRef.current} role={undefined} transition disablePortal>
              {({ TransitionProps, placement }) => (
                <Grow
                  {...TransitionProps}
                  style={{
                    transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom',
                  }}
                >
                  <Paper>
                    <ClickAwayListener onClickAway={handleClose}>
                      <MenuList id="split-button-menu">
                        {options.map((option, index) => (
                          <MenuItem
                            key={option}
                            selected={index === selectedIndex}
                            onClick={(event) => handleMenuItemClick(event, index)}
                          >
                            {option}
                          </MenuItem>
                        ))}
                      </MenuList>
                    </ClickAwayListener>
                  </Paper>
                </Grow>
              )}
            </Popper>

          </Grid>
          <Grid item xs={9}>
            <Typography variant="h3" gutterBottom>
              {book.volumeInfo?.title}
            </Typography>
            <Typography variant="subtitle1" gutterBottom>
              Author: {book.volumeInfo?.authors.join(', ')}
            </Typography>
            
            <Typography variant="body1" gutterBottom>
              {parse(description)}
            </Typography>
          </Grid>
          <Grid item xs={12} >
            <Paper >xs=12 sm=6</Paper>
          </Grid>
          <Grid item xs={6}>
            <Paper >xs=6 sm=3</Paper>
          </Grid>
        </Grid>
      </div>
      
    </Layout>
  )
}