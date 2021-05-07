import React from 'react'
import { Route, Switch } from 'react-router-dom'
import SignIn from './pages/SignIn'
import SignUp from './pages/SignUp'
import SearchResult from './pages/SearchResult'
import HomePage from './pages/HomePage'
import FriendsPage from './pages/FriendsPage'
import BookShelvesPage from './pages/BookShelvesPage'
import PrivateRoute from "./components/PrivateRoute";
import PersonalTimeline from "./pages/PersonalTimeline";

import './App.css';


function App() {
  return (
    <Switch>
      <Route exact path="/" component={SignIn} />
      <Route exact path="/SignUp" component={SignUp} />
      <PrivateRoute path="/SearchResult" component={SearchResult} />
      <PrivateRoute exact path="/home" component={HomePage} />
      <PrivateRoute exact path="/friends" component={FriendsPage} />
      <PrivateRoute exact path="/bookshelves" component={BookShelvesPage} />
      <PrivateRoute exact path="/personalTimeline" component={PersonalTimeline} />
    </Switch>
  );
}

export default App;
