<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="lt.bit.data.Person" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Persons edit</title>
    </head>
    <body>
        <%
            Person person = (Person) request.getAttribute("person");
            // No checking for person == null because it is always provided in model
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String birthDate = "";
            if (person.getBirthDate() != null) {
                birthDate = sdf.format(person.getBirthDate());
            }
            String salary = (person.getSalary() != null)?person.getSalary().toPlainString():"";
        %>
        <form action="./save" method="POST">
            <input type="hidden" name="id" value="<%=(person != null)?person.getId():""%>">
            First name: <input type="text" name="firstName" value="<%=(person.getFirstName() != null)?person.getFirstName():""%>"><br>
            Last name: <input type="text" name="lastName" value="<%=(person.getLastName() != null)?person.getLastName():""%>"><br>
            Birth date: <input type="date" name="birthDate" value="<%=birthDate%>"><br>
            Salary: <input type="number" step="0.01" name="salary" value="<%=salary%>"><br>
            <input type="submit" value="Save"><br>
        </form>
        <a href="./">Cancel</a>
    </body>
</html>
