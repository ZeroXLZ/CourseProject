package Logic;

public class Position {

    private final String name;
    private String payment;
    private String salary;

    public Position(String name, String payment, String salary) {
        this.name = name;
        this.payment = payment;
        this.salary = salary;
    }

    public String getName() {
        return this.name;
    }
    
    public String getPayment() {
        return this.payment;
    }
    
    public String getSalary() {
        return this.salary;
    }
    
    public void setPayment(String payment) {
        this.payment = payment;
    }
    
    public void setSalary(String salary) {
        this.salary = salary;
    }
}