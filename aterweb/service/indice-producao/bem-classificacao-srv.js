/* jshint evil:true, loopfunc: true */

(function(pNmModulo, pNmFactory, pNmController) {

'use strict';

angular.module(pNmModulo).factory(pNmFactory,
  ['$rootScope', '$http', 'toastr', 'SegurancaSrv', 'UtilSrv', '$stateParams', 'mensagemSrv',
    function($rootScope, $http, toastr, SegurancaSrv, UtilSrv, $stateParams, mensagemSrv) {
        var BemClassificacaoSrv = {
            endereco: $rootScope.servicoUrl + '/bem-classificacao',
            filtrar : function(filtro) {
                return $http.post(this.endereco + '/filtro-executar', filtro);
            },
        };
        return BemClassificacaoSrv;
    }
]);

})('principal', 'BemClassificacaoSrv');