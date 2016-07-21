/* global moment, zeroEsq */

(function(pNmModulo, pNmFactory, pNmController) {

'use strict';

angular.module(pNmModulo).factory(pNmFactory,
  ['$rootScope', '$http', 'toastr', 'SegurancaSrv', 'UtilSrv', '$stateParams', 'ComunidadeSrv',
    function($rootScope, $http, toastr, SegurancaSrv, UtilSrv, $stateParams, ComunidadeSrv) {
        'ngInject';
        
        var ProjetoCreditoRuralSrv = {
            funcionalidade: 'PROJETO_CREDITO_RURAL',
            endereco: $rootScope.servicoUrl + '/projeto-credito-rural',
            abrir : function(scp) {
              
                SegurancaSrv.acesso(this.funcionalidade, 'CONSULTAR');
                UtilSrv.dominio({ent: [
                   'PessoaGenero',
                   'PessoaGeracao',
                   'PessoaSituacao',
                   'PublicoAlvoSegmento',
                   'PublicoAlvoCategoria',
                   'AtividadeFormato',
                   'AtividadeFinalidade',
                   'AtividadeNatureza',
                   'AtividadePrioridade',
                   'AtividadeSituacao',
                   'Metodo',
                   'Assunto',
                   'Confirmacao',
                ]}).success(function(resposta) {
                    if (resposta && resposta.resultado) {
                        scp.cadastro.apoio.generoList = resposta.resultado[0];
                        scp.cadastro.apoio.pessoaGeracaoList = resposta.resultado[1];
                        scp.cadastro.apoio.pessoaSituacaoList = resposta.resultado[2];
                        scp.cadastro.apoio.publicoAlvoSegmentoList = resposta.resultado[3];
                        scp.cadastro.apoio.publicoAlvoCategoriaList = resposta.resultado[4];
                        scp.cadastro.apoio.atividadeFormatoList = resposta.resultado[5];
                        scp.cadastro.apoio.atividadeFinalidadeList = resposta.resultado[6];
                        scp.cadastro.apoio.atividadeNaturezaList = resposta.resultado[7];
                        scp.cadastro.apoio.atividadePrioridadeList = resposta.resultado[8];
                        scp.cadastro.apoio.atividadeSituacaoList = resposta.resultado[9];
                        scp.cadastro.apoio.metodoList = resposta.resultado[10];
                        scp.cadastro.apoio.assuntoList = resposta.resultado[11];
                        scp.cadastro.apoio.confirmacaoList = resposta.resultado[12];
                   }
                });
                if ($rootScope.isAuthenticated()) {
                    var t = $rootScope.token;

                    scp.cadastro.apoio.localList = [];
                    var fltr = null;
                    if (scp.cadastro.apoio.unidadeOrganizacionalSomenteLeitura) {
                        fltr = {unidadeOrganizacionalList: scp.cadastro.filtro.unidadeOrganizacionalList};
                    } else {
                        fltr = {pessoaJuridicaList: (t && t.lotacaoAtual && t.lotacaoAtual.pessoaJuridica) ? [angular.fromJson(t.lotacaoAtual.pessoaJuridica.id)] : null};
                    }
                    ComunidadeSrv.lista(fltr, scp.cadastro.apoio.localList, t);

                    if (!t || !t.lotacaoAtual || !t.lotacaoAtual.pessoaJuridica) {
                        toastr.warning('Não foi possível identificar a sua lotação', 'Erro ao carregar os dados');
                    }
                }

                scp.cadastro.filtro.inicio = new Date();
                scp.cadastro.filtro.inicio.setMonth(scp.cadastro.filtro.inicio.getMonth() - 6);
                scp.cadastro.filtro.inicio.setDate(1);
                scp.cadastro.filtro.termino = new Date();

                scp.cadastro.apoio.anoList = [];
                for (var i = 1; i <= 10; i++) {
                    scp.cadastro.apoio.anoList.push({codigo: i, descricao: 'Ano ' + zeroEsq('' + i, 2)});
                }
                scp.cadastro.apoio.periodicidadeList = [{codigo: 3, descricao: 'Trimestral'}, {codigo: 6, descricao: 'Semestral'}, {codigo: 12, descricao: 'Anual'}];

            },
            filtrar : function(filtro) {
                SegurancaSrv.acesso(this.funcionalidade, 'CONSULTAR');
                return $http.post(this.endereco + '/filtro-executar', filtro);
            },
            executarFiltro : function() {
                SegurancaSrv.acesso(this.funcionalidade, 'CONSULTAR');
            },
            novo : function() {
                SegurancaSrv.acesso(this.funcionalidade, 'INCLUIR');
                return $http.get(this.endereco + '/novo');
            },
            incluir : function(registro) {
                SegurancaSrv.acesso(this.funcionalidade, 'INCLUIR');
                return $http.post(this.endereco + '/incluir', registro);
            },
            visualizar : function(id) {
                SegurancaSrv.acesso(this.funcionalidade, 'VISUALIZAR');
                return $http.get(this.endereco + '/visualizar', {params: {'id': id}});
            },
            editar : function(registro) {
                SegurancaSrv.acesso(this.funcionalidade, 'EDITAR');
                return $http.post(this.endereco + '/editar', registro);
            },
            excluir : function() {
                SegurancaSrv.acesso(this.funcionalidade, 'EXCLUIR');
            },
            tagUnidade : function(nome) {
                return $http.post($rootScope.servicoUrl + '/unidade-organizacional/lista', {"nome":nome, "classificacao":["OP"]}, { cache: false } );
            },
            tagComunidade : function( unidade, nome) {
                return ComunidadeSrv.lista({"unidadeOrganizacionalList": [unidade], "nome":nome});
            },
            calcularCronograma : function(cronograma) {
                SegurancaSrv.acesso(this.funcionalidade, 'CONSULTAR');
                return $http.post(this.endereco + '/calcular-cronograma', cronograma);
            },

        };
        return ProjetoCreditoRuralSrv;
    }
]);

})('principal', 'ProjetoCreditoRuralSrv');
