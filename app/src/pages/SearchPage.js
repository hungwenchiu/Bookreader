/* eslint-disable no-use-before-define */
import React, {useState} from 'react';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import axios from 'axios';

export default function FreeSoloCreateOption() {
  const [book, setBook] = useState("123");
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

  return (
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
              <img src={book.volumeInfo.imageLinks.thumbnail} alt={book.title}/>
          ))}
      </div>
  );
}