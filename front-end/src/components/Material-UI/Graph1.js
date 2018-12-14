import React from 'react';
import ResponsiveContainer from 'recharts/lib/component/ResponsiveContainer';
import LineChart from 'recharts/lib/chart/LineChart';
import Line from 'recharts/lib/cartesian/Line';
import XAxis from 'recharts/lib/cartesian/XAxis';
import YAxis from 'recharts/lib/cartesian/YAxis';
import CartesianGrid from 'recharts/lib/cartesian/CartesianGrid';
import Tooltip from 'recharts/lib/component/Tooltip';
import Legend from 'recharts/lib/component/Legend';

// const data = [
//   { name: 'Jan', SubscriptionUsers: 2200, PayperviewUsers: 3400, AllUsers: 7000 },
//   { name: 'Feb', SubscriptionUsers: 1280, PayperviewUsers: 2398, AllUsers: 7000 },
//   { name: 'Mar', SubscriptionUsers: 5000, PayperviewUsers: 4300, AllUsers: 7000 },
//   { name: 'Apr', SubscriptionUsers: 4780, PayperviewUsers: 2908, AllUsers: 7000 },
//   { name: 'May', SubscriptionUsers: 5890, PayperviewUsers: 4800, AllUsers: 7000 },
//   { name: 'Jun', SubscriptionUsers: 4390, PayperviewUsers: 3800, AllUsers: 7000 },
//   { name: 'Jul', SubscriptionUsers: 4490, PayperviewUsers: 4300, AllUsers: 7000 },
//   { name: 'Aug', SubscriptionUsers: 4490, PayperviewUsers: 4300, AllUsers: 7000 },
//   { name: 'Sep', SubscriptionUsers: 4490, PayperviewUsers: 4300, AllUsers: 7000 },
//   { name: 'Oct', SubscriptionUsers: 4490, PayperviewUsers: 4300, AllUsers: 7000 },
//   { name: 'Nov', SubscriptionUsers: 4490, PayperviewUsers: 4300, AllUsers: 7000 },
//   { name: 'Dec', SubscriptionUsers: 4490, PayperviewUsers: 4300, AllUsers: 7000 },
// ];


class Graph1 extends React.Component {

  constructor(){
    super();
    this.state ={
      graphData : []
    }
  }
  
componentWillReceiveProps(nextProps){
  console.log(nextProps);
  if(nextProps.graphData){
    this.setState({
      graphData:nextProps.graphData
    });
  }
}

  render(){

    return (
      <ResponsiveContainer width="99%" height={320}>
        <LineChart data={this.state.graphData}>
          <XAxis dataKey="name" />
          <YAxis />
          <CartesianGrid vertical={false} strokeDasharray="3 3" />
          <Tooltip />
          <Legend />
          <Line type="monotone" dataKey="SubscriptionUsers" stroke="#FF5733" activeDot={{ r: 7 }} />
          <Line type="monotone" dataKey="PayPerViewUsers" stroke="#15590C" activeDot={{ r: 7 }} />
          <Line type="monotone" dataKey="UniqueActiveUsers" stroke="#3340FF" activeDot={{ r: 7 }} />
          <Line type="monotone" dataKey="UniqueUsers" stroke="#F61524" activeDot={{ r: 7 }} />
        </LineChart>
      </ResponsiveContainer>
    );
  }

}

export default Graph1;