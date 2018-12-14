import { combineReducers } from "redux";

export const data = (
  state = {
    customerInfo:{},
	moviePlayingHistory:[],
	topTenCustomers:[],
	customersByNameList:[]
  },
  action
) => {
  switch (action.type) {
    case "CUSTOMER_INFO":
      console.log("In customer info" + action.payload);
      state = {
        ...state,
        customerInfo: action.payload.result
      };
      break;

    case "REGISTER_USER":
      console.log("In Register user");
      console.log(action.payload);
      state = {
        ...state,
        message: action.payload
      };
      break;

    case "LOGIN_USER":
      console.log("In Login user");
      console.log(action.payload);
      state = {
        ...state,
        message: action.payload
      };
      break;

    case "USER_LOGGED_IN":
      console.log("In Is User Logged In");
      console.log(action.payload);
      state = {
        ...state,
        message: action.payload
      };
      break;

    case "MOVIE_PLAYING_HISTORY":
      state = {
        ...state,
        moviePlayingHistory: action.payload.result
      };
      break;
    case "TOP_TEN_CUSTOMERS":
    console.log(action.payload.result);
      state = {
        ...state,
        topTenMovies: action.payload.result
      };
      break;
      case "CUSTOMER_BY_NAME_LIST":
      console.log(action.payload.result);
        state = {
          ...state,
          customersByNameList: action.payload.result
          };
          break;

      case "RESET_CUSTOMER_LIST":
      console.log(action.payload.result);
        state = {
          ...state,
          customersByNameList: []
          };
          break;

    default:
      return state;
  }
  return state;
};
export default combineReducers({
  data
});
