<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><c:import url="../parts/fragments/htmlHeadTags.jsp"/></head>
<body>
<div>
    <div>
        <c:import url="../parts/fragments/bodyHeader.jsp"/>
    </div>
    <form action="<c:url value="${requestScope['javax.servlet.forward.servlet_path']}"/>" method="post">
        <div><label>Name
            <input type="text" name="name" placeholder="name" required></label></div>
        <div><label>Weight
            <input type="text" name="weight" placeholder="3.14" required></label></div>
        <div><label>Birthday
            <input type="datetime-local" name="birthday" required></label></div>
        <div><label>Species
            <select name="species" required>
                <c:forEach items="${species}" var="species" varStatus="status">
                    <option><c:out value="${species.name}"/></option>
                </c:forEach>
            </select></label></div>
        <div><input type="submit"></div>
    </form>
</div>
</body>
</html>
