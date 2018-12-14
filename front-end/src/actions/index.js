import * as types from "./types";
import axios from "axios";

const registerSuccess = data => {
  return {
    type: types.REGISTER_SUCCESS,
    data
  };
};

const registerFailure = data => {
  return {
    type: types.REGISTER_FAILURE,
    data
  };
};

const loginSuccess = data => {
  return {
    type: types.LOGIN_SUCCESS,
    data
  };
};

const loginFailure = data => {
  return {
    type: types.LOGIN_FAILURE,
    data
  };
};

const logoutSuccess = data => {
  return {
    type: types.LOGOUT_SUCCESS,
    data
  };
};

const logoutFailure = err => {
  return {
    type: types.LOGOUT_FAILURE,
    err
  };
};

const saveSearchResults = results => {
  return {
    ...results,
    type: types.SAVE_SEARCH_RESULTS
  };
};

const savePropertyDetails = data => {
  return {
    type: types.SAVE_PROPERTY_DETAILS,
    data
  };
};

export const handleLoginChange = data => {
  return {
    type: types.HANDLE_LOGIN_CHANGE,
    data
  };
};
export const saveAddPropertyStatus = status => {
  return {
    type: types.ADD_PROPERTY_STATUS,
    status
  };
};

export const handleLogin = data => {
  return dispatch => {
    axios.defaults.withCredentials = true;
    return axios.post("http://54.193.84.204:3001/Login", data).then(
      res => {
        dispatch(loginSuccess(res.data));
      },
      err => {
        dispatch(loginFailure(err.response.data));
      }
    );
  };
};

export const handleLogout = () => {
  return dispatch => {
    return axios.get("http://54.193.84.204:3001/Logout").then(
      () => {
        dispatch(logoutSuccess());
      },
      () => {
        dispatch(logoutFailure());
      }
    );
  };
};

export const registerUser = data => {
  return dispatch => {
    axios.defaults.withCredentials = true;
    return axios
      .post("http://54.193.84.204:3001/Register", data)
      .then(res => {
        dispatch(registerSuccess(res.data));
      })
      .catch(err => {
        dispatch(registerFailure(err.response.data));
      });
  };
};

// export const fetchTravelerProperties = () => {
//   return dispatch => {
//     return axios.get(`http://54.193.84.204:3001/TravelerDash`).then(
//       res => {
//         dispatch(saveTravelerProperties({ properties: res.data }));
//       },
//       err => {
//         console.log("Failed to fetch your properties!");
//       }
//     );
//   };
// };
export const saveTrips = data => {
  return {
    type: types.SAVE_TRIPS,
    data
  };
};

export const fetchTrips = () => {
  return dispatch => {
    return axios.get(`http://54.193.84.204:3001/TravelerDash`).then(
      res => {
        dispatch(saveTrips({ properties: res.data }));
      },
      err => {
        console.log("Failed to fetch your trips!");
      }
    );
  };
};

export const fetchOwnerProperties = () => {
  return dispatch => {
    return axios.get(`http://54.193.84.204:3001/OwnerDash/MyProps`).then(
      res => {
        dispatch(saveOwnerProperties({ properties: res.data }));
      },
      err => {
        console.log("Failed to fetch your properties!");
      }
    );
  };
};

export const saveSearch = data => {
  return {
    type: types.SAVE_SEARCH,
    data
  };
};

export const fetchSearchResults = params => {
  return dispatch => {
    return axios.get(`http://54.193.84.204:3001/PropertyList`, { params }).then(
      res => {
        dispatch(saveSearchResults({ properties: res.data }));
      },
      err => {
        console.log("Failed to fetch Search Results!");
      }
    );
  };
};

export const fetchPropertyDetails = (id, params) => {
  return dispatch => {
    return axios.get(`http://54.193.84.204:3001/Property/${id}`).then(
      res => {
        dispatch(savePropertyDetails({ details: res.data }));
      },
      err => {
        console.log("Failed to fetch Search Results!");
      }
    );
  };
};

const saveOwnerProperties = data => {
  return {
    type: types.SAVE_PROPERTIES,
    data
  };
};

export const addProperty = data => {
  console.log(data);
  return dispatch => {
    return axios
      .post("http://54.193.84.204:3001/AddProperty", data)
      .then(response => {
        console.log("Axios POST response:", response.status);
        dispatch(saveAddPropertyStatus(response.status));
      });
  };
};

const bookingSuccess = data => {
  return {
    type: types.BOOKING_SUCCESS,
    data
  };
};

const bookingFailure = () => {
  return {
    type: types.BOOKING_FAILURE
  };
};

export const book = data => {
  return dispatch => {
    return axios
      .post("http://54.193.84.204:3001/Booking", data)
      .then(response => {
        if (response.status === 200) {
          dispatch(bookingSuccess(response.data));
        } else {
          dispatch(bookingFailure());
        }
      });
  };
};

const messageSent = data => {
  return {
    type: types.MESSAGE_SENT,
    data
  };
};

const messageFailed = () => {
  return {
    type: types.MESSAGE_FAILED
  };
};

export const messageOwner = data => {
  console.log(data);
  return dispatch => {
    return axios
      .post("http://54.193.84.204:3001/AddMessage", data)
      .then(response => {
        if (response.status === 200) {
          dispatch(messageSent(response.data));
        } else {
          dispatch(messageFailed());
        }
      });
  };
};

const replySent = data => {
  return {
    type: types.REPLY_SENT,
    data
  };
};

const replyFailed = () => {
  return {
    type: types.REPLY_FAILED
  };
};

export const replyToMessage = data => {
  console.log(data);
  return dispatch => {
    return axios
      .post("http://54.193.84.204:3001/AddMessage", data)
      .then(response => {
        if (response.status === 200) {
          dispatch(replySent(response.data));
        } else {
          dispatch(replyFailed());
        }
      });
  };
};

const messagesReceived = data => {
  return {
    type: types.MESSAGES_RECEIVED,
    data
  };
};

const messagesNotReceived = () => {
  return {
    type: types.MESSAGES_NOT_RECEIVED
  };
};

export const fetchMessages = id => {
  return dispatch => {
    return axios
      .get(`http://54.193.84.204:3001/FetchMessages?id=${id}`)
      .then(response => {
        if (response.status === 200) {
          dispatch(messagesReceived(response.data));
        } else {
          dispatch(messagesNotReceived());
        }
      });
  };
};

/////////

const replySentTLR = data => {
  return {
    type: types.TLR_REPLY_SENT,
    data
  };
};

const replyFailedTLR = () => {
  return {
    type: types.TLR_REPLY_FAILED
  };
};

export const replyToMessageTLR = data => {
  console.log(data);
  return dispatch => {
    return axios
      .post("http://54.193.84.204:3001/AddMessage", data)
      .then(response => {
        if (response.status === 200) {
          dispatch(replySent(response.data));
        } else {
          dispatch(replyFailed());
        }
      });
  };
};

const messagesReceivedTLR = data => {
  return {
    type: types.TLR_MESSAGES_RECEIVED,
    data
  };
};

const messagesNotReceivedTLR = () => {
  return {
    type: types.TLR_MESSAGES_NOT_RECEIVED
  };
};

export const fetchMessagesTLR = id => {
  return dispatch => {
    return axios
      .get(`http://54.193.84.204:3001/FetchMessages?id=${id}`)
      .then(response => {
        if (response.status === 200) {
          dispatch(messagesReceivedTLR(response.data));
        } else {
          dispatch(messagesNotReceivedTLR());
        }
      });
  };
};
