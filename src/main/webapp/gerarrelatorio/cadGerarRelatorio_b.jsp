<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title><s:text name="label.titulo.pagina.gerarrelatorio"/></title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
	</head>
	<body class="bg-secondary">

		<div class="container">
			<s:form id="formularioAcao" action="/gerarExcelGerarRelatorio.action">

				<div class="card mt-5">
					<div class="card-header">
						<div class="row">
							<div class="col-sm">
								<h5 class="card-title">Gerar Relatório</h5>
							</div>
						</div>
					</div>
					
					<div class="card-body">
						<div class="row align-items-center">
							<label for="id" class="col-sm-1 col-form-label text-center">
								Data Inicial:
							</label>	

							<div class="col-sm-3">
								<s:fielderror fieldName="dataInicial"/>
								<s:textfield type="date" cssClass="form-control" id="dataInicial" name="dataInicial"/>							
							</div>	
						</div>
						
						<div class="row align-items-center mt-3">
							<label for="nome" class="col-sm-1 col-form-label text-center">
								Data Final:
							</label>	

							<div class="col-sm-3">
								<s:fielderror fieldName="dataFinal"/>
								<s:textfield type="date" cssClass="form-control" id="dataFinal" name="dataFinal"/>							
							</div>	
						</div>
					</div>

					<div class="card-footer">
						<div class="form-row">
							<button class="btn btn-primary">Gerar Excel</button>
							<button class="btn btn-primary"onclick="gerarHTML()">Gerar HTML</button>
							
						</div>
					</div>
				</div>
			</s:form>			
		</div>
		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
		<script>
			function gerarHTML(){
				var formulario = document.getElementById("formularioAcao");
				formulario.action = "/avaliacao/gerarHTMLGerarRelatorio.action";
				formulario.target = "_blank";
				formulario.submit();
			}
		</script>
	</body>
</html>