<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/ssi/ssi_bbs.jsp"%>
<jsp:useBean id="dao" class="com.bbs.BbsDAO"/>
<jsp:useBean id="dto" class="com.bbs.BbsDTO"/>
<jsp:setProperty name="dto" property="*"/>

<%
Map map = new HashMap();
map.put("bbsno", dto.getBbsno());
map.put("passwd", dto.getPasswd());

boolean pflag = dao.passCheck(map);

boolean flag = false;
if(pflag) flag = dao.delete(dto.getBbsno());
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function list(){
	let url = "list.jsp";
	url += "?nowPage=<%=request.getParameter("nowPage")%>";
	url += "&col=<%=request.getParameter("col")%>";
	url += "&word=<%=request.getParameter("word")%>";
	
	location.href= url;
}
</script>
</head>
<body>
<jsp:include page="/menu/top.jsp" />
<div class="container mt-3">
    <div class="container p-5 my-5 border">
        <%
        if (!pflag) {
                out.print("잘못된 비밀번호 입니다.");
        } else if (flag) {
                out.print("글 삭제를 성공했습니다.");
        } else {
                out.print("글 삭제를 실패했습니다.");
        }
        %>
    </div>
    <% if (!pflag) {%><button class="btn btn-dark" onclick="history.back()">다시시도</button><%}%>
    <button class="btn btn-light" onclick="location.href='createForm.jsp'">다시시도</button>
    <button type="button" class="btn btn-light" onclick="list()">목록</button>
</div>
</body>
</html>