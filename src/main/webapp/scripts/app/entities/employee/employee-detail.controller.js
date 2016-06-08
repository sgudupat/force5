'use strict';

angular.module('facilitymgmtApp')
    .controller('EmployeeDetailController', function ($scope, $rootScope, $stateParams, entity, Employee) {
        $scope.employee = entity;
        $scope.load = function (id) {
            Employee.get({id: id}, function(result) {
                $scope.employee = result;
            });
        };
        var unsubscribe = $rootScope.$on('facilitymgmtApp:employeeUpdate', function(event, result) {
            $scope.employee = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
