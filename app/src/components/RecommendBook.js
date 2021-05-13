import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import {makeStyles} from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Avatar from '@material-ui/core/Avatar';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemText from '@material-ui/core/ListItemText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import PersonIcon from '@material-ui/icons/Person';
import AddIcon from '@material-ui/icons/Add';
import Typography from '@material-ui/core/Typography';
import {blue} from '@material-ui/core/colors';
import axios from "axios";

// const users = ['username@gmail.com', 'user02@gmail.com'];
// var friends = [];

const useStyles = makeStyles({
    avatar: {
        backgroundColor: blue[100],
        color: blue[600],
    },
});

export default function RecommendBook(props) {
    const classes = useStyles();
    const {onClose, selectedValue, open, book} = props;
    const currentUserId = sessionStorage.getItem('currentUserID');
    const [allFriends, setAllFriends] = React.useState([]);


    useEffect(() => {
        // fetch all friends for a user
        axios.get(`/api/relationship/friends/${currentUserId}`)
            .then(res => {
                setAllFriends(res.data);
            });
    }, [])

    const handleClose = () => {
        onClose(selectedValue);
    };

    const handleListItemClick = (friend) => {
    const params = new URLSearchParams();
    params.append("recommenderID", sessionStorage.getItem("currentUserID"));
    params.append("userID", friend.id);
    params.append("bookID", book.id);
    axios.put(`/bookshelves/recommended/books`, params)
            .then(res => {
                console.log(res.data);
            });
        onClose(friend);
    };

    // findFriends();
    return (
        <Dialog onClose={handleClose} aria-labelledby="simple-dialog-title" open={open}>
            <DialogTitle id="simple-dialog-title">Recommend Book to a Friend</DialogTitle>
            <List>
                {allFriends.map((friend) => (
                    <ListItem button onClick={() => handleListItemClick(friend)} key={friend}>
                        <ListItemAvatar>
                            <Avatar className={classes.avatar}>
                                <PersonIcon/>
                            </Avatar>
                        </ListItemAvatar>
                        <ListItemText primary={friend.name}/>
                    </ListItem>
                ))}
            </List>
        </Dialog>
    );
}

RecommendBook.propTypes = {
    onClose: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired,
    selectedValue: PropTypes.string.isRequired,
};

// export default function SimpleDialogDemo() {
//   const [open, setOpen] = React.useState(false);
//   const [selectedValue, setSelectedValue] = React.useState(friends[0]);
//   const [allFriends, setAllFriends ] = React.useState([]);
//   const currentUserId = sessionStorage.getItem('currentUserID');
//
//   const handleClickOpen = () => {
//     setOpen(true);
//   };
//
//   const handleClose = (value) => {
//     setOpen(false);
//     setSelectedValue(value);
//   };
//
//   return (
//     <div>
//       <br />
//       <Button variant="outlined" color="primary" onClick={handleClickOpen}>
//         Recommend Book
//       </Button>
//       <SimpleDialog selectedValue={selectedValue} open={open} onClose={handleClose} />
//     </div>
//   );
// }
