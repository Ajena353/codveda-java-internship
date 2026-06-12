import java.util.*;

class Employee{
    private int id;
    private String name;
    private double salary;

    public Employee(int id,String name,double salary){
        this.id=id;
        this.name=name;
        if(salary>0)    this.salary=salary;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getSalary(){
        return salary;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSalary(double salary){
        if(salary > 0) this.salary = salary;
    }

    @Override
    public String toString(){
        return "Employee ID: "+id+", Name: "+name+", Salary: "+salary;
    }
}
public class EmployeeManagement {
    public static void addEmployee(ArrayList<Employee> employee, Scanner sc){
        System.out.println("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.println("Enter Employee Name: ");
        String name = sc.next();
        System.out.println("Enter Employee Salary: ");
        double salary = sc.nextDouble();
        employee.add(new Employee(id, name, salary));
        System.out.println("Employee added successfully.");
    }
    public static void viewEmployee(ArrayList<Employee> employee){
        if(employee.isEmpty()){
            System.out.println("No employees registered.");
            return;
        }
        for(Employee emp:employee){
            System.out.println(emp);
        }
    }
    public static void updateEmployee(ArrayList<Employee> employee, Scanner sc){
        System.out.println("Enter Employee ID to update: ");
        int id = sc.nextInt();
        for(Employee emp:employee){
            if(emp.getId() == id){
                System.out.println("Enter new name: ");
                String name = sc.next();
                System.out.println("Enter new salary: ");
                double salary = sc.nextDouble();
                emp.setName(name);
                emp.setSalary(salary);
                System.out.println("Employee updated successfully.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }
    public static void deleteEmployee(ArrayList<Employee> employee, Scanner sc){
        System.out.println("Enter Employee ID to delete: ");
        int id = sc.nextInt();
        for(int i=0;i<employee.size();i++){
            if(employee.get(i).getId() == id){
                employee.remove(i);
                System.out.println("Employee deleted successfully.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    public static void main(String[] args) {
        ArrayList<Employee> employee = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int choice;
        do{
            System.out.println("=== Employee Menu ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
        
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    addEmployee(employee, sc);
                    break;
                case 2:
                    viewEmployee(employee);
                    break;
                case 3:
                    updateEmployee(employee, sc);
                    break;
                case 4:
                    deleteEmployee(employee,sc);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
        }while(choice!=5);
        sc.close();
    }
}
