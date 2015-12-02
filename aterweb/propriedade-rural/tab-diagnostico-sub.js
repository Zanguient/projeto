(function(pNmModulo, pNmController, pNmFormulario) {

'use strict';


angular.module(pNmModulo).controller(pNmController,
    ['$scope', 'FrzNavegadorParams', '$uibModal', '$uibModalInstance', 'toastr', 'UtilSrv', 'mensagemSrv',
    function($scope, FrzNavegadorParams, $uibModal, $uibModalInstance, toastr, UtilSrv, mensagemSrv) {

    // inicializacao
    var init = function() {
        if (!angular.isObject($scope.cadastro.registro.diagnostico)) {
            $scope.cadastro.registro.diagnostico = [];
        }
        $scope.DiagnosticoNvg = new FrzNavegadorParams($scope.cadastro.registro.diagnostico, 8);
    };

    if (!$uibModalInstance) { init(); }

    if ($uibModalInstance === null) { $scope.modalEstado = null; } 
    else { $scope.modalEstado = 'filtro'; }

    // inicio rotinas de apoio
    // $scope.seleciona = function(DiagnosticoNvg, item) { };
    // $scope.mataClick = function(DiagnosticoNv, event, item){ };
    // fim rotinas de apoio

    // inicio das operaçoes atribuidas ao navagador
    $scope.abrir = function() { $scope.DiagnosticoNvg.mudarEstado('ESPECIAL'); };

    $scope.editar = function() {  $scope.incluir(); };

    $scope.incluir = function() {
        $scope.abreModal(  { arquivo: 'tab-diagnostico-sub-modal.html', descricao: 'Diagnóstico', tamanho :800,  dados: $scope.DiagnosticoNvg.selecao.item } );
    };


    $scope.agir = function() {};
    $scope.ajudar = function() {};
    $scope.alterarTamanhoPagina = function() {};
    $scope.cancelar = function() {};
    $scope.cancelarEditar = function() {};
    $scope.cancelarExcluir = function() {};
    $scope.cancelarFiltrar = function() {};
    $scope.cancelarIncluir = function() {};
    $scope.confirmar = function() {};
    $scope.confirmarEditar = function() {};
    $scope.confirmarExcluir = function() {};
    $scope.confirmarFiltrar = function() {};
    $scope.confirmarIncluir = function() {};
    $scope.excluir = function() {};
    $scope.filtrar = function() {};
    $scope.folhearAnterior = function() {};
    $scope.folhearPrimeiro = function() {};
    $scope.folhearProximo = function() {};
    $scope.folhearUltimo = function() {};

    $scope.informacao = function() {};
    $scope.limpar = function() {};
    $scope.paginarAnterior = function() {};
    $scope.paginarPrimeiro = function() {};
    $scope.paginarProximo = function() {};
    $scope.paginarUltimo = function() {};
    $scope.restaurar = function() {};
    $scope.visualizar = function() {};
    $scope.voltar = function() {};
    // fim das operaçoes atribuidas ao navagador

} // fim função
]);

})('propriedadeRural', 'DiagnosticoSubCtrl', 'Pessoas Vinculadas à Propriedade');