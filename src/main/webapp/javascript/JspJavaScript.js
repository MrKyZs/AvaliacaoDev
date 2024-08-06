function confirmarExclusao(excluir){
	var urlBase = excluir;
	var botaoSimModal = document.getElementById("botaoExcluirModal");
	botaoSimModal.href = urlBase;
}

function gerarExcel() {
	var formulario = document.getElementById("formularioAcao");
	formulario.action = "/avaliacao/gerarExcelGerarRelatorio.action";
	formulario.target = "_self";
	formulario.submit();
}

function gerarHTML() {
	var formulario = document.getElementById("formularioAcao");
	formulario.action = "/avaliacao/gerarHTMLGerarRelatorio.action";
	formulario.target = "_blank";
	formulario.submit();
}