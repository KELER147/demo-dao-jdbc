package application;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.Locale;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();


        System.out.println("=== TEST 1: findById =======");

        System.out.println("\n=== TEST 2: findAll =======");

        System.out.println("\n=== TEST 3: insert =======");
        Department dep =  new Department(null, "Production");
        departmentDao.insert(dep);
        if (dep.getId() != null) {System.out.println("Inserted! New id: " + dep.getId());}


        System.out.println("\n=== TEST 5: delete =======");




        sc.close();
    }
}
