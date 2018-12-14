import {combineReducers} from 'redux';

export const data = (state =  {
    movieInfo:{},
	moviesList:[],
	message:"",
	playPerViewList:[],
	topTenMovies:[]
}, action) =>{

	switch (action.type) {
		case "MOVIE_ADD":
			console.log("In movie add"+action.payload.message);
			state = {
				...state,
				message: action.payload.message
			  };
			  break;
		case "MOVIE_INFO":
			  console.log("In movie add"+action.payload.result);
			  state = {
				  ...state,
				  movieInfo: action.payload.result
				};
				break;
		case "MOVIE_PLAY_PER_VIEW":
            console.log("In movie add"+action.payload.result);
			state = {
				...state,
				playPerViewList: action.payload.result
			  };
			  break;
		case "ALL_MOVIES":
            console.log("In movie add"+action.payload.result);
			state = {
				...state,
				moviesList: action.payload.result
			  };
			  break;
		case "TOP_TEN_MOVIES":
			state = {
				...state,
				topTenMovies: action.payload.result
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