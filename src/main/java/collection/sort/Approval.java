package collection.sort;

/**
 * Created by xujia on 2019/12/10
 */
public class Approval implements Comparable<Object>{

    private String id;
    private String name;
    private long time;

    public Approval(String id, String name, long time) {
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Approval) {
            Approval approval = (Approval) o;
            return (int) (this.time - approval.time);
        } else if (o instanceof Fuck) {
            Fuck fuck = (Fuck) o;
            return (int) (this.time - fuck.getTime().getTime()) ;
        } else if (o instanceof Task) {
            Task task = (Task) o;
            return (int) (this.time - task.getTime().getTime()) ;
        }
        return 0;
    }
}
