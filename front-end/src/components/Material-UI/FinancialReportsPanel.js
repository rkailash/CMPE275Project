import React from 'react';
import PropTypes from 'prop-types';
import { createMuiTheme } from '@material-ui/core/styles';
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import red from '@material-ui/core/colors/red';
import Graph1 from './Graph1';
import Graph2 from './Graph2';
import {financialData} from "../../reducers/reducer_financial";
import * as getData from "../../actions/financialAction";

const styles = theme => ({
    root: {
        width: '100%',
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        fontWeight: theme.typography.fontWeightRegular,
    },
});

const theme = createMuiTheme({
    palette: {
        primary: red
    }

});



class FinancialReportsPanel extends React.Component {

    constructor(){
        super();
        this.state = {
            userReportList:[],
            incomeReportList:[]
        }
    }

    componentWillReceiveProps(nextProps) {
        console.log(nextProps);
        if(nextProps.financialData){
            this.setState({
                userReportList : nextProps.financialData.data.userReporting,
                incomeReportList : nextProps.financialData.data.incomeReporting
            });
        }
    }
    componentWillMount(){
        this.props.getUsersReporting();
        this.props.getIncomeReporting();
        //this.props.getMovieDetails(movie_id);
    }

    render() {
        const { financialData } = this.props;
        return (
            <div>
                <ExpansionPanel  defaultExpanded={false}>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography>View report for monthly subscription, pay-per-view, total unique and total unique active users</Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                    
                            <Graph1 graphData={this.state.userReportList}/>
                        
                    </ExpansionPanelDetails>
                </ExpansionPanel>
                <br/>
                <ExpansionPanel>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography>View financial report for subscription, pay-per-view and total monthly income</Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        
                            <Graph2 graphDataI = {this.state.incomeReportList} />
                       
                    </ExpansionPanelDetails>
                </ExpansionPanel>
                
            </div>
        );
    }
}

function mapStateToProps(state){
    return{
        financialData : state.FinancialReducer
    };
}

function mapDispatchToProps(dispatch){
    return bindActionCreators(getData,dispatch)

}

export default connect(mapStateToProps,mapDispatchToProps)(FinancialReportsPanel);

// FinancialReportsPanel.propTypes = {
//     classes: PropTypes.object.isRequired,
// };

// export default withStyles(styles)(FinancialReportsPanel);