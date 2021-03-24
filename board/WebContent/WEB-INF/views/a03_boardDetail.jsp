<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="path" value="${pageContext.request.contextPath}"/> 
<fmt:requestEncoding value="UTF-8" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${path}/a00_com/bootstrap.min.css" >
<link rel="stylesheet" href="${path}/a00_com/jquery-ui.css" >
<script src="${path}/a00_com/jquery.min.js"></script>
<script src="${path}/a00_com/popper.min.js"></script>
<script src="${path}/a00_com/bootstrap.min.js"></script>
<script src="${path}/a00_com/jquery-ui.js"></script>
<script type="text/javascript">
<%--
 
 
--%>
//
   $(document).ready(function(){
      	$("[name=fnames]").click(function(){
      		var fname=$(this).val();
      		if(confirm(fname+"파일을 다운로드 하시겠습니까?")){
      			location.href="${path}/board.do?method=download&fname="+fname;
      		}
      	});
   });
</script>
<style>
	.input-group-text{
		width:100%;
		background-color:linen;
		color:black;
		font-weight:bolder;
	}
	.input-group-prepend{
		width:20%;
	}
</style>
</head>
<div class="jumbotron text-center">
  <h2>게시판 상세화면[${param.no }]</h2>
</div>
<div class="container">
     <form method="post" enctype="multipart/form-data">
     <div class="input-group mb-3">
     	<div class="input-group-prepend">
     		<span class="input-group-text">글번호</span>
     	</div>
     	  <input class="form-control" 
          name="refno" value="${board.no }">
     	<div class="input-group-prepend">
     		<span class="input-group-text">상위글번호</span>
     	</div>
     	<input class="form-control" type="text" 
          name="refno" value="${board.refno }">
     </div>
       
          
      <div class="input-group mb-3">
     	<div class="input-group-prepend">
     		<span class="input-group-text">작성자</span>
     	</div>
     	   <input class="form-control" type="text" 
          name="writer" value="${board.writer }">
     	<div class="input-group-prepend">
     		<span class="input-group-text">조회수</span>
     	</div>
     	 <input class="form-control" type="text" 
          name="readcnt" value="${board.readcnt }">
     </div>
      
    
    <div class="input-group mb-3">
     	<div class="input-group-prepend">
     		<span class="input-group-text">제목</span>
     	</div>
     	 <input class="form-control" type="text" 
          name="subject" value="${board.subject }">             
     </div>
      
     <div class="input-group mb-3">
     	<div class="input-group-prepend">
     		<span class="input-group-text">등록일</span>
     	</div>
     	<input class="form-control" type="text" 
          name="regdte" value="<fmt:formatDate value="${board.regdte }"/>">
     	<div class="input-group-prepend">
     		<span class="input-group-text">수정일</span>
     	</div>
     	  <input class="form-control" type="text" 
          name="uptdte" value="<fmt:formatDate value="${board.uptdte }"/>">  
     </div>
     
          
    
     <div class="input-group mb-3">
     	<div class="input-group-prepend">
     		<span class="input-group-text">내용</span>
     	</div>
     	 <textarea class="form-control" rows=10
          name="content">${board.content }</textarea>
     </div>
      
     <c:set var="fcnt" value="${board.fileInfo.size() }"/>
     <c:forEach var="finf" items="${board.fileInfo}" varStatus="sts">
     <div class="input-group mb-3">
     	<div class="input-group-prepend">
     		<span class="input-group-text">첨부파일(${sts.count}/${fcnt})</span>
     	</div>
     	<!-- 다운로드할 파일 정보 -->
     	<input class="form-control fileInfo" name="fnames" value="${finf.fname }"/>
     	 <div class="custom-file">
     		<!-- 변경할  파일정보(report ==> vo 의 property) -->
	     	<input type="file" name="report" class="custom-file-input" id="file01"/>
	     	<label class="custom-file-label" for="file01">
	     	변경하려면 파일을 선택하세요!</label>
    	 </div>
     </div>
     </c:forEach>
     <div style="text-align:right;">
     	<input type="button" class="btn btn-info" value="수정" id="uptBtn"/>
     	<input type="button" class="btn btn-danger" value="삭제" id="delBtn"/>
     	<input type="button" class="btn btn-warning" value="답글" id="reBtn"/>
     	<input type="button" class="btn btn-success" value="조회화면으로" id="goMain"/>
     </div>
    
             
     </form>
     
</div>
</body>
</html>