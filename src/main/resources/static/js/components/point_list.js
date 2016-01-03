import React, {PropTypes, Component} from 'react';
import shouldPureComponentUpdate from 'react-pure-render/function';
class PointList extends Component{
  constructor(props) {
      console.log('constructor of list', props);
      super(props.data);
  }
  render(){
    /*var points = "";
    console.log('render list', this.props.data);
    if(this.props.data){
        console.log('exists data', this.props.data);
        points = this.props.data.map(function(coordinate){
            console.log('render points');
            return (<Point lat={43.54} lng={11.32} text={label}></Point>);
        });
    }
    return (
        {points}
    );*/
    return 'lalala';
  }
}
export default PointList;