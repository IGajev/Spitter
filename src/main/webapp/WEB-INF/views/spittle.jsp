<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
	<head>
		<title>Spittr</title>
		<link rel="stylesheet"
			type="text/css"
			href="<c:url value="/resources/style.css" />" >
	</head>
	<body>
		<h1>Spittle</h1>
			<div class="spittleView">
				<div class="spittleMessage"> <c:out value="spittle.message"> </c:out> </div>
				<div> <span class="spittleTime"><c:out value="spittle.time"> </c:out> </span> </div>
			</div>
	</body>
</html>




