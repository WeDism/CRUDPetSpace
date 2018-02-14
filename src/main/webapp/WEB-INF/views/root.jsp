<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="fragments/htmlHeadTags.jsp"/>
    <script src='<c:url value="/web_resources/js/custom/root.js"/>'></script>
</head>
<body>
<div class="container">
    <div>
        <div>
            <c:import url="fragments/bodyHeader.jsp"/>
        </div>
    </div>
    <div>
        <c:import url="fragments/tableUserInfo.jsp"/>
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
                <td>
                    <a href="<c:url value="${homepage}"/>/essence?nickname=<c:out value="${user.nickname}"/>">${user.nickname}</a>
                </td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.surname}"/></td>
                <td><c:out value="${user.patronymic}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><select name="user-essence-roles" class="user-essence-roles">
                    <c:forEach items="${roles}" var="role" varStatus="status">
                        <option value="<c:out value="${role}"/>"
                                <c:if test="${role == user.role}">selected</c:if>>
                            <c:out value="${role}"/></option>
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
        <c:import url="fragments/addSpeciesPart.jsp"/>
    </div>
</div>
</body>
</html>
