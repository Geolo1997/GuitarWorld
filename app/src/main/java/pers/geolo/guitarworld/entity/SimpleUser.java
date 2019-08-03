package pers.geolo.guitarworld.entity;

import java.util.Date;
import java.util.List;


public class SimpleUser extends User {

    protected String sex;
    protected String hometown;
    protected Date birthday;

    protected List<String> attentions;
    protected List<String> fans;

    public void attention(String username) {
        attentions.add(username);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
