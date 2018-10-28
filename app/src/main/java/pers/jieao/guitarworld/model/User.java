package pers.jieao.guitarworld.model;

public class User {

    protected String id;
    protected String password;



    public User() {
        this.id = null;
        this.password = null;

    }

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        String information = new StringBuilder()
                .append(id + " ")
                .append(password + " ")
                .toString();
        return information;
    }


}
