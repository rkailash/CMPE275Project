package com.movie.central.MovieCentral.service;

import com.movie.central.MovieCentral.enums.Genre;
import com.movie.central.MovieCentral.enums.MovieType;
import com.movie.central.MovieCentral.enums.MpaaRating;
import com.movie.central.MovieCentral.model.*;
import com.movie.central.MovieCentral.repository.*;
import com.movie.central.MovieCentral.util.LocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.central.MovieCentral.model.Actor;
import com.movie.central.MovieCentral.model.Customer;
import com.movie.central.MovieCentral.model.Director;
import com.movie.central.MovieCentral.model.Movie;
import com.movie.central.MovieCentral.exceptions.Error;
import com.movie.central.MovieCentral.exceptions.MovieCentralException;
import com.movie.central.MovieCentral.repository.*;
import com.movie.central.MovieCentral.response.PlayDetails;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerRatingRepository customerRatingRepository;

    @Autowired
    PlayHistoryRepository playHistoryRepository;

    @Autowired
    BillingRepository billingRepository;


    public void addMovie(Movie movie) throws Exception{
        Movie movieFound = movieRepository.findByTitleAndReleaseYear(movie.getTitle(), movie.getReleaseYear());
//        List<Movie> list =  findByEmail_idAndPassword(user.getEmail_id(), user.getPassword());
        if (movieFound != null) {
            throw new MovieCentralException(Error.DUPLICATE_MOVIE);
        }else{
            Movie newMovie = movieRepository.save(movie);

            System.out.println("New Id of movie"+newMovie.getId());
        }
    }

    public void editMovie(Movie movie) throws Exception{
        Movie movieFound = movieRepository.findMovieById(movie.getId());
        movieFound.setTitle(movie.getTitle());
        movieFound.setGenre(movie.getGenre());
        movieFound.setReleaseYear(movie.getReleaseYear());
        movieFound.setStudio(movie.getStudio());
        movieFound.setSynopsys(movie.getSynopsys());
        movieFound.setImageUrl(movie.getImageUrl());
        movieFound.setMovieUrl(movie.getMovieUrl());
        movieFound.setAverageRating(movie.getAverageRating());
        movieFound.setCountry(movie.getCountry());
        movieFound.setType(movie.getType());
        movieFound.setPrice(movie.getPrice());
        movieFound.setMpaaRating(movie.getMpaaRating());
        movieFound.setDirector(movie.getDirector());
        List<Actor> actors = movie.getActors();
        movieFound.setActors(actors);

        Movie newMovie = movieRepository.save(movieFound);
    }

    public Movie getMovieById(Long id){
        return movieRepository.findMovieById(id);
    }


    public Set<Movie> searchMoviesByKeywords(String[] keywords){
        Set<Movie> result = new HashSet<>();
        if(keywords.length<1)
            return result;
        result.addAll(searchMoviesByKeyword(keywords[0]));

        for(int i=1; i<keywords.length; i++){
            result = getIntersectionTwoSets(result, searchMoviesByKeyword(keywords[i]));
        }
        return result;
    }

    public Set<Movie> searchMoviesByKeyword(String keyword){
        HashSet<Movie> result = new HashSet<>();
        result.addAll(movieRepository.findMoviesByAttributes(keyword, keyword, keyword, keyword, keyword, keyword));
        result.addAll(actorRepository.findMoviesByActor(keyword));
        result.addAll(directorRepository.findMoviesByDirector(keyword));
        return result;
    }

    public Set<Movie> getIntersectionTwoSets(Set<Movie> movies1, Set<Movie> movies2){
        movies1.retainAll(movies2);
        return movies1;
    }

    public Set<Movie> getIntersectionMultipleSets(List<Set<Movie>> movieSets){
        Set<Movie> result = new HashSet<>();
        if(movieSets.size()<1)
            return result;

        result.addAll(movieSets.get(0));
        for(int i=1; i<movieSets.size(); i++){
            result.retainAll(movieSets.get(i));
        }
        return result;
    }

    public List<Movie> filterByGenres(List<Genre> genres){
        return movieRepository.findDistinctByGenreIsIn(genres);
    }

    public List<Movie> filterByReleaseYears(Integer releaseYear){
        return movieRepository.findDistinctByReleaseYearIs(releaseYear);
    }

    public List<Movie> filterByActors(String actorName){
        return new ArrayList<>(actorRepository.findMoviesByActors(actorName));
    }

    public List<Movie> filterByDirectors(String directorName){
        return new ArrayList<>(directorRepository.findMoviesByDirector(directorName));
    }

    public List<Movie> filterByMpaaRatings(List<MpaaRating> mpaaRatings){
        return movieRepository.findDistinctByMpaaRatingIsIn(mpaaRatings);
    }


    public List<Movie> filterByAverageRatingGreaterThan(Double averageRating){
        return movieRepository.findDistinctByAverageRatingIsGreaterThanEqual(averageRating);
    }

    public List<Movie> findAllMovies(){
        return movieRepository.findAll();
    }

    public boolean isEmptyFilter(MovieFilter movieFilter){
        if((movieFilter.getKeywords()==null || movieFilter.getKeywords()=="")
                && (movieFilter.getAverageRating()==null || movieFilter.getAverageRating()==0.0)
                && (movieFilter.getMpaaRatings()==null || movieFilter.getMpaaRatings().size()==0)
                && (movieFilter.getGenres()==null || movieFilter.getGenres().size()==0)
                && (movieFilter.getDirector()==null || movieFilter.getDirector()=="")
                && (movieFilter.getActor()==null || movieFilter.getActor()=="")
                && (movieFilter.getReleaseYear()==null || movieFilter.getReleaseYear()==0))
            return true;
        return false;
    }

    public List<Movie> filterMoviesByMovieFilter(MovieFilter movieFilter){

        //check if filter is null , if yes, return all movies
        if(isEmptyFilter(movieFilter))
            return findAllMovies();

        List<Movie> result = new ArrayList<>();

        List<Set<Movie>> movieSets = new ArrayList<>();
        Set<Movie> genreMovies = new HashSet<>();

        if(movieFilter.getGenres()!= null && !movieFilter.getGenres().isEmpty()){
            genreMovies.addAll(filterByGenres(movieFilter.getGenres()));
            movieSets.add(genreMovies);
        }

        Set<Movie> releaseYearMovies = new HashSet<>();
        if(movieFilter.getReleaseYear() != null && movieFilter.getReleaseYear()!=0){
            releaseYearMovies.addAll(filterByReleaseYears(movieFilter.getReleaseYear()));
            movieSets.add(releaseYearMovies);
        }

        Set<Movie> actorMovies = new HashSet<>();
        if(movieFilter.getActor()!=null && movieFilter.getActor()!=""){
            actorMovies.addAll(filterByActors(movieFilter.getActor()));
            movieSets.add(actorMovies);
        }

        Set<Movie> directorMovies = new HashSet<>();
        if(movieFilter.getDirector() != null && movieFilter.getDirector()!=""){
            directorMovies.addAll(filterByDirectors(movieFilter.getDirector()));
            movieSets.add(directorMovies);
        }

        Set<Movie> mpaaRatingMovies = new HashSet<>();
        if(movieFilter.getMpaaRatings() != null && !movieFilter.getMpaaRatings().isEmpty()){
            mpaaRatingMovies.addAll(filterByMpaaRatings(movieFilter.getMpaaRatings()));
            movieSets.add(mpaaRatingMovies);
        }

        Set<Movie> averatingRatingMovies = new HashSet<>();
        if(movieFilter.getAverageRating() != null && !movieFilter.getAverageRating().isNaN() && movieFilter.getAverageRating()!=0.0){
            averatingRatingMovies.addAll(filterByAverageRatingGreaterThan(movieFilter.getAverageRating()));
            movieSets.add(averatingRatingMovies);
        }

        Set<Movie> keywordSearchMovies = new HashSet<>();
        if(movieFilter.getKeywords() != null && movieFilter.getKeywords()!=""){
            keywordSearchMovies.addAll(searchMoviesByKeywords(movieFilter.getKeywords().split(" ")));
            movieSets.add(keywordSearchMovies);
        }

        Set<Movie> commonMovies = getIntersectionMultipleSets(movieSets);

        result.addAll(commonMovies);
        return result;
    }

    public void reviewMovie(Long customerId, Long movieId, Integer rating, String comment){

        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(customer.isPresent() && movie.isPresent()){
            Customer c = customer.get();
            Movie m = movie.get();
            boolean alreadyReviewed = customerAlreadyReviewed(c,m);

            // Update the average rating of the movie
            if(!alreadyReviewed){
                CustomerRatingId customerRatingId = new CustomerRatingId(c, m);
                CustomerRating customerRating = CustomerRating.builder().customerRatingId(customerRatingId)
                        .rating(rating).ratingComment(comment).ratingTime(LocalDateTime.now(ZoneId.systemDefault())).build();
                Integer previousRatingCount = m.getRatings().size();
                customerRating.setReviewerScreenName(c.getScreenName());
                Double newAverageRating = (m.getAverageRating()+rating)/(previousRatingCount+1);
                m.setAverageRating(newAverageRating);
                movieRepository.save(m);
                customerRatingRepository.save(customerRating);
            }

        }
    }

    public boolean customerAlreadyReviewed(Customer c, Movie m){
        List<CustomerRating> ratings = customerRatingRepository.
                findDistinctByCustomerRatingIdCustomerAndCustomerRatingIdMovie(c,m);
        return ratings!= null && ratings.size() >0;
    }

    public List<CustomerRating> getAllMovieRatings(Long movieId){
        Optional<Movie> movie = movieRepository.findById(movieId);
        List<CustomerRating> ratings = new ArrayList<>();
        if(movie.isPresent()){
            ratings = movie.get().getRatings();
        }
        return ratings;
    }

    public List<PlayDetails> getPlayPerMovie() throws Exception {
        List<Object[]> playDetails = playHistoryRepository.getPlayPerMovie();
        List<PlayDetails> playDetailsNew = new ArrayList<PlayDetails>();

        try {
            if (playDetails != null && playDetails.size() > 0) {


                for (Object[] obj : playDetails) {
                    PlayDetails playDet = new PlayDetails();
                    playDet.setId((BigInteger)obj[0]);
                    playDet.setName((String) obj[1]);
                    playDet.setPlayCount((BigInteger)obj[2]);
                    playDetailsNew.add(playDet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playDetailsNew;

    }

    public List<PlayDetails> getMostPlayedMovies() throws Exception {
        List<Object[]> playDetails = playHistoryRepository.getMostPlayedMovies();
        List<PlayDetails> playDetailsNew = new ArrayList<PlayDetails>();

        try {
            if (playDetails != null && playDetails.size() > 0) {
                for (Object[] obj : playDetails) {
                    PlayDetails playDet = new PlayDetails();
                    playDet.setId((BigInteger)obj[0]);
                    playDet.setName((String) obj[1]);
                    playDet.setPlayCount((BigInteger)obj[2]);
                    playDetailsNew.add(playDet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playDetailsNew;

    }
//  public JSONArray getPlayPerMovie() throws Exception{
//        List <Object[]> playDetails = playHistoryRepository.getPlayPerMovie();
//        List <JSONObject> playDetailsNew = new ArrayList<JSONObject>();
//        JSONArray jsonArray = new JSONArray();
////        Gson gson = new Gson();
////        String
//        try{
//        if(playDetails != null && playDetails.size()>0){
//
//
//            for(Object[] obj : playDetails){
//                JSONObject jsonObject = new JSONObject();
//                //BigInteger id  = Big;
//                jsonObject.put("id",obj[0]);
//                jsonObject.put("title",(String)obj[1]);
//                jsonObject.put("count",obj[2]);
//                jsonArray.put(jsonObject);
//                //jsonObject.put("count",Integer.valueOf((String)obj[2]));
//                //playDetailsNew.add(jsonObject);
//            }
//        }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return jsonArray;
//    }



    public void isMoviePlayAllowedForCustomer(Long customerId, Long movieId) throws Exception{
        Optional<Movie> movie = movieRepository.findById(movieId);
        Optional<Customer> customer = customerRepository.findById(customerId);

        if(customer.isPresent() && movie.isPresent()){
            Customer c = customer.get();
            Movie m = movie.get();
            List<Billing> billings = new ArrayList<>();
            if(m.getType().equals(MovieType.PAY_PER_VIEW)){
                // check if user paid for the pay per view movie
                billings = billingRepository.findDistinctByCustomerAndMovieAndPlayCountExhausted(c,m,false);
                if(billings.size()<=0)
                    throw new MovieCentralException(Error.MOVIE_NEEDS_PAYPERVIEW);

            }else if(m.getType().equals(MovieType.SUBSCRIPTION_ONLY)) {
                // Check if customer is subscribed
                LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
                billings = billingRepository.findDistinctByCustomerAndEndTimeGreaterThan(c, now);
                if (billings.size() <= 0)
                    throw new MovieCentralException(Error.MOVIE_NEEDS_SUBSCRIPTION);
            }
        }

    }


}
