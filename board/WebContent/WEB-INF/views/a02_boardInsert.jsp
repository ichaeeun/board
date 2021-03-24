<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="path" value="${pageContext.request.contextPath}"/> 
<fmt:requestEncoding value="UTF-8"/>
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
      $("#mainBtn").click(function(){
         location.href="${path}/board.do?method=list";
      });

  	 var isInsert = "${param.subject}";
     if (isInsert != "") {
         if (confirm("등록완료!!\n조회화면으로 이동하시겠습니까?")) {
            location.href = "${path}/board.do?method=list";
         }
      }
      
      $("#addFun").click(function(){
    	  // .clone()을 해서 복사해서 DOM객체가 추가하여 생성할 수 있게 한다. 
    	  $("#fileArea").append($(".custom-file").eq(0).clone());
      });

   });
   
   function rm(obj){
	   // 삭제 전에 validate check(유효성체크) file객체가 한개 초과하여 
	   // 있을 때 가능하도록 설정 
	   var len = $("[type=file]").length;
	   if(len>1){	// 화면 ui를 위해 한개는 남겨둔다. 
		   $(obj).parent().remove(); 
	   // $(obj).parent() : div class="custom-file"  삭제 
	   // 상위 custom-file 삭제 시 하위에 현재 File객체도 함께 삭제된다. 
	   }
   }
</script>
</head>
<div class="jumbotron text-center">
   <h2>게시판 등록</h2>
</div>
<div class="container">
   <form action="${path}/board.do?method=insert" method="post"
   	enctype="multipart/form-data">
   <input type="hidden" name="refno" value="0"/>
   <table class="table table-hover table-striped">
   <col width="30%">
   <tbody>      <%--상위글번호(0,hidden), 글제목, 작성자, 내용, 첨부파일--%>
      <tr class="text-center">
         <th class="table-success">제목</th>
         <td><input type="text" name="subject" class="form-control"/></td>
      </tr>
      <tr class="text-center">
         <th class="table-success">작성자</th>
         <td><input type="text" name="writer" class="form-control" value="${mem.id}"/></td>
      </tr>  
      <tr class="text-center">
         <th class="table-success">내용</th>
         <td>
            <textarea name="content" rows="10" cols="" 
               class="form-control"></textarea></td>
      </tr>
      <tr class="text-center">
         <th class="table-success">첨부파일
         	<span id="addFun" class="badge badge-inf">[추가]</span></th>
         <td id="fileArea">
            <div class="custom-file">
            	<%--
            	rm(this) : rm함수를 호출하면서 클릭한 span객체를 
            	함수의 매개변수로 object로 전송 
            	계층 구조로 상위 DOM <div class="custom-file">을 가지고 있다. 
            	 --%>
               <span onclick="rm(this)" class="badge badge-danger"> [X] </span>&nbsp;
               <input name="report" type="file"/><br>
            </div>
         </td>
      </tr>
      <tr class="text-center">
         <td colspan="2">
            <button class="btn btn-info" id="regBtn" type="submit">등록</button>
            <button class="btn btn-success" id="mainBtn" type="button">메인화면</button>
         </td>
      </tr>
   </tbody>
   </table>  
   </form>  
</div>
</body>
</html>