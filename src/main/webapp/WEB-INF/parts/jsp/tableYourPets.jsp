<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
    <caption>Your pets</caption>
    <thead>
    <tr>
        <th>Name</th>
        <th>Weight</th>
        <th>Birthday</th>
        <th>Species name</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${user.pets}" var="pet" varStatus="status">
        <tr>
            <td>${pet.name}</td>
            <td>${pet.weight}</td>
            <td>${pet.birthday}</td>
            <td>${pet.species.name}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>