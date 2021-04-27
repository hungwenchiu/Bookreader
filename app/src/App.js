import React from 'react'
import { Route, Switch } from 'react-router-dom'
import SignIn from './pages/SignIn'
import SignUp from './pages/SignUp'
import SearchResult from './pages/SearchResult'
import HomePage from './pages/HomePage'
import FriendsPage from './pages/FriendsPage'
import BookShelvesPage from './pages/BookShelvesPage'
import BookPage from './pages/BookPage'
import PrivateRoute from "./components/PrivateRoute";

import './App.css';

function App() {
  return (
    <Switch>
      <Route exact path="/" component={SignIn} />
      <Route exact path="/signup" component={SignUp} />
      <PrivateRoute path="/book/:id" component={BookPage} />
      <PrivateRoute path="/searchresult" component={SearchResult} />
      <PrivateRoute exact path="/home" component={HomePage} />
      <PrivateRoute exact path="/friends" component={FriendsPage} />
      <PrivateRoute exact path="/bookshelves" component={BookShelvesPage} />
    </Switch>
  );
}

export default App;
