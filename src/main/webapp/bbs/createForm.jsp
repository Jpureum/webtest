<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/menu/top.jsp"/>
<div class="container mt-3">
  <h2>게시판 생성</h2>
  <form action="createProc.jsp"
  		method="post">
    <div class="mb-3 mt-3">
      <label for="name">Name:</label>
      <input type="text" class="form-control" id="wname" placeholder="Enter Name" name="wname">
    </div>
    <div class="mb-3">
      <label for="pwd">Title:</label>
      <input type="text" class="form-control" id="title" placeholder="Enter Title" name="title">
    </div>
    <div class="mb-3">
      <label for="pwd">Password:</label>
      <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="passwd">
    </div>
    <div class="mb-3 mt-3">
      <label for="comment">Comments:</label>
      <textarea class="form-control" rows="5" id="comment" name="content"></textarea>
    </div>
    <button type="submit" class="btn btn-primary">등록</button>
  </form>
</div>
</body>
</html>