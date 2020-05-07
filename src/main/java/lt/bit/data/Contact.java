package lt.bit.data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contact")
@NamedQueries({
        @NamedQuery(name = "Contacts.findAll", query = "SELECT c FROM Contact c"),
        @NamedQuery(name = "Contacts.findById", query = "SELECT c FROM Contact c WHERE c.id = :id"),
        @NamedQuery(name = "Contacts.findByType", query = "SELECT c FROM Contact c WHERE c.contactType = :contactType"),
        @NamedQuery(name = "Contacts.findByContact", query = "SELECT c FROM Contact c WHERE c.contact = :contact")})
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "contact")
    private String contact;
    @Column(name = "contact_type")
    private String contactType;
    //1
//    @ManyToOne
//    @JoinColumn(name = "person_id")
//    private Person person;

    //3
//    @ManyToOne
//    @JoinColumn(name = "person_id")
//    private Person person;

    //4
    @ManyToOne
    private  Person person;



    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact1 = (Contact) o;
        return id == contact1.id &&
                Objects.equals(contact, contact1.contact) &&
                Objects.equals(contactType, contact1.contactType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contact, contactType);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", contact='" + contact + '\'' +
                ", contactType='" + contactType + '\'' +
                ", person=" + person +
                '}';
    }
}
