package org.senla.statisticsservice.repository;

import org.senla.statisticsservice.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {


    @Query("SELECT s.createDate, s.countSensors, s.statisticsByType " +
            "FROM Statistics s " +
            "WHERE s.createDate BETWEEN :startDate AND :endDate ")
//            "GROUP BY s.createDate " +
//            "ORDER BY s.createDate DESC " +
//            "LIMIT 1")
    List<Object[]> findDailyStatsBetweenDates(LocalDate startDate, LocalDate endDate);

}
