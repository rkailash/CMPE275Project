import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import axios from 'axios/index';
import CreditCardTemplate from './CreditCardTemplate';
import Grid from '@material-ui/core/Grid';
import Modal from '@material-ui/core/Modal';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle'


const styles = theme => ({
    paper: {
        position: 'absolute',
        width: theme.spacing.unit * 50,
        backgroundColor: theme.palette.background.paper,
        boxShadow: theme.shadows[5],
        padding: theme.spacing.unit * 4,
    },
});


class Subscription extends Component {

    constructor() {
        super();
        this.state = {
            customer_id: '',
            noOfMonths: '1',
            price: '',
            months: '1'
        }
    }

    state = {
        open: false,
        months: '1'
    };

    result = () => '$' + this.state.months * 10;

    updateMonths = e => this.setState({ months: +e.target.value });

    handleClickOpen = () => {
        this.setState({ open: true });
    };

    handleClose = () => {
        this.setState({ open: false });
    };

    handleChange(event) {
        this.setState({ value: event.target.value });
    }



    handleSubmit(e) {

        let SubscriptionAPI = "/api/customer/subscribe";
        let apiPayload = {};
        apiPayload.customer_id = '';
        apiPayload.noOfMonths = '';
        apiPayload.price = '';


        axios.post(SubscriptionAPI, apiPayload)
            .then(res => {
                this.props.sendResult(res.data.result)
            })
            .catch(err => {
                console.error(err);
            });


        e.preventDefault();
        alert('Payment Successful');
    }

    render() {
        const classes = this.props;
        return (<div>
            <Grid container justify="center">
                <h4>Subscribe now for just $10/month.</h4>
            </Grid>
            <Grid container justify="center">
                <h5> Number of months:</h5></Grid>
            <Grid container justify="center">
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
                </select></Grid>
            <Grid container justify="center">
                Your total is: {this.result()}. Enter your card details below to proceed.
        </Grid>
            <CreditCardTemplate />
            <Grid container justify="center">
                <Button size="small" onClick={this.handleClickOpen}>Pay</Button>
            </Grid>
            <Dialog
                open={this.state.open}
                onClose={this.handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">{"Are you sure?"}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        You will be charged once you click Pay Now.
            </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleClose} color="primary">
                        Cancel
            </Button>
                    <Button onClick={this.handleSubmit} color="primary" autoFocus>
                        Pay Now
            </Button>
                </DialogActions>
            </Dialog>




        </div>
        );
    }
}


export default Subscription;