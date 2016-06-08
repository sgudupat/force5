'use strict';

angular.module('facilitymgmtApp')
    .controller('ClientDetailController', function ($scope, $rootScope, $stateParams, entity, Client) {
        $scope.client = entity;
        $scope.load = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
            });
        };
        var unsubscribe = $rootScope.$on('facilitymgmtApp:clientUpdate', function(event, result) {
            $scope.client = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
