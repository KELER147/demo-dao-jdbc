package model.entities;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {
    //Attributes
    private Integer id;
    private String name;
    private String email;
    private Date birthDate;
    private Double baseSalary;

        //->association
        private Department department;


    //Constructors
    public Seller() {
    }
    public Seller(Integer id, String name, String email, Date birthDate, Double baseSalary,  Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.department = department;
    }


    //Methods
        //->Equals and HashCode
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;

            Seller seller = (Seller) o;
            return Objects.equals(id, seller.id);
        }
        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        //->ToString
        @Override
        public String toString() {
            return "Seller [" +
                    " id: " + id +
                    ", name: " + name +
                    ", email: " + email +
                    ", birthDate: " + birthDate +
                    ", baseSalary: " + baseSalary +
                    ", department: " + department +
                    " ]";
        }

    //Getters and Setters
        //->Id
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }

        //->Name
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        //->Email
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        //->BirthDate
        public Date getBirthDate() {
            return birthDate;
        }
        public void setBirthDate(Date birthDate) {
            this.birthDate = birthDate;
        }

        //->BaseSalary
        public Double getBaseSalary() {
            return baseSalary;
        }
        public void setBaseSalary(Double baseSalary) {
            this.baseSalary = baseSalary;
        }

        //->Department
        public Department getDepartment() {
            return department;
        }
        public void setDepartment(Department department) {
            this.department = department;
        }
}