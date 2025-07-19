package application;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import view.DepartmentView;
import view.SellerView;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        int mainOption;

        do {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1 - Seller Operations");
            System.out.println("2 - Department Operations");
            System.out.println("0 - Exit");
            System.out.print("Choose an option: ");
            mainOption = sc.nextInt();
            sc.nextLine();

            switch (mainOption) {
                case 1:
                    SellerView.showMenu(sellerDao, sc);
                    break;
                case 2:
                    DepartmentView.showMenu(departmentDao, sc);
                    break;
                case 0:
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (mainOption != 0);

        sc.close();
    }
}
