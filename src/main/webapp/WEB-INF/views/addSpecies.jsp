<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <input type="text" name="name" placeholder="name" required><input type="submit"></label></div>
    </form>
</div>
</body>
</html>
