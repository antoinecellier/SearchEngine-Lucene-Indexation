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
    	<c:set var="count" value="0" scope="page" />
    	<c:forEach items="${results}" var="document">
	 		<hr>
	 		<div class="row">
			  	<div class="col-md-12">
			  		<h1><c:out value="${ document.getTitle() }" /></h1>
			  		<a href="http://fr.wikipedia.org/wiki?curid=${ document.getId() }" target="_blank">Wiki</a>
			  		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
					  <div class="panel panel-default">
					    <div class="panel-heading" role="tab" id="headingOne">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#<c:out value='${ count }' />-result" aria-expanded="false" aria-controls="collapseOne">
					          Entities
					        </a>
					      </h4>
					    </div>
					    <div id="${ count }-result" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
					      <div class="panel-body">
					      			  	
									   	<ul>
										   	<c:forEach items="${document.getEntities()}" var="entity">
										   		<li><c:out value="${ entity.getKey() }" />(<c:out value="${ entity.getValue() }" />)</li>
										   	</c:forEach>
									   	</ul>
					      </div>
					    </div>
					  </div>
					</div>
			  	</div>
			</div>
			<c:set var="count" value="${count + 1}" scope="page"/>
    	</c:forEach>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
	
</body>
</html>