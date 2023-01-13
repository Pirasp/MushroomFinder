<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mushroom Lexicon</title>
</head>
<body>
<h1>Mushroom Lexicon</h1>
<form action="/lexicon" method="get">
    <label for="searchTerm">Search Term:</label>
    <input type="text" id="searchTerm" name="searchTerm" value="${searchTerm}">
    <input type="submit" value="Search">
</form>
<table>
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Picture</th>
    </tr>
    <c:forEach var="mushroom" items="${mushrooms}">
        <tr>
            <td>${mushroom.name}</td>
            <td>${mushroom.description}</td>
            <td><img src="pictures/${mushroom.picture}" alt="mushroom picture" style="height: 150px"></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>