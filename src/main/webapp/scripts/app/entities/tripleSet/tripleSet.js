'use strict';

angular.module('proj2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tripleSet', {
                parent: 'entity',
                url: '/tripleSets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TripleSets'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tripleSet/tripleSets.html',
                        controller: 'TripleSetController'
                    }
                },
                resolve: {
                }
            })
            .state('tripleSet.detail', {
                parent: 'entity',
                url: '/tripleSet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TripleSet'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tripleSet/tripleSet-detail.html',
                        controller: 'TripleSetDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TripleSet', function($stateParams, TripleSet) {
                        return TripleSet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tripleSet.new', {
                parent: 'tripleSet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tripleSet/tripleSet-dialog.html',
                        controller: 'TripleSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    control: null,
                                    parent: null,
                                    child: null,
                                    config: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tripleSet', null, { reload: true });
                    }, function() {
                        $state.go('tripleSet');
                    })
                }]
            })
            .state('tripleSet.edit', {
                parent: 'tripleSet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tripleSet/tripleSet-dialog.html',
                        controller: 'TripleSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TripleSet', function(TripleSet) {
                                return TripleSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tripleSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('tripleSet.delete', {
                parent: 'tripleSet',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tripleSet/tripleSet-delete-dialog.html',
                        controller: 'TripleSetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TripleSet', function(TripleSet) {
                                return TripleSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tripleSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
