/* eslint-disable no-use-before-define */
/*reference https://www.youtube.com/watch?v=LGcgChoD_qY*/
import React, {useState, useEffect} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import axios from 'axios';
import Layout from '../components/Layout'
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import {
    useLocation
} from "react-router-dom";

import logo from "../assets/logo-small.png"

const StyleSheet = makeStyles((theme) => ({
    root: {
        marginLeft: '4%',
        marginRight: '4%',
        marginTop: '2%'
    },
    titleBar: {
        background:
            'linear-gradient(to top, rgba(0,0,0, 0.7) 0%, rgba(0,0,0, 0.3) 70%, rgba(0,0,0, 0) 100%)',
    },
}))

export default function SearchResult() {
    const classes = StyleSheet()
    let query = new URLSearchParams(useLocation().search);
    let keyword = query.get("keyword")
    const [result, setResult] = useState([]);
    const apiKey = "AIzaSyAu1E-pEKMYEw14bjqcdQDsEybKHIaZfaY";
    const maxResult = 20;

    useEffect(() => {
        axios.get("https://www.googleapis.com/books/v1/volumes?q=" + keyword + "&key=" + apiKey + "&maxResults=" + maxResult)
            .then(res => {
                setResult(res.data.items);
                console.log(res.data.items);
                console.log(keyword);
            });
    }, [keyword])

    return (
        <Layout>
            <div className={classes.root}>

                <GridList cellHeight='auto' cols={8} spacing={20} className={classes.gridList}>
                    {result.map((book) => (
                        <GridListTile key={book.id}>
                            <a href={"/book/" + book.id}>
                                <img
                                    src={book.volumeInfo.imageLinks === undefined ? logo : `${book.volumeInfo.imageLinks.thumbnail}`}
                                    alt={""}/>
                            </a>
                            <GridListTileBar
                                title={book.volumeInfo.title}
                                classes={{
                                    root: classes.titleBar,
                                }}
                                subtitle={
                                    <span>author: {book.volumeInfo.authors ? book.volumeInfo.authors[0] : "not available"}</span>}
                            />
                        </GridListTile>

                    ))}
                </GridList>
            </div>
        </Layout>
    );
}