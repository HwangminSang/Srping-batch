package io.springbatch.springbatchlecture.util.jpa.converter;



import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
//그런데 Entity Listener에서는 의존성 주입이 되지 않기 때문에 static 메소드를 가진 BeanUtils 클래스를 만들어서 Bean을 가져오겠습니다.

@Component
public class BeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }


}
