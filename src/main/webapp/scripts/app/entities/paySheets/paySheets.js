'use strict';

angular.module('proj2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('paySheets', {
                parent: 'entity',
                url: '/paySheetss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PaySheetss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/paySheets/paySheetss.html',
                        controller: 'PaySheetsController'
                    }
                },
                resolve: {
                }
            })
            .state('paySheets.detail', {
                parent: 'entity',
                url: '/paySheets/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PaySheets'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/paySheets/paySheets-detail.html',
                        controller: 'PaySheetsDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PaySheets', function($stateParams, PaySheets) {
                        return PaySheets.get({id : $stateParams.id});
                    }]
                }
            })
            .state('paySheets.new', {
                parent: 'paySheets',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/paySheets/paySheets-dialog.html',
                        controller: 'PaySheetsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    month: null,
                                    year: null,
                                    regularDays: null,
                                    daysWorked: null,
                                    weeklyOff: null,
                                    compOff: null,
                                    holidays: null,
                                    overtime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('paySheets', null, { reload: true });
                    }, function() {
                        $state.go('paySheets');
                    })
                }]
            })
            .state('paySheets.edit', {
                parent: 'paySheets',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/paySheets/paySheets-dialog.html',
                        controller: 'PaySheetsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PaySheets', function(PaySheets) {
                                return PaySheets.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('paySheets', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('paySheets.delete', {
                parent: 'paySheets',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/paySheets/paySheets-delete-dialog.html',
                        controller: 'PaySheetsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PaySheets', function(PaySheets) {
                                return PaySheets.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('paySheets', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
