<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <%@include file="../parts/jspf/htmlHeadTags.jspf" %>
</head>
<body>
<div>
    <div>
        <c:choose>
            <c:when test="${speciesPetIsAdded}">
                <h2>Species added</h2>
            </c:when>
            <c:otherwise>
                <h2>Species not added</h2>
            </c:otherwise>
        </c:choose>
    </div>
    <form action="${pageContext.request.contextPath}${requestScope['javax.servlet.forward.servlet_path']}"
          method="post">
        <div><label>Name
            <input type="text" name="name" placeholder="name" required></label></div>
        <div><input type="submit"></div>
    </form>
    <a href="${pageContext.request.contextPath}/${fn:toLowerCase(user.role)}">Return to main page</a>
</div>
</body>
</html>
