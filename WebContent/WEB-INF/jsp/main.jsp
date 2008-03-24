<%@ include file="/WEB-INF/jsp/include.jsp"%>

<html>
<head>
<title>Backpack</title>
</head>



<body>
<h1 align="center">Backpack GTD</h1>

<table width="100%">
	<tr valign="top">
		<th width="10%">Page</th>
		<th width="30%">Next List</th>
		<th width="30%">Waiting List</th>
		<th width="30%">Later List</th>
	</tr>

	<c:forEach var="gtdList" items="${model.gtdLists}">
		<tr valign="top">
			<td>${gtdList.key}</td>
			<td><c:if test="${gtdList.value.nextList != null}">
				<ul>
					<c:forEach var="item"
						items="${gtdList.value.nextList.itemsAsStringList}">
						<li>${item}</li>
					</c:forEach>
				</ul>
			</c:if></td>
			<td><c:if test="${gtdList.value.waitingList != null}">
				<ul>
					<c:forEach var="item"
						items="${gtdList.value.waitingList.itemsAsStringList}">
						<li>${item}</li>
					</c:forEach>
				</ul>
			</c:if></td>
			<td><c:if test="${gtdList.value.laterList != null}">
				<ul>
					<c:forEach var="item"
						items="${gtdList.value.laterList.itemsAsStringList}">
						<li>${item}</li>
					</c:forEach>
				</ul>
			</c:if></td>
		</tr>
	</c:forEach>

	<tr valign="top">
		<td width="10%" align="center"><b>Inbox</b></td>
	</tr>
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