<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-hover">
    <caption>Your pets</caption>
    <thead>
    <tr>
        <th>Name</th>
        <th>Weight</th>
        <th>Birthday</th>
        <th>Genus pet name</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${user.pets}" var="pet" varStatus="status">
        <tr>
            <td><c:out value="${pet.name}"/></td>
            <td><c:out value="${pet.weight}"/></td>
            <td><c:out value="${pet.birthday}"/></td>
            <td><c:out value="${pet.genusPet.name}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>