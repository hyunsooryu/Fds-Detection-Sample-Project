package com.hyunsoo.detectapp.repository.cache;

import com.hyunsoo.detectapp.beans.Scenario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScenarioCacheRepository extends CrudRepository<Scenario, Integer> {

}
