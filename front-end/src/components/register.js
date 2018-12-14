import React from "react";
import { render } from "react-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Redirect } from "react-router-dom";
import { BrowserRouter, Route, Link } from "react-router-dom";
import { bindActionCreators } from "redux";
import { withStyles } from "@material-ui/core/styles";
import MenuItem from "@material-ui/core/MenuItem";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import { customerData } from "./../reducers/reducer_customer";
import * as getData from "./../actions/customerAction";

const styles = {
  container: {
    textAlign: "center"
  }
};

class Register extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  onChange(e) {
    this.setState({
      [e.target.name]: e.target.value
    });
  }

  register(e) {
    e.preventDefault();
    this.props
      .registerUser(this.state)
      .then(res => {
        console.log("Response: ", res);
        if (res.data.message) alert(res.data.message);

        this.setState({
          redirectLogin: true
        });
      })
      .catch(err => {
        console.log(err);
        if (err && err.data && err.data.message)
          alert(err.response.data.message);
        else alert("Error registering user");
      });
  }

  render() {
    const { customerData } = this.props;

    if (this.state.redirectLogin)
      return (
        <Redirect
          to={{
            pathname: "/login"
          }}
        />
      );

    return (
      <div style={styles.container}>
        <h1>Register User</h1>
        <form style={{ marginBottom: "40px" }} noValidate autoComplete="off">
          <TextField
            id="outlined-name"
            label="Name"
            value={this.state.name}
            name="name"
            onChange={this.onChange.bind(this)}
            margin="normal"
            variant="outlined"
            style={{ width: 500 }}
          />
          <br />
          <TextField
            id="outlined-name"
            label="Email"
            required
            value={this.state.email}
            name="email"
            type="email"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
          <br />
          <TextField
            id="outlined-name"
            label="Screen Name"
            value={this.state.screenName}
            name="screenName"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
            fullWidth
          />
          <br />
          <TextField
            id="outlined-name"
            label="Password"
            required
            value={this.state.password}
            type="password"
            name="password"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
            fullWidth
          />
          <br />

          <br />
          {/*<button className="btn btn-warning mt20" onClick={this.addMovie.bind(this)}>Add Movie</button>*/}
          <Button
            variant="contained"
            size="large"
            color="primary"
            onClick={this.register.bind(this)}
          >
            Register User
          </Button>
        </form>
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    customerData: state.CustomerReducer
  };
}

function mapDispatchToProps(dispatch) {
  return bindActionCreators(getData, dispatch);
}
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Register);
