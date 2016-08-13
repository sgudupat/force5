'use strict';

angular.module('proj2App').controller('SalarySheetsController', SalarySheetsController);

SalarySheetsController.$inject = ['$scope', 'Client', 'SalarySheets','BillingSheets','InvoiceSheets'];

function SalarySheetsController($scope, Client, SalarySheets,BillingSheets,InvoiceSheets) {
    $scope.clients = Client.query();

    $scope.alertMessage = false;
    $scope.clear = function () {
        $scope.salarySheets = {
            clientId: null, month: null, year: null
        };
    };

    var onSaveSuccess = function (result) {
       $scope.alertMessage = true;
       $scope.message = "Report generated successfully";
       setTimeout(function(){$scope.alertMessage = false;},1000)
    };

    var onSaveError = function (result) {
        //do nothing
    };

    $scope.salaryReport = function () {
       console.log("In save")
       console.dir($scope.salarySheets)
       SalarySheets.generateSalarySheets($scope.salarySheets, onSaveSuccess, onSaveError);
    };

     $scope.billingReport = function () {
       console.log("In billingReport save")
       console.dir($scope.salarySheets)
       BillingSheets.generateBillingReport($scope.salarySheets,onSaveSuccess,onSaveError);
    };

     $scope.invoiceReport = function () {
       console.log("In invoiceReport save")
       console.dir($scope.salarySheets)
       InvoiceSheets.generateInvoiceReport($scope.salarySheets,onSaveSuccess,onSaveError);
    };
}

angular.module('proj2App')
    .factory('SalarySheets',function ($resource, DateUtils) {
        return $resource('api/salarySheets/generate', {}, {
            'generateSalarySheets': { method:'PUT' }
         });
    })
    .factory('BillingSheets',function ($resource, DateUtils) {
        return $resource('api/billingReport/generate', {}, {
            'generateBillingReport': { method:'PUT' }
         });
    })
     .factory('InvoiceSheets',function ($resource, DateUtils) {
            return $resource('api/invoiceReport/generate', {}, {
                'generateInvoiceReport': { method:'PUT' }
             });
        });
