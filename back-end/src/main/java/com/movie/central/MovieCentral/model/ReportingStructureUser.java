package com.movie.central.MovieCentral.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
public class ReportingStructureUser implements Serializable {
    public String name;
    public Integer SubscriptionUsers;
    public Integer PayPerViewUsers;
    public Integer UniqueUsers;
    public Integer UniqueActiveUsers;
}
