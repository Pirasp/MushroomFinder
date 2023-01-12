<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Mushroom</title>
</head>
<body>
    <h1>Add Mushroom</h1>
    <form:form action="add" method="post" modelAttribute="mushroom">
        <table>
            <tr>
                <td>Name:</td>
                <td><form:input path="name"/></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><form:textarea path="description"/></td>
            </tr>
            <tr>
                <td>Picture:</td>
                <td><form:input type="file" path="picture"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</body>
</html>