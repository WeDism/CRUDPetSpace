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
        <form id="findFriendForm"
              action="${pageContext.request.contextPath}${requestScope['javax.servlet.forward.servlet_path']}"
              method="post">
            <div style="margin-bottom: 25px">
                <label>Name
                    <input type="text" name="name" placeholder="name"></label>
            </div>
            <div style="margin-bottom: 25px">
                <label>Surname
                    <input type="text" name="surname" placeholder="surname"></label>
            </div>
            <div style="margin-bottom: 25px">
                <label>Patronymic
                    <input type="text" name="patronymic" placeholder="patronymic"></label>
            </div>
            <div style="margin-top:10px">
                <div>
                    <input type="submit" value="find">
                </div>
            </div>
        </form>
    </div>
    <c:if test="${friends != null}">
        <div>
            <table>
                <caption>Friends</caption>
                <thead>
                <tr>
                    <th>Nickname</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Patronymic</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Pets</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${friends}" var="friend" varStatus="status">
                    <tr data-essence-id="${friend.userEssenceId}">
                        <td><a href="<c:url value="/essence?nickname=${friend.nickname}"/>">${friend.nickname}</a></td>
                        <td>${friend.name}</td>
                        <td>${friend.surname}</td>
                        <td>${friend.patronymic}</td>
                        <td>${friend.email}</td>
                        <td>${friend.role}</td>
                        <td>${friend.statusEssence}</td>
                        <td>
                            <c:forEach items="${friend.pets}" var="pet" varStatus="status">
                                ${pet.name}&nbsp;(${pet.species.name})<br/>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>
</body>
</html>
