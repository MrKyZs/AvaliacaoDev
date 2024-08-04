function confirmarExclusao(excluir){
	var urlBase = excluir;
	var botaoExcluirModal = document.getElementById("botaoExcluirModal");
	botaoExcluirModal.href = urlBase;
	var modal = new bootstrap.Modal(document.getElementById("confirmarExclusao"));
	modal.show();
}