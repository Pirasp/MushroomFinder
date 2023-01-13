<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset=de.othr.im.entities.Mushroom"UTF-8">
<title>Add Mushroom</title>
</head>
<body>
    <h1>Add Mushroom</h1>
    <form:form action="add" method="post" modelAttribute="mushroom" enctype="multipart/form-data">
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
                <td><form:input type="file" name="picture" accept="image/png, image/jpeg" path="picture"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</body>
</html>