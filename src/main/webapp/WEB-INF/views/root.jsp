<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../parts/jspf/htmlHeadTags.jspf" %>
</head>
<body>
<script>
    init = function (button) {
        $.ajax({
            url: "${pageContext.request.contextPath}${requestScope['javax.servlet.forward.servlet_path']}" + '?user=' + button.value,
            async: false,
            type: "DELETE",
            success: function () {
                button.parentNode.parentNode.parentNode.removeChild(button.parentNode.parentNode);
            }
        })
    };
</script>
<div>
    <div>
        <div>Root space</div>
        <div style="width: 100%; display: inline-block; text-align: right;">
            <a href="<c:url value="/login?logout"/>">Exit</a>
        </div>
    </div>
    <div>
        <jsp:include page="../parts/jsp/tableUserInfo.jsp" flush="true"/>
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
            <tr>
                <td>
                    <button value="${user.userEntryId}" onclick="init(this)">
                        <img src="<c:url value="/web_resources/images/delete.ico"/>" alt="delete user" width="8">
                    </button>
                </td>
                <td><a href="<c:url value="/user?id=${user.userEntryId}"/>">${user.nickname}</a></td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.patronymic}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>${user.statusEntry}</td>
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
        <jsp:include page="../parts/jsp/addSpeciesPart.jsp" flush="true"/>
    </div>
</div>
</body>
</html>
