<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="fragments/htmlHeadTags.jsp"/>
    <c:import url="fragments/htmlHeadAdminAndUserTags.jsp"/></head>
<body>
<div class="container">
    <c:import url="fragments/bodyHeader.jsp"/>
    <div class="row c-row">
        <c:import url="fragments/userCard.jsp"/>
    </div>
    <div class="row c-row">
        <c:import url="fragments/tableYourFriends.jsp"/>
    </div>
    <div class="row c-row">
        <c:import url="fragments/tableYourPets.jsp"/>
    </div>
    <div class="row">
        <div class="col-3">
            <c:import url="fragments/addGenusPetPart.jsp"/>
        </div>
        <div class="col-2 offset-2">
            <a class="btn btn-lg btn-primary btn-block" href="<c:url value="/admin/add_pet"/>">Add pet</a>
        </div>
        <div class="col-3 offset-2">
            <a class="btn btn-lg btn-primary btn-block" href="<c:url value="/admin/find_friend"/>">Find friend</a>
        </div>
    </div>
</div>
</body>
</html>
