package model.dao.impl;
import model.dao.DepartmentDao;
import model.entities.Department;
import java.sql.Connection;
import java.util.List;


public class DepartmentDaoJDBC implements DepartmentDao {
    //Attributes
        private Connection conn;

    //Constructors
        public DepartmentDaoJDBC(Connection conn) {
            this.conn = conn;
        }

    //Methods
        @Override
        public void deleteById(Integer id) {

        }

        @Override
        public List<Department> findAll() {
            return List.of();
        }

        @Override
        public void insert(Department obj) {

        }

        @Override
        public Department findById(Integer id) {
            return null;
        }

        @Override
        public void update(Department obj) {

        }
}