package com.hyunsoo.detectapp.service;

import com.hyunsoo.detectapp.beans.Scenario;
import com.hyunsoo.detectapp.repository.cache.ScenarioCacheRepository;
import com.hyunsoo.detectapp.repository.db.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final ScenarioCacheRepository scenarioCacheRepository;

    private final ScenarioRepository scenarioRepository;

    @Override
    public boolean isValidScenarioNow(int scenarioId) {
        boolean isValid = false;
        try {
            Optional<Scenario> scenarioIdOptional = scenarioCacheRepository.findById(scenarioId);
            Scenario scenario = scenarioIdOptional.orElseGet(()-> {
                Optional<Scenario> scenarioFromDB = scenarioRepository.findById(scenarioId);
                Scenario targetScenario = scenarioFromDB.orElse(null);
                if(!Objects.isNull(targetScenario)) {
                    scenarioCacheRepository.save(targetScenario);
                    log.info("시나리오:{} 케시 저장 ", targetScenario.getScenarioId());
                }
                return targetScenario;
            });
            if(Objects.isNull(scenario)){
                isValid = false;
            }
            else if("N".equals(scenario.getWorkYn()) || scenario.getWorkYn() == null){
                isValid = false;
            }
            else {
                LocalDateTime from = scenario.getFrom();
                LocalDateTime to = scenario.getTo();
                LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
                isValid = ("Y".equals(scenario.getWorkYn())) &&
                        (now.isEqual(from) || now.isAfter(from)) &&
                        (now.isBefore(to) || now.isEqual(to));
            }
        }catch (Exception e){
            log.error("유효한 시나리오 여부 조회 에러 ", e);
        }
        return isValid;
    }

    @Override
    public void insertScenario(Scenario scenario) {
        try {
            scenarioRepository.save(scenario);
        }catch (Exception e){
            log.error("시나리오 DB 저장 중 에러", e);
        }
    }

    @Override
    public void modifyScenarioPeriod(int scenarioId, LocalDateTime from, LocalDateTime to) {
        try{
            Scenario scenario = scenarioRepository.getById(scenarioId);
            if(scenario == null){
                log.error("해당하는 시나리오가 없습니다.");
            }else{
                scenario.setFrom(from);
                scenario.setTo(to);
                scenarioRepository.save(scenario);
            }
        }catch (Exception e){
            log.error("시나리오 기한에 대하여 DB 수정 중 에러", e);
        }
    }

    @Override
    public void modifyScenarioWorkYn(int scenarioId, String workYn) {
        try{
            Scenario scenario = scenarioRepository.getById(scenarioId);
            if(scenario == null){
                log.error("해당하는 시나리오가 없습니다.");
            }else{
                scenario.setWorkYn(workYn);
                scenarioRepository.save(scenario);
            }
        }catch (Exception e){
            log.error("시나리오 작동 여부에 대하여 DB 수정 중 에러", e);
        }
    }

    @Override
    public List<Scenario> getScenarioList() {
        List<Scenario> scenarioList = Collections.EMPTY_LIST;
        try {
            scenarioList = scenarioRepository.findAll();
        }catch (Exception e){
            log.error("시나리오 리스트 DB 조회 중 에러", e);
        }
        return scenarioList;
    }

    @Override
    public boolean isAlreadyRegisterdScenario(int scenarioId) {
        Optional<Scenario> optionalScenario
                = scenarioCacheRepository.findById(scenarioId);
        if(optionalScenario.isPresent()){
            return true;
        }else{
            Optional<Scenario> tmpScenario = scenarioRepository.findById(scenarioId);
            if(tmpScenario.isPresent()){
                scenarioCacheRepository.save(tmpScenario.get());
                return true;
            }
        }
        return false;
    }
}
