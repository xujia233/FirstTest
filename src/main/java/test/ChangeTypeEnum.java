package test;

/**
 * Created by xujia on 2019/9/12
 */
public enum ChangeTypeEnum {

    GROUP("分类"),
    DIRECTORY("目录项")
    ;

    private String desc;

    ChangeTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
