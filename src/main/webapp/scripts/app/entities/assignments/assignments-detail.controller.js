'use strict';

angular.module('proj2App')
    .controller('AssignmentsDetailController', function ($scope, $rootScope, $stateParams, entity, Assignments, Employee, Client) {
        $scope.assignments = entity;
        $scope.load = function (id) {
            Assignments.get({id: id}, function(result) {
                $scope.assignments = result;
            });
        };
        var unsubscribe = $rootScope.$on('proj2App:assignmentsUpdate', function(event, result) {
            $scope.assignments = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
