package entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xujia on 2019/6/8
 */
@Data
public class Dog implements Serializable {

    private static final long serialVersionUID = 6556611436996991093L;
    private String name;
    public Dog(String name) {this.name = name;}
}
