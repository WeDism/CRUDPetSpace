<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
    <caption>About of <c:out value="${user.name}"/> <c:out value="${user.surname}"/></caption>
    <tr>
        <td>Nickname</td>
        <td><c:out value="${user.nickname}"/></td>
    </tr>
    <tr>
        <td>Name</td>
        <td><c:out value="${user.name}"/></td>
    </tr>
    <tr>
        <td>Surname</td>
        <td><c:out value="${user.surname}"/></td>
    </tr>
    <tr>
        <td>Email</td>
        <td><c:out value="${user.email}"/></td>
    </tr>
    <tr>
        <td>Role</td>
        <td><c:out value="${user.role}"/></td>
    </tr>
</table>
