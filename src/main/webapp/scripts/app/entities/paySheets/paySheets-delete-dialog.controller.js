'use strict';

angular.module('proj2App')
	.controller('PaySheetsDeleteController', function($scope, $uibModalInstance, entity, PaySheets) {

        $scope.paySheets = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PaySheets.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
