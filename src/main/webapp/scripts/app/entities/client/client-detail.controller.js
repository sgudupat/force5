'use strict';

angular.module('proj2App')
    .controller('ClientDetailController', function ($scope, $rootScope, $stateParams, entity, Client) {
        $scope.client = entity;
        $scope.load = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
            });
        };
        var unsubscribe = $rootScope.$on('proj2App:clientUpdate', function(event, result) {
            $scope.client = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
