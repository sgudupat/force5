'use strict';

angular.module('proj2App')
    .controller('PaySheetsDetailController', function ($scope, $rootScope, $stateParams, entity, PaySheets, Assignments) {
        $scope.paySheets = entity;
        $scope.load = function (id) {
            PaySheets.get({id: id}, function(result) {
                $scope.paySheets = result;
            });
        };
        var unsubscribe = $rootScope.$on('proj2App:paySheetsUpdate', function(event, result) {
            $scope.paySheets = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
