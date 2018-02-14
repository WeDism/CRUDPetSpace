<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="fragments/htmlHeadTags.jsp"/></head>
<body>
<div class="container">
    <c:import url="fragments/bodyHeader.jsp"/>
    <div class="row">
        <div class="col-4 offset-4">
            <h2>Add your pet</h2>
        </div>
    </div>
    <div class="row">
        <form class="col" action="<c:url value="${requestScope['javax.servlet.forward.servlet_path']}"/>" method="post">
            <div class="row">
                <div class="col-4"><label>Name
                    <input type="text" name="name" placeholder="name" required></label></div>
                <div class="col-4 offset-4"><label>Weight
                    <input type="text" name="weight" placeholder="3.14" required></label></div>
            </div>
            <div class="row">
                <div class="col-4"><label>Birthday
                    <input type="datetime-local" name="birthday" required></label></div>
                <div class="col-4 offset-4"><label>Species
                    <select name="species" required>
                        <c:forEach items="${species}" var="species" varStatus="status">
                            <option><c:out value="${species.name}"/></option>
                        </c:forEach>
                    </select></label></div>
            </div>
            <div class="row"><div class="col-2 offset-9"><input class="btn btn-lg btn-primary btn-block" type="submit"></div></div>
        </form>
    </div>
</div>
</body>
</html>
