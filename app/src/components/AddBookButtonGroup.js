import React, { useRef, useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import Popper from '@material-ui/core/Popper';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import Grow from '@material-ui/core/Grow';
import Paper from '@material-ui/core/Paper';
import axios from 'axios';

const options = ['Want to Read', 'Add to Favorite'];
const bookshelves = ['WantToRead', 'Favorite'];

const useStyles = makeStyles({
  buttonGroup: {
    marginTop: '30px'
  },
})

export default function AddBookButtonGroup(props) {
  const classes = useStyles()
  const { bookID } = props;
  const [open, setOpen] = useState(false)
  const anchorRef = useRef(null);

  const [selectedIndex, setSelectedIndex] = React.useState(0);

  const addToBookshelf = () => {
    console.log("add to bookshelf")
    const bookshelfName = bookshelves[selectedIndex]
    axios.put(`/api/bookshelves/${bookshelfName}/books?bookID=${bookID}&userID=${sessionStorage.getItem("currentUserID")}`)
    .then(res =>{
        console.log('Inside add book');
        console.log(res);
    })
    .catch( error => {
        console.log(error);
    });
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
    <div>
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
    </div>
    
  )
}
