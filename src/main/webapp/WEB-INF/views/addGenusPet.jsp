<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="fragments/htmlHeadTags.jsp"/></head>
<body>
<div class="container">
    <c:import url="fragments/bodyHeader.jsp"/>
    <div class="row">
        <div class="col-4 offset-4">
            <h2>Add genusPet pet</h2>
        </div>
    </div>
    <c:choose>
        <c:when test="${not empty genusPetIsAdded and not genusPetIsAdded}">
            <div class="row">
                <div class="col-4 offset-4 bg-danger">
                    <h2>Genus pet already exists</h2>
                </div>
            </div>
        </c:when>
        <c:when test="${not empty genusPetIsAdded and genusPetIsAdded}">
            <div class="row">
                <div class="col-4 offset-4 bg-success">
                    <h2>Genus pet added</h2>
                </div>
            </div>
        </c:when>
    </c:choose>
    <div class="row">
        <form class="col" action="${pageContext.request.contextPath}${requestScope['javax.servlet.forward.servlet_path']}"
              method="post">
            <div class="row">
                <div class="col-4"><label>Genus pet <input type="text" name="name" placeholder="Input name" required></label></div>
                <div class="col-2 offset-8"><input class="btn btn-lg btn-primary btn-block" type="submit"></div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col">
            <table class="table table-hover">
                <caption>Genus pet</caption>
                <c:set var="genusPetSet" value="<%=com.pets_space.storages.GenusPetStorage.getInstance().getAll()%>"/>
                <c:forEach items="${genusPetSet}" var="genusPet">
                    <tr>
                        <td>
                            <c:out value="${genusPet.name}"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>
