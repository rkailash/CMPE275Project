package com.movie.central.MovieCentral.service;

import com.movie.central.MovieCentral.model.CustomUserDetails;
import com.movie.central.MovieCentral.model.Customer;
import com.movie.central.MovieCentral.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> optionalUsers = customerRepository.findDistinctByEmail(email);
        optionalUsers.orElseThrow(() -> new UsernameNotFoundException("User does not exist with this email"));
        CustomUserDetails customUserDetails = optionalUsers.map(user -> {
                    return new CustomUserDetails(user);
                }).get();
        return customUserDetails;
    }
}
