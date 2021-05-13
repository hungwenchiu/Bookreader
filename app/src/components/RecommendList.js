import React, {useState, useEffect} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import axios from 'axios';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';

const useStyles = makeStyles({
  container: {
    marginLeft: '15%',
    marginRight: '15%',
    marginTop: '1%',
  },
  gridList: {
    flexWrap: 'nowrap',
    // Promote the list into his own layer on Chrome. This cost memory but helps keeping high FPS.
    transform: 'translateZ(0)',
  },
  cover: {
    width: 160,
    height: 220,
  },
})

export default function RecommendList(Props) {
  const classes = useStyles() 
  const { type } = Props
  const [bookIds, setBookIds] = useState([])

  useEffect(() => {
    axios.get(`/api/systemCount/top10/`+type)
    .then(res => {
      setBookIds(res.data)
      console.log(res.data)
    })
  }, [])

  return(
    <GridList className={classes.gridList} cols={2.5}>
      {/* {books.map((book) => (
        <GridListTile key={tile.img}>
          <img src={tile.img} alt={tile.title} />
          <GridListTileBar
            title={tile.title}
            classes={{
              root: classes.titleBar,
              title: classes.title,
            }}
            actionIcon={
              <IconButton aria-label={`star ${tile.title}`}>
                <StarBorderIcon className={classes.title} />
              </IconButton>
            }
          />
        </GridListTile>
      ))} */}
    </GridList>
  )
}

