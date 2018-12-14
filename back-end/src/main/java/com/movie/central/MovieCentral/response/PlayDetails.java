package com.movie.central.MovieCentral.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Data
@Getter
@Setter
public class PlayDetails {
    private BigInteger id;
    private BigInteger playCount;
    private String name;

}
