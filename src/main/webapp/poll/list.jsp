<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/ssi/ssi_poll.jsp" %>
<jsp:useBean id="service" class="com.poll.PollService" />
<%
	//검색
	String col = Utility.checkNull(request.getParameter("col"));
	String word = Utility.checkNull(request.getParameter("word"));
	
	if(col.equals("total")){
		word="";
	}
	
	//페이지
	int nowPage = 1;
	if(request.getParameter("nowPage") != null){
		nowPage = Integer.parseInt(request.getParameter("nowPage"));
	}
	int recordPerPage = 5;
	
	int sno = ((nowPage - 1) * recordPerPage);
	
	Map map = new HashMap();
	map.put("col", col);
	map.put("word", word);
	map.put("sno", sno);
	map.put("eno", recordPerPage);
	
	//목록
	Vector<PollDTO> list = service.getList(map);
	Iterator<PollDTO> iter = list.iterator();
	
	int total = service.total(map);
	
	String url = "poll.jsp#section1";
	
	String paging = Utility.paging(total, nowPage, recordPerPage, col, word, url);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function read(num){
		let url = "poll.jsp";
		url += "?num="+num;
		location.href = url + "#section2";
	}
</script>
</head>
<body>
<div class="container mt-3">
  <h2>설문 목록</h2>
  <form action="poll.jsp#section1" class="pt-1">
    <div class="row mt-3">
      <div class="col">
		<select class="form-select" name="col">
			<option value="question"
				<%if(col.equals("question"))out.print("selected"); %>
			>설문내용</option>
			<option value="sdate"
				<%if(col.equals("sdate"))out.print("selected"); %>
			>시작일</option>
			<option value="edate"
				<%if(col.equals("edate"))out.print("selected"); %>
			>마감일</option>
			<option value="total"
				<%if(col.equals("total"))out.print("selected"); %>
			>전체출력</option>
   		</select>
      </div>
      <div class="col">
        <input type="search" class="form-control" placeholder="검색어" name="word" value="<%=word%>">
      </div>
      <div class="col">
      	<button class="btn btn-dark">검색</button>
      </div>
    </div>
  </form>
  
  <table class="table table-hover mt-2">
    <thead>
      <tr>
        <th>No.</th>
        <th>제목</th>
        <th>시작일 - 종료일</th>
      </tr>
    </thead>
    <tbody>
    <% if(list.size() == 0){ %>
      <tr>
        <td colspan="3">등록된 설문이 없습니다.</td>
      </tr>
      <%
    	} else {
    		//while(iter.hasNext()){
    			//PollDTO dto = iter.next();
    		//}
    		for(int i = 0; i < list.size(); i++){
    			PollDTO dto = list.get(i);
      %>
      <tr>
        <td><%=dto.getNum()%></td>
        <td><a class="nav-link" href="javascript:read('<%=dto.getNum()%>')"><%=dto.getQuestion()%></a></td>
        <td><%=dto.getSdate()%> - <%=dto.getEdate()%></td>
      </tr>
      
      <%	}//for end
    	}//if end
    	
   	  %>
    </tbody>
  </table>
  <%=paging%>
</div>
</body>
</html>