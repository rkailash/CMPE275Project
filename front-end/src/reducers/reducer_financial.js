import { combineReducers } from 'redux';

export const data = (state = {
    subscriptionList: [],
    payPerViewList: [],
    allList: [],
    subscriptionIncome: [],
    payperviewIncome: [],
    totalIncome: [],
    activeUser: [],
    userReporting: [],
    incomeReporting: []
}, action) => {

    switch (action.type) {
        case "MONTHLY_SUBSCRIPTIONS":
            console.log("In monthly subscriptions" + action.payload.message);
            state = {
                ...state,
                subscriptionList: action.payload.message
            };
            break;
        case "MONTHLY_PAYPERVIEWS":
            console.log("In monthly payperviews" + action.payload.result);
            state = {
                ...state,
                payPerViewList: action.payload.result
            };
            break;
        case "MONTHLY_ALL":
            state = {
                ...state,
                allList: action.payload
            };
            break;
        case "SUBSCRIPTION_INCOME":
            state = {
                ...state,
                subscriptionIncome: action.payload
            };
            break;
        case "PAYPERVIEW_INCOME":
            state = {
                ...state,
                payperviewIncome: action.payload
            };
            break;
        case "TOTAL_INCOME":
            state = {
                ...state,
                totalIncome: action.payload
            };
            break;
        case "ACTIVE_USER":
            state = {
                ...state,
                activeUser: action.payload
            };
            break;

        case "USERS":
            state = {
                ...state,
                userReporting: action.payload.result
            };
            break;

        case "INCOME":
            state = {
                ...state,
                incomeReporting: action.payload.result
            };
            break;



        default:
            return state;
    }
    return state;
}
export default combineReducers({
    data
});