/* jslint evil: true, browser: true, plusplus: true, loopfunc: true */
/* global criarEstadosPadrao, removerCampo */ 

(function(pNmModulo, pNmController, pNmFormulario, pUrlModulo) {

    'use strict';

    angular.module(pNmModulo, ['ui.bootstrap', 'ui.utils', 'ui.router', 'ngAnimate', 'frz.navegador', 'frz.form', 'ngSanitize']);
    angular.module(pNmModulo).config(['$stateProvider', function($stateProvider) {
        'ngInject';

        criarEstadosPadrao($stateProvider, pNmModulo, pNmController, pUrlModulo);
    }]);

    angular.module(pNmModulo).controller(pNmController, ['$scope', 'toastr', 'FrzNavegadorParams', '$state', '$rootScope', '$uibModal', '$log', '$uibModalInstance', 'modalCadastro', 'UtilSrv', 'mensagemSrv', 'PerfilSrv', 
        function($scope, toastr, FrzNavegadorParams, $state, $rootScope, $uibModal, $log, $uibModalInstance, modalCadastro, UtilSrv, mensagemSrv, PerfilSrv) {
            'ngInject';

            var ordem = 0;
            $scope.CABEC = {
                ID : ordem++,
                NOME : ordem++,
                CODIGO : ordem++,
                ATIVO : ordem++,
            };

            // inicializacao
            $scope.crudInit($scope, $state, null, pNmFormulario, PerfilSrv);

            // código para verificar se o modal está ou não ativo
            $scope.verificaEstado($uibModalInstance, $scope, 'filtro', modalCadastro, pNmFormulario);
            // inicio: atividades do Modal
            $scope.modalOk = function() {
                // Retorno da modal
                $uibModalInstance.close({cadastro: angular.copy($scope.cadastro), selecao: angular.copy($scope.navegador.selecao)});
            };
            $scope.modalCancelar = function() {
                // Cancelar a modal
                $uibModalInstance.dismiss('cancel');
                toastr.warning('Operação cancelada!', 'Atenção!');
            };
            $scope.modalAbrir = function(size) {
                // abrir a modal
                var template = '<ng-include src=\"\'' + pNmModulo + '/' + pNmModulo + '-modal.html\'\"></ng-include>';
                var modalInstance = $uibModal.open({
                    animation: true,
                    template: template,
                    controller: pNmController,
                    size: size,
                    resolve: {
                        modalCadastro: function() {
                            return $scope.cadastroBase();
                        }
                    }
                });
                // processar retorno da modal
                modalInstance.result.then(function(cadastroModificado) {
                    // processar o retorno positivo da modal
                    $scope.navegador.setDados(cadastroModificado.lista);
                }, function() {
                    // processar o retorno negativo da modal
                    $log.info('Modal dismissed at: ' + new Date());
                });
            };
            // fim: atividades do Modal

            // inicio das operaçoes atribuidas ao navagador
            $scope.visualizarDepois = function(registro) {
                removerCampo(registro, ['@jsonId']);
                if (registro.perfilFuncionalidadeComandoList) {
                    if ($scope.cadastro.apoio.funcionalidadeComandoList) {
                        for (var i in $scope.cadastro.apoio.funcionalidadeComandoList) {
                            for (var j in registro.perfilFuncionalidadeComandoList) {
                                if ($scope.cadastro.apoio.funcionalidadeComandoList[i].funcionalidadeComando.id === registro.perfilFuncionalidadeComandoList[j].funcionalidadeComando.id) {
                                    $scope.cadastro.apoio.funcionalidadeComandoList[i] = angular.copy(registro.perfilFuncionalidadeComandoList[j]);
                                    break;
                                }
                            }

                        }
                    }
                }
                $scope.$broadcast('visualizarDepois');
            };

            var confirmarSalvarAntes = function(cadastro) {
                cadastro.registro.perfilFuncionalidadeComandoList = angular.copy($scope.cadastro.apoio.funcionalidadeComandoList);
            };
            
            $scope.confirmarIncluirAntes = function(cadastro) {
                confirmarSalvarAntes(cadastro);
            };
            
            $scope.confirmarEditarAntes = function(cadastro) {
                confirmarSalvarAntes(cadastro);
            };
            
            // fim das operaçoes atribuidas ao navagador

            // inicio ações especiais

            // nomes dos campos para listagem

            // fim ações especiais

            // inicio trabalho tab
            
            // fim trabalho tab

            // inicio dos watches

            // fim dos watches
        }
    ]);
})('perfil', 'PerfilCtrl', 'Cadastro de Perfis do Sistema', 'perfil');