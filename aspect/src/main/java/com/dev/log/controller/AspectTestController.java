package com.dev.log.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.log.domain.ResponseVO;
import com.dev.log.domain.SampleVO;



@RestController
@RequestMapping("/api/aspects")
public class AspectTestController {
    
    @PostMapping("/test-header-aspect")
    public ResponseEntity<ResponseVO> testHeaderAspect(@RequestBody SampleVO vo) {
        
        vo.getHeader().setTxTime(System.currentTimeMillis());

        return ResponseEntity.ok(new ResponseVO());
    }
    

}
