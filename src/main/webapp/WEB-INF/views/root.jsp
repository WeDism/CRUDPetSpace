<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="fragments/htmlHeadTags.jsp"/>
    <script src='<c:url value="/web_resources/js/custom/root.js"/>'></script>
</head>
<body>
<div class="container">
    <c:import url="fragments/bodyHeader.jsp"/>
    <div class="row">
        <c:import url="fragments/userCard.jsp"/>
    </div>
    <div class="row">
        <div class="col">
            <table class="table table-hover">
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
                        <td>
                            <c:choose>
                                <c:when test="${'ROOT' != user.role}">
                                    <select name="user-essence-roles" class="user-essence-roles">
                                        <c:forEach items="${roles}" var="role" varStatus="status">
                                            <option value="<c:out value="${role}"/>" <c:if test="${role == user.role}">selected</c:if>>
                                                <c:out value="${role}"/>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <label><c:out value="ROOT"/></label>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${user.statusEssence}</td>
                        <td>
                            <c:forEach items="${user.pets}" var="pet" varStatus="status">
                                ${pet.name}&nbsp;(${pet.genusPet.name})<br/>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="row">
        <div class="col-3 offset-7">
            <c:import url="fragments/addGenusPetPart.jsp"/>

        </div>
    </div>
</div>
</body>
</html>
