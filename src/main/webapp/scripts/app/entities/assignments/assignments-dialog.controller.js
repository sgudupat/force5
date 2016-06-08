'use strict';

angular.module('facilitymgmtApp').controller('AssignmentsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Assignments', 'Employee', 'Client',
        function($scope, $stateParams, $uibModalInstance, entity, Assignments, Employee, Client) {

        $scope.assignments = entity;
        $scope.employees = Employee.query();
        $scope.clients = Client.query();
        $scope.load = function(id) {
            Assignments.get({id : id}, function(result) {
                $scope.assignments = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('facilitymgmtApp:assignmentsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.assignments.id != null) {
                Assignments.update($scope.assignments, onSaveSuccess, onSaveError);
            } else {
                Assignments.save($scope.assignments, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForStartDate = {};

        $scope.datePickerForStartDate.status = {
            opened: false
        };

        $scope.datePickerForStartDateOpen = function($event) {
            $scope.datePickerForStartDate.status.opened = true;
        };
        $scope.datePickerForEndDate = {};

        $scope.datePickerForEndDate.status = {
            opened: false
        };

        $scope.datePickerForEndDateOpen = function($event) {
            $scope.datePickerForEndDate.status.opened = true;
        };
}]);
