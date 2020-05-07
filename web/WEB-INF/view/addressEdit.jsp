<%@ page import="lt.bit.data.Person" %>
<%@ page import="lt.bit.data.Address" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Address edit</title>
    </head>
    <body>
<%
        Person person = (Person) request.getAttribute("person");
%>
        <h1>Address for <%=person.getFirstName() + " " + person.getLastName()%></h1>
<%
        Address address = (Address) request.getAttribute("address");
%>
        <form action="./save" method="POST">
            <input type="hidden" name="id" value="<%=(address.getId() != null)?address.getId():""%>">
            <input type="hidden" name="personId" value="<%=(person.getId() != null)?person.getId():""%>">
            Address: <input type="text" name="address" value="<%=(address.getAddress() != null)?address.getAddress():""%>"><br>
            City: <input type="text" name="city" value="<%=(address.getCity() != null)?address.getCity():""%>"><br>
            Postal code: <input type="text" name="postalCode" value="<%=(address.getPostalCode() != null)?address.getPostalCode():""%>"><br>
            <input type="submit" value="Save"><br>
        </form>
        <a href="./?personId=<%=(person.getId() != null)?person.getId():""%>">Cancel</a>
    </body>
</html>
