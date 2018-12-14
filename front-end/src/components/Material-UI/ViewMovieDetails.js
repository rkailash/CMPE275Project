import React from "react";
import PropTypes from "prop-types";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { Link, Redirect, withRouter } from "react-router-dom";
import { withStyles } from "@material-ui/core/styles";
import { movieData } from "../../reducers/reducer-movie";
import * as getData from "../../actions/movieAction";
import Grid from "@material-ui/core/Grid";
import StarRatingComponent from "react-star-rating-component";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Icon from "@material-ui/core/Icon";
import DeleteIcon from "@material-ui/icons/Delete";
import EditIcon from "@material-ui/icons/Edit";
import Fab from "@material-ui/core/Fab";
import Modal from "@material-ui/core/Modal";
import Typography from "@material-ui/core/Typography";
import ReactPlayer from "react-player";
import * as getCustomerData from "../../actions/customerAction";

function rand() {
  return Math.round(Math.random() * 20) - 10;
}

function getModalStyle() {
  const top = 50 + rand();
  const left = 50 + rand();

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`
  };
}

const stylesNew = {
  container: {
    textAlign: "center"
  }
};

const styles = theme => ({
  paper: {
    position: "absolute",
    width: theme.spacing.unit * 50,
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: theme.spacing.unit * 4
  }
});

class ViewMovieDetails extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      movieInfo: {},
      playing: true,
      open: false,
      redirectLogin: ""
    };
  }

  componentWillReceiveProps(nextProps) {
    console.log(nextProps.movieData);
    if (nextProps.movieData.data.movieInfo) {
      this.setState({
        movieInfo: nextProps.movieData.data.movieInfo
      });
    }
  }
  componentWillMount() {
    console.log(this.props);
    this.props.getMovieInfo(this.props.match.params.movie_id);
    this.handleIsLoggedIn();
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

  rateNow(e) {
    e.preventDefault();
    if (this.state.rating == 0) {
      alert("Please select number of stars");
    } else {
      let ratingDetails = {};
      ratingDetails.comment = this.state.comment;
      ratingDetails.rating = this.state.rating;
      ratingDetails.movieId = this.props.match.params.movie_id;
      ratingDetails.customerId = 3; //this.props.match.params.movie_id;;
      this.props.rateNow(ratingDetails).then(
        data => {
          this.props.getMovieInfo(this.props.match.params.movie_id);
        },
        err => {}
      );
    }
  }

  onStarClick(nextValue, prevValue, name) {
    this.setState({ rating: nextValue });
  }

  editMovie() {
    this.setState({
      playing: false
    });
  }

  checkValidity() {
    this.props.getCustomerValidity(2, this.props.match.params.movie_id).then(
      data => {},
      err => {
        console.log(err.response);
        this.setState({
          playing: false,
          errMsg: err.response.data.message
        });
        this.handleOpen();
      }
    );
  }

  payNow() {
    console.log(this.props);
    let path = "payperview/" + this.props.match.params.movie_id;
    this.props.history.push(path);
  }

  subscribe() {}
  handleOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  render() {
    const { movieData } = this.props;
    console.log(this.state.movieInfo.title);
    if (this.state.redirectLogin)
      return (
        <Redirect
          to={{
            pathname: "/login"
          }}
        />
      );
    return (
      <div class="mt40">
        <div>
          <div class="bar">Movie Details</div>
          <div>
            {this.state.movieInfo ? (
              <div
                style={{ display: "inline-flex" }}
                class="movie-details-view"
              >
                <div>
                  <img
                    className="img-fluid img-thumbnail"
                    src={this.state.movieInfo.imageUrl}
                    alt="http://placehold.it/400x300"
                    style={{
                      width: "310px",
                      height: "300px"
                    }}
                  />
                </div>
                <div class="ml100 w75">
                  <div class="float-right">
                    <Fab color="secondary" aria-label="Edit">
                      <EditIcon onClick={this.editMovie.bind(this)}>
                        edit_icon
                      </EditIcon>
                    </Fab>
                    <div class="mt10" />
                    <Fab aria-label="Delete">
                      <DeleteIcon />
                    </Fab>
                  </div>

                  <div class="m10 bold-font">
                    Movie Name : {this.state.movieInfo.title}
                  </div>
                  <div class="m10">
                    Average Rating :{" "}
                    <StarRatingComponent
                      name="rate1"
                      starCount={5}
                      value={this.state.movieInfo.averageRating}
                    />{" "}
                    - {this.state.movieInfo.averageRating} / 5
                  </div>
                  <div class="m10">Genre : {this.state.movieInfo.genre}</div>
                  <div class="m10">
                    Release Year : {this.state.movieInfo.releaseYear}
                  </div>
                  <div class="m10">
                    MPAA Rating : {this.state.movieInfo.mpaaRating}
                  </div>
                  <div class="m10">
                    Synopsys : {this.state.movieInfo.synopsys}
                  </div>

                  <div class="m10">
                    Actors :
                    {this.state.movieInfo.actors &&
                      this.state.movieInfo.actors.map((row, i) => {
                        return (
                          <label key={i}>
                            <label> {row.name}</label>
                            {i < this.state.movieInfo.actors.length - 1 ? (
                              <label>, </label>
                            ) : (
                              <label />
                            )}
                          </label>
                        );
                      })}
                  </div>
                </div>
              </div>
            ) : (
              <div />
            )}
          </div>
        </div>
        <div class="mt30">
          <div class="bar">Watch Movie</div>
          <Grid container justify="center">
            <ReactPlayer
              id="react-player"
              url={this.state.movieInfo.movieUrl}
              width={1000}
              height={500}
              controls={true}
              playing={this.state.playing}
              onStart={this.checkValidity.bind(this)}
            />
          </Grid>
        </div>
        <div class="mt30">
          <div class="bar">Ratings and Reviews</div>
          <Grid container justify="center">
            <div class="w75">
              <form onSubmit={this.rateNow.bind(this)}>
                <div>
                  <h2>Rate the movie</h2>
                  <StarRatingComponent
                    required
                    name="rate1"
                    starCount={5}
                    value={this.state.rating}
                    onStarClick={this.onStarClick.bind(this)}
                  />
                </div>
                <div>
                  <TextField
                    id="outlined-name"
                    value={this.state.releaseYear}
                    name="comment"
                    required
                    onChange={e =>
                      this.setState({
                        comment: e.target.value
                      })
                    }
                    margin="normal"
                    style={{ width: 500 }}
                    variant="outlined"
                    placeholder="Enter the review comment"
                  />
                </div>
                <Button
                  variant="contained"
                  size="small"
                  color="primary"
                  onClick={this.rateNow.bind(this)}
                >
                  Submit Rating
                </Button>
              </form>
              <hr />

              {this.state.movieInfo.ratings &&
              this.state.movieInfo.ratings.length > 0 ? (
                <div className="details-font">
                  {this.state.movieInfo.ratings.map((ratings, i) => (
                    <div key={i}>
                      {/*<div>Rating - <label className="rating-font-color">{rating.rating}/5</label></div>*/}

                      <div class="display-inline-flex">
                        <img src="https://openclipart.org/download/280989/user-icon.svg" />
                        <div class="mt10">By {ratings.reviewerScreenName}</div>
                      </div>
                      <div>
                        Rating -
                        <StarRatingComponent
                          name="rate1"
                          starCount={5}
                          value={ratings.rating}
                        />
                      </div>
                      <div>
                        Review -{" "}
                        <label class="overflow-wrap">
                          {ratings.ratingComment}
                        </label>
                      </div>
                      <hr />
                    </div>
                  ))}
                </div>
              ) : (
                <div className="details-font">Not Rated Yet</div>
              )}
            </div>
          </Grid>
        </div>
        <Modal
          aria-labelledby="simple-modal-title"
          aria-describedby="simple-modal-description"
          disableBackdropClick={true}
          open={this.state.open}
          onClose={this.handleClose}
        >
          <div className="modal-style">
            <Typography variant="h6" id="modal-title" class="title-modal">
              There is a problem!!
            </Typography>
            <Typography
              variant="subtitle1"
              id="simple-modal-description"
              class="main-text-font"
            >
              {this.state.errMsg}
            </Typography>
            {this.state.errMsg ==
            "Movie needs pay per view, please pay to view the movie" ? (
              <Link
                to={"/payperview/" + this.props.match.params.movie_id}
                target="_blank"
              >
                Pay Now
              </Link>
            ) : (
              <Link
                to={"/payperview/" + this.props.match.params.movie_id.id}
                target="_blank"
              >
                Subscribe
              </Link>
            )}
          </div>
        </Modal>
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
)(ViewMovieDetails);
