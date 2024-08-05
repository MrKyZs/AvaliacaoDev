<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title><s:text name="label.titulo.pagina.consulta"/></title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
	</head>
	<body class="bg-secondary">	
	
		<div class="container">
			<div class="row">
				<table class="table table-light table-striped align-middle">
					<thead>
						<tr>
							<th><s:text name="label.id"/></th>
							<th><s:text name="label.data"/></th>							
							<th><s:text name="label.id.funcionario"/></th>
							<th><s:text name="label.nome.funcionario"/></th>
							<th><s:text name="label.id.exame"/></th>							
							<th><s:text name="label.nome.exame"/></th>
						</tr>
					</thead>
					
					<tbody>
						<s:iterator value="listaGerarRelatorio">
							<tr>							
								<td>${rowid}</td>
								<td>
									<s:date name="dataExame" format="dd/MM/yyyy"/>
								</td>
								<td>${funcionario.rowid}</td>
								<td>${funcionario.nome}</td>	
								<td>${exame.rowid}</td>
								<td>${exame.nome}</td>
							</tr>
						</s:iterator>
					</tbody>			
				</table>
			</div>

			<div class="row">
			
			</div>
		</div>
		
		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	</body>
</html>