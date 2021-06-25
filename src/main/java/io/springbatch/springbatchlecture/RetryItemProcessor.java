package io.springbatch.springbatchlecture;

import org.springframework.batch.item.ItemProcessor;

public class RetryItemProcessor implements ItemProcessor<String, String> {

	private int cnt = 0;

	@Override
	public String process(String item) throws Exception {
		if(item.equals("6")) {
			cnt++;
			throw new RetryableException("Process failed. cnt:" + cnt);
		}
		else {
			return String.valueOf(Integer.valueOf(item) * -1);
		}
	}
}