package application;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== Test 1: seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== Test 2 seller findByEmail===");
        seller = sellerDao.findByEmail("maria@gmail.coma");
        System.out.println(seller);

        System.out.println("\n=== Test 3: seller findByDepartment ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        list.forEach(System.out::println);

        System.out.println("\n=== Test 4: seller findAll ===");
        list = sellerDao.findAll();
        list.forEach(System.out::println);

        System.out.println("\n=== Test 5: seller Insert ===");
        Seller newSeller = new Seller(null, "Gregory", "Gregory@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(newSeller);
        if (newSeller.getId() != null) {
            System.out.println("Inserted! New id: " + newSeller.getId());
        }

        System.out.println("\n=== Test 6: seller Update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha  Waine");
        sellerDao.update(seller);
        System.out.println("Updated completed");

        System.out.println("\n=== Test 7: seller Update ===");
        System.out.println("Enter id for delete test: ");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete completed");

        sc.close();
    }
}