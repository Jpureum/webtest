<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/ssi/ssi_poll.jsp" %>
<jsp:useBean id="service" class="com.poll.PollService" />

<%
	int num = Integer.parseInt(request.getParameter("num"));

	String[] itemnum = request.getParameterValues("itemnum");
	System.out.println(itemnum.length);
	boolean flag = service.updateCount(itemnum);
	String msg = "투표가 등록되지 않습니다.";
	if(flag) msg="투표가 정상적으로 등록되었습니다.";
%>

<script>
	alert("<%=msg%>");
	location.href = "poll.jsp?num=<%=num%>#section2";
</script>
