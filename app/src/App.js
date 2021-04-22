import React from 'react'
import { Route, Switch } from 'react-router-dom'
import SignIn from './pages/SignIn'
import SignUp from './pages/SignUp'
import SearchPage from './pages/SearchPage'
import HomePage from './pages/HomePage'
import FriendsPage from './pages/FriendsPage'
import BookShelvesPage from './pages/BookShelvesPage'
import PersonalTimeline from "./pages/PersonalTimeline";

import './App.css';


function App() {
  return (
    <Switch>
      <Route exact path="/" component={SignIn} />
      <Route exact path="/SignUp" component={SignUp} />
      <Route exact path="/SearchPage" component={SearchPage} />
      <Route exact path="/home" component={HomePage} />
      <Route exact path="/friends" component={FriendsPage} />
      <Route exact path="/bookshelves" component={BookShelvesPage} />
      <Route exact path="/personalTimeline" component={PersonalTimeline} />
    </Switch>
  );
}

export default App;
