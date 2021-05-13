import React, {useState, useEffect} from 'react';
import Layout from '../components/Layout'
import {makeStyles} from '@material-ui/core/styles';
import axios from 'axios';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import RecommendList from '../components/RecommendList'

const useStyles = makeStyles({
  container: {
    marginLeft: '15%',
    marginRight: '15%',
    marginTop: '1%',
  },
})

export default function HomePage() {
  const classes = useStyles()

  return (
    <Layout>
      <div className={classes.container}>
        <RecommendList type="wantToRead" />
      </div>

    </Layout> 
  )
}


