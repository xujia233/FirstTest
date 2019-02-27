package entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by xujia on 2019/2/1
 */
public class User implements Serializable {

    private static final long serialVersionUID = -3363120264501521428L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    private String id;
    private String name;

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

    public User (String id, String name) {
        this.id = id;
        this.name = name;
    }
}
