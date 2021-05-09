/* eslint-disable no-use-before-define */
/*reference https://www.youtube.com/watch?v=LGcgChoD_qY*/
import React, {useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import axios from 'axios';
import Layout from '../components/Layout'
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import {
    useLocation
} from "react-router-dom";

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

  const [result, setResult] = useState([]);
  const apiKey = "AIzaSyAu1E-pEKMYEw14bjqcdQDsEybKHIaZfaY";
  const maxResult = 10;
  let query = new URLSearchParams(useLocation().search);
  let book = query.get("keyword");

  useEffect(() => {
    axios.get("https://www.googleapis.com/books/v1/volumes?q="+book+"&key="+apiKey+"&maxResults="+maxResult)
    .then(res => {
        setResult(res.data.items);
        console.log(res.data.items);
        console.log(book);
    });
  }, [])

  function handleAddBook(book, event) {
      event.preventDefault();
      console.log(book.volumeInfo.title);
      let firstAuthor = "";
      if (book.volumeInfo.authors) {
          firstAuthor = book.volumeInfo.authors[0]
      }

      const requestOptions = {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ googleBookId: book.id, title: book.volumeInfo.title,
          author: firstAuthor, totalPage: parseInt(book.volumeInfo.pageCount), kind: book.kind})
      };
      fetch('/api/book', requestOptions)
          .then(response => {
              return response.json()
          })
          .then(data => {
              console.log(data)
          });
  }

  return (
    <Layout>
      <div className={classes.root}>

        <GridList cellHeight='auto' cols={7} spacing={20} className={classes.gridList}>
        {result.map((book) => (
          <GridListTile key={book.volumeInfo.imageLinks.thumbnail}>
              <a href={"/book/"+book.id}>
                <img src={book.volumeInfo.imageLinks.thumbnail} alt={book.volumeInfo.title} />
              </a>
            <GridListTileBar
              title={book.volumeInfo.title}
              subtitle={<span>author: {book.volumeInfo.authors[0]}</span>}
            />
          </GridListTile>
        ))}
        </GridList>
      </div>
    </Layout>
  );
}