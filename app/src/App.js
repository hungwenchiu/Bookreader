import React from 'react'
import { Route, Switch } from 'react-router-dom'
import SignIn from './pages/SignIn'
import SignUp from './pages/SignUp'
import SearchPage from './pages/SearchPage'

import './App.css';

function App() {
  return (
    <Switch>
      <Route exact path="/" component={SignIn} />
      <Route exact path="/SignUp" component={SignUp} />
      <Route exact path="/SearchPage" component={SearchPage} />
    </Switch>
  );
}

export default App;
