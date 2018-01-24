<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../parts/jspf/htmlHeadTags.jspf" %>
</head>
<body>
<div>
    <h2>
        <div>
            <a href="<c:url value="/admin"/>">Pet space</a>
        </div>
        <div style="width: 100%; display: inline-block; text-align: right;">
            <a href="<c:url value="/login?logout"/>">Exit</a>
        </div>
    </h2>
    <div>
        <jsp:include page="../parts/jsp/tableUserInfo.jsp" flush="true"/>
    </div>
    <div>
        <jsp:include page="../parts/jsp/tableYourPets.jsp" flush="true"/>
    </div>
    <div>
        <jsp:include page="../parts/jsp/addSpeciesPart.jsp" flush="true"/>
    </div>
    <div>
        <a href="<c:url value="/admin/add_pet"/>">Add pet</a>
    </div>
    <div>
        <a href="<c:url value="/admin/find_friend"/>">Find friend</a>
    </div>
</div>
</body>
</html>
