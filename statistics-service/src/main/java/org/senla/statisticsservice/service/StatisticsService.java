package org.senla.statisticsservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.senla.statisticsservice.dto.SensorStatisticSender;
import org.senla.statisticsservice.dto.StatisticMapper;
import org.senla.statisticsservice.entity.Statistics;
import org.senla.statisticsservice.repository.SensorRepository;
import org.senla.statisticsservice.repository.StatisticsRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService implements StatisticServiceImp {

    private final SensorRepository sensorRepository;
    private final StatisticsRepository statisticsRepository;
    private final ObjectMapper objectMapper;
    private final StatisticMapper statisticMapper;


    @KafkaListener(topics = "statistics", groupId = "group")
    public void save(String sensors) throws JsonProcessingException {
        List<SensorStatisticSender> sensorList = objectMapper.readValue(sensors,
                new TypeReference<List<SensorStatisticSender>>() {});
        sensorList.stream()
                .map(statisticMapper::toSensor)
                .forEach(sensorRepository::save);
        saveData();
    }

    private void saveData() throws JsonProcessingException {
        Integer statisticsCount = sensorRepository.obtainStatisticsByAllTypes();
        List<Object[]> statType = sensorRepository.obtainStatisticsByTypes();
        String data = objectMapper.writeValueAsString(
                statType.stream()
                        .map(arr -> Map.of("type", arr[0], "count", arr[1]))
                        .collect(Collectors.toList())
        );
        statisticsRepository.save(Statistics.builder().countSensors(statisticsCount).statisticsByType(data).build());
    }

    public Integer getStatistic(){
        return sensorRepository.obtainStatisticsByAllTypes();
    }

    public List<String> getStatisticByDate(LocalDate fromDate, LocalDate toDate){
        List<Object[]> results = statisticsRepository.findDailyStatsBetweenDates(fromDate, toDate);
        return results.stream()
                .map(result -> String.format("Date: %s, Count sensors: %d, Statistics by type: %s",
                        result[0], result[1], result[2]))
                .toList();
    }

}
