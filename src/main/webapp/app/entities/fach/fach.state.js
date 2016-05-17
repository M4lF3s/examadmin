(function() {
    'use strict';

    angular
        .module('examadminApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fach', {
            parent: 'entity',
            url: '/fach',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Faches'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fach/faches.html',
                    controller: 'FachController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('fach-detail', {
            parent: 'entity',
            url: '/fach/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fach'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fach/fach-detail.html',
                    controller: 'FachDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Fach', function($stateParams, Fach) {
                    return Fach.get({id : $stateParams.id});
                }]
            }
        })
        .state('fach.new', {
            parent: 'fach',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fach/fach-dialog.html',
                    controller: 'FachDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                fID: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fach', null, { reload: true });
                }, function() {
                    $state.go('fach');
                });
            }]
        })
        .state('fach.edit', {
            parent: 'fach',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fach/fach-dialog.html',
                    controller: 'FachDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fach', function(Fach) {
                            return Fach.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('fach', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fach.delete', {
            parent: 'fach',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fach/fach-delete-dialog.html',
                    controller: 'FachDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Fach', function(Fach) {
                            return Fach.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('fach', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
