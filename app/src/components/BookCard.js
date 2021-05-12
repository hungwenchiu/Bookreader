import React, {useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Slider from '@material-ui/core/Slider';
import TextField from '@material-ui/core/TextField';

const marks = [
  {
    value: 0,
    label: '0%',
  },
  {
    value: 25,
    label: '25%',
  },
  {
    value: 50,
    label: '50%',
  },
  {
    value: 75,
    label: '75%',
  },
  {
    value: 100,
    label: '100%',
  },
]

const useStyles = makeStyles({
  root: {
    display: 'flex',
  },
  cover: {
    width: 160,
    height: 220,
  },
  content: {
    display: 'flex'
  },
  button: {
    width: 100,
    height: 50
  },
  slider: {
    marginTop: 40,
    width: 400,
  }
})

function valuetext(value) {
  return `${value}%`;
}

export default function BookCard(props) {
  const classes = useStyles()
  const { image, title, author, progress } = props;
  const altSrc = "http://books.google.com/books/content?id=ka2VUBqHiWkC&printsec=frontcover&img=1&zoom=3&edge=curl&imgtk=AFLRE71XOCtVTXTJUp_t11pB2FYbAZEcqe3SuSAnacpG4MD_1_LNl36pkNMfYj8vLPquitV_ECZ7UmhIG90TL6hdGLKvVSQ1iCi9j0oHFIViNzfWFpkiln4Zazh5urR5NKG9clTCoGD6&source=gbs_api"
  return(
    <Card className={classes.root}>
      <CardMedia
        className={classes.cover}
        image={image}
        alt={altSrc}
      />
      <div className={classes.details}>
        <CardContent className={classes.content}>
          <Grid container spacing={3} >
            <Grid item xs={10}>
              <Typography component="h5" variant="h5">
                {title}
              </Typography>
              <Typography variant="subtitle1" color="textSecondary">
                {author}
              </Typography>

              <Typography id="current-progress" gutterBottom>
                Current Progress
              </Typography>
              <Slider 
                defaultValue={progress}
                getAriaValueText={valuetext}
                aria-labelledby="current-progress"
                step={1}
                marks={marks}
                valueLabelDisplay="on"
                className={classes.slider}
              />
            </Grid>

            <Grid item xs={2} className={classes.button}>
              <Button variant="contained" color="primary">
                Remove
              </Button>
              <Button variant="contained" color="primary">
                Add to Favorite
              </Button>
              <TextField id="outlined-basic" label="Pages Finished" variant="outlined" style ={{width: '100%'}} inputStyle ={{width: '100%'}} />
            </Grid>

          </Grid>
        </CardContent>
      </div>
    </Card>
  )
}

BookCard.propTypes = {
  image: PropTypes.any.isRequired, 
  title: PropTypes.any.isRequired, 
  author: PropTypes.any.isRequired,
  progress:PropTypes.any.isRequired,
};