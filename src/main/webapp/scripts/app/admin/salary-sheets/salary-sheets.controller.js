'use strict';

angular.module('proj2App')
    .controller('UserManagementController', function ($scope, Principal, User, ParseLinks, Client, SalarySheets ) {
        $scope.salarySheet = entity;
        $scope.salarysheet = [];
        $scope.clients = Client.query();
        console.log($scope.clients);
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN"];

		Principal.identity().then(function(account) {
            $scope.currentAccount = account;
        });
        $scope.page = 1;
        $scope.loadAll = function () {
            User.query({page: $scope.page - 1, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.users = result;
            });
        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.setActive = function (user, isActivated) {
            user.activated = isActivated;
            User.update(user, function () {
                $scope.loadAll();
                $scope.clear();
            });
        };

        $scope.clear = function () {
            $scope.user = {
                id: null, login: null, firstName: null, lastName: null, email: null,
                activated: null, langKey: null, createdBy: null, createdDate: null,
                lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                resetKey: null, authorities: null
            };
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.save = function () {
            $scope.isSaving = true;
//            if ($scope.assignments.id != null) {
                SalarySheets.generateSalarySheets($scope.assignments, onSaveSuccess, onSaveError);
//            } else {
//                SalarySheets.save($scope.assignments, onSaveSuccess, onSaveError);
//            }
        };
    });
