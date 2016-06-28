'use strict';

angular.module('proj2App')
    .factory('PaySheets', function ($resource, DateUtils) {
        return $resource('api/paySheetss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
