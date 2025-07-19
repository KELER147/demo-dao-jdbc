package view;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SellerView {

    public static void showMenu(SellerDao sellerDao, Scanner sc) {
        int option;
        do {
            System.out.println("\n=== SELLER MENU ===");
            System.out.println("1 - Find by ID");
            System.out.println("2 - Find by Email");
            System.out.println("3 - Find by Department");
            System.out.println("4 - List All");
            System.out.println("5 - Insert");
            System.out.println("6 - Update");
            System.out.println("7 - Delete");
            System.out.println("0 - Back");
            System.out.print("Choose an option: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter Seller ID: ");
                    int id = sc.nextInt();
                    System.out.println("Result: " + sellerDao.findById(id));
                    break;
                case 2:
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    System.out.println("Result: " + sellerDao.findByEmail(email));
                    break;
                case 3:
                    System.out.print("Enter Department ID: ");
                    int depId = sc.nextInt();
                    Department dep = new Department(depId, null);
                    List<Seller> sellersByDep = sellerDao.findByDepartment(dep);
                    sellersByDep.forEach(System.out::println);
                    break;
                case 4:
                    List<Seller> allSellers = sellerDao.findAll();
                    allSellers.forEach(System.out::println);
                    break;
                case 5:
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String newEmail = sc.nextLine();
                    System.out.print("Base Salary: ");
                    double salary = sc.nextDouble();
                    System.out.print("Department ID: ");
                    int deptId = sc.nextInt();
                    sc.nextLine();
                    Seller newSeller = new Seller(null, name, newEmail, new Date(), salary, new Department(deptId, null));
                    sellerDao.insert(newSeller);
                    System.out.println("Inserted! New ID: " + newSeller.getId());
                    break;
                case 6:
                    System.out.print("Enter Seller ID to update: ");
                    int updId = sc.nextInt();
                    sc.nextLine();
                    Seller s = sellerDao.findById(updId);
                    if (s != null) {
                        System.out.print("New name: ");
                        s.setName(sc.nextLine());
                        sellerDao.update(s);
                        System.out.println("Seller updated.");
                    } else {
                        System.out.println("Seller not found.");
                    }
                    break;
                case 7:
                    System.out.print("Enter Seller ID to delete: ");
                    int delId = sc.nextInt();
                    sellerDao.deleteById(delId);
                    System.out.println("Seller deleted.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        } while (option != 0);
    }
}
