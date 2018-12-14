import {combineReducers} from 'redux'
import MovieReducer from './reducer-movie';
import FinancialReducer from './reducer_financial';
import CustomerReducer from './reducer_customer';


const allReducers = combineReducers({
    MovieReducer, FinancialReducer, CustomerReducer
});

export default allReducers;