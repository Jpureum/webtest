<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	body {
	    position: relative; 
	}
	#section1:target {
    	background-color: rgb(40, 98, 100);
    	color: beige;
	}
	 
	#section2:target {
	    background-color: rgb(77, 77, 69);
	    color: rgb(248, 248, 237);
	}
	 
	#section3:target {
	    background-color: burlywood;
	}
</style>
</head>
<body>

<jsp:include page="/menu/top.jsp"/>

<div data-bs-spy="scroll" data-bs-target=".navbar" data-bs-offset="50">
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-bottom">
	  <div class="container-fluid">
	    <ul class="navbar-nav">
	      <li class="nav-item">
	        <a class="nav-link" href="#section1">설문 목록</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#section2">설문 정보</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#section3">설문 등록</a>
	      </li>
	    </ul>
	  </div>
	</nav>

	<div id="section1" class="container-fluid pt-2 pb-2">
		<jsp:include page="/poll/list.jsp" />
	</div>

	<div id="section2" class="container-fluid pt-2 pb-2">
	  	<jsp:include page="/poll/pollItem.jsp" />
	</div>

	<div id="section3" class="container-fluid pt-2 pb-5">
	  <jsp:include page="/poll/pollCreate.jsp" />
	</div>
</div>
</body>
</html>