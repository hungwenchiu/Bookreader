import React, {useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Layout from '../components/Layout'
import BookCard from '../components/BookCard'

function TabPanel(props) {
  const { children, value, index, ...other } = props;

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
  },
  slider: {
    marginTop: 40,
    width: 400,
  },
})

export default function BookShelvesPage() {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);
  const altSrc = "http://books.google.com/books/content?id=ka2VUBqHiWkC&printsec=frontcover&img=1&zoom=3&edge=curl&imgtk=AFLRE71XOCtVTXTJUp_t11pB2FYbAZEcqe3SuSAnacpG4MD_1_LNl36pkNMfYj8vLPquitV_ECZ7UmhIG90TL6hdGLKvVSQ1iCi9j0oHFIViNzfWFpkiln4Zazh5urR5NKG9clTCoGD6&source=gbs_api"

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return(
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
        <Tab label="Reading" {...a11yProps(1)} />
        <Tab label="Read" {...a11yProps(2)} />
        <Tab label="Favorite" {...a11yProps(3)} />
      </Tabs>
      <TabPanel value={value} index={0}>
        <BookCard image={altSrc} title='Book Title' author='Book Author' progress={50} />
      </TabPanel>
      <TabPanel value={value} index={1}>
        Item Two
      </TabPanel>
      <TabPanel value={value} index={2}>
        Item Three
      </TabPanel>
          
    </div>

  </Layout>
  ) 
}