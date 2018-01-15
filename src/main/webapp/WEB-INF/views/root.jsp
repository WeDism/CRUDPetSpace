<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../jspf/htmlHeadTags.jspf" %>
</head>
<body>
<div>
    <div>
        <div>Root space</div>
        <div style="width: 100%; display: inline-block; text-align: right;">
            <a href="<c:url value="/login?logout"/>">Exit</a>
        </div>
    </div>
    <div>
        <%@include file="../jspf/tableUserInfo.jspf" %>
    </div>
    <table>
        <thead>
        <tr>
            <th>Nickname</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Patronymic</th>
            <th>Email</th>
            <th>Role</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user" varStatus="status">
            <tr>
                <td><a style="text-decoration: none; color:inherit;"
                       href="<c:url value="/user?id=${user.userEntryId}"/>"><c:out value="${user.nickname}"/></a></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.surname}"/></td>
                <td><c:out value="${user.patronymic}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td><c:out value="${user.statusEntry}"/></td>
                <td>
                    <c:forEach items="${user.pets}" var="pet" varStatus="status">
                        <c:out value="${pet.name}"/>&nbsp;(<c:out value="${pet.species.name}"/>)<br/>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>
