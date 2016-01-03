import React, {PropTypes, Component} from 'react';
import $ from 'jquery';


var data = [];
class ContactBox extends Component{

  getInitialState() {
    return {data: []};
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
  componentDidMount() {
      this.reload;
      setInterval(this.reload, this.props.pollInterval);
    }
  render() {
    return (
    <div className="contactsBox">
      <h1>Contacts</h1>
       <ContactList data={this.state.data}/>
    </div>
    );
  }
}

class ContactList extends Component{
  render() {
    var contacts = this.props.data.map(function(contact){
       return (<Contact firstName={contact.firstName} lastName={contact.lastName} city={contact.city}></Contact>);
    });
    return (
      <div className="contactList">
        {contacts}
      </div>
    );
  }
}

class Contact extends Component{
  render() {
    return (
      <div className="contact">
        <h2 className="contact">
          {this.props.city}
        </h2>
        <p>{this.props.firstName}</p>
        <p>{this.props.lastName}</p>
      </div>
    );
  }
}

export default ContactBox

