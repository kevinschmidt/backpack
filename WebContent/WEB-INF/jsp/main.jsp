<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
<head>
<title>Backpack</title>

<style>
    tr.odd	{background-color: rgb( 149, 206, 145 );}
    tr.even	{background-color: rgb( 189, 246, 185 );}
</style>
</head>



<body>
<h1 align="center">Backpack GTD</h1>

<table width="100%">
	<tr valign="top">
		<td width="10%"><strong>Private</strong></td>
		<td width="30%"><strong>Next List</strong></td>
		<td width="30%"><strong>Waiting List</strong></td>
		<td width="30%"><strong>Later List</strong></td>
	</tr>
	<c:forEach var="gtdList" items="${model.privateLists}" varStatus="status">
		<%@ include file="/WEB-INF/jsp/gtddisplay.jsp" %>
	</c:forEach>
	
	<tr valign="top">
		<td width="10%"><strong>Half-Private</strong></td>
		<td width="30%"><strong>Next List</strong></td>
		<td width="30%"><strong>Waiting List</strong></td>
		<td width="30%"><strong>Later List</strong></td>
	</tr>
	<c:forEach var="gtdList" items="${model.halfPrivateLists}" varStatus="status">
		<%@ include file="/WEB-INF/jsp/gtddisplay.jsp" %>
	</c:forEach>
	
	<tr valign="top">
		<td width="10%"><strong>Work</strong></td>
		<td width="30%"><strong>Next List</strong></td>
		<td width="30%"><strong>Waiting List</strong></td>
		<td width="30%"><strong>Later List</strong></td>
	</tr>
	<c:forEach var="gtdList" items="${model.workLists}" varStatus="status">
		<%@ include file="/WEB-INF/jsp/gtddisplay.jsp" %>
	</c:forEach>

	<tr><td><br/></td></tr>

	<tr valign="top">
		<td width="10%" align="center"><b>Other</b></td>
		<th width="30%">List</th>
	</tr>
	<c:forEach var="map" items="${model.extraLists}">
		<tr valign="top" class="${status.index%2==0 ? 'odd' : 'even'}">
			<td><strong>${map.key}</strong></td>
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