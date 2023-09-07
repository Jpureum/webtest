<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/ssi/ssi_bbs.jsp" %> 
<jsp:useBean id="dao" class="com.bbs.BbsDAO" />
<%
	int bbsno = Integer.parseInt(request.getParameter("bbsno"));
	BbsDTO dto = dao.read(bbsno);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/menu/top.jsp"/>
<div class="container mt-3">
  <h2>게시판 수정</h2>
  <form action="updateProc.jsp" method="post">
  	<input type="hidden" name="bbsno" value="<%=dto.getBbsno()%>">
  	<input type="hidden" name="nowPage" value="<%=request.getParameter("nowPage")%>">
  	<input type="hidden" name="col" value="<%=request.getParameter("col")%>">
  	<input type="hidden" name="word" value="<%=request.getParameter("word")%>">
    <div class="mb-3 mt-3">
      <label for="name">Name:</label>
      <input type="text" class="form-control" id="wname" value="<%=dto.getWname()%>" name="wname">
    </div>
    <div class="mb-3">
      <label for="pwd">Title:</label>
      <input type="text" class="form-control" id="title" value="<%=dto.getTitle()%>" name="title">
    </div>
    <div class="mb-3 mt-3">
      <label for="comment">Comments:</label>
      <textarea class="form-control" rows="5" id="comment" name="content"><%=dto.getContent()%></textarea>
    </div>
    <div class="mb-3">
      <label for="pwd">Password:</label>
      <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="passwd">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
</div>
</body>
</html>