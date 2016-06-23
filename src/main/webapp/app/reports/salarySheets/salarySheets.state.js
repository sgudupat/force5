(function() {
    'use strict';

    angular
        .module('facilitymgmtApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('salarySheets', {
            parent: 'reports',
            url: '/salarySheets',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Salary Sheets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/reports/salarySheets/salarySheets.html',
                    controller: 'SettingsController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
