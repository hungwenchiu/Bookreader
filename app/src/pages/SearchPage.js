/* eslint-disable no-use-before-define */
/*reference https://www.youtube.com/watch?v=LGcgChoD_qY*/
import React, {useState} from 'react';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import axios from 'axios';
import Layout from '../components/Layout'

export default function FreeSoloCreateOption() {
  const [book, setBook] = useState("");
  const [result, setResult] = useState([]);
  const [apiKey, setApiKey] = useState("AIzaSyAu1E-pEKMYEw14bjqcdQDsEybKHIaZfaY");
  const maxResult = 10;

  function handleSearch(event) {
    event.preventDefault();
    axios.get("https://www.googleapis.com/books/v1/volumes?q="+book+"&key="+apiKey+"&maxResults="+maxResult)
        .then(res => {
            setResult(res.data.items);
            console.log(res.data.items);
            console.log(book);
        });
  }

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
      <div>
          <form onSubmit={handleSearch}>
          <div style={{ width: 300 }}>
            <TextField
               label="Search input"
               margin="normal"
               variant="outlined"
               autoFocus
               onChange={e => setBook(e.target.value)}
            />
            <div>
              <Button type="submit" variant="contained" color="primary" >
                Search
              </Button>
            </div>
          </div>
          </form>

        {result.map(book => (
            <li>
            <a href={book.volumeInfo.previewLink}>
              <img src={book.volumeInfo.imageLinks.thumbnail} alt={book.title}/>
            </a>
            <button onClick={(e) => handleAddBook(book, e)}>Add Book</button>
            </li>
          ))}
      </div>
    </Layout>
  );
}