package io.springbatch.springbatchlecture.service;

import io.springbatch.springbatchlecture.batch.domain.ApiInfo;
import io.springbatch.springbatchlecture.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.batch.domain.ApiResponseVO;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import io.springbatch.springbatchlecture.batch.entity.ApiResult;
import io.springbatch.springbatchlecture.batch.repository.ApiResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService1 extends AbstractApiService{

    private final ApiResultRepository apiResultRepository;
    @Override
    public ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo){
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:9001/api/product/1", apiInfo, String.class);
        apiInfo.setUrl("http://localhost:9001/api/product/1");
        int statusCodeValue = response.getStatusCodeValue();
        ApiResponseVO apiResponseVO = new ApiResponseVO(statusCodeValue + "", response.getBody());

        ApiResult build = ApiResult.builder()
                .status(Boolean.valueOf(response.getBody()))
                .serviceName(apiInfo.getUrl())
                .build();
        apiResultRepository.save(build);
        return apiResponseVO;
    }
}
