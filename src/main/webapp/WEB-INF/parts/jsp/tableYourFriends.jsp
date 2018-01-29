<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
    <caption>Your friends</caption>
    <thead>
    <tr>
        <th>Nickname</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${user.requestedFriendsTo}" var="entry">
        <tr>
            <td><a href="<c:url value="/essence"/>?nickname=<c:out value="${entry.key}"/>">Friend</a></td>
            <td><c:out value="${entry.value}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>