package io.springbatch.springbatchlecture.batch.job.api;

import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import io.springbatch.springbatchlecture.batch.rowmapper.ProductRowMapper;
import org.springframework.batch.item.database.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryGenerator {

    public static ProductVO[] getProductList(DataSource dataSource) {
//타입별로 스레드를 만든다.  -> STEPEXECUTIOM
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ProductVO> productList = jdbcTemplate.query("SELECT TYPE as type from PRODUCT group by TYPE" +
                "", new ProductRowMapper() {
            @Override  // 타입만 가져오기위해 별도 저으이
            public ProductVO mapRow(ResultSet rs, int i) throws SQLException {
                return ProductVO.builder()
                        .type(rs.getString("type"))
                        .build();
            }
        });

        return productList.toArray(new ProductVO[]{});
    }

    // 동적으로 파라미터 생성 < 멀티스레드 환경 >
    public static Map<String, Object> getParameterForQuery(String parameter, String value) {

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(parameter, value);
        return parameters;
    }




}
