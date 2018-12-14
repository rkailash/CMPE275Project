package com.movie.central.MovieCentral.service;

import com.movie.central.MovieCentral.enums.SubscriptionType;
import com.movie.central.MovieCentral.enums.UserRole;
import com.movie.central.MovieCentral.exceptions.Error;
import com.movie.central.MovieCentral.exceptions.MovieCentralException;
import com.movie.central.MovieCentral.model.Billing;
import com.movie.central.MovieCentral.model.Customer;

import com.movie.central.MovieCentral.model.CustomerRating;
import com.movie.central.MovieCentral.model.Movie;

import com.movie.central.MovieCentral.model.PlayHistory;

import com.movie.central.MovieCentral.repository.*;
import com.movie.central.MovieCentral.response.PlayDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PlayHistoryRepository playHistoryRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    CustomerRatingRepository customerRatingRepository;

    @Autowired
    MovieRepository movieRepository;

    public void register(Customer customer) throws Exception {

        LocalDateTime startTime = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime endTime = getSubscriptionEndDate(startTime, 1).withHour(0).withMinute(0).withSecond(0);
        customer.setSubscriptionEndTime(endTime);
        customerRepository.save(customer);
    }

    private LocalDateTime getSubscriptionEndDate(LocalDateTime startTime, Integer months) {
        LocalDateTime endDate = startTime.plusMonths(months).withHour(0).withMinute(0).withSecond(0).withNano(0);
        System.out.println(endDate + " " + ZoneId.systemDefault());
        return endDate;
    }

    // TODO - Apply aspect to change customer subscription end date
    public void subscribe(Long customerId, Integer months, Double totalAmount) throws Exception{
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
                Customer c = customer.get();
                LocalDateTime startTime = LocalDateTime.now(ZoneId.systemDefault());
                LocalDateTime endTime = getSubscriptionEndDate(startTime, months).withHour(0).withMinute(0).withSecond(0);
                c.setSubscriptionEndTime(endTime);
                Billing userBilling = Billing.builder().customer(c).endTime(endTime).totalAmount(totalAmount).
                        startTime(startTime).subscriptionType(SubscriptionType.SUBSCRIPTION).build();
                billingRepository.save(userBilling);
                customerRepository.save(c);
        }else{
            throw new MovieCentralException(Error.USER_NOT_FOUND);
        }

    }

    public void subscribePayPerView(Long customerId, Long movieId, Double totalAmount) throws Exception{
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            Customer c = customer.get();
            Optional<Movie> movie = movieRepository.findById(movieId);
            if(movie.isPresent()){
                Movie m = movie.get();
                LocalDateTime startTime = LocalDateTime.now(ZoneId.systemDefault());
                Billing userBilling = Billing.builder().customer(c).totalAmount(totalAmount).movie(m).startTime(startTime)
                        .subscriptionType(SubscriptionType.PAY_PER_VIEW).build();
                billingRepository.save(userBilling);
            }else{
                throw new MovieCentralException(Error.MOVIE_NOT_FOUND);
            }
        }else{
            throw new MovieCentralException(Error.USER_NOT_FOUND);
        }

    }

    public LocalDateTime getBillingStatus(Long customerId) throws Exception{
        Optional<Customer> customer = customerRepository.findById(customerId);
       if(customer.isPresent()){
           return customer.get().getSubscriptionEndTime();
       }else{
           throw new MovieCentralException(Error.USER_NOT_FOUND);
       }
    }


    public List<Customer> findAllCustomers(){
        return customerRepository.findDistinctByUserRole(UserRole.CUSTOMER);
    }

    public List<CustomerRating> getAllCustomerRatings(Long customerId){
        Optional<Customer> customer = customerRepository.findById(customerId);
        List<CustomerRating> ratings = new ArrayList<>();
        if(customer.isPresent()){
            ratings = customer.get().getRatings();
        }
        return ratings;
    }

    public Customer getCustomerDetails(Long customerId) throws Exception{
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            Customer c = customer.get();
            return c;
        }else{
            throw new MovieCentralException(Error.USER_NOT_FOUND);
        }
    }

    public List<PlayHistory> getCustomerWatchHistory(Long customerId) throws Exception{
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            Customer c = customer.get();
            List<PlayHistory> playHistory = playHistoryRepository.findMovieAndPlayHistoryByCustomer_Id(customerId);
            List<PlayHistory> responsePlayHistory = new ArrayList<PlayHistory>();
            //List<PlayHistory> playHistory = playHistoryRepository.findMovieAndPlayHistoryByCustomer_IdCheck(customerId);

            for (PlayHistory history: playHistory) {
                PlayHistory updatedHistory = new PlayHistory();
                updatedHistory = history;
                updatedHistory.getCustomer().setPassword("");
                responsePlayHistory.add(updatedHistory);
            }
            return responsePlayHistory;
        }else{
            throw new MovieCentralException(Error.USER_NOT_FOUND);
        }
    }

    public List<PlayDetails> getMostActiveCustomers() throws Exception {
        List<Object[]> playDetails = playHistoryRepository.getMostActiveCustomers();
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

}
