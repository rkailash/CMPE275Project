import PropTypes from 'prop-types';
import React from 'react';

export default class temporaryLogic extends React.Component {

  state = { months: 1};

  result = () => '$' + this.state.months * 10;

  updateMonths = e => this.setState({ months: +e.target.value });


  render() {
    return (
      <div>
            <select onChange={this.updateMonths} value={this.state.months}>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
            <option value="10">10</option>
            <option value="11">11</option>
            <option value="12">12</option>
            </select>
           <br/>
        Your Total: {this.result()}
      </div>
    );
  }
}