package org.senla.statisticsservice.controller;

import lombok.RequiredArgsConstructor;
import org.senla.statisticsservice.service.StatisticServiceImp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticServiceImp statisticService;

    @GetMapping
    public ResponseEntity<Integer> statisticsCount(){
        return ResponseEntity.ok(statisticService.getStatistic());
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<String>> getStatisticsByDateRange(
            @RequestParam("from")
            @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate from,

            @RequestParam("to")
            @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate to) {
        if (from.isAfter(to)) {
            return ResponseEntity.badRequest().build();
        }
        List<String> result = statisticService.getStatisticByDate(from, to);
        return ResponseEntity.ok(result);
    }

}
