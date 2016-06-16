'use strict';

angular.module('proj2App')
	.controller('TripleSetDeleteController', function($scope, $uibModalInstance, entity, TripleSet) {

        $scope.tripleSet = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TripleSet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
