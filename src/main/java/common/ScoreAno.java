package common;

import java.lang.annotation.*;

/**
 * Created by xujia on 2019/3/13
 */
@Retention(RetentionPolicy.RUNTIME) // 运行时保留
@Target(ElementType.METHOD) // 作用在方法
@Documented // 被javadoc工具所记录
public @interface ScoreAno {
    /**
     * 查询方式枚举
     */
    enum QueryType{COUNT, PAGE}

    /**
     * 查询类型，根据业务来定
     * @return
     */
    String filterType();

    /**
     * 查询方式，这里为分页和总数
     * @return
     */
    QueryType queryType();
}
