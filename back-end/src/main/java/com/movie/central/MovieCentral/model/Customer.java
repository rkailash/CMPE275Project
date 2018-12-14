package com.movie.central.MovieCentral.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.movie.central.MovieCentral.enums.AuthType;
import com.movie.central.MovieCentral.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="screen_name")
    private String screenName;


    @Column(name="password")
    private String password;

    @Column(name="auth_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthType authType;

//    @Column(name="subscription_start_time")
//    private LocalDateTime subscriptionStartTime;

    @Column(name="subscription_end_time")
    private LocalDateTime subscriptionEndTime;

    @Column(name="user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name="is_account_verified", columnDefinition = "tinyint(1) default 0")
    private boolean isAccountVerified;

    @JsonManagedReference
    @JsonUnwrapped
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "customerRatingId.customer")
    private List<CustomerRating> ratings;

    @Column(name="registration_time")
    private LocalDateTime registrationDateTime;

    public Customer(Customer customer){
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.screenName = customer.getScreenName();
        this.password = customer.getPassword();
        this.authType = customer.getAuthType();
        this.subscriptionEndTime = customer.getSubscriptionEndTime();
        this.userRole = customer.getUserRole();
        this.isAccountVerified = customer.isAccountVerified();
        this.ratings = customer.getRatings();
        this.registrationDateTime = customer.getRegistrationDateTime();
    }


}

