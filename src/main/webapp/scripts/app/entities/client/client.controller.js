'use strict';

angular.module('proj2App')
    .controller('ClientController', function ($scope, $state, Client) {

        $scope.clients = [];
        $scope.loadAll = function() {
            Client.query(function(result) {
               $scope.clients = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.client = {
                name: null,
                contactPerson: null,
                address: null,
                city: null,
                state: null,
                zipcode: null,
                pf: null,
                esic: null,
                vda: null,
                workHours: null,
                id: null
            };
        };
    });
