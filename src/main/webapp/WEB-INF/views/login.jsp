<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="fragments/htmlHeadTags.jsp"/>
    <link rel="stylesheet" href="<c:url value="/web_resources/css/custom/signin.css"/>">
</head>
<body>
<div class="container">
    <form id="loginForm" class="form-signin" action="<c:url value="/login"/>" method="post">
        <h2 class="form-signin-heading"><a href="<c:url value="/login"/>">Pet network</a></h2>
        <input class="form-control" type="text" name="nickname" placeholder="Nickname" required autofocus>
        <input class="form-control" type="password" name="password" placeholder="Password" required>
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Sign in">
    </form>
</div>
</body>
</html>