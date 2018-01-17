<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../jspf/htmlHeadTags.jspf" %>
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
</div>
</body>
</html>
