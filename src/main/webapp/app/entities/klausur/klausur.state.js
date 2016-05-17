(function() {
    'use strict';

    angular
        .module('examadminApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('klausur', {
            parent: 'entity',
            url: '/klausur',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Klausurs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/klausur/klausurs.html',
                    controller: 'KlausurController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('klausur-detail', {
            parent: 'entity',
            url: '/klausur/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Klausur'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/klausur/klausur-detail.html',
                    controller: 'KlausurDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Klausur', function($stateParams, Klausur) {
                    return Klausur.get({id : $stateParams.id});
                }]
            }
        })
        .state('klausur.new', {
            parent: 'klausur',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/klausur/klausur-dialog.html',
                    controller: 'KlausurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                pfad: null,
                                art: null,
                                pruefungsform: null,
                                art: null,
                                kID: null,
                                semester: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('klausur', null, { reload: true });
                }, function() {
                    $state.go('klausur');
                });
            }]
        })
        .state('klausur.edit', {
            parent: 'klausur',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/klausur/klausur-dialog.html',
                    controller: 'KlausurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Klausur', function(Klausur) {
                            return Klausur.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('klausur', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('klausur.delete', {
            parent: 'klausur',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/klausur/klausur-delete-dialog.html',
                    controller: 'KlausurDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Klausur', function(Klausur) {
                            return Klausur.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('klausur', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
