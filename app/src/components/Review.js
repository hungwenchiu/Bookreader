import React, {useEffect, useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import DefaultAvatar from '../assets/default_avatar.png'
import ListItem from '@material-ui/core/ListItem';
import Divider from '@material-ui/core/Divider';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import Rating from '@material-ui/lab/Rating';

const useStyles = makeStyles({
  
})

export default function Review(props) {
  const {userId, content, rating} = props
  const [username, setUsername] = useState("username")
  const classes = useStyles()
  const altSrc = "http://books.google.com/books/content?id=ka2VUBqHiWkC&printsec=frontcover&img=1&zoom=3&edge=curl&imgtk=AFLRE71XOCtVTXTJUp_t11pB2FYbAZEcqe3SuSAnacpG4MD_1_LNl36pkNMfYj8vLPquitV_ECZ7UmhIG90TL6hdGLKvVSQ1iCi9j0oHFIViNzfWFpkiln4Zazh5urR5NKG9clTCoGD6&source=gbs_api"
  const value = 2.5
  
  // useEffect(() => {
  //   // get user information
  //   axios.get(`/api/user/`+id)
  //   .then(res => {
  //     setUsername(res.data.name)
  //   })
  // }, [])

  return(
    <ListItem>
      <ListItemAvatar>
        <Avatar alt={DefaultAvatar} src={DefaultAvatar} />
      </ListItemAvatar>
      <ListItemText
        primary={
          <div>
            <Typography variant="body1">{username}</Typography>
            <Rating value={rating} precision={0.5} readOnly size="small" className={classes.rating} />
          </div>
        }
        secondary={
          <Typography component="span" variant="body2">
            {content}
          </Typography>
        }
      />
    </ListItem>
  )
}

Review.propTypes = {
  userId: PropTypes.any.isRequired,
  content: PropTypes.any.isRequired, 
  rating: PropTypes.any.isRequired,
};