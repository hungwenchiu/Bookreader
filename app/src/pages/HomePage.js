import React, {useState, useEffect} from 'react';
import Layout from '../components/Layout'
import {makeStyles} from '@material-ui/core/styles';
import axios from 'axios';
import Typography from '@material-ui/core/Typography';
import RecommendList from '../components/RecommendList'
import {initiateSocket, sendMessage, socket} from "../components/Socketio";

const useStyles = makeStyles((theme) => ({
  container: {
    marginLeft: '15%',
    marginRight: '15%',
  },
  divider: {
    marginTop: 20,
    marginBottom: 20,
  },
  titles: {
    marginTop: 20,
  },
  subtitles:{
    marginBottom: 20,
  }
}))

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
        <Typography variant="h5" className={classes.titles}>Now Trending</Typography>
        <Typography variant="subtitle1" color="textSecondary" className={classes.subtitles}>See what's trending now</Typography>

        <RecommendList type="reading" />

        <Typography variant="h5" className={classes.titles}>Everyone's Favorite</Typography>
        <Typography variant="subtitle1" color="textSecondary" className={classes.subtitles}>Explore from the favorite collection</Typography>

        <RecommendList type="favorite" />

        <Typography variant="h5" className={classes.titles}>Most Read</Typography>
        <Typography variant="subtitle1" color="textSecondary" className={classes.subtitles}>See what others have read</Typography>

        <RecommendList type="read" />

        <Typography variant="h5" className={classes.titles}>Explore More</Typography>
        <Typography variant="subtitle1" color="textSecondary" className={classes.subtitles}>See what others want to read</Typography>

        <RecommendList type="wantToRead" />
      </div>

    </Layout> 
  )
}


