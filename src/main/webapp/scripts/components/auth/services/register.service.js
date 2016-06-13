'use strict';

angular.module('proj2App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


