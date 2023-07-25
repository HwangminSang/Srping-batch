package com.example.batchapiserver;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiRequestVO{

    private Long id;
    private ProductVO productVO;
    private ApiResponseVO apiResponseVO;

}