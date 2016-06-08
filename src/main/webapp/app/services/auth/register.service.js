(function () {
    'use strict';

    angular
        .module('facilitymgmtApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
