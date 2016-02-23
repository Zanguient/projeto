/* global StringMask:false */

(function(pNmModulo, pNmController, pNmFormulario) {

'use strict';

angular.module(pNmModulo).controller(pNmController,
    ['$scope', 'FrzNavegadorParams', '$uibModal', '$uibModalInstance', 'toastr', 'UtilSrv', 'mensagemSrv', '$log',
    function($scope, FrzNavegadorParams, $uibModal, $uibModalInstance, toastr, UtilSrv, mensagemSrv, $log) {

    // inicializacao
    var init = function() {
        if (!angular.isObject($scope.cadastro.registro.publicoAlvo)) {
            $scope.cadastro.registro.publicoAlvo = {};
        }
        if (!angular.isObject($scope.cadastro.registro.publicoAlvoPropriedadeRuralList)) {
            $scope.cadastro.registro.publicoAlvoPropriedadeRuralList = [];
        }
        $scope.publicoAlvoPropriedadeRuralNvg = new FrzNavegadorParams($scope.cadastro.registro.publicoAlvoPropriedadeRuralList, 4);
    };
    if (!$uibModalInstance) { init(); }

    // inicio rotinas de apoio
    var jaCadastrado = function(conteudo) {
        for (var j in $scope.cadastro.registro.publicoAlvoPropriedadeRuralList) {
            if (angular.equals($scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j].publicoAlvoPropriedadeRural.endereco, conteudo.publicoAlvoPropriedadeRural.endereco)) {
                if ($scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j].cadastroAcao === 'E') {
                    return true;
                } else {
                    toastr.error('Registro já cadastrado');
                    return false;
                }
            }
        }
        return true;
    };
    var editarItem = function (destino, item) {
        $scope.modalSelecinarPropriedadeRural();
    };
    $scope.modalSelecinarPropriedadeRural = function (size) {
        // abrir a modal
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'pessoa/pessoa-modal.html',
            controller: 'PessoaCtrl',
            size: 'lg',
            resolve: {
                modalCadastro: function() {
                    return $scope.cadastroBase();
                }
            }
        });
        // processar retorno da modal
        modalInstance.result.then(function (resultado) {
            // processar o retorno positivo da modal
            var reg = null;
            if (resultado.selecao.tipo === 'U') {
                reg = {
                    publicoAlvo: {
                        pessoa: {
                            id: resultado.selecao.item[0], 
                            nome: resultado.selecao.item[1],
                            pessoaTipo: resultado.selecao.item[3],
                        },
                    },
                };
                $scope.preparaClassePessoa(reg.publicoAlvo.pessoa);
                if (!$scope.cadastro.registro.publicoAlvoPropriedadeRuralList) {
                    $scope.cadastro.registro.publicoAlvoPropriedadeRuralList = [];
                    $scope.publicoAlvoPropriedadeRuralNvg.setDados($scope.cadastro.registro.publicoAlvoPropriedadeRuralList);
                }
                $scope.cadastro.registro.publicoAlvoPropriedadeRuralList.push(reg);
            } else {
                for (var i in resultado.selecao.items) {
                    reg = {
                        publicoAlvo: {
                            pessoa: {
                                id: resultado.selecao.items[i][0], 
                                nome: resultado.selecao.items[i][1],
                                pessoaTipo: resultado.selecao.items[i][3],
                            },
                        },
                    };
                    $scope.preparaClassePessoa(reg.publicoAlvo.pessoa);
                    if (!$scope.cadastro.registro.publicoAlvoPropriedadeRuralList) {
                        $scope.cadastro.registro.publicoAlvoPropriedadeRuralList = [];
                        $scope.publicoAlvoPropriedadeRuralNvg.setDados($scope.cadastro.registro.publicoAlvoPropriedadeRuralList);
                    }
                    $scope.cadastro.registro.publicoAlvoPropriedadeRuralList.push(reg);
                }
            }
            toastr.info('Operação realizada!', 'Informação');
        }, function () {
            // processar o retorno negativo da modal
            
        });
    };

    $scope.modalVerPropriedadeRural = function (id) {
        // abrir a modal
        var modalInstance = $uibModal.open({
            animation: true,
            template: '<ng-include src=\"\'pessoa/pessoa-form-modal.html\'\"></ng-include>',
            controller: 'PessoaCtrl',
            size: 'lg',
            resolve: {
                modalCadastro: function () {
                    var cadastro = {registro: {id: id}, filtro: {}, lista: [], original: {}, apoio: [],};
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
    // fim rotinas de apoio

    // inicio das operaçoes atribuidas ao navagador
    $scope.abrir = function() { $scope.publicoAlvoPropriedadeRuralNvg.mudarEstado('ESPECIAL'); };
    $scope.incluir = function() {
        var item = {publicoAlvoPropriedadeRural: {endereco: null}};
        editarItem(null, item);
    };
    $scope.editar = function() {
        var item = null;
        var i, j;
        if ($scope.publicoAlvoPropriedadeRuralNvg.selecao.tipo === 'U' && $scope.publicoAlvoPropriedadeRuralNvg.selecao.item) {
            item = angular.copy($scope.publicoAlvoPropriedadeRuralNvg.selecao.item);
            editarItem($scope.publicoAlvoPropriedadeRuralNvg.selecao.item, item);
        } else if ($scope.publicoAlvoPropriedadeRuralNvg.selecao.items && $scope.publicoAlvoPropriedadeRuralNvg.selecao.items.length) {
            for (i in $scope.publicoAlvoPropriedadeRuralNvg.selecao.items) {
                for (j in $scope.cadastro.registro.publicoAlvoPropriedadeRuralList) {
                    if (angular.equals($scope.publicoAlvoPropriedadeRuralNvg.selecao.items[i], $scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j])) {
                        item = angular.copy($scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j]);
                        editarItem($scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j], item);
                    }
                }
            }
        }
    };
    $scope.excluir = function() {
        mensagemSrv.confirmacao(false, 'confirme a exclusão').then(function (conteudo) {
            var i, j;
            if ($scope.publicoAlvoPropriedadeRuralNvg.selecao.tipo === 'U' && $scope.publicoAlvoPropriedadeRuralNvg.selecao.item) {
                for (j = $scope.cadastro.registro.publicoAlvoPropriedadeRuralList.length -1; j >= 0; j--) {

                    delete $scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j].publicoAlvoPropriedadeRural['@jsonId'];
                    delete $scope.publicoAlvoPropriedadeRuralNvg.selecao.item.publicoAlvoPropriedadeRural['@jsonId'];


                    if (angular.equals($scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j].publicoAlvoPropriedadeRural, $scope.publicoAlvoPropriedadeRuralNvg.selecao.item.publicoAlvoPropriedadeRural)) {
                        //$scope.cadastro.registro.publicoAlvoPropriedadeRuralList.splice(j, 1);
                        $scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j].cadastroAcao = 'E';
                    }
                }
                $scope.publicoAlvoPropriedadeRuralNvg.selecao.item = null;
                $scope.publicoAlvoPropriedadeRuralNvg.selecao.selecionado = false;
            } else if ($scope.publicoAlvoPropriedadeRuralNvg.selecao.items && $scope.publicoAlvoPropriedadeRuralNvg.selecao.items.length) {
                for (j = $scope.cadastro.registro.publicoAlvoPropriedadeRuralList.length-1; j >= 0; j--) {
                    for (i in $scope.publicoAlvoPropriedadeRuralNvg.selecao.items) {

                        delete $scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j].publicoAlvoPropriedadeRural['@jsonId'];
                        delete $scope.publicoAlvoPropriedadeRuralNvg.selecao.items[i].publicoAlvoPropriedadeRural['@jsonId'];

                        if (angular.equals($scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j].publicoAlvoPropriedadeRural, $scope.publicoAlvoPropriedadeRuralNvg.selecao.items[i].publicoAlvoPropriedadeRural)) {
                            //$scope.cadastro.registro.publicoAlvoPropriedadeRuralList.splice(j, 1);
                            $scope.cadastro.registro.publicoAlvoPropriedadeRuralList[j].cadastroAcao = 'E';
                            break;
                        }
                    }
                }
                for (i = $scope.publicoAlvoPropriedadeRuralNvg.selecao.items.length -1; i >= 0; i--) {
                    $scope.publicoAlvoPropriedadeRuralNvg.selecao.items.splice(i, 1);
                }
            }
        }, function () {
        });
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

})('propriedadeRural', 'PropriedadeRuralPublicoAlvoCtrl', 'Beneficiários vinculados à propriedade rural');