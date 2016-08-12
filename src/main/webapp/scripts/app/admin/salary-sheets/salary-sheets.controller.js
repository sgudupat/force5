'use strict';

angular.module('proj2App').controller('SalarySheetsController', SalarySheetsController);

SalarySheetsController.$inject = ['$scope', 'Client', 'SalarySheets','BillingSheets','InvoiceSheets'];

function SalarySheetsController($scope, Client, SalarySheets,BillingSheets,InvoiceSheets) {
    $scope.clients = Client.query();

        $scope.clear = function () {
            $scope.salarySheets = {
                clientId: null, month: null, year: null
            };
        };

    $scope.salaryReport = function () {
       console.log("In save")
       console.dir($scope.salarySheets)
       SalarySheets.generateSalarySheets($scope.salarySheets);
    };

     $scope.billingReport = function () {
       console.log("In billingReport save")
       console.dir($scope.salarySheets)
       BillingSheets.generateBillingReport($scope.salarySheets);
    };

     $scope.invoiceReport = function () {
       console.log("In invoiceReport save")
       console.dir($scope.salarySheets)
       InvoiceSheets.generateInvoiceReport($scope.salarySheets);
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
