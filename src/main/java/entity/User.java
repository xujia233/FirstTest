package entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xujia on 2019/2/1
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -3363120264501521428L;

    private String id;
    private String name;
}
