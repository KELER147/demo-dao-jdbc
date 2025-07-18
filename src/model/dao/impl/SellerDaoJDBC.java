package model.dao.impl;
import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
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
            PreparedStatement st = null;
            ResultSet rs = null;
            Seller sll =   findByEmail(obj.getEmail());
            try {
                if (sll != null) {
                    System.out.println("Error! Seller Existent");
                } else {
                    st = conn.prepareStatement(
                            "INSERT INTO seller "
                                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                                    + "VALUES "
                                    + "(?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);

                    st.setString(1, obj.getName());
                    st.setString(2, obj.getEmail());
                    st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
                    st.setDouble(4, obj.getBaseSalary());
                    st.setInt(5, obj.getDepartment().getId());

                    int rowsAffected = st.executeUpdate();
                    if (rowsAffected > 0) {
                        rs = st.getGeneratedKeys();
                        if (rs.next()) {
                            int id = rs.getInt(1);
                            obj.setId(id);
                        }
                    } else {
                        throw new DbException("Unexpected error! No rows affected!");
                    }
                }

            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }

        }

        @Override
        public void update(Seller obj) {
            PreparedStatement st = null;
            try {
              st = conn.prepareStatement(
                  "UPDATE seller "
                  +"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                  +"WHERE Id = ?"
              );
                st.setString(1, obj.getName());
                st.setString(2, obj.getEmail());
                st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
                st.setDouble(4, obj.getBaseSalary());
                st.setInt(5, obj.getDepartment().getId());
                st.setInt(6, obj.getId());

                st.executeUpdate();

            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
            }
        }

        @Override
        public void deleteById(Integer id) {
            PreparedStatement st = null;
            try {
                st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
                st.setInt(1, id);
                st.executeUpdate();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }  finally {
                DB.closeStatement(st);
            }
        }

        @Override
        public Seller findById(Integer id) {
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    +"FROM seller INNER JOIN department "
                    +"ON seller.DepartmentID = department.Id "
                    +"WHERE seller.Id = ?");

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
        public Seller findByEmail(String email) {
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareStatement(
                        "SELECT seller.*,department.Name as DepName "
                                +"FROM seller INNER JOIN department "
                                +"ON seller.DepartmentID = department.Id "
                                +"WHERE seller.Email = ?");

                st.setString(1, email);
                rs = st.executeQuery();
                if (rs.next()) {
                    Department dep = instantiateDepartment(rs);
                    return instantiateSeller(rs, dep);
                } else {
                    System.out.println("Seller with email " + email + " not found!");
                    return  null;
                }

            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }
        }


        @Override
        public List<Seller> findAll() {
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "ORDER BY Name ");


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
                    + "ORDER BY Name ");


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
