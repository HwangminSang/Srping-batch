package com.example.batchapiserver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api")
public class Controller {


    @PostMapping("/product/1")
    public ResponseEntity<String> checkType(@RequestBody ApiInfo apiInfo){

        log.info("url = > :" + apiInfo.getUrl() );

        List<? extends ApiRequestVO> apiRequestList = apiInfo.getApiRequestList();

        Long aLong = apiRequestList.stream().map(ApiRequestVO::getId).findFirst().get();
        log.info("상품 아이디" + aLong);




        return ResponseEntity.ok("true" );
    }


    @PostMapping("/product/2")
    public ResponseEntity<String> checkType2(@RequestBody ApiInfo apiInfo){


        List<? extends ApiRequestVO> apiRequestList = apiInfo.getApiRequestList();

        Long aLong = apiRequestList.stream().map(ApiRequestVO::getId).findFirst().get();
        log.info("상품 아이디" + aLong);


        return ResponseEntity.ok("true" );
    }

    @PostMapping("/product/3")
    public ResponseEntity<String> checkType3( @RequestBody ApiInfo apiInfo){



        List<? extends ApiRequestVO> apiRequestList = apiInfo.getApiRequestList();

        Long aLong = apiRequestList.stream().map(ApiRequestVO::getId).findFirst().get();
        log.info("상품 아이디" + aLong);


        return ResponseEntity.ok("true" );
    }


}
