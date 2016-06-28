'use strict';

angular.module('proj2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reports', {
                abstract: true,
                parent: 'site'
            });
    });
