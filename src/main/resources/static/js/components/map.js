import React, {PropTypes, Component} from 'react';
import shouldPureComponentUpdate from 'react-pure-render/function';
import $ from 'jquery';
import GoogleMap from 'google-map-react';
import Point from './point.js';
import PointList from './point_list.js';



function createMapOptions(maps) {
    console.log('create options');
  // next props are exposed at maps
  // "Animation", "ControlPosition", "MapTypeControlStyle", "MapTypeId",
  // "NavigationControlStyle", "ScaleControlStyle", "StrokePosition", "SymbolPath", "ZoomControlStyle",
  // "DirectionsStatus", "DirectionsTravelMode", "DirectionsUnitSystem", "DistanceMatrixStatus",
  // "DistanceMatrixElementStatus", "ElevationStatus", "GeocoderLocationType", "GeocoderStatus", "KmlLayerStatus",
  // "MaxZoomStatus", "StreetViewStatus", "TransitMode", "TransitRoutePreference", "TravelMode", "UnitSystem"
  return {
    zoomControlOptions: {
      position: maps.ControlPosition.RIGHT_CENTER,
      style: maps.ZoomControlStyle.SMALL
    },
    mapTypeControlOptions: {
      position: maps.ControlPosition.TOP_RIGHT
    },
    mapTypeControl: true
  };
}
class PointCanvas extends Component {
    constructor(props) {
        super(props);
        this.state = {data:[]};
    }
    reload(){
        console.log('reload', this);
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                console.log('data: ', data.content[0]);
                this.setState({data: data.content});
            }.bind(this),
            error: function(xhr, status, err) {
              console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    }
    componentDidMount(){
        setInterval(()=> this.reload(), this.props.pollInterval);
    }
    render() {
        var points =<Point lat={43.54} lng={11.32} text={'BL'}></Point>;
        if(this.state!=null){
                points = this.state.data.map(function(coordinate){
                    return (<Point lat={coordinate.latitude} lng={coordinate.longitude} text={coordinate.label}></Point>);
            });
        }
    return (
       <GoogleMap
        // apiKey={YOUR_GOOGLE_MAP_API_KEY} // set if you need stats etc ...
        center={this.props.center}
        zoom={this.props.zoom}
        options={createMapOptions}>
            {points}
      </GoogleMap>
    );
  }
}

PointCanvas.propTypes = {
                  center: PropTypes.array,
                  zoom: PropTypes.number
                };
PointCanvas.defaultProps = {
                  center: [48.141353, 11.518652],
                  zoom: 13
                };
PointCanvas.shouldComponentUpdate = shouldPureComponentUpdate;

export default PointCanvas