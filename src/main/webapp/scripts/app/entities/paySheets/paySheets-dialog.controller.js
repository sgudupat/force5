'use strict';

angular.module('proj2App').controller('PaySheetsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PaySheets', 'Employee',
        function($scope, $stateParams, $uibModalInstance, entity, PaySheets, Employee) {

        $scope.paySheets = entity;
        $scope.employees = Employee.query();
        $scope.load = function(id) {
            PaySheets.get({id : id}, function(result) {
                $scope.paySheets = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('proj2App:paySheetsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.paySheets.id != null) {
                PaySheets.update($scope.paySheets, onSaveSuccess, onSaveError);
            } else {
                PaySheets.save($scope.paySheets, onSaveSuccess, onSaveError);
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
