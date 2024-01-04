package com.dev.logbook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dev.logbook.domain.SampleVO;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/logbook")
public class LogbookTestController {
    
    @GetMapping("/logging-access-log")
    public ResponseEntity<List<SampleVO>> postMethodName(@RequestBody SampleVO vo) {
        
        List<SampleVO> listVO = new ArrayList<>();
        
        SampleVO tot = new SampleVO();
        tot.setLeague("PL");
        tot.setTeam("TOT");
        tot.setName("Son Heung-min");
        tot.setNumber(7);

        listVO.add(tot);
        listVO.add(vo);


        return ResponseEntity.ok(listVO);
    }
    

}
