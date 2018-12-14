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


class SubscribePayPerView extends Component {

    constructor() {
        super();
        this.state = {
            movie_id: '',
            price: '5.0'
        }
    }

    state= {
        open: false
    };

    handleClickOpen = () => {
        this.setState({ open: true });
      };
    
      handleClose = () => {
        this.setState({ open: false });
      };

    
    


    handleSubmit(e) {

        let subscribePayPerViewAPI = "/api/customer/subscribe-payperview";
        let apiPayload = {};
        apiPayload.movieId = 2;
        apiPayload.customerId = 2;
        apiPayload.price = '5';


        axios.post(subscribePayPerViewAPI, apiPayload)
            .then(res => {
                this.props.sendResult(res.data.result)
            })
            .catch(err => {
                console.error(err);
            });


        e.preventDefault();
        alert('Payment Successful');
    }

    componentWillMount() {
        console.log(this.props.match.params.movie_id);
        let movie_id = this.props.match.params.movie_id;
        this.setState({
            movieId: this.props.match.params.movie_id,
            price: '5'
        });
    }

    render() {
        const classes = this.props;
        return (<div>
            <Grid container justify="center">
            <h4>Pay-per-view rate for this movie is $5.</h4>
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


export default SubscribePayPerView;