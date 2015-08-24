/* global criarEstadosPadrao */

(function(pNmModulo, pNmController, pNmFormulario) {

'use strict';

angular.module(pNmModulo, ['ui.bootstrap','ui.utils','ui.router','ngAnimate', 'frz.navegador']);

angular.module(pNmModulo).config(['$stateProvider', function($stateProvider) {

    criarEstadosPadrao($stateProvider, pNmModulo, pNmController);

}]);

angular.module(pNmModulo).controller(pNmController,
    ['$scope', 'toastr', 'FrzNavegadorParams', '$state', '$rootScope', '$modal', '$log', '$modalInstance',
    'modalCadastro', 'utilSrv', 'mensagemSrv',
    function($scope, toastr, FrzNavegadorParams, $state, $rootScope, $modal, $log, $modalInstance,
        modalCadastro, utilSrv, mensagemSrv) {

    // inicializacao
    $scope.crudInit($scope, $state, null, pNmFormulario);

    // código para verificar se o modal está ou não ativo
    $scope.verificaEstado($modalInstance, $scope, 'filtro', modalCadastro, pNmFormulario);

    // inicio: atividades do Modal
    $scope.modalOk = function () {
        // Retorno da modal
        $scope.cadastro.lista = [];
        $scope.cadastro.lista.push({id: 21, nome: 'Fernando'});
        $scope.cadastro.lista.push({id: 12, nome: 'Frazao'});

        $modalInstance.close($scope.cadastro);
        toastr.info('Operação realizada!', 'Informação');
    };
    $scope.modalCancelar = function () {
        // Cancelar a modal
        $modalInstance.dismiss('cancel');
        toastr.warning('Operação cancelada!', 'Atenção!');
    };
    $scope.modalAbrir = function (size) {
        // abrir a modal
        var template = '<ng-include src=\"\'' + pNmModulo + '/' + pNmModulo + '-modal.html\'\"></ng-include>';
        var modalInstance = $modal.open({
            animation: true,
            template: template,
            controller: pNmController,
            size: size,
            resolve: {
                modalCadastro: function () {
                    return angular.copy($scope.cadastro);
                }
            }
        });
        // processar retorno da modal
        modalInstance.result.then(function (cadastroModificado) {
            // processar o retorno positivo da modal
            $scope.navegador.setDados(cadastroModificado.lista);
        }, function () {
            // processar o retorno negativo da modal
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
    // fim: atividades do Modal

    // inicio das operaçoes atribuidas ao navagador
    $scope.abrir = function(scp) {
        // ajustar o menu das acoes especiais
        $scope.navegador.botao('acao', 'acao')['subFuncoes'] = [
        {
            nome: 'Enviar E-mail',
            descricao: 'Enviar e-mails',
            acao: function() {console.log($scope.navegador.tamanhoPagina);$scope.enviarEmailConfirmacao();},
            exibir: function() {
                return $scope.navegador.estadoAtual() === 'LISTANDO' && ($scope.navegador.selecao.tipo === 'U' && $scope.navegador.selecao.selecionado) ||
                ($scope.navegador.selecao.tipo === 'M' && $scope.navegador.selecao.marcado > 0);
            },
        },
        {
            nome: 'Desbloquear Usuário',
            descricao: 'Desbloquear Usuários',
            acao: function() {console.log('sub acao click ' + this.nome);},
            exibir: function() {
                return $scope.navegador.selecao.tipo === 'M' && $scope.navegador.selecao.marcado > 1;
            },
        },
        ];
        $rootScope.abrir(scp);
    };
    // fim das operaçoes atribuidas ao navagador

    // inicio ações especiais
    $scope.enviarEmailConfirmacao = function () {
        var conf = $scope.pegarConfirmacao('Confirme o envio do e-mail?');

        conf.result.then(function () {
          $scope.exibirAlerta('E-mail enviado');
        }, function () {
          toastr.warning('O e-mail não foi enviado...', 'Atenção!');
        });

    };
    // fim ações especiais

    $scope.modalSelecinarExecutor = function (size) {
        // abrir a modal
        var modalInstance = $modal.open({
            animation: true,
            template: '<ng-include src=\"\'pessoa/pessoa-modal.html\'\"></ng-include>',
            controller: 'PessoaCtrl',
            size: size,
            resolve: {
                modalCadastro: function () {
                    return {filtro: {}, lista: [], registro: {}, original: {}};
                }
            }
        });
        // processar retorno da modal
        modalInstance.result.then(function (cadastroModificado) {
            // processar o retorno positivo da modal
            $scope.cadastro.registro.executor = cadastroModificado.registro;
        }, function () {
            // processar o retorno negativo da modal
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.modalVerExecutor = function (size) {
        // abrir a modal
        var modalInstance = $modal.open({
            animation: true,
            template: '<ng-include src=\"\'pessoa/pessoa-form-modal.html\'\"></ng-include>',
            controller: 'PessoaCtrl',
            size: size,
            resolve: {
                modalCadastro: function () {
                    var cadastro = {registro: angular.copy($scope.cadastro.registro.executor), filtro: {}, lista: [], original: {}};
                    return cadastro;
                }
            }
        });
        // processar retorno da modal
        modalInstance.result.then(function (cadastroModificado) {
            // processar o retorno positivo da modal

        }, function () {
            // processar o retorno negativo da modal
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.modalSelecinarContraparte = function (size) {
        // abrir a modal
        var modalInstance = $modal.open({
            animation: true,
            template: '<ng-include src=\"\'pessoa/pessoa-modal.html\'\"></ng-include>',
            controller: 'PessoaCtrl',
            size: size,
            resolve: {
                modalCadastro: function () {
                    return {filtro: {}, lista: [], registro: {}, original: {}};
                }
            }
        });
        // processar retorno da modal
        modalInstance.result.then(function (cadastroModificado) {
            // processar o retorno positivo da modal
            $scope.cadastro.registro.contraparte = cadastroModificado.registro;
        }, function () {
            // processar o retorno negativo da modal
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.modalVerContraparte = function (size) {
        // abrir a modal
        var modalInstance = $modal.open({
            animation: true,
            template: '<ng-include src=\"\'pessoa/pessoa-form-modal.html\'\"></ng-include>',
            controller: 'PessoaCtrl',
            size: size,
            resolve: {
                modalCadastro: function () {
                    var cadastro = {registro: angular.copy($scope.cadastro.registro.contraparte), filtro: {}, lista: [], original: {}};
                    return cadastro;
                }
            }
        });
        // processar retorno da modal
        modalInstance.result.then(function (cadastroModificado) {
            // processar o retorno positivo da modal

        }, function () {
            // processar o retorno negativo da modal
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

}]);

})('contrato', 'ContratoCtrl', 'Cadastro de Contratos & Convênios');