package view;
import model.dao.DepartmentDao;
import model.entities.Department;
import java.util.List;
import java.util.Scanner;

public class DepartmentView {
    public static void showMenu(DepartmentDao departmentDao, Scanner sc) {
        int option;
        do {
            System.out.println("\n=== DEPARTMENT MENU ===");
            System.out.println("1 - Find by ID");
            System.out.println("2 - Find by Name");
            System.out.println("3 - List All");
            System.out.println("4 - Insert");
            System.out.println("5 - Update");
            System.out.println("6 - Delete");
            System.out.println("0 - Back");
            System.out.print("Choose an option: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter Department ID: ");
                    int id = sc.nextInt();
                    System.out.println("Result: " + departmentDao.findById(id));
                    break;
                case 2:
                    System.out.print("Enter Department Name: ");
                    String name = sc.nextLine();
                    System.out.println("Result: " + departmentDao.findByName(name));
                    break;
                case 3:
                    List<Department> list = departmentDao.findAll();
                    list.forEach(System.out::println);
                    break;
                case 4:
                    System.out.print("Enter new department name: ");
                    String newName = sc.nextLine();
                    Department newDep = new Department(null, newName);
                    departmentDao.insert(newDep);
                    System.out.println("Inserted! New ID: " + newDep.getId());
                    break;
                case 5:
                    System.out.print("Enter Department ID to update: ");
                    int updId = sc.nextInt();
                    sc.nextLine();
                    Department d = departmentDao.findById(updId);
                    if (d != null) {
                        System.out.print("New name: ");
                        d.setName(sc.nextLine());
                        departmentDao.update(d);
                        System.out.println("Department updated.");
                    } else {
                        System.out.println("Department not found.");
                    }
                    break;
                case 6:
                    System.out.print("Enter Department ID to delete: ");
                    int delId = sc.nextInt();
                    departmentDao.deleteById(delId);
                    System.out.println("Department deleted.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        } while (option != 0);
    }

}
