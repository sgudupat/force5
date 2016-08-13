'use strict';

angular.module('proj2App')
    .controller('PaySheetsController', function ($scope, $state, PaySheets) {

        $scope.paySheetss = [];
        $scope.loadAll = function() {
            PaySheets.query(function(result) {
               $scope.paySheetss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.paySheets = {
                month: null,
                year: null,
                regularDays: null,
                daysWorked: null,
                weeklyOff: null,
                compOff: null,
                holidays: null,
                overtime: null,
                id: null
            };
        };
    });
