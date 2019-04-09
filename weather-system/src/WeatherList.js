import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class WeatherList extends Component {

  constructor(props) {
    super(props);
    this.state = {groups: [], isLoading: true};
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/weather_list')
      .then(response => response.json())
      .then(data => this.setState({groups: data, isLoading: false}));
  }

  render() {
    const {groups, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const weatherList = groups.map(group => {
      const query_date = `${group.day || ''} ${group.month || ''} ${group.year || ''}`;
      return <tr key={group.id}>
        <td style={{whiteSpace: 'nowrap'}}>{group.config_data.city}</td>
        <td style={{whiteSpace: 'nowrap'}}>{group.config_data.country}</td>
        <td style={{whiteSpace: 'nowrap'}}>{group.config_data.site}</td>
        <td>{group.temp}</td>
        <td>{group.feel_temp}</td>
        <td>{group.status}</td>
        <td>{group.wind_data.wind_spd}</td>
        <td>{group.additional_data.humidity}</td>
        <td>{query_date}</td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <h3 align="center">Weather data full list</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="10%">City</th>
              <th width="10%">Country</th>
              <th width="10%">Site</th>
              <th width="10%">Current Temperature (C)</th>
              <th width="10%">Current Feel Temperature (C)</th>
              <th width="10%">Status</th>
              <th width="10%">Wind speed (m/s)</th>
              <th width="10%">Humidity (hPA)</th>
              <th width="20%">Date of estimation</th>
            </tr>
            </thead>
            <tbody>
            {weatherList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default WeatherList;