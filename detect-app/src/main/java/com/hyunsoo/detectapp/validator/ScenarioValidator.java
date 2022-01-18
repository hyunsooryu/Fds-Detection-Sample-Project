package com.hyunsoo.detectapp.validator;

import com.hyunsoo.detectapp.beans.Scenario;
import com.hyunsoo.detectapp.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ScenarioValidator implements Validator {

    private final ScenarioService scenarioService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Scenario.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Scenario scenario = (Scenario) target;
        if(scenario.getScenarioId() <= 1000 || scenario.getScenarioId() > 5000){
            errors.rejectValue("scenarioId", "boundary", "제한 값을 초과합니다.");
            return;
        }
        if(scenarioService.isAlreadyRegisterdScenario(scenario.getScenarioId())){
            errors.rejectValue("scenarioId", "alreadyRegisterd", "이미 등록된 시나리오(탐지코드) 입니다.");
            return;
        }
        if(scenario.getTo().isBefore(scenario.getFrom())){
            errors.rejectValue("to", "dayerror", "날짜 에러");
        }

        return;
    }
}
