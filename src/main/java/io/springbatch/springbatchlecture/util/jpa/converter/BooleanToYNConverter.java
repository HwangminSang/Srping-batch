package io.springbatch.springbatchlecture.util.jpa.converter;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

//컨버터를 생성할 때는 @Converter 어노테이션을 사용하고, 필드에 적용할때는 @Convert를 사용
@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, String > {


    /**
     *  database 값으로 변환 (예시: true -> "Y")
     * @param attribute
     * @return
     */
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    /**
     * convertToEntityAttribute(): Entity의 값으로 변환 (예시: "Y" -> true)
     * @param yn
     *  @return Boolean 대소문자 상관없이 Y 인 경우 true, N 인 경우 false
     */
    @Override
    public Boolean convertToEntityAttribute(String yn) {
        return "Y".equalsIgnoreCase(yn);
    }
}
