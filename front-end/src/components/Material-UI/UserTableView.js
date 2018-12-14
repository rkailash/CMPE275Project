import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import React from 'react';
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {movieData} from "../../reducers/reducer-movie";
import * as getData from "../../actions/movieAction";


const styles = {
    container: {
        textAlign: "center"
    }
};


class TableViewDetails extends React.Component {

    constructor(props){
        super(props);
        this.state= {
            viewList: [],
            panel:""
        }
    }

    componentWillReceiveProps(nextProps) {
        console.log(nextProps);
        if(nextProps){
            this.setState({
                viewList : nextProps.viewList
            });
        }
    }
    componentWillMount(){
        console.log(this.props);
        //this.props.getPlaysPerMovie();
        //this.props.getMovieDetails(movie_id);
    }

    render() {
        const { movieData } = this.props;
        return (
            <div   style={styles.container}>
                <Paper >
                    <Table>
                        <TableHead>
                            <TableRow><TableCell>Serial Number</TableCell>
                                <TableCell >Customer Name</TableCell>
                                <TableCell>Play Count</TableCell></TableRow>


                        </TableHead>
                        <TableBody>
                            {this.props.viewList && this.props.viewList.map((row,i) => {
                                return (
                                    <TableRow key={i}>
                                        <TableCell component="th" scope="row">
                                            {i+1}
                                        </TableCell >
                                        <TableCell>{row.name}</TableCell>
                                        <TableCell>{row.playCount}</TableCell>
                                    </TableRow>
                                );
                            })}
                        </TableBody>
                    </Table>
                </Paper>
            </div>
        );
    }
}


function mapStateToProps(state){
    return{
        movieData : state.MovieReducer
    };
}

function mapDispatchToProps(dispatch){
    return bindActionCreators(getData,dispatch)

}

export default connect(mapStateToProps,mapDispatchToProps)(TableViewDetails);