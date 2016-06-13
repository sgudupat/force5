'use strict';

angular.module('proj2App')
	.controller('AssignmentsDeleteController', function($scope, $uibModalInstance, entity, Assignments) {

        $scope.assignments = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Assignments.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
