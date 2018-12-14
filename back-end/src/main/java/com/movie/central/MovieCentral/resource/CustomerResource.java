package com.movie.central.MovieCentral.resource;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.central.MovieCentral.enums.AuthType;
import com.movie.central.MovieCentral.enums.UserRole;
import com.movie.central.MovieCentral.exceptions.Error;
import com.movie.central.MovieCentral.exceptions.MovieCentralException;
import com.movie.central.MovieCentral.model.Customer;

import com.movie.central.MovieCentral.model.CustomerRating;
import com.movie.central.MovieCentral.model.Movie;
import com.movie.central.MovieCentral.model.PlayHistory;
import com.movie.central.MovieCentral.response.PlayDetails;

import com.movie.central.MovieCentral.response.Response;
import com.movie.central.MovieCentral.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/api/customer")
@RestController
public class CustomerResource {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody Map<String, String> input, HttpSession session) throws Exception{

        try{
            String name = input.get("name");
            String email = input.get("email");
            String screenName = input.get("screenName");
            String password = input.get("password");
            AuthType authType;
            if(input.get("authType")!=null)
                authType = AuthType.getByName(input.get("authType"));
            else
                authType = AuthType.LOCAL;
            UserRole role = UserRole.CUSTOMER;
            if(email.contains("@sjsu.edu")){
                role = UserRole.ADMIN;
            }

            password = bCryptPasswordEncoder.encode(password);
            Customer customer = Customer.builder().name(name).email(email).screenName(screenName)
                    .password(password).authType(authType).registrationDateTime(LocalDateTime.now(ZoneId.systemDefault()))
                    .userRole(role).build();
            customerService.register(customer);
            Response response = new Response("Customer Registered Successfully", HttpStatus.CREATED);
            return new ResponseEntity(response, response.getStatus());
        }catch(DataIntegrityViolationException ex){
            throw new MovieCentralException(Error.DUPLICATE_USER);
        }

    }

    @RequestMapping(value = "/login", headers = "Accept=application/json", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> login(@RequestBody Map<String, String> input, HttpSession session){
        try{
            String email = input.get("email");
            String password = input.get("password");
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = this.authenticationManager.authenticate(token);
            SecurityContext context = SecurityContextHolder.getContext();
            SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
            context.setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);
            return new ResponseEntity<>(authentication.getPrincipal(), HttpStatus.OK);

        }catch(Exception e){
            System.out.println(e.getMessage());

        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/isLoggedIn", method = RequestMethod.GET)
    public ResponseEntity<?> isLoggedIn(HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal().equals("anonymousUser"))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.invalidate();
    }


    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<?> subscribe(@RequestBody Map<String,String> input, HttpSession session) throws Exception{
        Long customerId = Long.valueOf(input.get("customerId"));
        Integer noOfMonths = Integer.valueOf(input.get("months"));
        if(noOfMonths < 1)
            throw new MovieCentralException(Error.INVALID_SUBSCRIPTION_MONTHS);
        Double price = Double.valueOf(input.get("price"));
        customerService.subscribe(customerId, noOfMonths,price);
        Response response = new Response("Subscription was successful", HttpStatus.OK);
        return new ResponseEntity(response, response.getStatus());
    }


    @RequestMapping(value = "/subscribe-payperview", method = RequestMethod.POST)
    public ResponseEntity<?> subscribeForPayPerView(@RequestBody Map<String,String> input, HttpSession session) throws Exception{
        Long customerId = Long.valueOf(input.get("customerId"));
        Long movieId = Long.valueOf(input.get("movieId"));
        Double price = Double.valueOf(input.get("price"));
        customerService.subscribePayPerView(customerId, movieId, price);
        Response response = new Response("Subscription was successful", HttpStatus.OK);
        return new ResponseEntity(response, response.getStatus());
    }

    @RequestMapping(value = "/subscription-status", method = RequestMethod.GET)
    public ResponseEntity<?> getBillingStatus(@RequestParam Long customerId, HttpSession session) throws Exception{
        LocalDateTime subEndTime = customerService.getBillingStatus(customerId);
        Map<String,String> response = new HashMap<>();
        response.put("result" , subEndTime==null || subEndTime.isBefore(LocalDateTime.now(ZoneId.systemDefault())) ? "Not Subscribed" : subEndTime.toString());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-customer-details", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerDetails(@RequestParam String id, HttpSession session) throws Exception{
        Long customerId = Long.parseLong(id);
        Customer customer = customerService.getCustomerDetails(customerId);
        Map<String,Customer> response = new HashMap<>();
        response.put("result" , customer);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-customer-watch-history", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerWatchHistory(@RequestParam String id, HttpSession session) throws Exception{
        Long customerId = Long.parseLong(id);
        List<PlayHistory> watchHistory = customerService.getCustomerWatchHistory(customerId);
        Map<String,List<PlayHistory>> response = new HashMap<>();
        response.put("result" , watchHistory);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/get_most_active_customers", method = RequestMethod.GET)
    public ResponseEntity<?> getMostActiveCustomers(HttpSession session) throws Exception{

        List<PlayDetails> play_details = customerService.getMostActiveCustomers();

        Map<String,List<PlayDetails>> response = new HashMap<>();
        response.put("result" , play_details);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/searchAll", method = RequestMethod.GET)
    public ResponseEntity<?> findAllCustomers(HttpSession session) throws Exception{
        Map<String,List<Customer>> response = new HashMap<>();
        List<Customer> customers = customerService.findAllCustomers();
        response.put("result" , customers);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/reviews", method = RequestMethod.GET)
    public ResponseEntity<?> getAllReviews(@RequestParam Long customerId, HttpSession session) throws Exception{
        List<CustomerRating> ratings = customerService.getAllCustomerRatings(customerId);
        Map<String, List<CustomerRating>> response = new HashMap<>();
        response.put("result", ratings);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
