<%@ include file="/WEB-INF/jsp/include.jsp"%>

<tr valign="top" class="${status.index%2==0 ? 'odd' : 'even'}">
	<td><strong>${gtdList.key}</strong></td>
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