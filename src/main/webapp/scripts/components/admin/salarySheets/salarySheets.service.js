'use strict';

angular.module('proj2App')
    .factory('SalarySheets', function ($resource, DateUtils) {
    console.log('Salary Sheets services');
        return $resource('api/salarySheets/generate', {}, {
            'generateSalarySheets': { method:'PUT' }
        });
    });
