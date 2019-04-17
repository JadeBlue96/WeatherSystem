import React, { Component } from 'react';
import { Tab, Tabs } from 'react-bootstrap';
import YearTabsRouter from './tabs/yearTabsRouter';
import MonthTabs from './tabs/monthTabs';
import axios from 'axios';

import { Container, Table } from 'reactstrap';
import AppNavbar from '../app/AppNavbar';
import { withRouter } from 'react-router-dom';
import { instanceOf } from 'prop-types';
import { withCookies, Cookies } from 'react-cookie';

class WeatherList extends Component {

  static propTypes = {
    cookies: instanceOf(Cookies).isRequired
  };

  constructor(props) {
    super(props);
    const {cookies} = props;
    this.state = {data: [], selectedYear: 2019, activeTab: 2019, csrfToken: cookies.get('XSRF-TOKEN')};
    this.getData = this.getData.bind(this);
    this.handleSelect = this.handleSelect.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if(nextProps.history.location.search){
    var search = nextProps.history.location.search;
    search = search.substring(1);
    var searchObj = JSON.parse('{"' + decodeURI(search).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g,'":"') + '"}');
    this.setState({activeTab: parseInt(searchObj.year)});
    this.setState({selectedYear: searchObj.year});
    this.setState({selectedMonth: searchObj.month});
    this.getData(this, searchObj.year, searchObj.month);
  }else{
      this.getData(this, 2019, 'All');
    }
  }

  componentDidMount() {
    this.getData(this, 2019, 'All');
    
  }

  getData(ev, year, month){
    axios.get('http://localhost:3000/api/weather/ym/'+year+'/'+month)
      .then(function(response) {
        ev.setState({data: response.data, isLoading: false});
        ev.setState({selectedYear: parseInt(year)});
        ev.setState({selectedMonth: month});
      }).catch(() => this.props.history.push('/'));


}

  handleSelect(selectedTab) {
    this.setState({
      activeTab: selectedTab,
      selectedYear: selectedTab
    });
 }

  render() {
    const {data, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const weatherList = data.map(group => {
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
        <div>
        <Tabs activeKey={this.state.activeTab} onSelect={this.handleSelect}>
          <Tab eventKey={2016} title={<YearTabsRouter year='2016' />}><MonthTabs year='2016' monthlyActiveTab={this.state.selectedMonth}/></Tab>
          <Tab eventKey={2017} title={<YearTabsRouter year='2017' />}><MonthTabs year='2017' monthlyActiveTab={this.state.selectedMonth}/></Tab>
          <Tab eventKey={2018} title={<YearTabsRouter year='2018'/>}><MonthTabs year='2018' monthlyActiveTab={this.state.selectedMonth}/></Tab>
          <Tab eventKey={2019} title={<YearTabsRouter year='2019'/>}><MonthTabs year='2019' monthlyActiveTab={this.state.selectedMonth}/></Tab>
        </Tabs>
        </div>
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

export default withCookies(withRouter(WeatherList));