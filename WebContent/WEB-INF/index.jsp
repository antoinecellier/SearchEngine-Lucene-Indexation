<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/ui/css/jqcloud.css">

<title>Search Engine</title>

</head>
<body>

<%@ include file="menu.jsp" %>
	
    <div class="container">
   	
    
    	<c:set var="count" value="0" scope="page" />
    	<h4>Nombre de résultat : <c:out value='${results.size()}'/><h4>
    	<c:forEach items="${results}" var="document">
	 		<hr>
	 		<div class="row result"")>
			  	<div class="col-md-6">
			  		<h1><c:out value="${ document.getTitle() }" /> <a href="http://fr.wikipedia.org/wiki?curid=${ document.getId() }" target="_blank">Wiki</a></h1>
			  		
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
						   	<ul class="list-entities">
							   	<c:forEach items="${document.getEntities()}" var="entity">
							   		<li><a target="_blank" href="http://fr.wikipedia.org/wiki/<c:out value="${ entity.getKey() }" />"><span class="entity-value"><c:out value="${ entity.getKey() }" /></span>(<span class="entity-nbr"><c:out value="${ entity.getValue() }" /></span>)</a></li>
							   	</c:forEach>
						   	</ul>
					      </div>
					    </div>
					  </div>
					</div>					
			  	</div>
			  	<div class="col-md-6">
				    <div class="nuage-word" style="height: 350px;"></div>
				</div>

			</div>
			<c:set var="count" value="${count + 1}" scope="page"/>
    	</c:forEach>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
	<script src="<%= request.getServletContext().getContextPath() %>/ui/js/jqcloud-1.0.4.min.js"></script>
	<script src="<%= request.getServletContext().getContextPath() %>/ui/js/cloud.js"></script>

	
</body>
</html>