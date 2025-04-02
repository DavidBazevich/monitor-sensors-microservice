package org.senla.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"unit", "type", "range"})
@EqualsAndHashCode(of = "name")
@Builder
@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String model;
    private Range range;
    @ManyToOne(fetch = FetchType.LAZY)
    private Type type;
    @ManyToOne(fetch = FetchType.LAZY)
    private Units unit;
    private String location;
    private String description;

}
