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


        System.out.println("\n=== TEST 1: findById =======");
        Department dep = departmentDao.findById(2);
        System.out.println(dep);


        System.out.println("\n=== TEST 2: findByName =======");
        dep = departmentDao.findByName("admin");
        System.out.println(dep);


        System.out.println("\n=== TEST 3: findAll =======");


        System.out.println("\n=== TEST 4: insert =======");
        dep =  new Department(null, "Production");
        departmentDao.insert(dep);
        if (dep.getId() != null) {System.out.println("Inserted! New id: " + dep.getId());}


        System.out.println("\n=== TEST 5: delete =======");




        sc.close();
    }
}
