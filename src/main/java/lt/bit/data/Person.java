package lt.bit.data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
@NamedQueries({
        @NamedQuery(name = "Person.filter", query = "SELECT p FROM Person p where lower(p.firstName) like lower(concat('%', :filter, '%')) or lower(p.lastName) like lower(concat('%', :filter, '%'))")
})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "salary")
    private BigDecimal salary;
    //1 vienas personas turi daug adresu
//    @OneToMany @JoinColumn(name = "person_id")
    //2
//    @OneToMany(mappedBy = "person")
//    @OneToMany(fetch = FetchType.LAZY) // jei tai listas, set.. @OneToMany defaultas yra LAZY
//    @OneToMany(fetch = FetchType.EAGER)
//    private List<Address> addresses;
//    @OneToMany(mappedBy = "person")
//    private List<Contact> contacts;

    //3
//    @OneToMany(mappedBy = "person")
//    private List<Address> addresses;
//    @OneToMany
//    @JoinColumn(name = "person_id")
//    private List<Contact> contacts;

    //4
    @OneToMany(mappedBy = "person")
    private List<Address> addresses;
    @OneToMany(mappedBy = "person")
    private List<Contact> contacts;

    public Person() {

    }

    public Person(String firstName, String lastName, Date birthDate, BigDecimal salary) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.salary = salary;
        this.addresses = addresses;
    }


    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", salary=" + salary +
                ", addresses=" + addresses +
                ", contacts=" + contacts +
                '}';
    }
}
