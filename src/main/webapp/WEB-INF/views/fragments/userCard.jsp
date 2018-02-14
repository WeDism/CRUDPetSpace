<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-4">
    <div class="card text-white bg-info">
        <img src="<c:url value="/web_resources/images/user.png"/>" class="card-img-top" height="320" width="320">
        <h4 class="card-text">About of <c:out value="${user.name}"/> <c:out value="${user.surname}"/></h4>
        <p class="card-text"><c:out value="${user.aboutOfSelf}"/></p>
    </div>
</div>
<div class="col-8">
    <div class="row">
        <div class="col-6">Nickname</div>
        <div class="col-6"><c:out value="${user.nickname}"/></div>
    </div>
    <div class="row">
        <div class="col-6">Name</div>
        <div class="col-6"><c:out value="${user.name}"/></div>
    </div>
    <div class="row">
        <div class="col-6">Surname</div>
        <div class="col-6"><c:out value="${user.surname}"/></div>
    </div>
    <div class="row">
        <div class="col-6">Email</div>
        <div class="col-6"><c:out value="${user.email}"/></div>
    </div>
    <div class="row">
        <div class="col-6">Role</div>
        <div class="col-6"><c:out value="${user.role}"/></div>
    </div>
</div>
