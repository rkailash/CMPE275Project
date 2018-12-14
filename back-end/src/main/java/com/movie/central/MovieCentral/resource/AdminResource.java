package com.movie.central.MovieCentral.resource;

import com.movie.central.MovieCentral.enums.SubscriptionType;
import com.movie.central.MovieCentral.model.Billing;
import com.movie.central.MovieCentral.model.Customer;
import com.movie.central.MovieCentral.model.ReportingStructureIncome;
import com.movie.central.MovieCentral.model.ReportingStructureUser;
import com.movie.central.MovieCentral.service.CustomerService;
import com.movie.central.MovieCentral.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RequestMapping("/api/admin/")
@RestController
public class AdminResource {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private FinancialReportService financialReportService;

    @RequestMapping(value = "/uniqueSubscriptionUsers", method = RequestMethod.POST)
    public ResponseEntity<?> getUniqueSubscriptionUsersPerMonth(@RequestBody Map<String, String> input , HttpSession session) throws Exception {
        int month = Integer.valueOf(input.get("month"));
        int year = Integer.valueOf(input.get("year"));
        Integer subscriptionUsers = financialReportService.getUniqueSubscriptionUsersGivenMonth(month, year);
        Map<String, Integer> response = new HashMap<>();
        response.put("result", subscriptionUsers);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/uniquePayPerViewUsers", method = RequestMethod.POST)
    public ResponseEntity<?> getUniquePayPerViewUsersPerMonth(@RequestBody Map<String, String> input , HttpSession session) throws Exception {
        int month = Integer.valueOf(input.get("month"));
        int year = Integer.valueOf(input.get("year"));
        Integer payPerViewUsers = financialReportService.getUniquePayPerViewUsersGivenMonth(month, year);
        Map<String, Integer> response = new HashMap<>();
        response.put("result", payPerViewUsers);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/allUniqueUsers", method = RequestMethod.POST)
    public ResponseEntity<?> getAllUniqueCustomersForGivenMonth(@RequestBody Map<String, String> input , HttpSession session) throws Exception {
        int month = Integer.valueOf(input.get("month"));
        int year = Integer.valueOf(input.get("year"));
        Integer allList = financialReportService.getAllUniqueRegisteredUsersGivenMonth(month, year);
        Map<String, Integer> response = new HashMap<>();
        response.put("result", allList);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/subscriptionIncome", method = RequestMethod.POST)
    public ResponseEntity<?> getSubscriptionIncomeGivenMonth(@RequestBody Map<String, String> input , HttpSession session) throws Exception {
        int month = Integer.valueOf(input.get("month"));
        int year = Integer.valueOf(input.get("year"));
        Double subscriptionIncomeForMonth = financialReportService.getSubscriptionOrPayPerViewIncome(month, year, SubscriptionType.SUBSCRIPTION);
        Map<String, Double> response = new HashMap<>();
        response.put("result", subscriptionIncomeForMonth);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/payPerViewIncome", method = RequestMethod.POST)
    public ResponseEntity<?> getPayPerViewIncomeGivenMonth(@RequestBody Map<String, String> input , HttpSession session) throws Exception {
        int month = Integer.valueOf(input.get("month"));
        int year = Integer.valueOf(input.get("year"));
        Double payPerViewIncomePerMonth = financialReportService.getSubscriptionOrPayPerViewIncome(month, year, SubscriptionType.PAY_PER_VIEW);
        Map<String, Double> response = new HashMap<>();
        response.put("result", payPerViewIncomePerMonth);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/totalIncome", method = RequestMethod.POST)
    public ResponseEntity<?> getTotalIncomeGivenMonth(@RequestBody Map<String, String> input , HttpSession session) throws Exception {
        int month = Integer.valueOf(input.get("month"));
        int year = Integer.valueOf(input.get("year"));
        Double subscriptionIncomeForMonth = financialReportService.getSubscriptionOrPayPerViewIncome(month, year, SubscriptionType.SUBSCRIPTION);
        Double payPerViewIncomePerMonth = financialReportService.getSubscriptionOrPayPerViewIncome(month, year, SubscriptionType.PAY_PER_VIEW);
        Map<String, Double> response = new HashMap<>();
        response.put("result", subscriptionIncomeForMonth + payPerViewIncomePerMonth);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/uniqueActiveUsers", method = RequestMethod.POST)
    public ResponseEntity<?> getUniqueActiveUsersGivenMonth(@RequestBody Map<String, String> input , HttpSession session) throws Exception {
        int month = Integer.valueOf(input.get("month"));
        int year = Integer.valueOf(input.get("year"));
        Integer activeUsersPerMonth = financialReportService.getAllUniqueActiveUsers(month, year);
        Map<String, Integer> response = new HashMap<>();
        response.put("result", activeUsersPerMonth);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/usersReporting", method = RequestMethod.GET)
    public ResponseEntity<?> getReportingForUsersForLast12Months(HttpSession session) throws Exception {
        List<ReportingStructureUser> reportingStructureUsers = financialReportService.getReportingForUsersForLast12Months();
        Map<String, List<ReportingStructureUser>> response = new HashMap<>();
        response.put("result", reportingStructureUsers);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/incomeReporting", method = RequestMethod.GET)
    public ResponseEntity<?> getReportingForIncomeForLast12Months(HttpSession session) throws Exception {
        List<ReportingStructureIncome> reportingStructureIncomes = financialReportService.getReportingForIncomeForLast12Months();
        Map<String, List<ReportingStructureIncome>> response = new HashMap<>();
        response.put("result", reportingStructureIncomes);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


}

