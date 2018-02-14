<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="fragments/htmlHeadTags.jsp"/></head>
<body>
<div class="container">
    <div>
        <c:import url="fragments/bodyHeader.jsp"/>
    </div>
    <div>
        <c:if test="${speciesPetIsAdded}">
            <h2>Species added</h2>
        </c:if>
    </div>
    <form action="${pageContext.request.contextPath}${requestScope['javax.servlet.forward.servlet_path']}"
          method="post">
        <div><label>Name
            <input type="text" name="name" placeholder="name" required>
            <input ass="btn btn-lg btn-primary btn-block" type="submit"></label></div>
    </form>
</div>
</body>
</html>
