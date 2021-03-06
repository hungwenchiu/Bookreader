import React, {useState, useEffect} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Layout from '../components/Layout'
import axios from 'axios';
import {useParams} from "react-router";
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import parse from 'html-react-parser'
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import Review from '../components/Review';
import Rating from '@material-ui/lab/Rating';
import ReviewPostDialog from '../components/ReviewPostDialog';
import RecommendBook from '../components/RecommendBook'
import Button from "@material-ui/core/Button";
import FavoriteIcon from '@material-ui/icons/Favorite';
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';

const StyleSheet = makeStyles({
    container: {
        marginLeft: '15%',
        marginRight: '15%',
        marginTop: '2%'
    },
    rating: {},
    divider: {
        marginTop: 20,
        marginBottom: 20,
    },
    button: {
        marginTop: '15px',
        width: "12.5em"
    },
})

export default function BookPage() {
    const classes = StyleSheet()

    const apiKey = "AIzaSyAu1E-pEKMYEw14bjqcdQDsEybKHIaZfaY";
    const altSrc = "http://books.google.com/books/content?id=ka2VUBqHiWkC&printsec=frontcover&img=1&zoom=3&edge=curl&imgtk=AFLRE71XOCtVTXTJUp_t11pB2FYbAZEcqe3SuSAnacpG4MD_1_LNl36pkNMfYj8vLPquitV_ECZ7UmhIG90TL6hdGLKvVSQ1iCi9j0oHFIViNzfWFpkiln4Zazh5urR5NKG9clTCoGD6&source=gbs_api"

    const [book, setBook] = useState({})
    const [description, setDescription] = useState("")
    const [reviews, setReviews] = useState([])
    const [rating, setRating] = useState(0)
    const {id} = useParams()

    const [wantToRead, setWantToRead] = React.useState(false);
    const [open, setOpen] = React.useState(false);
    const [favorite, setFavorite] = React.useState(false);
    const [selectedValue, setSelectedValue] = React.useState({});
    const currentUserId = sessionStorage.getItem('currentUserID');

    useEffect(() => {

        // get book information
        axios.get(`https://www.googleapis.com/books/v1/volumes/` + id + `?key=` + apiKey)
            .then(res => {
                setBook(res.data)
                res.data.volumeInfo.description = res.data.volumeInfo.description ? res.data.volumeInfo.description : "No Description...";
                setDescription(res.data.volumeInfo.description)
                handleAddBook(res.data) // when user click the book and see the detail, I will insert the book in DB
            })

        axios.get(`/api/review/book/` + id)
            .then(res => {
                setReviews(res.data)
                const avgRating = calculateRating(res.data)
                setRating(avgRating)
            })
    }, [])

    const calculateRating = (reviews) => {
        const avgRating = reviews.reduce((total, next) => total + next.rating, 0) / reviews.length
        return avgRating
    }

    // handle open/close of recommend dialog
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = (value) => {
        setOpen(false);
        setSelectedValue(value);
    };

    // insert book to database
    function handleAddBook(book) {
        let firstAuthor = "";
        if (book.volumeInfo.authors) {
            firstAuthor = book.volumeInfo.authors[0]
        }

        let thumbnailLink = altSrc;
        if (book.volumeInfo.imageLinks && book.volumeInfo.imageLinks.thumbnail) {
            thumbnailLink = book.volumeInfo.imageLinks.thumbnail
        }

        let description = "";
        if (book.volumeInfo.description) {
            description = book.volumeInfo.description;
        }


        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                googleBookId: book.id, title: book.volumeInfo.title,
                author: firstAuthor, totalPage: parseInt(book.volumeInfo.pageCount), kind: book.kind,
                thumbnail: thumbnailLink, description: description
            })
        };
        fetch('/api/book', requestOptions)
            .then(response => {
                return response.json();
            })
            .then(data => {
                console.log(data)
            });
    }

    function addToBookshelf(bookshelfName) {
        axios.put(`/api/bookshelves/${bookshelfName}/books?bookID=${book.id}&userID=${currentUserId}`)
            .then(res => {
                console.log('Inside add to bookshelf');
            })
            .catch(error => {
                console.log(error);
            });
    }

    return (
        <Layout>
            <div className={classes.container}>
                <Grid container spacing={3}>
                    <Grid item xs={3}>
                        <img src={book.volumeInfo?.imageLinks?.thumbnail || altSrc} alt={""} width="180" height="270"/>
                        <div>
                            <br/>
                            <Button variant="contained" color="primary" onClick={handleClickOpen}>
                                Recommend Book
                            </Button>
                            <RecommendBook selectedValue={selectedValue} open={open} onClose={handleClose} book={book}/>
                        </div>
                        <Button className={classes.button}
                                variant="contained"
                                disabled={wantToRead}
                                onClick={() => {
                                    addToBookshelf("WantToRead")
                                    setWantToRead(true)
                                }}>Want to Read</Button>

                        <Button className={classes.button}
                                onClick={() => {
                                    addToBookshelf("Favorite")
                                    setFavorite(true)
                                }}>
                            {favorite ? <FavoriteIcon color="secondary" fontSize="large"/> :
                                <FavoriteBorderIcon color="secondary" fontSize="large"/>}
                        </Button>

                    </Grid>
                    <Grid item xs={9}>
                        <Typography variant="h3" gutterBottom>
                            {book.volumeInfo?.title}
                        </Typography>
                        <Typography variant="subtitle1" gutterBottom>
                            Author: {book.volumeInfo?.authors.join(', ')}
                        </Typography>
                        <Rating value={rating} precision={0.1} readOnly size="large" className={classes.rating}/>
                        <Divider className={classes.divider}/>
                        <Typography variant="body1" gutterBottom>
                            {parse(description)}
                        </Typography>

                    </Grid>
                    <Grid item xs={12}>
                        <Divider className={classes.divider}/>
                        <Typography variant="h6" className={classes.title}>
                            Reviews
                        </Typography>
                    </Grid>

                    <Grid item xs={12}>
                        <ReviewPostDialog bookInfo={book} allReviews={reviews}
                                          setReviews={setReviews}></ReviewPostDialog>

                        <List>
                            {reviews.map((review) => (
                                <Review key={review.userId} userId={review.userId} content={review.content}
                                        rating={review.rating}/>
                            ))}
                        </List>
                    </Grid>

                </Grid>
            </div>

        </Layout>
    )
}