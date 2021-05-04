import React, {useEffect, useState} from 'react';
import Layout from '../components/Layout'
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import {
    Avatar,
    Collapse,
    Divider,
    IconButton,
    ListItemAvatar,
    ListItemSecondaryAction,
    ListSubheader
} from "@material-ui/core";
import axios from "axios";
import Container from "@material-ui/core/Container";
import AddIcon from '@material-ui/icons/Add';
import HourglassEmptyIcon from '@material-ui/icons/HourglassEmpty';
import Button from "@material-ui/core/Button";

const FriendsPage = () => {
    const [incomingRequests, setIncomingRequests] = useState([])
    const [allFriends, setAllFriends] = useState([])
    const [notFriends, setNotFriends] = useState([])
    const [update, setUpdate] = useState(false)
    const currentUser = {id: sessionStorage.getItem('currentUserID'), name: sessionStorage.getItem('currentUser')}

    useEffect(() => {
        axios.post(`/api/relationship/incoming`, currentUser)
            .then(res => {
                console.log(res.data);
                setIncomingRequests(res.data);
            });
        axios.post(`/api/relationship/friends`, currentUser)
            .then(res => {
                console.log(res.data);
                setAllFriends(res.data);
            });
        axios.post(`/api/relationship/none`, currentUser)
            .then(res => {
                console.log(res.data);
                setNotFriends(res.data);
            });
    }, [update]);

    function randomColor() {
        let hex = Math.floor(Math.random() * 0xFFFFFF);
        let color = "#" + hex.toString(16);
        return color;
    }

    function handleAddFriend(toUser) {
        const data = [currentUser, toUser]
        axios.post(`/api/relationship`, data)
            .then(res => {
                setUpdate(!update)
            });
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
                            <Avatar style={{
                                backgroundColor: randomColor()
                            }}>
                                {user.name.charAt(0).toUpperCase()}
                            </Avatar>
                        </ListItemAvatar>
                        <ListItemText primary={user.name} />
                        <ListItemSecondaryAction>
                            <Button variant="contained" color="primary" onClick={() => {console.log(user);handleAcceptFriend(user, currentUser)}}>
                                Accept
                            </Button>
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
                            <Avatar style={{
                                backgroundColor: randomColor()
                            }}>
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
                            <Avatar style={{
                                backgroundColor: randomColor()
                            }}>
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