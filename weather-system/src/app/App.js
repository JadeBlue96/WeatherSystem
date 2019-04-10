import React, { Component } from 'react';
import '../css/App.css';
import Home from '../weather/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import WeatherList from '../weather/WeatherList';
import { CookiesProvider } from 'react-cookie';

class App extends Component {
  render() {
    return (
      <CookiesProvider>
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/weather' exact={true} component={WeatherList}/>
        </Switch>
      </Router>
      </CookiesProvider>
    )
  }
}

export default App;
