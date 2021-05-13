import React, {useEffect, useState} from 'react';
import Layout from '../components/Layout'
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import {
    Avatar,
    Collapse,
    IconButton,
    ListItemAvatar,
    ListItemSecondaryAction,
    ListSubheader
} from "@material-ui/core";
import axios from "axios";
import Container from "@material-ui/core/Container";
import AddIcon from '@material-ui/icons/Add';
import Button from "@material-ui/core/Button";
import Box from "@material-ui/core/Box";
import {makeStyles} from "@material-ui/core/styles";
import {blue} from "@material-ui/core/colors";


const useStyles = makeStyles((theme) => ({
    avatar: {
        backgroundColor: blue[500],
    },
}));

const FriendsPage = () => {
    const classes = useStyles();
    const [incomingRequests, setIncomingRequests] = useState([])
    const [allFriends, setAllFriends] = useState([])
    const [notFriends, setNotFriends] = useState([])
    const [update, setUpdate] = useState(false)
    const currentUserId = sessionStorage.getItem('currentUserID');
    const currentUser = {id: sessionStorage.getItem('currentUserID'), name: sessionStorage.getItem('currentUser')}

    useEffect(() => {
        axios.get(`/api/relationship/incoming/${currentUserId}`)
            .then(res => {
                setIncomingRequests(res.data);
            });
        axios.get(`/api/relationship/friends/${currentUserId}`)
            .then(res => {
                setAllFriends(res.data);
            });
        axios.get(`/api/relationship/none/${currentUserId}`)
            .then(res => {
                setNotFriends(res.data);
            });
    }, [update]);

    function handleAddFriend(toUser) {
        const data = [currentUser, toUser]
        axios.post(`/api/relationship`, data)
            .then(res => {
                setUpdate(!update)
            });
        postEvent(currentUser.name, toUser.name, currentUser.id);
        postEvent(currentUser.name, toUser.name, toUser.id);
    }

    function handleAcceptFriend(fromUser, toUser) {
        const data = [fromUser, toUser]
        axios.post(`/api/relationship/accept`, data)
            .then(res => {
                setUpdate(!update)
            });
    }

    function handleDeleteFriendRequest(fromUser, toUser) {
        const data = [fromUser, toUser]
        axios.delete(`/api/relationship`, {data: data})
            .then(res => {
                setUpdate(!update)
            });
    }

    // post friendship event!!
    const postEvent = (currentUsername, toUsername, userid) => {

        const params = new URLSearchParams();
        params.append("userid", userid);
        params.append("name", currentUsername);
        params.append("bookName", null);
        params.append("action", "Friendship");
        params.append("content", `${currentUsername} and ${toUsername} become friends.`);
        params.append("googlebookid", null);

        axios.post(`/api/event`, params
        )
            .then(res => {
                console.log("post event - friendship success");
            });
    }

    return (
      <Layout>
        <Container maxWidth="sm">
            <List
                subheader={
                <ListSubheader component="div" id="incoming-request">
                    Incoming Requests
                </ListSubheader>
            }>
                {incomingRequests.map(user => (
                    <ListItem key={user.name}>
                        <ListItemAvatar>
                            <Avatar className={classes.avatar}>
                                {user.name.charAt(0).toUpperCase()}
                            </Avatar>
                        </ListItemAvatar>
                        <ListItemText primary={user.name} />
                        <ListItemSecondaryAction>

                            <Button variant="contained" color="primary" onClick={() => {handleAcceptFriend(user, currentUser)}}>
                                Accept
                            </Button>
                            <Box component="span" m={1} />
                            <Button variant="contained" color="secondary" onClick={() => handleDeleteFriendRequest(user, currentUser)}>
                                Delete
                            </Button>

                        </ListItemSecondaryAction>
                    </ListItem>
                ))}
            </List>

            <Collapse in={true} timeout="auto" unmountOnExit>
            <List
                subheader={
                    <ListSubheader component="div" id="all-friends">
                        Friends
                    </ListSubheader>
                }>
                {allFriends.map(user => (
                    <ListItem key={user.name}>
                        <ListItemAvatar>
                            <Avatar className={classes.avatar}>
                                {user.name.charAt(0).toUpperCase()}
                            </Avatar>
                        </ListItemAvatar>
                        <ListItemText primary={user.name} />
                    </ListItem>
                ))}
            </List>
            </Collapse>

            <List
                subheader={
                    <ListSubheader component="div" id="all-users">
                        Recommending Users
                    </ListSubheader>
                }>
                {notFriends.map(user => (
                    <ListItem key={user.name}>
                        <ListItemAvatar>
                            <Avatar className={classes.avatar}>
                                {user.name.charAt(0).toUpperCase()}
                            </Avatar>
                        </ListItemAvatar>
                        <ListItemText primary={user.name} />
                        <ListItemSecondaryAction>
                            <IconButton aria-label="Add" onClick={() => handleAddFriend(user)}>
                                <AddIcon />
                            </IconButton>
                        </ListItemSecondaryAction>
                    </ListItem>
                ))}
            </List>
        </Container>
      </Layout>
    )
}

export default FriendsPage;