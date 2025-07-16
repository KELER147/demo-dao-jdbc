package model.dao.impl;
import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    //Attributes
        private Connection conn;

    //Constructors
        public SellerDaoJDBC(Connection conn) {
            this.conn = conn;
        }

    //Methods
        @Override
        public void insert(Seller obj) {

        }

        @Override
        public void update(Seller obj) {

        }
        @Override
        public void deleteById(Integer id) {

        }

        @Override
        public Seller findById(Integer id) {
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                      + "FROM seller INNER JOIN department "
                      + "ON seller.DepartmentID = department.Id "
                      + "WHERE seller.Id = ?"
                    );
                st.setInt(1, id);
                rs = st.executeQuery();
                if (rs.next()) {

                    Department dep = instantiateDepartment(rs);

                    return instantiateSeller(rs, dep);
                }
                return  null;
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }
        }


        @Override
        public List<Seller> findAll() {
            return List.of();
        }

        @Override
        public List<Seller> findByDepartment(Department department) {
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name "
                );


                st.setInt(1, department.getId());
                rs = st.executeQuery();

                List<Seller> list = new ArrayList<>();
                Map<Integer, Department> map = new HashMap<>();
                while (rs.next()) {

                    Department dep  = map.get(rs.getInt("DepartmentId"));
                    if (dep == null) {
                        dep = instantiateDepartment(rs);
                        map.put(rs.getInt("DepartmentId"), dep);
                    }
                    Seller seller = instantiateSeller(rs, dep);
                    list.add(seller);
                }
                return  list;
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }
        }


    //Methods auxiliaries
        private Department instantiateDepartment(ResultSet rs) throws SQLException {
            Department dep = new Department();
            dep.setId(rs.getInt("DepartmentId"));
            dep.setName(rs.getString("DepName"));
            return dep;
        }

        //-->InstantiateSeller
        private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
            Seller seller = new Seller();
            seller.setId(rs.getInt("Id"));
            seller.setName(rs.getString("Name"));
            seller.setEmail(rs.getString("Email"));
            seller.setBirthDate(rs.getDate("BirthDate"));
            seller.setBaseSalary(rs.getDouble("BaseSalary"));
            seller.setDepartment(dep);
            return seller;
        }
}
