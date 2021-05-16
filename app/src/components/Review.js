import React, {useEffect, useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Typography from '@material-ui/core/Typography';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import Rating from '@material-ui/lab/Rating';
import axios from 'axios'
import {blue} from "@material-ui/core/colors";

const useStyles = makeStyles({
  avatar: {
    backgroundColor: blue[500],
  },
})

export default function Review(props) {
  const {userId, content, rating} = props
  const [username, setUsername] = useState("")
  const classes = useStyles()
  
  useEffect(() => {
    // get user information
    axios.get(`/api/user/`+userId)
    .then(res => {
      console.log(res.data)
      setUsername(res.data.name)
    })
  }, [])

  return(
    <ListItem>
      
      <ListItemAvatar>
          <Avatar className={classes.avatar}>
              {username.charAt(0).toUpperCase()}
          </Avatar>
      </ListItemAvatar>
      <ListItemText
        primary={
          <Typography variant="body1">{username}</Typography>
        }
        secondary={
          <Typography variant="body2">
            {content}
          </Typography>
        }
      >
      </ListItemText>
      <Rating value={rating} precision={0.5} readOnly size="small" className={classes.rating} />

    </ListItem>
  )
}

Review.propTypes = {
  userId: PropTypes.any.isRequired,
  content: PropTypes.any.isRequired, 
  rating: PropTypes.any.isRequired,
};