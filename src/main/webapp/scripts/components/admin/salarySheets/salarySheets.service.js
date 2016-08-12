'use strict';

angular.module('proj2App')
    .factory('SalarySheet', function ($resource, DateUtils) {
    console.log('Salary Sheets services');
        return $resource('api/salarySheets/generate', {}, {
            'generateSalarySheets': { method:'PUT' }
        });
    });
