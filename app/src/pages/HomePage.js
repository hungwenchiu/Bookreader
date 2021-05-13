import React, {useState, useEffect} from 'react';
import Layout from '../components/Layout'
import {makeStyles} from '@material-ui/core/styles';
import axios from 'axios';
import Typography from '@material-ui/core/Typography';
import RecommendList from '../components/RecommendList'
import {initiateSocket, sendMessage, socket} from "../components/Socketio";

const useStyles = makeStyles({
  container: {
    marginLeft: '15%',
    marginRight: '15%',
    marginTop: '1%',
  },
})

export default function HomePage() {
  const classes = useStyles()
  const currentUserId = sessionStorage.getItem("currentUserID");

  useEffect(() => {
    initiateSocket(currentUserId);
    sendMessage("newComer", currentUserId);
  }, []);

  return (
    <Layout>
      <div className={classes.container}>
        <Typography variant="h4">Top Want to Read</Typography>
        <RecommendList type="wantToRead" />
        <Typography variant="h4">Top Favorite</Typography>
        <RecommendList type="favorite" />
        <Typography variant="h4">Top Reading</Typography>
        <RecommendList type="reading" />
        <Typography variant="h4">Top Read</Typography>
        <RecommendList type="read" />
      </div>

    </Layout> 
  )
}


