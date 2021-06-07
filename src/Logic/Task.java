package Logic;

public class Task {

    private int id;
    private String name;
    private String staff_login;
    private String staff_name;
    private String deadline;
    private int points;
    private int completement;

    public Task(String name, String staff_login, String staff_name, String deadline, int points, int compl) {
        this.name = name;
        this.staff_login = staff_login;
        this.staff_name = staff_name;
        this.deadline = deadline;
        this.points = points;
        this.completement = compl;
    }
    
    public Task(int id, String name, String staff_login, String staff_name, String deadline, int points, int compl) {
        this.id = id;
        this.name = name;
        this.staff_login = staff_login;
        this.staff_name = staff_name;
        this.deadline = deadline;
        this.points = points;
        this.completement = compl;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getStaff_l() {
        return this.staff_login;
    }
    
    public String getStaff_n() {
        return this.staff_name;
    }

    public String getDeadline() {
        return this.deadline;
    }

    public int getPoints() {
        return this.points;
    }
    
    public int getCompletement() {
        return this.completement;
    }
}
