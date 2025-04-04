package org.senla.statisticsservice.repository;

import org.senla.statisticsservice.dto.SensorStatisticSender;
import org.senla.statisticsservice.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    @Query("SELECT COUNT(*) FROM Sensor t ")
    Integer obtainStatisticsByAllTypes();
    @Query("select (s.type, COUNT(*)) from Sensor s group by s.type")
    List<Object[]> obtainStatisticsByTypes();

}
