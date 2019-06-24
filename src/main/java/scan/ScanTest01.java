package scan;

import api.ScoreService;
import com.google.common.collect.Lists;
import common.ScoreAno;
import entity.PageResult;
import entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xujia on 2019/3/1
 */
public class ScanTest01 implements ScoreService {
    @Override
    public int getScore(int i) {
        return 0;
    }

    @ScoreAno(filterType = "test", queryType = ScoreAno.QueryType.PAGE)
    public PageResult<User> query (String id, String name) {
        // 这里为你所需要的业务逻辑
        PageResult<User> pageResult = new PageResult<>();
        pageResult.setList(Lists.newArrayList(new User(id, name)));
        pageResult.setTotal(1);
        return pageResult;
    }
}
