<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../jspf/htmlHeadTags.jspf" %>
</head>
<body>
<div>
    <h2>
        <div style="display: inline-block;">
            <a href="<c:url value="/user"/>">Pet space</a>
        </div>
        <div style="width: 100%; display: inline-block; text-align: right;">
            <a href="<c:url value="/login?logout"/>">Exit</a>
        </div>
    </h2>
    <div>
        <%@include file="../jspf/tableUserInfo.jspf" %>
    </div>
</div>
</body>
</html>
