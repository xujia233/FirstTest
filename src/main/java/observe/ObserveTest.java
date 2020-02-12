package observe;

/**
 * Created by xujia on 2019/6/28
 */
public class ObserveTest {

    public static void main(String[] args) {
        // 初始化斗鱼主播
        DouyuTvAnchor anchor = new DouyuTvAnchor("五五开");

        // 初始化斗鱼观众
        Watcher watcher1 = new Watcher("张三");
        Watcher watcher2 = new Watcher("李四");
        Watcher watcher3 = new Watcher("王五");

        // 订阅
        anchor.subscribe(watcher1);
        anchor.subscribe(watcher2);
        anchor.subscribe(watcher3);

        // 模拟主播开播
        anchor.startWork("lol目前钻一冲国服第一");

        // 取消关注
        anchor.cancelSubscribe(watcher2);

        System.out.println("当前主播：" + anchor.getAnchor() + "总关注人数为:" + anchor.countObservers());
    }
}
