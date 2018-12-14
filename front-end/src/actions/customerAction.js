import axios from "axios/index";

export function getTopTenCustomers() {
  return dispatch => {
    return axios
      .get("/api/customer/get_most_active_customers")
      .then(response => {
        dispatch(topTenCustomers(response.data));
      });
  };
}

export function getCustomerInfo(customer_id) {
  return dispatch => {
    return axios
      .get("/api/customer/get-customer-details", {
        params: { id: customer_id }
      })
      .then(response => {
        dispatch(customerInfo(response.data));
      });
  };
}

export function getPlayHistory(customer_id) {
  return dispatch => {
    return axios
      .get("/api/customer/get-customer-watch-history", {
        params: { id: customer_id }
      })
      .then(response => {
        console.log(response.data);
        dispatch(customerPlayHistory(response.data));
      });
  };
}

export function getCustomerByName(name) {
  return dispatch => {
    return axios
      .get("/api/customer/getCustomerByName", {
        params: { name: name }
      })
      .then(
        response => {
          dispatch(customerByNameList(response.data));
        },
        error => {
          dispatch(resetCustomerList());
        }
      );
  };
}

export function registerUser(user) {
  return dispatch => {
    return axios.post("/api/customer/register", user).then(response => {
      console.log(response.data);
      dispatch(register(response.data));
      return response;
    });
  };
}

export function getIsLoggedIn() {
  return dispatch => {
    return axios.get("/api/customer/isLoggedIn").then(response => {
      console.log(response);
      dispatch(isLoggedIn(response));
      return response;
    });
  };
}

export function loginUser(user) {
  return dispatch => {
    return axios.post("/api/customer/login", user).then(response => {
      dispatch(login(response));
      return response;
    });
  };
}

export function topTenCustomers(res) {
  return {
    type: "TOP_TEN_CUSTOMERS",
    payload: res
  };
}

export function customerInfo(res) {
  return {
    type: "CUSTOMER_INFO",
    payload: res
  };
}

export function customerPlayHistory(res) {
  return {
    type: "MOVIE_PLAYING_HISTORY",
    payload: res
  };
}

export function register(res) {
  return {
    type: "REGISTER_USER",
    payload: res
  };
}

export function isLoggedIn(res) {
  return {
    type: "USER_LOGGED_IN",
    payload: res
  };
}

export function login(res) {
  return {
    type: "LOGIN_USER",
    payload: res
  };
}

export function customerByNameList(res) {
  return {
    type: "CUSTOMER_BY_NAME_LIST",
    payload: res
  };
}

export function resetCustomerList() {
  return {
    type: "RESET_CUSTOMER_LIST",
    payload: "No Data"
  };
}
