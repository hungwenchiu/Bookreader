import React, {useState, useEffect} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import axios from 'axios';
import PropTypes from 'prop-types';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Layout from '../components/Layout'
import BookCard from '../components/BookCard'
import List from '@material-ui/core/List';


function TabPanel(props) {
    const {children, value, index, ...other} = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`bookshelf-tabpanel-${index}`}
            aria-labelledby={`bookshelf-tab-${index}`}
            {...other}
        >
            {value === index && (
                <div>
                    {children}
                </div>
            )}
        </div>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

function a11yProps(index) {
    return {
        id: `bookshelf-tab-${index}`,
        'aria-controls': `bookshelf-tabpanel-${index}`,
    };
}

const useStyles = makeStyles({
    container: {
        marginLeft: '15%',
        marginRight: '15%',
        marginTop: '1%',
    },
    cover: {
        width: 160,
        height: 220,
    },
    content: {
        display: 'flex'
    },
    button: {},
    slider: {
        marginTop: 40,
        width: 400,
    },
})

export default function BookShelvesPage() {
    const classes = useStyles();
    const [value, setValue] = React.useState(0);
    const [books, setBooks] = useState([])
    const [bookshelf, setBookshelfName] = useState({name: "WantToRead"});
    const [update, setUpdate] = useState(false)

    function getBooksFromBookshelf() {
        axios.get(`/api/bookshelves/${bookshelf.name}/books/info?userID=${sessionStorage.getItem("currentUserID")}`)
            .then(async res => {
                // process all books -> get the progress
                setBooks(res.data)
                console.log(res.data);
            })
            .catch(error => {
                console.log(error);
            });
    }

    useEffect(() => {
        getBooksFromBookshelf();
    }, [bookshelf.name, update])

    const handleChange = (event, newValue) => {
        setValue(newValue)
        if (newValue === 0) {
            setBookshelfName({name: "WantToRead"});
        } else if (newValue === 1) {
            setBookshelfName({name: "Reading"});
        } else if (newValue === 2) {
            setBookshelfName({name: "Read"});
        } else if (newValue === 3) {
            setBookshelfName({name: "Favorite"});
        } else {
            setBookshelfName({name: "Recommended"});
        }
    };

    async function getProgress(book) {
        try {
            const response = await axios.get(`/api/progress?userID=${sessionStorage.getItem("currentUserID")}&bookID=${book.googleBookId}`);
            const data = await response.data;
            return data;
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <Layout>
            <div className={classes.container}>
                <Tabs
                    value={value}
                    onChange={handleChange}
                    indicatorColor="primary"
                    textColor="primary"
                    centered
                >
                    <Tab label="Want to Read" {...a11yProps(0)} />
                    <Tab label="In Progress" {...a11yProps(1)} />
                    <Tab label="Complete" {...a11yProps(2)} />
                    <Tab label="Favorite" {...a11yProps(3)} />
                    <Tab label="Recommended" {...a11yProps(4)} />
                </Tabs>
                <TabPanel value={value} index={0}>
                    <List>
                        {
                            books.map((book) => (
                                <BookCard bookInfo={book}
                                          currentBookshelf={bookshelf.name}
                                          updateFunc={setUpdate}
                                          update={update}
                                />
                            ))
                        }
                    </List>
                </TabPanel>
                <TabPanel value={value} index={1}>
                    <List>
                        {
                            books.map((book) => (
                                <BookCard bookInfo={book}
                                          currentBookshelf={bookshelf.name}
                                          updateFunc={setUpdate}
                                          update={update}
                                />
                            ))
                        }
                    </List>
                </TabPanel>
                <TabPanel value={value} index={2}>
                    <List>
                        {
                            books.map((book) => (
                                <BookCard bookInfo={book}
                                          currentBookshelf={bookshelf.name}
                                          updateFunc={setUpdate}
                                          update={update}
                                />
                            ))
                        }
                    </List>
                </TabPanel>
                <TabPanel value={value} index={3}>
                    <List>
                        {
                            books.map((book) => (
                                <BookCard bookInfo={book}
                                          currentBookshelf={bookshelf.name}
                                          updateFunc={setUpdate}
                                          update={update}
                                />
                            ))
                        }
                    </List>
                </TabPanel>
                <TabPanel value={value} index={4}>
                    <List>
                        {
                            books.map((book) => (
                                <BookCard bookInfo={book}
                                          currentBookshelf={bookshelf.name}
                                          updateFunc={setUpdate}
                                          update={update}
                                />
                            ))
                        }
                    </List>
                </TabPanel>
            </div>
        </Layout>
    )
}
