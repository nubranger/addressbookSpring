package lt.bit.controllers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import lt.bit.data.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/")
public class PersonController {

    @Autowired
    private HttpServletRequest request;

    // Needed to convert date to string and back
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        EntityManager em = (EntityManager) request.getAttribute("em");
        Query q = em.createQuery("select p from Person p");
        List<Person> l = q.getResultList();
        ModelAndView mv = new ModelAndView("personList");
        mv.addObject("personList", l);
        return mv;
    }

    @RequestMapping(path = "edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(name = "id", required = false) String id) {
        EntityManager em = (EntityManager) request.getAttribute("em");
        Person person = null;
        try {
            Integer idInt = new Integer(id);
            person = em.find(Person.class, idInt);
        } catch (Exception ex) {
            // id was not passed - meaning new person should be created.
            // Another possiblity: passed id string could not be converted to number -
            // in real application we should report an error to client but here we will treat it
            // exactly the same as there was no id - meaning new person should be created.
        }
        if (person == null) {
            // Here can be three possibilities:
            // - id was not passed - normal flow for new object;
            // - passed id string could not be converted to number - should have been reported as error;
            // - id was ok but record was not found in database - should be reported as an error to client earlier.
            // For this simple example we will treat all cases as normal flow for creating new object.
            // Here we create new empty object to pass to view to avoid checking for null while generating view
            person = new Person();
        }
        ModelAndView mv = new ModelAndView("personEdit");
        mv.addObject("person", person);
        return mv;
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String save (
        @RequestParam(name = "id", required = false) String id,
        @RequestParam(name = "firstName", required = false) String firstName,
        @RequestParam(name = "lastName", required = false) String lastName,
        @RequestParam(name = "birthDate", required = false) String birthDate,
        @RequestParam(name = "salary", required = false) String salary
    ) {
        EntityManager em = (EntityManager) request.getAttribute("em");
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Person person = null;
        try {
            Integer idInt = new Integer(id);
            person = em.find(Person.class, idInt);
        } catch (Exception ex) {
            // id was not passed - meaning new person (record in DB) should be created.
            // Another possiblity: passed id string could not be converted to number -
            // in real application we should report an error to client but here we will treat it
            // exactly the same as there was no id - meaning new person should be created.
        }
        if (person == null) {
            // Here can be three possibilities:
            // - id was not passed - normal flow for new object;
            // - passed id string could not be converted to number - should have been reported as error earlier;
            // - id was ok but record was not found in database - should be reported as an error to client.
            // For this simple example we will treat all cases as normal flow for creating new object (record in DB).
            person = new Person();
        }
        // Setting properties for object
        person.setFirstName(firstName);
        person.setLastName(lastName);
        try {
            person.setBirthDate(sdf.parse(birthDate));
        } catch (Exception ex) {
            // Failed to parse passed date string:
            // - date string was not provided - set to null;
            // - date string was incorrectly formatted and could not be parsed - should be reported
            // as an error to client. Here we just set to null.
            person.setBirthDate(null);
        }
        try {
            person.setSalary(new BigDecimal(salary));
        } catch (Exception ex) {
            // Failed to parse passed number string:
            // - number string was not provided - set to null;
            // - number string was incorrectly formatted and could not be parsed - should be reported
            // as an error to client. Here we just set to null.
            person.setSalary(null);
        }
        // Saving object to DB
        em.persist(person);
        tx.commit();
        // Redirect to list view
        return "redirect:/";
    }

    @RequestMapping(path = "delete", method = RequestMethod.GET)
    public String delete (
            @RequestParam(name = "id") String id
    ) {
        EntityManager em = (EntityManager) request.getAttribute("em");
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Integer idInt = new Integer(id);
            Person person = em.find(Person.class, idInt);
            if (person != null) {
                em.remove(person);
            }
        } catch (Exception ex) {
            // Here can be three possibilities:
            // - id was not passed - should be reported as error;
            // - passed id string could not be converted to number - should be reported as an error;
            // - id was ok but record was not found in database - should be reported as an error.
            // In this simple example all error cases will result in no operation.
        }
        tx.commit();
        // Redirect to list view
        return "redirect:/";
    }
}
