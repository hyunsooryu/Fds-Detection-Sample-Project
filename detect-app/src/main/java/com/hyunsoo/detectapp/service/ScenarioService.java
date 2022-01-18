package com.hyunsoo.detectapp.service;


import com.hyunsoo.detectapp.beans.Scenario;

import java.time.LocalDateTime;
import java.util.List;

public interface ScenarioService {
    //1. 현재 유효한 시나리오(탐색코드)인지
    boolean isValidScenarioNow(int scenarioId);

    //2. 새 시나리오(탐색코드) 넣기
    void insertScenario(Scenario scenario);

    //3. 시나리오 기간 변경
    void modifyScenarioPeriod(int scenarioId, LocalDateTime from, LocalDateTime to);

    //4. 시나리오 상태 변경
    void modifyScenarioWorkYn(int scenarioId, String workYn);

    //5. 시나리오 리스트 조회
    List<Scenario> getScenarioList();

    //6. 시나리오 존재 여부 조회
    boolean isAlreadyRegisterdScenario(int scenarioId);

}
