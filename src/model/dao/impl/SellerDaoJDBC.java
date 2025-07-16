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
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    //Attributes
        private Connection conn;


    //Constructors
        public SellerDaoJDBC(Connection conn) {
            this.conn = conn;
        }


    //Methods
        //->Insert
        @Override
        public void insert(Seller obj) {

        }

        //->Update
        @Override
        public void update(Seller obj) {

        }

        //->DeleteById
        @Override
        public void deleteById(Integer id) {

        }

        //->FindById
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

                    Department dep = new Department();
                    dep.setId(rs.getInt("DepartmentId"));
                    dep.setName(rs.getString("DepName"));

                    Seller seller = new Seller();
                    seller.setId(rs.getInt("Id"));
                    seller.setName(rs.getString("Name"));
                    seller.setEmail(rs.getString("Email"));
                    seller.setBirthDate(rs.getDate("BirthDate"));
                    seller.setBaseSalary(rs.getDouble("BaseSalary"));
                    seller.setDepartment(dep);
                    return seller;
                }
                return  null;

            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }
        }

        //->FindALl
        @Override
        public List<Seller> findAll() {
            return List.of();
        }
}
