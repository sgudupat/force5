'use strict';

angular.module('proj2App')
    .factory('Assignments', function ($resource, DateUtils) {
        return $resource('api/assignmentss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                    data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
