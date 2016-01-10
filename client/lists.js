import React, {PropTypes, Component} from 'react';
import Avatar from 'material-ui/lib/avatar';
import FileFolder from 'material-ui/lib/svg-icons/file/folder';
import styles from 'material-ui/lib/styles';
import FontIcon from 'material-ui/lib/font-icon';
import List from 'material-ui/lib/lists/list';
import ListItem from 'material-ui/lib/lists/list-item';
import RandomMC from 'random-material-color';

const colors = styles.Colors;

class Content extends Component{

    constructor(props) {
        super(props);
    }
     shouldComponentUpdate(nextProps, nextState, nextContext) {
        return true; // this will throw without context, read on
      }
    reload(){
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState({data: data});
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
        var contacts =<Avatar>test</Avatar>;
        if(this.state!=null){
                contacts = this.state.data.map(function(contact){
                    var firstLetter = contact.firstName.substring(0, 1);
                    var randomColor = RandomMC.getColor({text: firstLetter});
                    return <ListItem
                                 disabled={true}
                                 leftAvatar={
                                   <Avatar
                                     backgroundColor={randomColor}
                                   >
                                      {firstLetter}
                                   </Avatar>
                                 }
                               >
                                 {contact.firstName}
                               </ListItem>;
            });
        }
        return <List>{contacts}</List>;
    }

};
export default Content