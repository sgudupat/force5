'use strict';

angular.module('facilitymgmtApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assignments', {
                parent: 'entity',
                url: '/assignmentss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Assignmentss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/assignments/assignmentss.html',
                        controller: 'AssignmentsController'
                    }
                },
                resolve: {
                }
            })
            .state('assignments.detail', {
                parent: 'entity',
                url: '/assignments/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Assignments'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/assignments/assignments-detail.html',
                        controller: 'AssignmentsDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Assignments', function($stateParams, Assignments) {
                        return Assignments.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assignments.new', {
                parent: 'assignments',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/assignments/assignments-dialog.html',
                        controller: 'AssignmentsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    regularDays: null,
                                    overtime: null,
                                    startDate: null,
                                    endDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('assignments', null, { reload: true });
                    }, function() {
                        $state.go('assignments');
                    })
                }]
            })
            .state('assignments.edit', {
                parent: 'assignments',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/assignments/assignments-dialog.html',
                        controller: 'AssignmentsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Assignments', function(Assignments) {
                                return Assignments.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('assignments', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('assignments.delete', {
                parent: 'assignments',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/assignments/assignments-delete-dialog.html',
                        controller: 'AssignmentsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Assignments', function(Assignments) {
                                return Assignments.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('assignments', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
