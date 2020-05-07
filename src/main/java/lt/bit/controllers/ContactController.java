package lt.bit.controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import lt.bit.data.Address;
import lt.bit.data.Contact;
import lt.bit.data.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/contact/")
public class ContactController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(
            // Required to show contacts for specific person
            @RequestParam(name = "personId", required = false) String personId
    ) {
        EntityManager em = (EntityManager) request.getAttribute("em");
        Person person = null;
        try {
            Integer idInt = new Integer(personId);
            person = em.find(Person.class, idInt);
        } catch (Exception ex) {
            // personId was not provided - should be reported as error
            // personId could not be parsed and converted to number - should be reported as error
        }
        if (person == null) {
            // personId was not provided - should be reported as error earlier
            // personId could not be parsed and converted to number - should be reported as error earlier
            // personId was ok but there is no record with specified id - should be reported as error
            // In this simple example: for all error cases redirect to person list view
            return new ModelAndView("redirect:../");
        }
        ModelAndView mv = new ModelAndView("contactList");
        mv.addObject("person", person);
        mv.addObject("contactList", person.getContacts());
        return mv;
    }

    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public ModelAndView edit(
            // Required for editting an existing contact
            @RequestParam(name = "id", required = false) String id,
            // Required for creating new contact
            @RequestParam(name = "personId", required = false) String personId
            // If both provided and contact record is found by id - personId is disregarded
    ) {
        EntityManager em = (EntityManager) request.getAttribute("em");
        Contact contact = null;
        try {
            // First we try to find an existing contact by id
            Integer idInt = new Integer(id);
            contact = em.find(Contact.class, idInt);
        } catch (Exception ex) {
            // id was not provided - normal flow for new contact
            // id was provided but could not be parsed and converted to number - should be reported as an error
        }
        // Here we should check if id was provided but corresponding contact record was not found in database
        // then report an error.
        // For this example we will treat it as a normal new contact entry flow.

        // Finding a person to whom this contact belongs
        Person person = null;
        if (contact != null) {
            // For existing contact - just retrieve related object
            person = contact.getPerson();
        } else {
            contact = new Contact();
            // For new contact find a person by provided personId parameter
            try {
                Integer idInt = new Integer(personId);
                person = em.find(Person.class, idInt);
            } catch (Exception ex) {
                // personId was not provided - should be reported as an error
                // personId was provided but could not be parsed an converted to number - should be reported as an error
            }
        }
        if (person == null) {
            // personId was not provided - should be reported as error earlier
            // personId could not be parsed and converted to number - should be reported as error earlier
            // personId was ok but there is no record with specified id - should be reported as error
            // In this simple example: for all error cases redirect to adddress list view which in turn will
            // redirect to person list view because there is no provided personId
            return new ModelAndView("redirect:../");
        }
        ModelAndView mv = new ModelAndView("contactEdit");
        mv.addObject("person", person);
        mv.addObject("contact", contact);
        return mv;
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save(
            // Required for updating an existing contact
            @RequestParam(name = "id", required = false) String id,
            // Required for creating new contact
            @RequestParam(name = "personId", required = false) String personId,
            // If both provided and contact record is found by id - personId is disregarded

            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "contact", required = false) String cont
    ) {
        EntityManager em = (EntityManager) request.getAttribute("em");
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Contact contact = null;
        try {
            // First we try to find an existing contact by id
            Integer idInt = new Integer(id);
            contact = em.find(Contact.class, idInt);
        } catch (Exception ex) {
            // id was not provided - normal flow for new contact
            // id was provided but could not be parsed and converted to number - should be reported as an error
        }
        // Here we should check if id was provided but corresponding contact record was not found in database
        // then report an error.
        // For this example we will treat it as a normal new contact entry flow.
        if (contact == null) {
            // Crearing new contact
            contact = new Contact();
        }
        if (contact.getPerson()== null) {
            // For new contact we have to find a person object to whom this contact should belong.
            try {
                Integer idInt = new Integer(personId);
                Person person = em.find(Person.class, idInt);
                contact.setPerson(person);
            } catch (Exception ex) {
                // personId was not provided for new contact - should be reported as an error
                // personId was provided but could not be parsed an converted to number - should be reported as an error
            }
        }
        if (contact.getPerson()== null) {
            // personId was not provided for new contact - should be reported as an error earlier
            // personId was provided but could not be parsed an converted to number - should be reported as an error earlier
            // Person was not found by provided personId - should be reported as an error
            // for this example for all error cases just redirect back to contact list
            // which in turn will redirect to person list because there is no provided personId
            return "redirect:./";
        }
        // Setting properties for object
        contact.setContactType(type);
        contact.setContact(cont);
        // Saving object to DB
        em.persist(contact);
        tx.commit();
        // Redirecting to list of contacts for specified person
        return "redirect:./?personId=" + contact.getPerson().getId();
    }

    @RequestMapping(path = "delete", method = RequestMethod.GET)
    public String delete(
            @RequestParam(name = "id") String id
    ) {
        EntityManager em = (EntityManager) request.getAttribute("em");
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Integer personId = null;
        try {
            Integer idInt = new Integer(id);
            Address address = em.find(Address.class, idInt);
            Contact contact = em.find(Contact.class, idInt);
            personId = contact.getPerson().getId();
            em.remove(contact);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            // Here can be three possibilities:
            // - id was not passed - should be reported as error;
            // - passed id string could not be converted to number - should be reported as an error;
            // - id was ok but record was not found in database - should be reported as an error.
            // In this simple example all error cases will result in no operation.
        }
        if (personId == null) {
            // if contact was not found and deleted (this variable will be null)
            // redirect back to contact list which in turn will redirect to person list
            // because there is no provided personId
            return "redirect:./";
        }
        // Redirecting to list of contacts for specified person
        return "redirect:./?personId=" + personId;
    }
}
