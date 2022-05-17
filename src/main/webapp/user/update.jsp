<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
<center>
    <h1>User Management</h1>
</center>
<div align="center">
    <form method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    Edit User
                </h2>
            </caption>
            <c:if test="${userList != null}">
                <input type="hidden" name="id" value="<c:out value='${userList.id}' />"/>
            </c:if>
            <tr>
                <th>User Name:</th>
                <td>
                    <input type="text" name="name" size="45"
                           value="<c:out value='${userList.name}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>User country:</th>
                <td>
                    <input type="text" name="country" size="45"
                           value="<c:out value='${userList.country}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>age:</th>
                <td>
                    <input type="number" name="age" size="15"
                           value="<c:out value='${userList.age}' />"
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="EDIT"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>