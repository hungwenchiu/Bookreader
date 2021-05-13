/* eslint-disable no-use-before-define */
/*reference https://www.youtube.com/watch?v=LGcgChoD_qY*/
import React, {useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import axios from 'axios';
import Layout from '../components/Layout'
import GridList from '@material-ui/core/GridList';
import logo from '../assets/logo.png'
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import {
    useLocation
} from "react-router-dom";
import Link from "@material-ui/core/Link";
import {Card, CardActionArea, CardActions} from "@material-ui/core";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";

const StyleSheet = makeStyles((theme) => ({
  root: {
    marginLeft: '4%',
    marginRight: '4%',
    marginTop: '2%'
  },
  gridList: {
    
  },
}))

export default function SearchResult() {
  const classes = StyleSheet()
    const altSrc = "http://books.google.com/books/content?id=ka2VUBqHiWkC&printsec=frontcover&img=1&zoom=3&edge=curl&imgtk=AFLRE71XOCtVTXTJUp_t11pB2FYbAZEcqe3SuSAnacpG4MD_1_LNl36pkNMfYj8vLPquitV_ECZ7UmhIG90TL6hdGLKvVSQ1iCi9j0oHFIViNzfWFpkiln4Zazh5urR5NKG9clTCoGD6&source=gbs_api"
  const [result, setResult] = useState([]);
  const apiKey = "AIzaSyAu1E-pEKMYEw14bjqcdQDsEybKHIaZfaY";
  const maxResult = 20;
  let query = new URLSearchParams(useLocation().search);
  let book = query.get("keyword");

  useEffect(() => {
    axios.get("https://www.googleapis.com/books/v1/volumes?q="+book+"&key="+apiKey+"&maxResults="+maxResult)
    .then(res => {
        setResult(res.data.items);
        console.log(res.data.items);
        res.data.items.map((test) => {
            console.log(test.volumeInfo);
        })
    });
  }, [book])


  // function handleAddBook(book, event) {
  //     event.preventDefault();
  //     console.log(book.volumeInfo.title);
  //     let firstAuthor = "";
  //     if (book.volumeInfo.authors) {
  //         firstAuthor = book.volumeInfo.authors[0]
  //     }

  //     let thumbnailLink = "";
  //     if (book.volumeInfo.imageLinks && book.volumeInfo.imageLinks.thumbnail) {
  //         thumbnailLink = book.volumeInfo.imageLinks.thumbnail
  //     }

  //     const requestOptions = {
  //         method: 'POST',
  //         headers: { 'Content-Type': 'application/json' },
  //         body: JSON.stringify({ googleBookId: book.id, title: book.volumeInfo.title,
  //         author: firstAuthor, totalPage: parseInt(book.volumeInfo.pageCount), kind: book.kind,
  //             thumbnail:thumbnailLink, textSnippet: book.searchInfo.textSnippet})
  //     };
  //     fetch('/api/book', requestOptions)
  //         .then(response => {
  //             return response.json()
  //         })
  //         .then(data => {
  //             console.log(data)
  //         });
  // }

  return (
    <Layout>
      <div className={classes.root}>

        <GridList cellHeight='auto' cols={7} spacing={20} className={classes.gridList}>
        {result.map((book) => (
            <Card style={{ maxWidth: 400, margin: 15 }}>
                <CardActionArea>
                    <div
                        style={{
                            display: "flex",
                            alignItem: "center",
                            justifyContent: "center"
                        }}
                    >
                        <Link href={"/book/" + book.id} color="inherit">
                            <CardMedia
                                style={{
                                    width: "auto",
                                    maxHeight: "200px"
                                }}
                                component="img"
                                image={book.volumeInfo?.imageLinks?.thumbnail ? book.volumeInfo?.imageLinks?.thumbnail : logo}
                                title={book.volumeInfo.title}
                            />
                        </Link>
                    </div>
                    <CardContent>
                        <Typography gutterBottom variant="headline" component="h2">
                            {book.volumeInfo.title}
                        </Typography>
                        <Typography component="p">
                            {<span>author: {book.volumeInfo.authors ? book.volumeInfo.authors[0] : "not available"}</span>}
                        </Typography>
                    </CardContent>
                </CardActionArea>
            </Card>
          // <GridListTile key={book.id}>
          //     <Link href={"/book/"+book.id}>
          //         <img src={book.volumeInfo?.imageLinks?.thumbnail ? book.volumeInfo?.imageLinks?.thumbnail : logo} />
          //     </Link>
          //   <GridListTileBar
          //     title={book.volumeInfo.title}
          //     subtitle={<span>author: {book.volumeInfo.authors ? book.volumeInfo.authors[0] : "not available"}</span>}
          //   />
          // </GridListTile>
        ))}
        </GridList>
      </div>
    </Layout>
  );
}