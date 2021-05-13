import React, {useState, useEffect} from 'react';
import Layout from '../components/Layout'
import {makeStyles} from '@material-ui/core/styles';
import axios from 'axios';
import Typography from '@material-ui/core/Typography';
import RecommendList from '../components/RecommendList'
import Divider from '@material-ui/core/Divider';

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
    marginBottom: 20,
  }
}))

export default function HomePage() {
  const classes = useStyles()

  return (
    <Layout>
      <div className={classes.container}>
        <Typography variant="h5" className={classes.titles}>Top Want to Read</Typography>
        <RecommendList type="wantToRead" />

        <Typography variant="h5" className={classes.titles}>Top Favorite</Typography>
        <RecommendList type="favorite" />

        <Typography variant="h5" className={classes.titles}>Top Reading</Typography>
        <RecommendList type="reading" />

        <Typography variant="h5" className={classes.titles}>Top Read</Typography>
        <RecommendList type="read" />
      </div>

    </Layout> 
  )
}


