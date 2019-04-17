import React from 'react';
import { Link } from 'react-router-dom';
class MonthTabsRouter extends React.Component {
 constructor(){
  super();
  this.state={style:{'fontSize': '10px'}}
 }
render(){
  if(this.props.tabId === 'All'){
   return <Link to={{pathname: '/weather/', search: '?month=All&year='+this.props.year}} >
     <p style={this.state.style}>Show All</p>
    </Link>
  }
else{
   return <Link to={{pathname: '/weather/', search: '?year='+this.props.year + '&month='+this.props.tabId  }} >
     <p style={this.state.style}>{this.props.tabId} {this.props.year}</p>
    </Link>
  }
}
}
export default MonthTabsRouter;