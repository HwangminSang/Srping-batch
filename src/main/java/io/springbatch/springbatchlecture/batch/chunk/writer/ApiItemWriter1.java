package io.springbatch.springbatchlecture.batch.chunk.writer;

import io.springbatch.springbatchlecture.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.batch.domain.ApiResponseVO;
import io.springbatch.springbatchlecture.batch.entity.ApiResult;
import io.springbatch.springbatchlecture.batch.repository.ApiResultRepository;
import io.springbatch.springbatchlecture.service.AbstractApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 각각의 API 서버로 호출 후 파일에 쓴다.
 */
@Slf4j
public class ApiItemWriter1 extends FlatFileItemWriter<ApiRequestVO> {

    private final AbstractApiService apiService;



    public ApiItemWriter1(AbstractApiService apiService) {
        this.apiService = apiService;
    }

    // 각각의 스
    @Override
    public void write(List<? extends ApiRequestVO> items) throws Exception {

        System.out.println("----------------------------------");
        items.forEach(item -> System.out.println("items = " + item));
        System.out.println("----------------------------------");

        ApiResponseVO response = apiService.service(items);
        System.out.println("response = " + response);

        // API 서버 통신결과값
        items.forEach(item -> item.setApiResponseVO(response));




        /**
         * AbstractFileItemWriter 의 접근
         * 각각의 스레드가 독립적으로 실행중
         */
        super.setResource(new FileSystemResource("C:\\work\\spring-batch-lecture\\src\\main\\resources\\write\\product1.txt"));
        // itemStream을 구현중이기떄문에. open 호출
        super.open(new ExecutionContext());
        super.setLineAggregator(new DelimitedLineAggregator<>());  // 구분자 방식으로 ( , )
        super.setAppendAllowed(true); // 처음부터 쓰기 x 계속 추가.
        super.write(items); // FlatFileItemWriter가 쓰도록 설정
    }
}
