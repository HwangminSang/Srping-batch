
package io.springbatch.springbatchlecture.batch.partition;

import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import io.springbatch.springbatchlecture.batch.job.api.QueryGenerator;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ProductPartitioner implements Partitioner {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		// 멀티스레드 환경 : 각각의 스레드는 각각의 ExecutionContext 가지고 있다.
		// 현재 3개의 type이 있다. = group by
		ProductVO[] productList = QueryGenerator.getProductList(dataSource);
		Map<String, ExecutionContext> result = new HashMap<>();
		int number = 0;

		for (int i = 0; i < productList.length; i++) {

			ExecutionContext value = new ExecutionContext();

			result.put("partition" + number, value);
			// 각각의 스레드에서  group by 로 가져온 vo를 이용하여 처리
			value.put("product", productList[i]);

			number++;
		}

		return result;
	}}
