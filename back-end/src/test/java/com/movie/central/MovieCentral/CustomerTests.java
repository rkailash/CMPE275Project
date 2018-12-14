package com.movie.central.MovieCentral;

import com.movie.central.MovieCentral.model.Customer;
import com.movie.central.MovieCentral.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTests {

    @Autowired
    CustomerRepository customerRepository;


    @Test
    public void testSaveCustomer(){
//       Customer.
    }
}
