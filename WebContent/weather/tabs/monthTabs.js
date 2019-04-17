import React from 'react';
import { Tab, Tabs } from 'react-bootstrap'
import MonthTabsRouter from './monthTabsRouter.js'

class MonthTabs extends React.Component {
 constructor(){
  super();
  this.state = {activeTab:''};
  this.handleSelect = this.handleSelect.bind(this);
}
componentWillReceiveProps(nextProps) {
    this.setState({activeTab:this.props.year+'-'+nextProps.monthlyActiveTab});
  }
handleSelect(selectedTab) {
     this.setState({
       activeTab: selectedTab
     });
 }
render(){
  return <Tabs activeKey={this.state.activeTab} onSelect={this.handleSelect}>
            <Tab eventKey={this.props.year+'-All'} title={<MonthTabsRouter tabId='All' year={this.props.year} />}></Tab>
            <Tab eventKey={this.props.year+'-JANUARY'} title={<MonthTabsRouter tabId='JANUARY' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-FEBRUARY'} title={<MonthTabsRouter tabId='FEBRUARY' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-MARCH'} title={<MonthTabsRouter tabId='MARCH' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-APRIL'} title={<MonthTabsRouter tabId='APRIL' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-MAY'} title={<MonthTabsRouter tabId='MAY' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-JUNE'} title={<MonthTabsRouter tabId='JUNE' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-JULY'} title={<MonthTabsRouter tabId='JULY' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-AUGUST'} title={<MonthTabsRouter tabId='AUGUST' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-SEPTEMBER'} title={<MonthTabsRouter tabId='SEPTEMBER' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-OCTOBER'} title={<MonthTabsRouter tabId='OCTOBER' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-NOVEMBER'} title={<MonthTabsRouter tabId='NOVEMBER' year={this.props.year}/>}></Tab>
            <Tab eventKey={this.props.year+'-DECEMBER'} title={<MonthTabsRouter tabId='DECEMBER' year={this.props.year}/>}></Tab>
    </Tabs>
}
}
export default MonthTabs;