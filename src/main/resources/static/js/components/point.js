import React, {PropTypes, Component} from 'react';
import shouldPureComponentUpdate from 'react-pure-render/function';

import {pointStyle} from './point_styles.js';

class Point extends Component {
  constructor(props) {
    super(props);
    this.shouldComponentUpdate = shouldPureComponentUpdate;
  }

  render() {
    return (
       <div style={pointStyle}>
          {this.props.text}
       </div>
    );
  }
}
Point.propTypes= {
   text: PropTypes.string
 };
Point.defaultProps={};
export default Point;
