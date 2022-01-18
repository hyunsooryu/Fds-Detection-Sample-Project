package com.hyunsoo.detectapp.repository.db;

import com.hyunsoo.detectapp.beans.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Integer> {

}
