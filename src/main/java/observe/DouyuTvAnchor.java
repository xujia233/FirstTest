package observe;

import java.util.Observable;
import java.util.Observer;

/**
 * 被观察者，斗鱼主播
 * Created by xujia on 2019/6/28
 */
public class DouyuTvAnchor extends Observable {

    /**
     * 主播名
     */
    private String anchor;
    /**
     * 开播标题
     */
    private String content;

    public DouyuTvAnchor() {super();}

    public DouyuTvAnchor(String anchor) {
        super();
        this.anchor = anchor;
    }

    /**
     * 订阅方法，一般告诉被观察者，你又被谁观察啦
     * @param observer
     * @return
     */
    public boolean subscribe(Observer observer) {
        // 为了展示更准确的信息，这里需强转
        if (observer instanceof Watcher) {
            Watcher watcher = (Watcher) observer;
            this.addObserver(watcher);
            System.out.println(anchor + "您好，" + watcher.getName() + "已成功订阅您，希望您再接再厉");
            return true;
        }
        return false;
    }

    /**
     * 开播方法
     * 具体场景为：主播打开斗鱼平台，填好当日开播标题，然后点击开播，调用该方法然后通知所有关注者
     * @param content
     */
    public void startWork(String content) {
        // 将开播状态置为开播
        setChanged();
        this.content = content;
        // 同时通知所有订阅的观众
        notifyObservers(content);
    }

    /**
     * 取消订阅
     * @param observer
     * @return
     */
    public boolean cancelSubscribe(Observer observer) {
        if (observer instanceof Watcher) {
            Watcher watcher = (Watcher) observer;
            this.deleteObserver(watcher);
            System.out.println(anchor + "您好，" + watcher.getName() + "已成功取消订阅，您嘴太臭啦");
            return true;
        }
        return false;
    }

    /**
     * 下播
     */
    public void endWork() {
        this.content = null;
        this.clearChanged();
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
