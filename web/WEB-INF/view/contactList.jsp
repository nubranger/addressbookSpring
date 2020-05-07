<%@ page import="lt.bit.data.Person" %>
<%@ page import="lt.bit.data.Contact" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Person contact list</title>
    </head>
    <body>
<%
        Person person = (Person) request.getAttribute("person");
%>
        <h1>Contacts for <%=person.getFirstName() + " " + person.getLastName()%></h1>
<%
        List<Contact> list = (List<Contact>) request.getAttribute("contactList");
        if (list == null || list.size() == 0) {
%>
        <h1>Empty list</h1>
<%
        } else {
%>
        <table>
            <tr>
                <td>Id</td>
                <td>Type</td>
                <td>Contact</td>
                <td colspan="2">Actions</td>
            </tr>
<%
            for (Contact contact : list) {
%>
            <tr>
                <td><%=contact.getId()%></td>
                <td><%=(contact.getContactType() != null)?contact.getContactType():""%></td>
                <td><%=(contact.getContact() != null)?contact.getContact():""%></td>
                <td><a href="./edit?id=<%=contact.getId()%>">Edit</a></td>
                <td><a href="./delete?id=<%=contact.getId()%>">Delete</a></td>
            </tr>
        <%
                }
        %>
        </table>
        <%
            }
        %>
        <a href="./edit?personId=<%=person.getId()%>">New</a>
        <br>
        <a href="../">Back</a>
    </body>
</html>
