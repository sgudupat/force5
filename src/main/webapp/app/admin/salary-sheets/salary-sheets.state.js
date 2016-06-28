(function() {
    'use strict';

    angular
        .module('facilitymgmtApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('salary-sheets', {
            parent: 'admin',
            url: '/salary-sheets',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'facilitymgmt'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/salary-sheets/salary-sheets.html',
                    controller: 'UserManagementController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('salary-sheets-detail', {
            parent: 'admin',
            url: '/user/:login',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'facilitymgmt'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/salary-sheets/salary-sheets-detail.html',
                    controller: 'UserManagementDetailController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('salary-sheets.new', {
            parent: 'salary-sheets',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/salary-sheets/salary-sheets-dialog.html',
                    controller: 'UserManagementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
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
                }).result.then(function() {
                    $state.go('salary-sheets', null, { reload: true });
                }, function() {
                    $state.go('salary-sheets');
                });
            }]
        })
        .state('salary-sheets.edit', {
            parent: 'salary-sheets',
            url: '/{login}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/salary-sheets/salary-sheets-dialog.html',
                    controller: 'UserManagementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['User', function(User) {
                            return User.get({login : $stateParams.login});
                        }]
                    }
                }).result.then(function() {
                    $state.go('salary-sheets', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salary-sheets.delete', {
            parent: 'salary-sheets',
            url: '/{login}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/salary-sheets/salary-sheets-delete-dialog.html',
                    controller: 'UserManagementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['User', function(User) {
                            return User.get({login : $stateParams.login});
                        }]
                    }
                }).result.then(function() {
                    $state.go('salary-sheets', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }
})();
