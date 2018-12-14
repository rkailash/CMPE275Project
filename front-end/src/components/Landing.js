import React from "react";
import { connect } from "react-redux";
import * as getData from "../actions/movieAction";
import { movieData } from "./../reducers/reducer-movie";
import { bindActionCreators } from "redux";
import StarRatingComponent from "react-star-rating-component";
import TextField from "@material-ui/core/TextField";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";
import ExpandMoreIcon from "@material-ui/core/SvgIcon/SvgIcon";
import Typography from "@material-ui/core/Typography";
import ExpansionPanelDetails from "@material-ui/core/ExpansionPanelDetails";
import ExpansionPanel from "@material-ui/core/ExpansionPanel";
import Divider from "@material-ui/core/Divider";
import Select from "react-select";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import * as getCustomerData from "../actions/customerAction";
import { Redirect } from "react-router-dom";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";

const styles = {
  container: {
    textAlign: "center"
  }
};

class CheckBox extends React.Component {
  render() {
    return (
      <input
        type="checkbox"
        id={this.props.id}
        value={this.props.value}
        onChange={this.props.onChange}
      />
    );
  }
}

const options = [
  { value: "chocolate", label: "Chocolate" },
  { value: "strawberry", label: "Strawberry" },
  { value: "vanilla", label: "Vanilla" }
];

class Landing extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      // moviesList : []
      moviesList: [],
      mpaaRatingData: [
        { key: "G", value: "G" },
        { key: "PG", value: "PG" },
        { key: "PG_13", value: "PG_13" },
        { key: "R", value: "R" },
        { key: "NC_17", value: "NC_17" }
      ],
      genreList: [
        { label: "ACTION", value: "ACTION" },
        { label: "ADVENTURE", value: "ADVENTURE" },
        { label: "COMEDY", value: "COMEDY" },
        { label: "DRAMA", value: "DRAMA" },
        { label: "EPICS", value: "EPICS" },
        { label: "HORROR", value: "HORROR" },
        { label: "SCIENCE_FICTION", value: "SCIENCE_FICTION" },
        { label: "WAR", value: "WAR" },
        { label: "CRIME", value: "CRIME" }
      ],
      mpaaOptionsChecked: [],
      movieFilter: {
        keywords: "",
        releaseYear: "",
        actors: [],
        directors: [],
        mpaaRatings: [],
        genreChecked: [],
        averageRating: ""
      },
      selectedOption: null
    };
  }

  componentWillReceiveProps(nextProps) {
    console.log(nextProps);
    if (nextProps.movieData) {
      if (nextProps.movieData.data.moviesList) {
        this.setState({
          moviesList: nextProps.movieData.data.moviesList
        });
      }
    }
  }

  componentWillMount() {
    //let movie_id = this.props.match.params.movie_id;
    this.handleIsLoggedIn();
    this.props.actions.movieAction.getAllMovies();
  }

  handleIsLoggedIn() {
    this.props.actions.customerAction
      .getIsLoggedIn()
      .then(res => {
        // do nothing
        this.setState({
          redirectLogin: false
        });
      })
      .catch(err => {
        // redirect to login
        this.setState({
          redirectLogin: true
        });
      });
  }

  handleChange = selectedOption => {
    this.setState({ selectedOption });
    console.log(`Option selected:`, selectedOption);
    //

    let checkedArray = this.state.movieFilter.genreChecked;
    checkedArray.push(selectedOption);
    this.setState({
      movieFilter: {
        genreChecked: checkedArray
      }
    });
  };

  onChange(e) {
    this.setState({
      movieFilter: {
        keywords: e.target.value
      }
    });
    this.props.filterMovies(this.state.movieFilter);
  }

  changeEvent(event) {
    let checkedArray = this.state.mpaaOptionsChecked;
    let selectedValue = event.target.value;

    if (event.target.checked === true) {
      checkedArray.push(selectedValue);
      this.setState({
        mpaaOptionsChecked: checkedArray
      });
    } else {
      let valueIndex = checkedArray.indexOf(selectedValue);
      checkedArray.splice(valueIndex, 1);

      this.setState({
        mpaaOptionsChecked: checkedArray
      });
    }
  }

  onStarClick(nextValue, prevValue, name) {
    alert(prevValue);
  }

  render() {
    const { movieData } = this.props;
    //alert(this.state.movieFilter.keywords);
    const { selectedOption } = this.state;

    let outputCheckboxes = this.state.mpaaRatingData.map(function(string, i) {
      return (
        <div class="inline-block">
          <CheckBox
            value={string.value}
            id={"string_" + i}
            onChange={this.changeEvent.bind(this)}
          />
          <label class="label-checkbox" htmlFor={"string_" + i}>
            {string.value}
          </label>
        </div>
      );
    }, this);

    if (this.state.redirectLogin)
      return (
        <Redirect
          to={{
            pathname: "/login"
          }}
        />
      );

    return (
      <div style={styles.container}>
        <div>
          <ExpansionPanel>
            <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
              <Typography>
                <label class="appbar-label">Filters</label>
                <IconButton color="inherit" aria-label="Open drawer">
                  <MenuIcon />
                </IconButton>
              </Typography>
            </ExpansionPanelSummary>
            <ExpansionPanelDetails>
              <Typography>
                <div class="disp-inline-flex">
                  <div>
                    <div class="m20">
                      <label class="fl label-checkbox ">MPAA Rating : </label>
                      <div class="inline-block">{outputCheckboxes}</div>
                    </div>
                    <br />
                    <div className="m20">
                      <label className="fl label-checkbox ">Genre : </label>
                      <br />
                      <div>
                        <Select
                          value={selectedOption}
                          onChange={this.handleChange}
                          options={this.state.genreList}
                          isMulti
                        />
                      </div>
                    </div>
                    <br />
                  </div>
                  <div class="ml160">
                    <div className="m20">
                      <label className="fl label-checkbox ">
                        Release Year :{" "}
                      </label>
                      <div className="inline-block">
                        <TextField
                          id="outlined-name"
                          value={this.state.movieFilter.releaseYear}
                          placeholder="Enter Search String"
                          name="name"
                          onChange={this.onChange.bind(this)}
                          margin="normal"
                          style={{ width: 500 }}
                          variant="outlined"
                        />
                      </div>
                    </div>
                    <br />
                    <div className="m20">
                      <label className="fl label-checkbox ">
                        Average Rating :{" "}
                      </label>
                      <br />
                      <div class="display-inline-block">
                        <StarRatingComponent
                          name="rate1"
                          starCount={5}
                          value={1}
                          onStarClick={this.onStarClick.bind(this)}
                        />{" "}
                        & up
                        <br />
                        <StarRatingComponent
                          name="rate1"
                          starCount={5}
                          value={2}
                        />{" "}
                        & up
                        <br />
                        <StarRatingComponent
                          name="rate1"
                          starCount={5}
                          value={3}
                        />{" "}
                        & up
                        <br />
                        <StarRatingComponent
                          name="rate1"
                          starCount={5}
                          value={4}
                        />{" "}
                        & up
                        <br />
                        <StarRatingComponent
                          name="rate1"
                          starCount={5}
                          value={5}
                        />{" "}
                        & up
                        <br />
                      </div>
                    </div>
                  </div>
                </div>
              </Typography>
            </ExpansionPanelDetails>
          </ExpansionPanel>
          <TextField
            id="outlined-name"
            value={this.state.searchString}
            placeholder="Enter Search String"
            name="name"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
        </div>
        {this.state.moviesList && this.state.moviesList.length > 0 ? (
          <div>
            {this.state.moviesList &&
              this.state.moviesList.map((movie, i) => {
                return (
                  <div class="m20 inline-block p20 h400">
                    <label key={i}>
                      <img
                        className="img-fluid img-thumbnail"
                        src={movie.imageUrl}
                        alt="https://rawapk.com/wp-content/uploads/2018/09/Movie-HD-Icon.png"
                        onError={e => {
                          e.target.onerror = null;
                          e.target.src =
                            "https://rawapk.com/wp-content/uploads/2018/09/Movie-HD-Icon.png";
                        }}
                        style={{
                          width: "200px",
                          height: "250px"
                        }}
                      />
                      <br />
                      <div>
                        <label> Movie Title : {movie.title}</label>
                        <br />
                        <label> Release Year : {movie.releaseYear}</label>
                        <br />
                        <label>
                          {" "}
                          Rating -<br />
                          <StarRatingComponent
                            name="rate1"
                            starCount={5}
                            value={movie.averageRating}
                          />
                        </label>
                        <br />
                      </div>
                    </label>
                  </div>
                );
              })}
          </div>
        ) : (
          <h2>No Movies Found</h2>
        )}
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    movieData: state.MovieReducer
  };
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      movieAction: bindActionCreators(getData, dispatch),
      customerAction: bindActionCreators(getCustomerData, dispatch)
    }
  };
}
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Landing);
