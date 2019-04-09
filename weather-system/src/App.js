import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import WeatherList from './WeatherList';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/weather' exact={true} component={WeatherList}/>
        </Switch>
      </Router>
    )
  }
}

export default App;
