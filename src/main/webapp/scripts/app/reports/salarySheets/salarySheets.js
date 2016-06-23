'use strict';

angular.module('proj2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('salarySheets', {
                parent: 'reports',
                url: '/salarySheets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Salary Sheets'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/reports/salarySheets/salarySheets.html',
                        controller: 'SettingsController'
                    }
                },
                resolve: {
                    
                }
            });
    });
