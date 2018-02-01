<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="../parts/fragments/htmlHeadTags.jsp"/></head>
<body>
<div>
    <div>
        <c:import url="../parts/fragments/bodyHeader.jsp"/>
    </div>
    <div>
        <c:choose>
            <c:when test="${empty speciesPetIsAdded}">
            </c:when>
            <c:when test="${speciesPetIsAdded.present}">
                <h2>Species added</h2>
            </c:when>
            <c:otherwise>
                <h2>Your species not added. Species could have been added earlier.</h2>
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
