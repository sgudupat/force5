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
                        controller: 'SalarySheetsController'
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
                                    controller: 'SalarySheetsController'
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
                                                            controller: 'SalarySheetsController'
                                                        }
                                                    },
                                                    resolve: {

                                                    }
                                                });
    });
