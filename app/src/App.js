import React from 'react'
import { Route, Switch } from 'react-router-dom'
import SignIn from './pages/SignIn'
import SignUp from './pages/SignUp'

import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <Switch>
      <Route exact path="/" component={SignIn} />
      <Route exact path="/SignUp" component={SignUp} />
    </Switch>
  );
}

export default App;
