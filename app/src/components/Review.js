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
  const altSrc = "http://books.google.com/books/content?id=ka2VUBqHiWkC&printsec=frontcover&img=1&zoom=3&edge=curl&imgtk=AFLRE71XOCtVTXTJUp_t11pB2FYbAZEcqe3SuSAnacpG4MD_1_LNl36pkNMfYj8vLPquitV_ECZ7UmhIG90TL6hdGLKvVSQ1iCi9j0oHFIViNzfWFpkiln4Zazh5urR5NKG9clTCoGD6&source=gbs_api"
  const value = 2.5
  
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