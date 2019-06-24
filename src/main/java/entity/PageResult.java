package entity;

import lombok.Data;

import java.util.List;

/**
 * 分页返回
 * Created by xujia on 2019/3/13
 */
@Data
public class PageResult<T> {

    /**
     * 数据集
     */
    private List<T> list;
    /**
     * 总数
     */
    private long total;
    /**
     * 当前页号
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;

}
