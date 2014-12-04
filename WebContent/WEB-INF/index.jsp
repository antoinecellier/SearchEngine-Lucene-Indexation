<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<title>Search Engine</title>
</head>
<body>
<%@ include file="menu.jsp" %>


    <div class="container">
    	<c:forEach items="${results}" var="document">
	 		<hr>
	 		<div class="row">
			  	<div class="col-md-12">
			  		<h1><c:out value="${ document.get('title') }" /></h1>
			  		<c:set var="entities" value="${fn:split(document.get('entities'), ';')}" />
				   	<ul>
					   	<c:forEach items="${entities}" var="entity">
					   		<li><c:out value="${ entity }" /></li>
					   	</c:forEach>
				   	</ul>
			  	</div>
			</div>   	
    	</c:forEach>
    </div>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

</body>
</html>