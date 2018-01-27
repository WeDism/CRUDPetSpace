<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../parts/jspf/htmlHeadTags.jspf" %>
</head>
<body>
<div>
    <div>
        <c:import url="../parts/jsp/bodyHeader.jsp"/>
    </div>
    <div>
        <table>
            <tr>
                <td>Nickname</td>
                <td><c:out value="${foundEssence.nickname}"/></td>
            </tr>
            <tr>
                <td>Name</td>
                <td><c:out value="${foundEssence.name}"/></td>
            </tr>
            <tr>
                <td>Surname</td>
                <td><c:out value="${foundEssence.surname}"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><c:out value="${foundEssence.email}"/></td>
            </tr>
            <tr>
                <td>Role</td>
                <td><c:out value="${foundEssence.role}"/></td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
