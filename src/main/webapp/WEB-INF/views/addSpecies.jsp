<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="fragments/htmlHeadTags.jsp"/></head>
<body>
<div class="container">
    <c:import url="fragments/bodyHeader.jsp"/>
    <div class="row">
        <div class="col-4 offset-4">
            <h2>Add species pet</h2>
        </div>
    </div>
    <c:choose>
        <c:when test="${not empty speciesPetIsAdded and not speciesPetIsAdded}">
            <div class="row">
                <div class="col-4 offset-4 bg-danger">
                    <h2>Species already exists</h2>
                </div>
            </div>
        </c:when>
        <c:when test="${not empty speciesPetIsAdded and speciesPetIsAdded}">
            <div class="row">
                <div class="col-4 offset-4 bg-success">
                    <h2>Species added</h2>
                </div>
            </div>
        </c:when>
    </c:choose>
    <div class="row">
        <form class="col" action="${pageContext.request.contextPath}${requestScope['javax.servlet.forward.servlet_path']}"
              method="post">
            <div class="row">
                <div class="col-4"><label>Name species <input type="text" name="name" placeholder="Input name" required></label></div>
                <div class="col-2 offset-8"><input class="btn btn-lg btn-primary btn-block" type="submit"></div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col">
            <table class="table table-hover">
                <caption>Species</caption>
                <c:set var="speciesSet" value="<%=com.pets_space.storages.SpeciesPetStorage.getInstance().getAll()%>"/>
                <c:forEach items="${speciesSet}" var="species">
                    <tr>
                        <td>
                            <c:out value="${species.name}"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>
