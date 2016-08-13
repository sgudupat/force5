'use strict';

angular.module('proj2App').controller('SalarySheetsController', SalarySheetsController);

SalarySheetsController.$inject = ['$scope', 'Client', 'SalarySheets','BillingSheets','InvoiceSheets', 'SalaryReports','BillingReports','InvoiceReports'];

function SalarySheetsController($scope, Client, SalarySheets,BillingSheets,InvoiceSheets, SalaryReports,BillingReports,InvoiceReports) {

    $scope.clients = Client.query();

    //Define variables as Array variables
    $scope.salaryReportsList = [];
    $scope.billingReportsList = [];
    $scope.invoiceReportsList = [];

    //Assign values during loadAll function
    $scope.loadAll = function() {
        SalaryReports.fetchSalarySheets(function(result) {
           $scope.salaryReportsList = result.reports;
        });

        BillingReports.fetchBillingSheets(function(result) {
           $scope.billingReportsList = result.reports;
        });

        InvoiceReports.fetchInvoiceSheets(function(result) {
           $scope.invoiceReportsList = result.reports;
        });
    };

   //Execute loadAll
    $scope.loadAll();

    $scope.alertMessage = false;
    $scope.clear = function () {
        $scope.salarySheets = {
            clientId: null, month: null, year: null
        };
    };

     var onSaveSuccess = function (result) {
       $scope.alertMessage = true;
       $scope.message = "Report generated successfully";
       $scope.loadAll();
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

//TODO: Merge
angular.module('proj2App')
    .factory('SalarySheets',function ($resource, DateUtils) {
        return $resource('api/salarySheets/generate', {}, { 'generateSalarySheets': { method:'PUT' } });
    })
    .factory('SalaryReports',function ($resource, DateUtils) {
        return $resource('api/salarySheets/fetch', {}, {'fetchSalarySheets': { method:'GET' }});
    })
    .factory('BillingSheets',function ($resource, DateUtils) {
        return $resource('api/billingReport/generate', {}, {'generateBillingReport': { method:'PUT' }});
    })
    .factory('BillingReports',function ($resource, DateUtils) {
            return $resource('api/billingSheets/fetch', {}, {'fetchBillingSheets': { method:'GET' }});
    })
    .factory('InvoiceSheets',function ($resource, DateUtils) {
            return $resource('api/invoiceReport/generate', {}, {'generateInvoiceReport': { method:'PUT' }});
    })
    .factory('InvoiceReports',function ($resource, DateUtils) {
            return $resource('api/invoiceSheets/fetch', {}, {'fetchInvoiceSheets': { method:'GET' }});
    });
