package com.dev.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.app.domain.SampleVO;
import com.dev.app.service.UtilTestService;
import com.dev.app.util.ContextUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/utils")
public class UtilTestController {
    private final UtilTestService utilTestService;
    
    public UtilTestController(UtilTestService utilTestService) {
	this.utilTestService = utilTestService;
    }

    @PostMapping("/set-thread-local")
    public ResponseEntity<String> setThreadLocal(@RequestBody SampleVO vo) {

	utilTestService.setThreadLocal(vo.getName(), vo.getLeague());

	String leagueName = ContextUtil.getThreadLocal(vo.getName());

	log.debug("leagueName from threadLocal is {}", leagueName);
	return ResponseEntity.ok(leagueName);
    }

    @PostMapping("/get-thread-local")
    public ResponseEntity<Void> getThreadLocal(@RequestBody SampleVO vo) {
	String leagueName = ContextUtil.getThreadLocal(vo.getName());

	log.debug("leagueName from threadLocal is {}", leagueName);
        
	return ResponseEntity.ok().build();
    }
    

}
