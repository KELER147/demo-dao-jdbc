package model.entities;
import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {
    //Attributes
    private Integer id;
    private String name;

    //Constructors
    public Department() {
    }
    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    //Methods
    //->Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    //->ToString
    @Override
    public String toString() {
        return "Department [" +
                "id: " + id +
                ", name: " + name +
                "]";
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
}