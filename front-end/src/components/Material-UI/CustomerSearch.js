import React from "react";
import PropTypes from "prop-types";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import { withStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import AddNewMovie from "./../AddNewMovie";
import UserTableView from "./UserTableView";
import { customerData } from "../../reducers/reducer_customer";
import * as getData from "../../actions/customerAction";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import CustomerPlayHistory from "./CustomerPlayHistory";

const styles = {
  container: {
    textAlign: "center"
  }
};

class CustomerSearch extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      customersByNameList: [],
      name: "",
      customerDetails: {}
    };
  }

  componentWillReceiveProps(nextProps) {
    console.log(nextProps.customerData.data.customersByNameList);
    if (nextProps.customerData.data.customersByNameList) {
      this.setState({
        customersByNameList: nextProps.customerData.data.customersByNameList
      });
    }
    if (nextProps.customerData.data.customerInfo) {
      this.setState({
        customerDetails: nextProps.customerData.data.customerInfo
      });
    }
  }
  componentWillMount() {
    console.log(this.props);
    //this.props.getCustomerByName(this.state.name);
    //this.props.getTopTenMovies();
    //this.props.getMovieDetails(movie_id);
  }

  onChange(e) {
    this.setState({
      [e.target.name]: e.target.value
    });
    if (e.target.value == "") {
      this.setState({
        customerDetails: {},
        customersByNameList: []
      });
    } else {
      this.props.getCustomerByName(e.target.value);
    }
  }

  findCustomer(id) {
    this.props.getCustomerInfo(id);
    this.setState({
      playHistoryId: id
    });
  }

  render() {
    const { customerData } = this.props;

    return (
      <div>
        <TextField
          id="outlined-name"
          value={this.state.name}
          placeholder="Enter Customer Name"
          name="name"
          onChange={this.onChange.bind(this)}
          margin="normal"
          style={{ width: 500 }}
          variant="outlined"
        />
        <div style={styles.container} class="mt40">
          <div />
          {this.state.customersByNameList &&
          this.state.customersByNameList.length > 0 ? (
            <div style={{ display: "inline-flex" }}>
              <div>
                <Paper>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>Serial Number</TableCell>
                        <TableCell>Customer Name</TableCell>
                        <TableCell />
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {this.state.customersByNameList.map((customerObj, i) => {
                        return (
                          <TableRow key={i}>
                            <TableCell component="th" scope="row">
                              {i + 1}
                            </TableCell>
                            <TableCell>{customerObj.name}</TableCell>
                            <TableCell>
                              <Button
                                variant="contained"
                                size="small"
                                color="primary"
                                onClick={() =>
                                  this.findCustomer(customerObj.id)
                                }
                              >
                                View Details
                              </Button>
                            </TableCell>
                          </TableRow>
                        );
                      })}
                    </TableBody>
                  </Table>
                </Paper>
              </div>
              <div class="cust-details">
                {this.state.customerDetails && this.state.customerDetails.id ? (
                  <div>
                    <div class="m10">
                      Customer Name : {this.state.customerDetails.name}
                    </div>
                    <div class="m10">
                      Email Id : {this.state.customerDetails.email}
                    </div>
                    <div class="m10">
                      Screen Name : {this.state.customerDetails.screenName}
                    </div>
                    <div class="m10 bold-font">
                      Subscription End Date:
                      {this.state.customerDetails.subscriptionEndTime.length >
                      0 ? (
                        <div>
                          {new Intl.DateTimeFormat("en-US", {
                            year: "numeric",
                            month: "2-digit",
                            day: "2-digit",
                            hour: "2-digit",
                            minute: "2-digit",
                            second: "2-digit"
                          }).format(
                            new Date(
                              Date.UTC(
                                this.state.customerDetails.subscriptionEndTime.slice(
                                  0,
                                  1
                                ),
                                this.state.customerDetails.subscriptionEndTime.slice(
                                  1,
                                  2
                                ),
                                this.state.customerDetails.subscriptionEndTime.slice(
                                  2,
                                  3
                                ),
                                this.state.customerDetails.subscriptionEndTime.slice(
                                  3,
                                  4
                                ),
                                this.state.customerDetails.subscriptionEndTime.slice(
                                  4,
                                  5
                                ),
                                this.state.customerDetails.subscriptionEndTime.slice(
                                  5,
                                  6
                                )
                              )
                            )
                          )}
                        </div>
                      ) : (
                        ""
                      )}
                    </div>
                    <div />
                    <div class="m10">
                      <Link
                        to={"/getPlayHistory/" + this.state.customerDetails.id}
                        target="_blank"
                      >
                        View Play History{" "}
                      </Link>
                    </div>
                  </div>
                ) : (
                  <div />
                )}
              </div>
            </div>
          ) : (
            <label>No Customers</label>
          )}
        </div>
        {/*<div>
			{ this.state.playHistoryId ? <CustomerPlayHistory userId={this.state.playHistoryId}/> : <div/>}
	</div>*/}
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
)(CustomerSearch);
