package observe;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者，即斗鱼tv某个主播的订阅者
 * Created by xujia on 2019/6/28
 */
public class Watcher implements Observer {

    /**
     * 用户名
     */
    private String name;

    public Watcher(){}

    public Watcher(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        // 为了展示更准确的信息，这里需强转
        if (o instanceof DouyuTvAnchor) {
            DouyuTvAnchor douyuTvAnchor = (DouyuTvAnchor) o;
            String play = (String) arg;
            System.out.println(name + "您好，" + "斗鱼TV的" + douyuTvAnchor.getAnchor() + "主播已开播，" + "标题为：" + play);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
