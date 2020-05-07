<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="lt.bit.data.Person" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Persons list</title>
    </head>
    <body>
<%
        List<Person> list = (List<Person>) request.getAttribute("personList");
        if (list == null || list.size() == 0) {
%>
        <h1>Empty list</h1>
<%
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>
        <table>
            <tr>
                <td>Id</td>
                <td>First name</td>
                <td>Last name</td>
                <td>Birth date</td>
                <td>Salary</td>
                <td colspan="4">Actions</td>
            </tr>
<%
            for (Person person : list) {
                String birthDate = "";
                if (person.getBirthDate() != null) {
                    birthDate = sdf.format(person.getBirthDate());
                }
                String salary = (person.getSalary() != null)?person.getSalary().toPlainString():"";
%>
            <tr>
                <td><%=person.getId()%></td>
                <td><%=(person.getFirstName() != null)?person.getFirstName():""%></td>
                <td><%=(person.getLastName() != null)?person.getLastName():""%></td>
                <td><%=birthDate%></td>
                <td><%=salary%></td>
                <td><a href="./edit?id=<%=person.getId()%>">Edit</a></td>
                <td><a href="./delete?id=<%=person.getId()%>">Delete</a></td>
                <td><a href="./address/?personId=<%=person.getId()%>">Addresses</a></td>
                <td><a href="./contact/?personId=<%=person.getId()%>">Contacts</a></td>
            </tr>
<%
            }
%>
        </table>
<%
        }
%>
        <a href="./edit">New</a>
    </body>
</html>
