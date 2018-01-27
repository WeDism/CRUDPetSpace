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
        <c:import url="../parts/jsp/tableYourFriends.jsp"/>
    </div>
    <div>
        <c:import url="../parts/jsp/tableUserInfo.jsp"/>
    </div>
    <div>
        <c:import url="../parts/jsp/tableYourPets.jsp"/>
    </div>
    <div>
        <c:import url="../parts/jsp/addSpeciesPart.jsp"/>
    </div>
    <div>
        <a href="<c:url value="/admin/add_pet"/>">Add pet</a>
    </div>
    <div>
        <a href="<c:url value="/admin/find_friend"/>">Find friend</a>
    </div>
</div>
</body>
</html>
