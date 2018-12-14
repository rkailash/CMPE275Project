import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import NoSsr from '@material-ui/core/NoSsr';
import Tab from '@material-ui/core/Tab';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import AddNewMovie from './../AddNewMovie';
import UserActivityPanel from './UserActivityPanel';
import MovieActivityPanel from './MovieActivityPanel';
import FinancialReportsPanel from './FinancialReportsPanel';

function TabContainer(props) {
    return (
        <Typography component="div" style={{ padding: 8 * 3 }}>
            {props.children}
        </Typography>
    );
}

TabContainer.propTypes = {
    children: PropTypes.node.isRequired,
};

function LinkTab(props) {
    return <Tab component="a" onClick={event => event.preventDefault()} {...props} />;
}

const styles = theme => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        color: '#d67612'
    },
    container: {
        textAlign: "center"
    }
});

class NavTabs extends React.Component {
    state = {
        value: 0,
    };

    handleChange = (event, value) => {
        this.setState({ value });
    };

    render() {
        const { classes } = this.props;
        const { value } = this.state;

        return (
            <NoSsr>
                <div className={classes.root}>
                    <AppBar position="static">
                        <Tabs fullWidth value={value} onChange={this.handleChange}>
                            <LinkTab label="User Activity" href="page1"/>
                            <LinkTab label="Movie ACtivity" href="page2" />
                            <LinkTab label="Financial Reports" href="page3" />
                        </Tabs>
                    </AppBar>
                    {value === 0 && <TabContainer>
                        <UserActivityPanel/>
                        </TabContainer>}
                    {value === 1 && <TabContainer><MovieActivityPanel/></TabContainer>}
                    {value === 2 && <TabContainer><FinancialReportsPanel/></TabContainer>}
                </div>
            </NoSsr>
        );
    }
}

NavTabs.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavTabs);