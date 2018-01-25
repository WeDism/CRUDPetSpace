<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../parts/jspf/htmlHeadTags.jspf" %>
    <script src='<c:url value="/web_resources/js/root.js"/>'></script>
</head>
<body>
<div>
    <div>
        <div>
            <c:import url="../parts/jsp/bodyHeader.jsp"/>
        </div>
    </div>
    <div>
        <c:import url="../parts/jsp/tableUserInfo.jsp"/>
    </div>
    <table>
        <caption>Users</caption>
        <thead>
        <tr>
            <th>Actions</th>
            <th>Nickname</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Patronymic</th>
            <th>Email</th>
            <th>Role</th>
            <th>Status</th>
            <th>Pets</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user" varStatus="status">
            <tr data-essence-id="${user.userEssenceId}">
                <td>
                    <button class="delete-user-of-button">
                        <img src="<c:url value="/web_resources/images/delete.ico"/>" alt="delete user" width="8">
                    </button>
                </td>
                <td><a href="<c:url value="/essence?nickname=${user.nickname}"/>">${user.nickname}</a></td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.patronymic}</td>
                <td>${user.email}</td>
                <td><select name="user-essence-roles" class="user-essence-roles">
                    <c:forEach items="${roles}" var="role" varStatus="status">
                        <option value="${role}" <c:if test="${role == user.role}"><c:out
                                value="selected"/></c:if>>${role}</option>
                    </c:forEach>
                </select></td>
                <td>${user.statusEssence}</td>
                <td>
                    <c:forEach items="${user.pets}" var="pet" varStatus="status">
                        ${pet.name}&nbsp;(${pet.species.name})<br/>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div>
        <c:import url="../parts/jsp/addSpeciesPart.jsp"/>
    </div>
</div>
</body>
</html>
