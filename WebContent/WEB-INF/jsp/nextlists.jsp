<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
<head><title>Backpack</title></head>
<body>
<h1>Next List</h1>

<table>
<tr><th>Page</th><th>Items</th></tr>
<c:forEach var="map" items="${model.allNextLists}" > 
	<tr valign="top">
		<td>${map.key}</td>
		<td>
			<ul>
				<c:forEach var="item" items="${map.value}">
					<li>${item}</li>
				</c:forEach>
			</ul>
		</td>
	</tr>
</c:forEach>
</table>
</body>
</html>