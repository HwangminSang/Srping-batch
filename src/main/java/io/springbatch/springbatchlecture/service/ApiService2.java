package io.springbatch.springbatchlecture.service;

import io.springbatch.springbatchlecture.batch.domain.ApiInfo;
import io.springbatch.springbatchlecture.batch.domain.ApiResponseVO;
import io.springbatch.springbatchlecture.batch.entity.ApiResult;
import io.springbatch.springbatchlecture.batch.repository.ApiResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ApiService2 extends AbstractApiService{
    private final ApiResultRepository apiResultRepository;

    @Override
    public ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo){

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:9002/api/product/2", apiInfo, String.class);
        apiInfo.setUrl("http://localhost:9002/api/product/2");
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
