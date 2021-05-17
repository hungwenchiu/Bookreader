// list of recommended book on recommendation page
import React, {useState, useEffect} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import axios from 'axios';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import {Paper} from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
    container: {
        marginLeft: '15%',
        marginRight: '15%',
        marginTop: '1%',
    },
    gridList: {
        flexWrap: 'nowrap',
        // Promote the list into his own layer on Chrome. This cost memory but helps keeping high FPS.
        transform: 'translateZ(0)',
    },
    cover: {
        width: 160,
        height: 220,
    },
    titleBar: {
        background:
            'linear-gradient(to top, rgba(93, 173, 226, 0.7) 0%, rgba(93, 173, 226, 0.3) 70%, rgba(93, 173, 226, 0) 100%)',
    },
    papers: {
        backgroundColor: "#F0F3F4",
        padding: theme.spacing(1),
    },
}))

export default function RecommendList(Props) {
    const classes = useStyles()
    const {type} = Props
    const [books, setBooks] = useState([])

    useEffect(() => {
        axios.get(`/api/systemCount/top10/` + type)
            .then(res => {
                console.log(res.data)
                const promises = res.data.map(book => getBook(book.googleBookId))
                Promise.all(promises).then(allBooks => {
                    console.log(allBooks)
                    setBooks(allBooks)
                })
            })
    }, [])

    function getBook(bookId) {
        return axios.get(`/api/book/` + bookId).then(res => {
            return res.data
        })
    }

    return (
        <Paper elevation={3} className={classes.papers}>

            <GridList cellHeight='auto' className={classes.gridList} cols={6}>

                {books.map((book) => {
                    if (book)
                        return (
                            <GridListTile key={book.thumbnail}>
                                <a href={"/book/" + book.googleBookId}>
                                    <img src={book.thumbnail} alt={""}/>
                                </a>
                                <GridListTileBar
                                    title={book.title}
                                    subtitle={book.author}
                                    classes={{
                                        root: classes.titleBar,
                                        title: classes.title,
                                    }}
                                />
                            </GridListTile>
                        )
                })}

            </GridList>
        </Paper>

    )
}

