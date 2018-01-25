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
                <td>${foundEssence.nickname}</td>
            </tr>
            <tr>
                <td>Name</td>
                <td>${foundEssence.name}</td>
            </tr>
            <tr>
                <td>Surname</td>
                <td>${foundEssence.surname}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${foundEssence.email}</td>
            </tr>
            <tr>
                <td>Role</td>
                <td>${foundEssence.role}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
