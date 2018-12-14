package com.movie.central.MovieCentral.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public class ReportingStructureIncome implements Serializable {
    public String name;
    public Double SusbscriptionIncome;
    public Double PayPerViewIncome;
    public Double TotalIncome;
}
