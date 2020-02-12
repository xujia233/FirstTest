package entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by xujia on 2019/2/1
 */
public class User implements Serializable,Cloneable {

    private static final long serialVersionUID = -3363120264501521428L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public Object clone() {
        User user = null;
        try {
            user = (User) super.clone();
        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private String id;
    private String name;
    private boolean flag;
    private List<String> str;
    private Dog dog;
    private Boolean can;
    private Map<String, String> map;

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public User (String id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<String> getStr() {
        return str;
    }

    public void setStr(List<String> str) {
        this.str = str;
    }

    public Boolean getCan() {
        return can;
    }

    public void setCan(Boolean can) {
        this.can = can;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public User(String id, String name, boolean flag, List<String> str, Map<String, String> map) {
        this.id = id;
        this.name = name;
        this.flag = flag;
        this.str = str;
        this.map = map;
    }

    public User() {}
}
