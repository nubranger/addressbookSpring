<%@ page import="lt.bit.data.Person" %>
<%@ page import="lt.bit.data.Address" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Person address list</title>
    </head>
    <body>
<%
        Person person = (Person) request.getAttribute("person");
%>
        <h1>Addresses for <%=person.getFirstName() + " " + person.getLastName()%></h1>
<%
        List<Address> list = (List<Address>) request.getAttribute("addressList");
        if (list == null || list.size() == 0) {
%>
        <h1>Empty list</h1>
<%
        } else {
%>
        <table>
            <tr>
                <td>Id</td>
                <td>Address</td>
                <td>City</td>
                <td>Postal code</td>
                <td colspan="2">Actions</td>
            </tr>
<%
            for (Address address : list) {
%>
            <tr>
                <td><%=address.getId()%></td>
                <td><%=(address.getAddress() != null)?address.getAddress():""%></td>
                <td><%=(address.getCity() != null)?address.getCity():""%></td>
                <td><%=(address.getPostalCode() != null)?address.getPostalCode():""%></td>
                <td><a href="./edit?id=<%=address.getId()%>">Edit</a></td>
                <td><a href="./delete?id=<%=address.getId()%>">Delete</a></td>
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
