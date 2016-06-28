'use strict';

angular.module('proj2App').controller('PaySheetsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PaySheets', 'Assignments',
        function($scope, $stateParams, $uibModalInstance, entity, PaySheets, Assignments) {

        $scope.paySheets = entity;
        $scope.assignmentss = Assignments.query();
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
}]);
