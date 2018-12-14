import React from 'react';
import PropTypes from 'prop-types';
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import { withStyles } from '@material-ui/core/styles';
import {Link} from 'react-router-dom';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import AddNewMovie from './../AddNewMovie';
import UserTableView from './UserTableView';
import CustomerSearch from './CustomerSearch';
import {customerData} from "../../reducers/reducer_customer";
import * as getData from "../../actions/customerAction";

const styles = theme => ({
    root: {
        width: '100%',
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        fontWeight: theme.typography.fontWeightRegular,
    },
});


class UserActivityPanel extends React.Component {

  	constructor(props){
        super(props);
        this.state= {
            topTenCustomers: []
        }
    }

    componentWillReceiveProps(nextProps) {
        console.log(nextProps);
        if(nextProps.customerData){
            this.setState({
                topTenCustomers : nextProps.customerData.data.topTenCustomers
            });
        }
    }
    componentWillMount(){
        console.log(this.props);
        this.props.getTopTenCustomers();
        //this.props.getTopTenMovies();
        //this.props.getMovieDetails(movie_id);
    }


    render() {
		const { customerData } = this.props;
        return (
            <div>

				<ExpansionPanel defaultExpanded={true}>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography>Customers Search</Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <Typography>
						{/*<Link to="/getPlayHistory/1" class="">User Activity</Link>*/}
						<CustomerSearch/>
                        </Typography>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
                <br/>
                <ExpansionPanel>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography>View most active users</Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <Typography>
                        <UserTableView viewList={this.state.topTenCustomers}/>
                        </Typography>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
                <br/>
            </div>
        );
    }
}


function mapStateToProps(state){
    return{
        customerData : state.CustomerReducer
    };
}

function mapDispatchToProps(dispatch){
    return bindActionCreators(getData,dispatch)

}

export default connect(mapStateToProps,mapDispatchToProps)(UserActivityPanel);