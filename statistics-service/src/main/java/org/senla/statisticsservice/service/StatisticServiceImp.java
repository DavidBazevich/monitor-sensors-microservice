package org.senla.statisticsservice.service;


import java.time.LocalDate;
import java.util.List;

public interface StatisticServiceImp {
    Integer getStatistic();
    List<String> getStatisticByDate(LocalDate fromDate, LocalDate toDate);
}
