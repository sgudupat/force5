'use strict';

angular.module('proj2App').controller('SalarySheetsController',SalarySheetsController);

SalarySheetsController.$inject = ['$scope', 'Principal', 'User', 'ParseLinks', 'Client', 'SalarySheet'];

function SalarySheetsController($scope, Principal, User, ParseLinks, Client, SalarySheet) {
    $scope.clients = Client.query();
    Principal.identity().then(function(account) {
        $scope.currentAccount = account;
    });

    $scope.clear = function () {
        $scope.user = {
            id: null, login: null, firstName: null, lastName: null, email: null,
            activated: null, langKey: null, createdBy: null, createdDate: null,
            lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
            resetKey: null
        };
        $scope.editForm.$setPristine();
        $scope.editForm.$setUntouched();
    };

    $scope.save = function () {
       console.log("In save")
       console.log("values:" + $scope.salarySheets)
        $scope.isSaving = true;
        SalarySheets.generateSalarySheets($scope.salarySheets, onSaveSuccess, onSaveError);
    };
}
