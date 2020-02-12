package collection.sort;

import java.util.Date;

/**
 * Created by xujia on 2019/12/10
 */
public class Fuck implements Comparable<Object>{

    private String id;
    private String name;
    private Date time;

    public Fuck(String id, String name, Date time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Fuck) {
            Fuck fuck = (Fuck) o;
            return (int) (this.time.getTime() - fuck.time.getTime());
        } else if (o instanceof Task) {
            Task task = (Task) o;
            return (int) (this.time.getTime() - task.getTime().getTime()) ;
        } else if (o instanceof Approval) {
            Approval approval = (Approval) o;
            return (int) (this.time.getTime() - approval.getTime());
        }
        return 0;
    }
}
