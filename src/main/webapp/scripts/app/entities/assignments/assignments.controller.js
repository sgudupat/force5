'use strict';

angular.module('facilitymgmtApp')
    .controller('AssignmentsController', function ($scope, $state, Assignments) {

        $scope.assignmentss = [];
        $scope.loadAll = function() {
            Assignments.query(function(result) {
               $scope.assignmentss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.assignments = {
                regularDays: null,
                overtime: null,
                startDate: null,
                endDate: null,
                id: null
            };
        };
    });
