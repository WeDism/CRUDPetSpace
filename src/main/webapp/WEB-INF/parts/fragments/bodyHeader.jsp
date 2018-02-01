<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div><h2>Pet space</h2></div>
<div>
    <h3><jsp:include page="home.jsp"/></h3>
</div>
<div style="width: 100%; display: inline-block; text-align: right;">
    <h3><a href="<c:url value="/login?logout"/>">Exit</a></h3>
</div>