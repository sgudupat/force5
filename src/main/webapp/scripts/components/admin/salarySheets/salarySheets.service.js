'use strict';

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
