package pers.jieao.guitarworld.model;

public abstract class User {

    protected long id;
    protected String password;



    public User() {
        this.id = 0;
        this.password = null;

    }

    public User(long id, String password) {
        this.id = id;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
