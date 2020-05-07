<%@ page import="lt.bit.data.Person" %>
<%@ page import="lt.bit.data.Contact" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contact edit</title>
    </head>
    <body>
<%
        Person person = (Person) request.getAttribute("person");
%>
        <h1>Contact for <%=person.getFirstName() + " " + person.getLastName()%></h1>
<%
        Contact contact = (Contact) request.getAttribute("contact");
%>
        <form action="./save" method="POST">
            <input type="hidden" name="id" value="<%=(contact.getId() != null)?contact.getId():""%>">
            <input type="hidden" name="personId" value="<%=(person.getId() != null)?person.getId():""%>">
            Type: <input type="text" name="type" value="<%=(contact.getContactType()!= null)?contact.getContactType():""%>"><br>
            Contact <input type="text" name="contact" value="<%=(contact.getContact()!= null)?contact.getContact():""%>"><br>
            <input type="submit" value="Save"><br>
        </form>
        <a href="./?personId=<%=(person.getId() != null)?person.getId():""%>">Cancel</a>
    </body>
</html>
