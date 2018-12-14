import React from 'react';
import ResponsiveContainer from 'recharts/lib/component/ResponsiveContainer';
import LineChart from 'recharts/lib/chart/LineChart';
import Line from 'recharts/lib/cartesian/Line';
import XAxis from 'recharts/lib/cartesian/XAxis';
import YAxis from 'recharts/lib/cartesian/YAxis';
import CartesianGrid from 'recharts/lib/cartesian/CartesianGrid';
import Tooltip from 'recharts/lib/component/Tooltip';
import Legend from 'recharts/lib/component/Legend';


class Graph2 extends React.Component {

  constructor(){
    super();
    this.state = {
      graphDataI : []
    }
  }
  
componentWillReceiveProps(nextProps){
  console.log(nextProps);
  if(nextProps.graphDataI){
    this.setState({
      graphDataI:nextProps.graphDataI
    });
  }
}

  render(){

    return (
      <ResponsiveContainer width="99%" height={320}>
        <LineChart data={this.state.graphDataI}>
          <XAxis dataKey="name" />
          <YAxis />
          <CartesianGrid vertical={false} strokeDasharray="3 3" />
          <Tooltip />
          <Legend />
          <Line type="monotone" dataKey="SusbscriptionIncome" stroke="#FF5733" activeDot={{ r: 7 }} />
          <Line type="monotone" dataKey="PayPerViewIncome" stroke="#15590C" activeDot={{ r: 7 }} />
          <Line type="monotone" dataKey="TotalIncome" stroke="#3340FF" activeDot={{ r: 7 }} />
        </LineChart>
      </ResponsiveContainer>
    );
  }

}

export default Graph2;