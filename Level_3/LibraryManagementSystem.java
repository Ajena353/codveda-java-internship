import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {

    static final String URL = "jdbc:mysql://localhost:3306/library_db";
    static final String USER = "root";
    static final String PASSWORD = "your_password";
    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            createTables();
            int choice;
            do {
                System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
                System.out.println("1. Add Book");
                System.out.println("2. View Books");
                System.out.println("3. Add User");
                System.out.println("4. View Users");
                System.out.println("5. Borrow Book");
                System.out.println("6. Return Book");
                System.out.println("7. Delete Book");
                System.out.println("8. Exit");
                System.out.print("Enter Choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                switch(choice){
                    case 1: addBook(); break;
                    case 2: viewBooks(); break;
                    case 3: addUser(); break;
                    case 4: viewUsers(); break;
                    case 5: borrowBook(); break;
                    case 6: returnBook(); break;
                    case 7: deleteBook(); break;
                    case 8: System.out.println("Thank You!"); break;
                    default: System.out.println("Invalid Choice!");
                }
            } while(choice != 8);
            con.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    static void createTables() throws SQLException{
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS Books(book_id INT AUTO_INCREMENT PRIMARY KEY,title VARCHAR(100),author VARCHAR(100),available BOOLEAN DEFAULT TRUE)");
        st.executeUpdate("CREATE TABLE IF NOT EXISTS Users(user_id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(100))");
        st.executeUpdate("CREATE TABLE IF NOT EXISTS Transactions(transaction_id INT AUTO_INCREMENT PRIMARY KEY,user_id INT,book_id INT,action VARCHAR(20),transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
    }

    static void addBook() throws SQLException{
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();
        PreparedStatement ps = con.prepareStatement("INSERT INTO Books(title,author) VALUES(?,?)");
        ps.setString(1,title);
        ps.setString(2,author);
        ps.executeUpdate();
        System.out.println("Book Added Successfully.");
    }

    static void viewBooks() throws SQLException{
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Books");
        while(rs.next()){
            System.out.println(rs.getInt("book_id")+" | "+rs.getString("title")+" | "+rs.getString("author")+" | "+(rs.getBoolean("available")?"Available":"Borrowed"));
        }
    }

    static void addUser() throws SQLException{
        System.out.print("Enter User Name: ");
        String name = sc.nextLine();
        PreparedStatement ps = con.prepareStatement("INSERT INTO Users(name) VALUES(?)");
        ps.setString(1,name);
        ps.executeUpdate();
        System.out.println("User Added Successfully.");
    }

    static void viewUsers() throws SQLException{
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Users");
        while(rs.next()){
            System.out.println(rs.getInt("user_id")+" | "+rs.getString("name"));
        }
    }

    static void borrowBook() throws SQLException{
        System.out.print("Enter User ID: ");
        int uid=sc.nextInt();
        System.out.print("Enter Book ID: ");
        int bid=sc.nextInt();
        PreparedStatement chk=con.prepareStatement("SELECT available FROM Books WHERE book_id=?");
        chk.setInt(1,bid);
        ResultSet rs=chk.executeQuery();
        if(rs.next() && rs.getBoolean("available")){
            PreparedStatement up=con.prepareStatement("UPDATE Books SET available=FALSE WHERE book_id=?");
            up.setInt(1,bid);
            up.executeUpdate();
            PreparedStatement tr=con.prepareStatement("INSERT INTO Transactions(user_id,book_id,action) VALUES(?,?,?)");
            tr.setInt(1,uid); tr.setInt(2,bid); tr.setString(3,"BORROW");
            tr.executeUpdate();
            System.out.println("Book Borrowed Successfully.");
        } else {
            System.out.println("Book unavailable.");
        }
    }

    static void returnBook() throws SQLException{
        System.out.print("Enter User ID: ");
        int uid=sc.nextInt();
        System.out.print("Enter Book ID: ");
        int bid=sc.nextInt();
        PreparedStatement up=con.prepareStatement("UPDATE Books SET available=TRUE WHERE book_id=?");
        up.setInt(1,bid);
        up.executeUpdate();
        PreparedStatement tr=con.prepareStatement("INSERT INTO Transactions(user_id,book_id,action) VALUES(?,?,?)");
        tr.setInt(1,uid); tr.setInt(2,bid); tr.setString(3,"RETURN");
        tr.executeUpdate();
        System.out.println("Book Returned Successfully.");
    }

    static void deleteBook() throws SQLException{
        System.out.print("Enter Book ID: ");
        int id=sc.nextInt();
        PreparedStatement ps=con.prepareStatement("DELETE FROM Books WHERE book_id=?");
        ps.setInt(1,id);
        ps.executeUpdate();
        System.out.println("Book Deleted Successfully.");
    }
}
