'use strict';

angular.module('proj2App')
    .factory('TripleSet', function ($resource, DateUtils) {
        return $resource('api/tripleSets/:id', {}, {
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
