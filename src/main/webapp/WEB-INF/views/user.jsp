<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="../parts/fragments/htmlHeadTags.jsp"/></head>
<body>
<div>
    <div>
        <c:import url="../parts/fragments/bodyHeader.jsp"/>
    </div>
    <div>
        <c:import url="../parts/fragments/tableYourFriends.jsp"/>
    </div>
    <div>
        <c:import url="../parts/fragments/tableUserInfo.jsp"/>
    </div>
    <div>
        <c:import url="../parts/fragments/tableYourPets.jsp"/>
    </div>
    <div>
        <a href="<c:url value="/user/add_pet"/>">Add pet</a>
    </div>
    <div>
        <a href="<c:url value="/user/find_friend"/>">Find friend</a>
    </div>
</div>
</body>
</html>
