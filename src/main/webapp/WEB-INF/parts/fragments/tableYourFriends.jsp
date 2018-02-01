<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="custom_tags" %>
<table>
    <caption>Your friends</caption>
    <thead>
    <tr>
        <th>Nickname</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Role</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${ctg:getFriends(user.requestedFriendsTo)}" var="liteEssence">
        <tr>
            <td>
                <a href="<c:url value="${homepage}"/>/essence?nickname=<c:out value="${liteEssence.nickname}"/>">${liteEssence.nickname}</a>
            </td>
            <td><c:out value="${liteEssence.name}"/></td>
            <td><c:out value="${liteEssence.surname}"/></td>
            <td><c:out value="${liteEssence.role}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>