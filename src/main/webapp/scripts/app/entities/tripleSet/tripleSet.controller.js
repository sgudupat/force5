'use strict';

angular.module('proj2App')
    .controller('TripleSetController', function ($scope, $state, TripleSet) {

        $scope.tripleSets = [];
        $scope.loadAll = function() {
            TripleSet.query(function(result) {
               $scope.tripleSets = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.tripleSet = {
                control: null,
                parent: null,
                child: null,
                config: null,
                id: null
            };
        };
    });
