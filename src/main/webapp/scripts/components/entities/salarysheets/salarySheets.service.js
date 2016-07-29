'use strict';

angular.module('proj2App')
    .factory('SalarySheets', function ($resource, DateUtils) {
        return $resource('api/salarySheets/generate', {}, {
            'generateSalarySheets': { method:'PUT' }
        });
    });
