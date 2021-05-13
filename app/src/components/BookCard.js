import React, {useState, useEffect} from 'react';
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
import Box from "@material-ui/core/Box";
import axios from 'axios';

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
    marginTop: "1.5em",
    paddingTop: "1em",
    paddingBottom: "1em",
    marginBottom: "1.5em",
    justifyContent: "center",
  },
  cover: {
    display: "inline-block",
    width: 160,
    height: 220,
    marginRight: "2em"
  },
  content: {
    display: 'flex'
  },
  buttons: {
    width: 100,
    height: 50
  },
  button: {
    marginTop: 5,
    marginBottom: 5,
    marginLeft:40,
    width: "13em"
  },
  slider: {
    marginTop: 40,
    width: 400,
  },
  description: {
    flexGrow: 1,
    textAlign: 'center'
  }
})

function valuetext(value) {
  return `${value}%`;
}

export default function BookCard(props) {
  const classes = useStyles()
  const { bookInfo, currentBookshelf, updateFunc, update} = props;

  const [progressNum, setProgressNum] = useState(bookInfo.progress)
  console.log(bookInfo)


  const altSrc = "http://books.google.com/books/content?id=ka2VUBqHiWkC&printsec=frontcover&img=1&zoom=3&edge=curl&imgtk=AFLRE71XOCtVTXTJUp_t11pB2FYbAZEcqe3SuSAnacpG4MD_1_LNl36pkNMfYj8vLPquitV_ECZ7UmhIG90TL6hdGLKvVSQ1iCi9j0oHFIViNzfWFpkiln4Zazh5urR5NKG9clTCoGD6&source=gbs_api"

  const moveToRead = () => {
      // move book to different bookshelf
      const moveBookParams = new URLSearchParams();
      moveBookParams.append("userID", sessionStorage.getItem("currentUserID"));
      moveBookParams.append("bookID", bookInfo.book.googleBookId);
      moveBookParams.append("newBookshelf", "Reading");

      axios.put('/api/bookshelves/' + currentBookshelf, moveBookParams)
      .then(res => {
        console.log("Moved to Reading successfully.");
      })
      props.updateFunc(!update);
  }

  const addToFavorite = () => {
    // move book to different bookshelf
    const moveBookParams = new URLSearchParams();
    moveBookParams.append("userID", sessionStorage.getItem("currentUserID"));
    moveBookParams.append("bookID", bookInfo.book.googleBookId);

    axios.put('/api/bookshelves/Favorite/books', moveBookParams)
        .then(res => {
          console.log("Moved to Favorite successfully.");
        })
    props.updateFunc(!update);
  }

  const removeFromBookshelf = () => {
    // move book to different bookshelf
    const moveBookParams = new URLSearchParams();
    moveBookParams.append("userID", sessionStorage.getItem("currentUserID"));
    moveBookParams.append("bookID", bookInfo.book.googleBookId);

    axios.delete('/api/bookshelves/' + currentBookshelf, {data: moveBookParams})
        .then(res => {
          console.log("remove book from bookshelf successfully.");
        })
    props.updateFunc(!update);
  }

  const handleChange = (event, newValue) => {
    if (event.key === 'Enter') {
        // update the progress
        const progressParams = new URLSearchParams();
        progressParams.append("userID", sessionStorage.getItem("currentUserID"));
        progressParams.append("bookID", bookInfo.book.googleBookId);
        progressParams.append("pagesFinished",event.target.value);

        axios.put(`/api/progress`, progressParams)
        .then(res => {
          console.log("Updated pages finished "+res.data);
        });

        // // move book to different bookshelf
        // const moveBookParams = new URLSearchParams();
        // moveBookParams.append("userID", sessionStorage.getItem("currentUserID"));
        // moveBookParams.append("bookID", bookInfo.book.googleBookId);

        // axios.put(`/api/bookshelves`, progressParams
        // )
        // .then(res => {
        //   console.log("Updated pages finished");
        // })
    }
  }

  return(
      <div key={bookInfo.book.googleBookId}>
        <Card className={classes.root}>
          <CardMedia
            className={classes.cover}
            image={bookInfo.book.thumbnail}
            alt={altSrc}
          />
          <div className={classes.details}>
            <CardContent className={classes.content}>
              <Grid container >
                <Grid item xs={10}>
                  <Typography component="h5" variant="h5">
                    {bookInfo.book.title}
                  </Typography>
                  <Typography variant="subtitle1" color="textSecondary">
                    {bookInfo.book.author ? bookInfo.book.author : "not available"}
                  </Typography>

                  <Box m={3} />
                  {
                    currentBookshelf == "Reading" &&
                    (<Slider
                        defaultValue={bookInfo.progress}
                        getAriaValueText={valuetext}
                        aria-labelledby="current-progress"
                        step={1}
                        marks={marks}
                        valueLabelDisplay="on"
                        className={classes.slider}
                        disabled={true}
                    />)
                  }
                  {
                    currentBookshelf == "Reading" &&
                    <Typography id="current-progress" color="textSecondary" className={classes.description}>
                      Current Progress
                    </Typography>
                  }
                </Grid>

                <Grid item xs={2} className={classes.buttons}>

                  {
                    currentBookshelf != "Favorite" && !bookInfo.isFavorite &&
                    <Button variant="contained" color="primary" className={classes.button} onClick={addToFavorite}>Favorite</Button>
                  }

                  {
                    currentBookshelf != "Reading" && !bookInfo.isReading &&
                    <Button variant="contained" color="primary" className={classes.button} onClick={moveToRead}>Start Reading</Button>
                  }

                  <Button variant="contained" className={classes.button} onClick={removeFromBookshelf}>
                    Remove
                  </Button>
                  {
                    currentBookshelf == "Reading" &&
                    <TextField id="outlined-basic" label="Pages Finished" variant="outlined" className={classes.button}
                               style ={{width: '11em'}} onKeyDown={handleChange}/>
                  }
                </Grid>

              </Grid>
            </CardContent>
          </div>
        </Card>
      </div>
  )
}

BookCard.propTypes = {
  bookInfo: PropTypes.any.isRequired,
  currentBookshelf: PropTypes.any.isRequired,
  updateFunc: PropTypes.any.isRequired,
  update: PropTypes.any.isRequired,
};