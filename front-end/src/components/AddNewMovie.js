import React from "react";
import { connect } from "react-redux";
import { Redirect } from "react-router-dom";
import * as getData from "../actions/movieAction";
import { bindActionCreators } from "redux";
import Button from "@material-ui/core/Button";
import * as getCustomerData from "../actions/customerAction";

const styles = {
  container: {
    textAlign: "center"
  }
};

class AddNewMovie extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      title: "",
      synopsys: "",
      genre: "",
      releaseYear: "",
      studio: "",
      imageUrl: "",
      movieUrl: "",
      averageRating: "",
      country: "",
      type: "",
      price: "",
      mpaaRating: "",
      actors: "",
      status: "ACTIVE",
      director: "",
      mpaaRatingData: [
        { key: "G", value: "G" },
        { key: "PG", value: "PG" },
        { key: "PG_13", value: "PG_13" },
        { key: "R", value: "R" },
        { key: "NC_17", value: "NC_17" }
      ],
      genreData: [
        { key: "ACTION", value: "ACTION" },
        { key: "ADVENTURE", value: "ADVENTURE" },
        { key: "COMEDY", value: "COMEDY" },
        { key: "DRAMA", value: "DRAMA" },
        { key: "CRIME", value: "CRIME" },
        { key: "EPICS", value: "EPICS" },
        { key: "HORROR", value: "HORROR" },
        { key: "SCIENCE_FICTION", value: "SCIENCE_FICTION" },
        { key: "WAR", value: "WAR" }
      ],
      movieTypeData: [
        { key: "PAY_PER_VIEW", value: "PAY_PER_VIEW" },
        { key: "SUBSCRIPTION_ONLY", value: "SUBSCRIPTION_ONLY" },
        { key: "FREE", value: "FREE" },
        { key: "PAID", value: "PAID" }
      ],
      statusData: [
        { key: "ACTIVE", value: "ACTIVE" },
        { key: "INACTIVE", value: "INACTIVE" }
      ],
      redirectLogin: ""
    };
  }

  componentWillReceiveProps(nextProps) {
    console.log(nextProps);
    if (nextProps.moviesData) {
      if (nextProps.moviesData.data.movieInfo) {
        this.setState({
          title: nextProps.moviesData.data.movieInfo.title,
          synopsys: nextProps.moviesData.data.movieInfo.synopsys,
          genre: nextProps.moviesData.data.movieInfo.genre,
          releaseYear: nextProps.moviesData.data.movieInfo.releaseYear,
          studio: nextProps.moviesData.data.movieInfo.studio,
          imageUrl: nextProps.moviesData.data.movieInfo.imageUrl,
          movieUrl: nextProps.moviesData.data.movieInfo.movieUrl,
          averageRating: nextProps.moviesData.data.movieInfo.averageRating,
          country: nextProps.moviesData.data.movieInfo.country,
          type: nextProps.moviesData.data.movieInfo.type,
          price: nextProps.moviesData.data.movieInfo.price,
          mpaaRating: nextProps.moviesData.data.movieInfo.mpaaRating,
          actors: nextProps.moviesData.data.movieInfo.actors,
          director: nextProps.moviesData.data.movieInfo.director,
          status: nextProps.moviesData.data.movieInfo.status
        });
      }
    }
  }

  componentWillMount() {
    this.handleIsLoggedIn();
    //let movie_id = this.props.match.params.movie_id;
    //this.props.getMovieDetails(movie_id);
  }

  handleIsLoggedIn() {
    this.props
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

  onChange(e) {
    this.setState({
      [e.target.name]: e.target.value
    });
  }

  addMovie(e) {
    e.preventDefault();
    //alert("wo");
    var movie = {
      title: this.state.title,
      genre: this.state.genre,
      releaseYear: this.state.releaseYear,
      studio: this.state.studio,
      synopsys: this.state.synopsys,
      imageUrl: this.state.imageUrl,
      movieUrl: this.state.movieUrl,
      averageRating: this.state.averageRating,
      country: this.state.country,
      type: this.state.type,
      price: this.state.price,
      mpaaRating: this.state.mpaaRating,
      actors: this.state.actors,
      director: this.state.director,
      status: "ACTIVE"
    };

    this.props.newMovie(movie).then(
      data => {
        this.setState({
          redirectHome: true
        });
      },
      err => {
        console.log(err.response);
        this.setState({
          errors: err.response.data.message
        });
      }
    );
  }

  handleChange = e => {
    console.log(e.target.value);
    this.setState({
      type: e.target.value
    });
    var value = this.state.optionsdata.filter(function(item) {
      return item.key == e.target.value;
    });
  };

  handleIsLoggedIn() {
    this.props
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

  render() {
    const { classes } = this.props;
    if (this.state.redirectHome)
      return (
        <Redirect
          to={{
            pathname: "/adminDashboard"
          }}
        />
      );
    const { errors } = this.state;
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
        <h1>Add New Movie</h1>
        <div>{errors && <div className="help-block">{errors}</div>}</div>
        <form
          style={{ marginBottom: "40px" }}
          onSubmit={this.addMovie.bind(this)}
          class="add-form"
        >
          <label>Enter Movie Title</label>
          <br />
          <input
            id="outlined-name"
            label="Movie Title"
            value={this.state.title}
            name="title"
            required
            onChange={this.onChange.bind(this)}
            margin="normal"
            variant="outlined"
            style={{ width: 500 }}
          />
          <br />
          <label>Enter Movie Genre</label>
          <br />
          <input
            id="outlined-name"
            label="Movie Genre"
            value={this.state.genre}
            name="genre"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
          <br />
          <label>Enter Country</label>
          <br />
          <input
            id="outlined-name"
            label="Movie Country"
            value={this.state.country}
            name="country"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
          <br />
          <label>Enter Movie Release year</label>
          <br />
          <input
            id="outlined-name"
            label="Release Year"
            value={this.state.releaseYear}
            name="releaseYear"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
          <br />
          <label>Enter Description</label>
          <br />
          <input
            id="outlined-name"
            label="Synopsys"
            value={this.state.synopsys}
            name="synopsys"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
          <br />
          <label>Enter Movie Actors (comma separated values)</label>
          <br />
          <input
            id="outlined-name"
            label="Movie Actors"
            value={this.state.actors}
            name="actors"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
          <br />
          <label>Enter Studio</label>
          <br />
          <input
            id="outlined-name"
            label="Studio"
            value={this.state.studio}
            name="studio"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
          <br />
          <label>Enter Image Url</label>
          <br />
          <input
            id="outlined-name"
            label="Movie Image URL"
            value={this.state.imageUrl}
            name="imageUrl"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
          <br />
          <label>Enter Movie Url</label>
          <br />
          <input
            id="outlined-name"
            label="Movie URL"
            value={this.state.movieUrl}
            name="movieUrl"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
          <br />
          <label>Enter Average Rating</label>
          <br />
          <input
            id="outlined-name"
            label="Average rating"
            value={this.state.averageRating}
            name="averageRating"
            style={{ width: 500 }}
            onChange={this.onChange.bind(this)}
            margin="normal"
            variant="outlined"
          />
          <br />
          <label>Select Movie Type</label>
          <br />

          <select
            className="select-style"
            name="type"
            required
            value={this.state.type}
            onChange={this.onChange.bind(this)}
          >
            {this.state.movieTypeData.map(function(data, key) {
              return (
                <option key={key} value={data.key}>
                  {data.value}
                </option>
              );
            })}
          </select>
          <br />
          <label>Enter Movie Price</label>
          <br />
          <input
            id="outlined-name"
            label="Price of movie"
            value={this.state.price}
            name="price"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
          />
          <br />
          <label>Select MPAA Rating</label>
          <br />
          <select
            className="select-style"
            name="mpaaRating"
            required
            value={this.state.mpaaRating}
            onChange={this.onChange.bind(this)}
          >
            {this.state.mpaaRatingData.map(function(data, key) {
              return (
                <option key={key} value={data.key}>
                  {data.value}
                </option>
              );
            })}
          </select>
          <br />
          <label>Enter Director Name</label>
          <br />
          <input
            id="outlined-name"
            label="Director"
            value={this.state.director}
            name="director"
            onChange={this.onChange.bind(this)}
            margin="normal"
            style={{ width: 500 }}
            variant="outlined"
            required={true}
          />
          <br />
          {/*<button className="btn btn-warning mt20" onClick={this.addMovie.bind(this)}>Add Movie</button>*/}
          <Button
            variant="contained"
            size="large"
            color="primary"
            type="submit"
          >
            Add Movie
          </Button>
        </form>
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
  return bindActionCreators(
    Object.assign({}, getData, getCustomerData),
    dispatch
  );
}
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AddNewMovie);
