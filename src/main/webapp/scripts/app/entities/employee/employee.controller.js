'use strict';

angular.module('proj2App')
    .controller('EmployeeController', function ($scope, $state, Employee) {

        $scope.employees = [];
        $scope.loadAll = function() {
            Employee.query(function(result) {
               $scope.employees = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.employee = {
                name: null,
                basic: null,
                allowances: null,
                startDate: null,
                endDate: null,
                mobile: null,
                mobile2: null,
                address: null,
                city: null,
                state: null,
                zipcode: null,
                id: null
            };
        };
    });
