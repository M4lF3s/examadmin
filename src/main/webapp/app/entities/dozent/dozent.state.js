(function() {
    'use strict';

    angular
        .module('examadminApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dozent', {
            parent: 'entity',
            url: '/dozent',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Dozents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dozent/dozents.html',
                    controller: 'DozentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('dozent-detail', {
            parent: 'entity',
            url: '/dozent/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Dozent'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dozent/dozent-detail.html',
                    controller: 'DozentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Dozent', function($stateParams, Dozent) {
                    return Dozent.get({id : $stateParams.id});
                }]
            }
        })
        .state('dozent.new', {
            parent: 'dozent',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dozent/dozent-dialog.html',
                    controller: 'DozentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                vorname: null,
                                nachname: null,
                                dID: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dozent', null, { reload: true });
                }, function() {
                    $state.go('dozent');
                });
            }]
        })
        .state('dozent.edit', {
            parent: 'dozent',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dozent/dozent-dialog.html',
                    controller: 'DozentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dozent', function(Dozent) {
                            return Dozent.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('dozent', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dozent.delete', {
            parent: 'dozent',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dozent/dozent-delete-dialog.html',
                    controller: 'DozentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Dozent', function(Dozent) {
                            return Dozent.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('dozent', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
