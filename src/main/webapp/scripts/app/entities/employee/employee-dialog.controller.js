'use strict';

angular.module('facilitymgmtApp').controller('EmployeeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Employee',
        function($scope, $stateParams, $uibModalInstance, entity, Employee) {

        $scope.employee = entity;
        $scope.load = function(id) {
            Employee.get({id : id}, function(result) {
                $scope.employee = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('facilitymgmtApp:employeeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.employee.id != null) {
                Employee.update($scope.employee, onSaveSuccess, onSaveError);
            } else {
                Employee.save($scope.employee, onSaveSuccess, onSaveError);
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
