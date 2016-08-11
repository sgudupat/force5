'use strict';

angular.module('proj2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('salary-sheets', {
                parent: 'admin',
                url: '/salary-sheets',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Salary Sheets'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/salary-sheets/salary-sheets.html',
                        controller: 'UserManagementController'
                    }
                },
                resolve: {

                }
            })

            .state('billing-sheets', {
                            parent: 'admin',
                            url: '/billing-sheets',
                            data: {
                                authorities: ['ROLE_ADMIN'],
                                pageTitle: 'Billing Sheets'
                            },
                            views: {
                                'content@': {
                                    templateUrl: 'scripts/app/admin/salary-sheets/billing-sheets.html',
                                    controller: 'UserManagementController'
                                }
                            },
                            resolve: {

                            }
                        })



                             .state('invoice-sheets', {
                                                    parent: 'admin',
                                                    url: '/invoice-sheets',
                                                    data: {
                                                        authorities: ['ROLE_ADMIN'],
                                                        pageTitle: 'Invoice Sheets'
                                                    },
                                                    views: {
                                                        'content@': {
                                                            templateUrl: 'scripts/app/admin/salary-sheets/invoice-sheets.html',
                                                            controller: 'UserManagementController'
                                                        }
                                                    },
                                                    resolve: {

                                                    }
                                                })




            .state('salary-sheets-detail', {
                parent: 'admin',
                url: '/user/:login',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'User'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/salary-sheets/salary-sheets-detail.html',
                        controller: 'UserManagementDetailController'
                    }
                },
                resolve: {

                }
            })
            .state('salary-sheets.new', {
                parent: 'salary-sheets',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/admin/salary-sheets/salary-sheets-dialog.html',
                        controller: 'UserManagementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null, login: null, firstName: null, lastName: null, email: null,
                                    activated: true, langKey: null, createdBy: null, createdDate: null,
                                    lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                                    resetKey: null, authorities: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('salary-sheets', null, { reload: true });
                    }, function() {
                        $state.go('salary-sheets');
                    })
                }]
            })
            .state('salary-sheets.edit', {
                parent: 'salary-sheets',
                url: '/{login}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/admin/salary-sheets/salary-sheets-dialog.html',
                        controller: 'UserManagementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['User', function(User) {
                                return User.get({login : $stateParams.login});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('salary-sheets', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('salary-sheets.delete', {
                parent: 'salary-sheets',
                url: '/{login}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/admin/salary-sheets/salary-sheets-delete-dialog.html',
                        controller: 'user-managementDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['User', function(User) {
                                return User.get({login : $stateParams.login});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('salary-sheets', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
