'use strict';

angular.module('proj2App')
    .controller('TripleSetDetailController', function ($scope, $rootScope, $stateParams, entity, TripleSet) {
        $scope.tripleSet = entity;
        $scope.load = function (id) {
            TripleSet.get({id: id}, function(result) {
                $scope.tripleSet = result;
            });
        };
        var unsubscribe = $rootScope.$on('proj2App:tripleSetUpdate', function(event, result) {
            $scope.tripleSet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
