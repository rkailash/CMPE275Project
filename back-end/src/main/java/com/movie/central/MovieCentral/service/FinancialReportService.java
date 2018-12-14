package com.movie.central.MovieCentral.service;

import com.movie.central.MovieCentral.enums.SubscriptionType;
import com.movie.central.MovieCentral.model.*;
import com.movie.central.MovieCentral.repository.BillingRepository;
import com.movie.central.MovieCentral.repository.CustomerRepository;
import com.movie.central.MovieCentral.repository.PlayHistoryRepository;
import com.movie.central.MovieCentral.util.LocalDateTimeUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinancialReportService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private PlayHistoryRepository playHistoryRepository;


    public Integer getUniqueSubscriptionUsersGivenMonth(int month, int year) throws Exception {
        LocalDateTime startDateTime = LocalDateTimeUtil.getFirstDayOfGivenMonth(month, year);

        LocalDateTime endDateTime = LocalDateTimeUtil.getLastDayOfGivenMonth(startDateTime);

        List<Billing> subscriptionList = billingRepository.findDistinctBySubscriptionTypeAndStartTimeGreaterThanEqualAndStartTimeLessThanEqual( SubscriptionType.SUBSCRIPTION, startDateTime, endDateTime);
        Set<Customer> uniqueSubscriptionUsers = subscriptionList.stream()
                .map(elem -> elem.getCustomer())
                .collect(Collectors.toSet());;
        return uniqueSubscriptionUsers.size();
    }

    public Integer getUniquePayPerViewUsersGivenMonth(int month, int year) throws Exception {
        LocalDateTime startDateTime = LocalDateTimeUtil.getFirstDayOfGivenMonth(month, year);
        LocalDateTime endDateTime = LocalDateTimeUtil.getLastDayOfGivenMonth(startDateTime);

        List<Billing> payPerViewList = billingRepository.findDistinctBySubscriptionTypeAndStartTimeGreaterThanEqualAndStartTimeLessThanEqual( SubscriptionType.PAY_PER_VIEW, startDateTime, endDateTime);
        Set<Customer> uniqueSubscriptionUsers = payPerViewList.stream()
                .map(elem -> elem.getCustomer())
                .collect(Collectors.toSet());;
        return uniqueSubscriptionUsers.size();
    }

    public Integer getAllUniqueRegisteredUsersGivenMonth(int month, int year) throws Exception {
        LocalDateTime startDateTime = LocalDateTimeUtil.getFirstDayOfGivenMonth(month, year);

        LocalDateTime endDateTime = LocalDateTimeUtil.getLastDayOfGivenMonth(startDateTime);

        List<Customer> allList = customerRepository.findDistinctByRegistrationDateTimeGreaterThanEqualAndRegistrationDateTimeLessThanEqual
                (startDateTime, endDateTime);
        return allList.size();
    }

    public Double getSubscriptionOrPayPerViewIncome(int month, int year, SubscriptionType subscriptionType) throws Exception {
        LocalDateTime startDateTime = LocalDateTimeUtil.getFirstDayOfGivenMonth(month, year);
        LocalDateTime endDateTime = LocalDateTimeUtil.getLastDayOfGivenMonth(startDateTime);

        List<Billing> subscriptionList = billingRepository.findDistinctBySubscriptionTypeAndStartTimeGreaterThanEqualAndStartTimeLessThanEqual(subscriptionType, startDateTime, endDateTime);
        Double income = subscriptionList.stream().mapToDouble(sub -> sub.getTotalAmount()).sum();
        return income;
    }

    public Integer getAllUniqueActiveUsers(int month, int year){
        LocalDateTime startDateTime = LocalDateTimeUtil.getFirstDayOfGivenMonth(month, year);
        LocalDateTime endDateTime = LocalDateTimeUtil.getLastDayOfGivenMonth(startDateTime);

        Long uniqueActiveUsers = playHistoryRepository.getActiveCustomersByPlayTime(startDateTime, endDateTime);
        return uniqueActiveUsers == null ? 0 : uniqueActiveUsers.intValue();
    }



    public List<ReportingStructureUser> getReportingForUsersForLast12Months() throws Exception{
        List<ReportingStructureUser> reportingStructureUsers = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        List<Integer> months = new ArrayList<>();
        List<Integer> years = new ArrayList<>();
        for(int i=1; i<=12; i++){
            Integer month = now.getMonthValue();
            Integer year = now.getYear();
            months.add(month);
            years.add(year);
            now = now.minusMonths(1);
        }
        if(months.size() == years.size()){
            for(int i=0; i<months.size(); i++){
                int month = months.get(i);
                Integer subscriptionUsers = getUniqueSubscriptionUsersGivenMonth(month, years.get(i));
                Integer payPerViewUsers = getUniquePayPerViewUsersGivenMonth(month, years.get(i));
                Integer registeredUsers = getAllUniqueRegisteredUsersGivenMonth(month, years.get(i));
                Integer activeUsers = getAllUniqueActiveUsers(month, years.get(i));
                ReportingStructureUser reportingStructureUser = ReportingStructureUser.builder().name(Month.of(month).name())
                        .SubscriptionUsers(subscriptionUsers).PayPerViewUsers(payPerViewUsers)
                        .UniqueUsers(registeredUsers).UniqueActiveUsers(activeUsers).build();
                reportingStructureUsers.add(reportingStructureUser);
            }
        }
        return reportingStructureUsers;

    }

    public List<ReportingStructureIncome> getReportingForIncomeForLast12Months() throws Exception{
        List<ReportingStructureIncome> reportingStructureIncomes= new ArrayList<>();
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        List<Integer> months = new ArrayList<>();
        List<Integer> years = new ArrayList<>();
        for(int i=1; i<=12; i++){
            Integer month = now.getMonthValue();
            Integer year = now.getYear();
            months.add(month);
            years.add(year);
            now = now.minusMonths(1);
        }
        if(months.size() == years.size()){
            for(int i=0; i<months.size(); i++){
                int month = months.get(i);
                Double subscriptionIncome = getSubscriptionOrPayPerViewIncome(month, years.get(i),SubscriptionType.SUBSCRIPTION);
                Double payPerViewIncome = getSubscriptionOrPayPerViewIncome(month, years.get(i),SubscriptionType.PAY_PER_VIEW);
                Double totalIncome = subscriptionIncome + payPerViewIncome;
                ReportingStructureIncome reportingStructureIncome = ReportingStructureIncome.builder().name(Month.of(month).name())
                        .SusbscriptionIncome(subscriptionIncome).PayPerViewIncome(payPerViewIncome)
                        .TotalIncome(totalIncome).build();
                reportingStructureIncomes.add(reportingStructureIncome);
            }
        }
        return reportingStructureIncomes;

    }

}