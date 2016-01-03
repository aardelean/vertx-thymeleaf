'use strict';

import * as React from 'react';
import ContactBox from './components/contacts.js';
import ReactDOM from 'react-dom';

var fetchContactsUrl= 'http://localhost/api/contacts/';

ReactDOM.render(
  <ContactBox url={fetchContactsUrl} pollInterval={2000}/>,
   document.getElementById('content')
);