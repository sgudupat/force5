'use strict';

angular.module('proj2App').controller('TripleSetDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TripleSet',
        function($scope, $stateParams, $uibModalInstance, entity, TripleSet) {

        $scope.tripleSet = entity;
        $scope.load = function(id) {
            TripleSet.get({id : id}, function(result) {
                $scope.tripleSet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('proj2App:tripleSetUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.tripleSet.id != null) {
                TripleSet.update($scope.tripleSet, onSaveSuccess, onSaveError);
            } else {
                TripleSet.save($scope.tripleSet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
