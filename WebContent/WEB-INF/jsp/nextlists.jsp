<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
<head><title>Backpack</title></head>
<body>
<h1>Next List</h1>
<table>
<tr><th>Name</th><th>Items</th></tr>
<c:forEach var="map" items="${model.allNextLists}" > 
	<tr><td>${map.key}</td><td>${map.value}</td></tr>
</c:forEach>
</table>
</body>
</html>