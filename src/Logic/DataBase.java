package Logic;

import java.sql.*;

public class DataBase {

    private static Connection conn;
    private static Statement stat;
    private static ResultSet rs;

    private static void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:CW.s3db");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void closeDB() {
        try {
            conn.close();
            stat.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void addWorker(Worker worker) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("INSERT INTO Staff (login, password, full_name, mobile_phone, position, points) "
                    + "VALUES ('%s', '%s', '%s', '%s', '%s', '%d')",
                    worker.getLogin(), worker.getPassword(), worker.getName(), worker.getMobile(), worker.getPosition(), worker.getPoints()));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteWorker(String login) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("DELETE FROM Staff WHERE login = '%s'", login));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean checkWorker(String login) {
        Boolean check = false;
        try {
            connect();
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT login FROM Staff");
            while (rs.next()) {
                String db_login = rs.getString("login");
                if (db_login.equals(login)) {
                    check = true;
                    break;
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return check;
    }

    public static boolean checkWorker(String login, String password) {
        Boolean check = false;
        try {
            connect();
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT login, password FROM Staff");
            while (rs.next()) {
                String db_login = rs.getString("login");
                String db_password = rs.getString("password");
                if (db_login.equals(login) && db_password.equals(password)) {
                    check = true;
                    break;
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return check;
    }

    public static void changeWorker(Worker worker) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("UPDATE Staff SET password = '%s', full_name = '%s', mobile_phone = '%s', position = '%s', points = %d WHERE login = '%s'",
                    worker.getPassword(), worker.getName(), worker.getMobile(), worker.getPosition(), worker.getPoints(), worker.getLogin()));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void changePoints(int points) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("UPDATE Staff SET points = %d", points));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Worker getWorker(String login, String password) {
        String[] info = new String[4];
        try {
            connect();
            stat = conn.createStatement();
            rs = stat.executeQuery(String.format("SELECT * FROM Staff WHERE login = '%s' and password = '%s'", login, password));
            for (int i = 3; i < 7; i++) {
                info[i - 3] = rs.getString(i);
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        Worker worker = new Worker(login, password, info[0], info[1], info[2], Integer.parseInt(info[3]));
        return worker;
    }

    public static String[][] getWorkers() {
        String[][] workers = null;
        try {
            connect();
            stat = conn.createStatement();
            ResultSet rs2 = stat.executeQuery("SELECT count(*) FROM Staff");
            int r = 0;
            while (rs2.next()) {
                r = rs2.getInt(1);
            }
            rs = stat.executeQuery("SELECT ROW_NUMBER () OVER (ORDER BY points DESC) N, full_name, position, points FROM Staff");
            workers = new String[r][4];
            while (rs.next()) {
                for (int i = 0; i < 4; i++) {
                    workers[rs.getRow() - 1][i] = rs.getString(i + 1);
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return workers;
    }
    
    public static String[] getWorkersList() {
        String[] workers = null;
        try {
            connect();
            stat = conn.createStatement();
            ResultSet rs2 = stat.executeQuery("SELECT count(*) FROM Staff");
            int r = 0;
            while (rs2.next()) {
                r = rs2.getInt(1);
            }
            rs = stat.executeQuery("SELECT login FROM Staff");
            workers = new String[r];
            while (rs.next()) {
                    workers[rs.getRow() - 1] = rs.getString(1);
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return workers;
    }
    
    public static String getWorkerName(String login) {
        String worker = new String();
        try {
            connect();
            stat = conn.createStatement();
            rs = stat.executeQuery(String.format("SELECT full_name FROM Staff WHERE login = '%s'", login));
                    worker = rs.getString(1);
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return worker;
    }

    public static String[][] getFullWorkersInfo() {
        String[][] workers = null;
        try {
            connect();
            stat = conn.createStatement();
            ResultSet rs2 = stat.executeQuery("SELECT count(*) FROM Staff");
            int r = 0;
            while (rs2.next()) {
                r = rs2.getInt(1);
            }
            rs = stat.executeQuery("SELECT * FROM Staff ORDER BY points DESC");
            workers = new String[r][6];
            while (rs.next()) {
                for (int i = 0; i < 6; i++) {
                    workers[rs.getRow() - 1][i] = rs.getString(i + 1);
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return workers;
    }

    public static void addPosition(Position position) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("INSERT INTO Positions (name, payment_type, salary) VALUES ('%s', '%s', '%s')",
                    position.getName(), position.getPayment(), position.getSalary()));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deletePosition(String name) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("DELETE FROM Positions WHERE name = '%s'", name));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean checkPosition(String name) {
        Boolean check = false;
        try {
            connect();
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT name FROM Positions");
            while (rs.next()) {
                String db_name = rs.getString("name");
                if (db_name.equals(name)) {
                    check = true;
                    break;
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return check;
    }

    public static void changePosition(Position position) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("UPDATE Positions SET payment_type = '%s', salary = '%s' WHERE name = '%s'",
                    position.getPayment(), position.getSalary(), position.getName()));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String[][] getPositions() {
        String[][] positions = null;
        try {
            connect();
            stat = conn.createStatement();
            ResultSet rs2 = stat.executeQuery("SELECT count(*) FROM Positions");
            int r = 0;
            while (rs2.next()) {
                r = rs2.getInt(1);
            }
            rs = stat.executeQuery("SELECT * FROM Positions");
            positions = new String[r][3];
            while (rs.next()) {
                for (int i = 0; i < 3; i++) {
                    positions[rs.getRow() - 1][i] = rs.getString(i + 1);
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return positions;
    }

    public static String[] getPositionsList() {
        String[] positions = null;
        try {
            connect();
            stat = conn.createStatement();
            ResultSet rs2 = stat.executeQuery("SELECT count(*) FROM Positions");
            int r = 0;
            while (rs2.next()) {
                r = rs2.getInt(1);
            }
            rs = stat.executeQuery("SELECT name FROM Positions");
            positions = new String[r];
            while (rs.next()) {
                positions[rs.getRow() - 1] = rs.getString(1);
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return positions;
    }

    public static void addTask(Task task) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("INSERT INTO Tasks (task, staff_login, staff_name, deadline, points, completed) VALUES ('%s', '%s', '%s', '%s', '%d', '%d')",
                    task.getName(), task.getStaff_l(), task.getStaff_n(), task.getDeadline(), task.getPoints(), task.getCompletement()));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteTask(int id) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("DELETE FROM Tasks WHERE id = '%d'", id));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static boolean checkTask(String id) {
        Boolean check = false;
        try {
            connect();
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT id FROM Tasks");
            while (rs.next()) {
                if (rs.getString("id").equals(id)) {
                    check = true;
                    break;
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return check;
    }

    public static boolean checkTaskDesc(String task) {
        Boolean check = false;
        try {
            connect();
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT task, completed FROM Tasks");
            while (rs.next()) {
                if (rs.getString("task").equals(task) && rs.getString(1).equals("1")) {
                    check = true;
                    break;
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return check;
    }

    public static void cleanTasks() {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate("DELETE FROM Tasks");
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void changeTask(Task task) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("UPDATE Tasks SET task = '%s', deadline = '%s', staff_login = '%s', staff_name = '%s', points = %d, completed = %d WHERE id = %d",
                    task.getName(), task.getDeadline(), task.getStaff_l(), task.getStaff_n(), task.getPoints(), task.getCompletement(), task.getID()));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void completeTask(String task) {
        try {
            connect();
            stat = conn.createStatement();
            stat.executeUpdate(String.format("UPDATE Tasks SET completed = '%s' WHERE task = '%s'", "1", task));
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String[][] getWTasks(Worker worker) {
        String[][] tasks = null;
        try {
            connect();
            stat = conn.createStatement();
            ResultSet rs2 = stat.executeQuery(String.format("SELECT count(*) FROM Tasks WHERE staff_login = '%s' and completed = 0", worker.getLogin()));
            int r = 0;
            while (rs2.next()) {
                r = rs2.getInt(1);
            }
            rs = stat.executeQuery(String.format("SELECT ROW_NUMBER() OVER(ORDER BY deadline) AS N, task, deadline, points FROM Tasks WHERE staff_login = '%s' and completed = 0", worker.getLogin()));
            tasks = new String[r][4];
            while (rs.next()) {
                for (int i = 0; i < 4; i++) {
                    tasks[rs.getRow() - 1][i] = rs.getString(i + 1);
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tasks;
    }

    public static String[][] getTasks() {
        String[][] tasks = null;
        try {
            connect();
            stat = conn.createStatement();
            ResultSet rs2 = stat.executeQuery("SELECT count(*) FROM Tasks");
            int r = 0;
            while (rs2.next()) {
                r = rs2.getInt(1);
            }
            rs = stat.executeQuery("SELECT * FROM Tasks");
            tasks = new String[r][7];
            while (rs.next()) {
                for (int i = 0; i < 7; i++) {
                    tasks[rs.getRow() - 1][i] = rs.getString(i + 1);
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tasks;
    }
    
    public static boolean checkDeleteWorker(String login) {
        Boolean check = false;
        try {
            connect();
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT staff_login FROM Tasks");
            while (rs.next()) {
                String db_login = rs.getString("staff_login");
                if (db_login.equals(login)) {
                    check = true;
                    break;
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return check;
    }
    
    public static boolean checkDeletePosition(String pos) {
        Boolean check = false;
        try {
            connect();
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT position FROM Staff");
            while (rs.next()) {
                String db_login = rs.getString("position");
                if (db_login.equals(pos)) {
                    check = true;
                    break;
                }
            }
            closeDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return check;
    }
}