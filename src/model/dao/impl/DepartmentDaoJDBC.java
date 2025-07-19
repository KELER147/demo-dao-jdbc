package model.dao.impl;
import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
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
        public void insert(Department obj) {
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                Department dep = findByName(obj.getName());
                if (dep != null) {
                    System.out.println("Error! Department Existent");
                } else {
                    st = conn.prepareStatement(
                            "INSERT INTO department "
                            + "(Name) "
                            + "VALUES "
                            + "(?)",
                            Statement.RETURN_GENERATED_KEYS);
                    st.setString(1, obj.getName());
                    int rowsAffected = st.executeUpdate();
                    if (rowsAffected > 0) {
                        rs = st.getGeneratedKeys();
                        if (rs.next()) {
                            obj.setId(rs.getInt(1));
                        } else {
                            throw new DbException("Unexpected error! No rows affected!");
                        }
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
        public void update(Department obj) {
            PreparedStatement st = null;
            try {
                if (obj.getId() != null) {
                st = conn.prepareStatement("UPDATE department SET Name = ? WHERE id = ?");
                st.setString(1, obj.getName());
                st.setInt(2, obj.getId());
                st.executeUpdate();
                System.out.println("Update completed");
                }
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
                Department dep = findById(id);
                if (dep != null) {
                    st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
                    st.setInt(1, id);
                    st.executeUpdate();
                    System.out.println("Delete completed");
                }
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
            }
        }

        @Override
        public Department findById(Integer id) {
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
                st.setInt(1, id);
                rs = st.executeQuery();
                if (rs.next()) {
                    Department dep = new Department();
                    dep.setId(rs.getInt("Id"));
                    dep.setName(rs.getString("Name"));
                    return dep;
                } else {
                    System.out.println("Error! Department with name " + id + " not found!");
                    return null;
                }
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }
        }

        @Override
        public Department findByName(String name) {
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareStatement("SELECT * FROM department WHERE Name = ?");
                st.setString(1, name);
                rs = st.executeQuery();
                if (rs.next()) {
                    Department dep = new Department();
                    dep.setId(rs.getInt("Id"));
                    dep.setName(rs.getString("Name"));
                    return dep;
                } else {
                    System.out.println("Error! Department with name " + name + " not found!");
                    return null;
                }
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }
        }

        @Override
        public List<Department> findAll() {
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
                rs = st.executeQuery();

                List<Department> list = new ArrayList<>();
                while (rs.next()) {
                    Department dep = new Department();
                    dep.setId(rs.getInt("Id"));
                    dep.setName(rs.getString("Name"));
                    list.add(dep);
                }
                return list;
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }
        }

}