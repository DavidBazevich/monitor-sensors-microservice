package org.senla.statisticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorStatisticSender {
    private String name;
    private String model;
    private String type;
    private String unit;
}