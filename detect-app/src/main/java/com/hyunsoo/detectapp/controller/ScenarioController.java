package com.hyunsoo.detectapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyunsoo.detectapp.beans.*;
import com.hyunsoo.detectapp.service.ScenarioService;
import com.hyunsoo.detectapp.validator.ScenarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/scenario")
public class ScenarioController {

    private final ScenarioService scenarioService;

    private final MessageSource messageSource;

    private final ScenarioValidator scenarioValidator;

    @ResponseBody
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Scenario> getScenarioList(){
        return scenarioService.getScenarioList();
    }

    @GetMapping("")
    public String getDetectPointPage(@Condition Criteria criteria, Model model) throws JsonProcessingException {
        List<Scenario> scenarioList = scenarioService.getScenarioList();
        model.addAttribute("renderJson", scenarioList);
        return "main/scenario";
    }

    @PostMapping("/api")
    public ResponseEntity<Map<String,String>> insertScenario(@RequestBody @Validated Scenario scenario, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach((err)->{
                if(err.getCodes() != null && err.getCodes().length > 0){
                    String _err = err.getCodes()[0];
                    System.out.println(_err);
                    throw new ScenarioApiException(HttpStatus.BAD_REQUEST, messageSource.getMessage(_err,null, null));
                }
            });
            throw new ScenarioApiException(HttpStatus.BAD_REQUEST,  bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        scenarioService.insertScenario(scenario);

        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap("message","등록완료")
        );

    }

    @ExceptionHandler(value = ScenarioApiException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(ScenarioApiException ex){
        return ResponseEntity
                .status(ex.getStatus())
                .body(Collections.singletonMap("message", ex.getMessage()));
    }

    @InitBinder
    void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(scenarioValidator);
    }

}
