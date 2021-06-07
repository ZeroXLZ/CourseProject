package Logic;

public class Worker {

    private final String login;
    private String password;
    private String full_name;
    private String mobile_phone;
    private String position;
    private int points;

    public Worker(String login, String password, String full_name, String mobile_phone, String position, int points) {
        this.login = login;
        this.password = password;
        this.full_name = full_name;
        this.mobile_phone = mobile_phone;
        this.position = position;
        this.points = points;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.full_name;
    }

    public String getMobile() {
        return this.mobile_phone;
    }

    public String getPosition() {
        return this.position;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public void setName(String name) {
        this.full_name = name;
    }

    public void setMobile(String phone) {
        this.mobile_phone = phone;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
