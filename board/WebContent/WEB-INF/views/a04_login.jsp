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
      var loginSucc = "${loginSucc}";
      if(loginSucc=="Y"){
    	  alert("로그인 성공!");
    	  location.href="${path}/board.do?method=list";
      }
      if(loginSucc=="N"){
    	  alert("아이디/비밀번호를 확인해주세요");
    	  $("[name=id]").focus();
      }
   });
</script>
</head>
<body>
<div class="jumbotron text-center">
  <h2>LOGIN</h2>
</div>
<div class="container">
  <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
     <form class="form-inline" method="post" action="${path }/board.do?method=login">
       <input class="form-control mr-sm-2" type="text" 
          name="id" value=""
          placeholder="아이디">
       <input class="form-control mr-sm-2" type="password" 
          name="pass"  value=""
          placeholder="비밀번호">
       <button class="btn btn-success" type="submit">login</button>
     </form>
  </nav>
</div>
</body>
</html>