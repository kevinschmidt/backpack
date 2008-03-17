<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
<head>
	<title>Backpack</title>
</head>



<body>
<h1 align="center">Backpack GTD</h1>

<table width="100%">
<tr valign="top"><th width="33%">Next Lists</th><th width="33%">Waiting Lists</th><th width="33%">Later Lists</th></tr>
<tr valign="top">

<c:forEach var="allLists" items="${model}">
<c:if test="${fn:startsWith(allLists, 'all')}" >
<td>
<table>
<!--<tr valign="top"><th>Page</th><th>Items</th></tr>-->
<c:forEach var="map" items="${allLists.value}"> 
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
</td>
</c:if>
</c:forEach>

</tr>

<tr valign="top"><td width="33%" align="center"><b>Inbox</b></td></tr>
<tr valign="top">

<td>
<table>
<c:forEach var="map" items="${model.extraLists}"> 
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
</td>

</tr>
</table>

</body>
</html>