<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../parts/jspf/htmlHeadTags.jspf" %>
</head>
<body>
<div>
    <div>
        <jsp:include page="../parts/jsp/bodyHeader.jsp"/>
    </div>
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
