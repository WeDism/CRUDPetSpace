<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="custom_tags" %>
<table>
    <caption>Your friends</caption>
    <thead>
    <tr>
        <th>Nickname</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Role</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody data-path-for-state-friend="${pageContext.request.contextPath}${homepage}/state_friend_controller">
    <c:forEach items="${ctg:getFriends(user.requestedFriendsTo)}" var="liteEssence">
        <tr data-essence-id="${liteEssence.userEssenceId}">
            <td>
                <a href="<c:url value="${homepage}"/>/essence?nickname=<c:out value="${liteEssence.nickname}"/>">${liteEssence.nickname}</a>
            </td>
            <td><c:out value="${liteEssence.name}"/></td>
            <td><c:out value="${liteEssence.surname}"/></td>
            <td><c:out value="${liteEssence.role}"/></td>
            <td><select class="action-friends" name="action-friends" required>
                <c:set var="stateFriendSet" value="<%=com.pets_space.models.essences.StateFriend.values()%>"/>
                <c:forEach items="${stateFriendSet}" var="stateFriend" varStatus="status">
                    <option value="<c:out value="${stateFriend}"/>"
                            <c:if test="${user.requestedFriendsTo.get(liteEssence.userEssenceId).equals(stateFriend)}">selected</c:if>>
                        <c:out value="${stateFriend}"/></option>
                </c:forEach>
            </select></td>
        </tr>
    </c:forEach>
    </tbody>
</table>