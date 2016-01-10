import React from "react";
import ReactDOM from "react-dom";
import Content from "./lists"

const url='http://localhost/api/contacts/';
const pollInterval=5000;
ReactDOM.render(
 <Content url={url} pollInterval={pollInterval}/>,
  document.body
);
