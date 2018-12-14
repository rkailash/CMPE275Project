import axios from "axios/index";

// export function getTopTenCustomers() {
//   return dispatch => {
//     return axios
//       .get("/api/customer/get_most_active_customers")
//       .then(response => {
//         dispatch(topTenCustomers(response.data));
//       });
//   };
// }

// export function getCustomerInfo(customer_id) {
//   return dispatch => {
//     return axios
//       .get("/api/customer/get-customer-details", {
//         params: { customer_id: customer_id }
//       })
//       .then(response => {
//         dispatch(customerInfo(response.data));
//       });
//   };
// }

export function getSubscriptionUsers() {
    return dispatch => {
        return axios.post("/api/admin/uniqueSubscriptions").then(response => {
            dispatch(subscriptionUsers(response.data));
        });
    };
}

export function getPayPerViewUsers() {
    return dispatch => {
        return axios.post("/api/admin/uniquePayPerView").then(response => {
            dispatch(payPerViewUsers(response.data));
        });
    };
}

export function getAllUsers() {
    return dispatch => {
        return axios.post("/api/admin/allUniqueUsers").then(response => {
            dispatch(allUsers(response.data));
        });
    };
}

export function getSubscriptionIncome() {
    return dispatch => {
        return axios.post("/api/admin/subscriptionIncome").then(response => {
            dispatch(subscriptionIncome(response.data));
        });
    };
}

export function getPayPerViewIncome() {
    return dispatch => {
        return axios.post("/api/admin/payPerViewIncome").then(response => {
            dispatch(payPerViewIncome(response.data));
        });
    };
}

export function getTotalIncome() {
    return dispatch => {
        return axios.post("/api/admin/totalIncome").then(response => {
            dispatch(totalIncome(response.data));
        });
    };
}

export function getUniqueActiveUsers() {
    return dispatch => {
        return axios.post("/api/admin/uniqueActiveUsers").then(response => {
            dispatch(uniqueActiveUsers(response.data));
        });
    };
}

export function getUsersReporting() {
    return dispatch => {
        return axios.get("/api/admin/usersReporting").then(response => {
            dispatch(usersReporting(response.data));
        });
    };
}

export function getIncomeReporting() {
    return dispatch => {
        return axios.get("/api/admin/incomeReporting").then(response => {
            dispatch(incomeReporting(response.data));
        });
    };
}

export function subscriptionUsers(res) {
    return {
      type: "MONTHLY_SUBSCRIPTIONS",
      payload: res
    };
  }
  
  export function payPerViewUsers(res) {
    return {
      type: "MONTHLY_PAYPERVIEWS",
      payload: res
    };
  }
  
  export function allUsers(res) {
    return {
      type: "MONTHLY_ALL",
      payload: res
    };
  }
  
  export function subscriptionIncome(res) {
    return {
      type: "SUBSCRIPTION_INCOME",
      payload: res
    };
  }

  export function payPerViewIncome(res) {
    return {
      type: "PAYPERVIEW_INCOME",
      payload: res
    };
  }

  export function totalIncome(res) {
    return {
      type: "TOTAL_INCOME",
      payload: res
    };
  }

  export function uniqueActiveUsers(res) {
    return {
      type: "ACTIVE_USER",
      payload: res
    };
  }

  export function usersReporting(res){
      return{
          type:"USERS",
          payload: res
      };
  }

  export function incomeReporting(res){
    return{
        type:"INCOME",
        payload: res
    };
}