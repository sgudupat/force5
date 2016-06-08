'use strict';

describe('Controller Tests', function() {

    describe('Assignments Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAssignments, MockEmployee, MockClient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAssignments = jasmine.createSpy('MockAssignments');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockClient = jasmine.createSpy('MockClient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Assignments': MockAssignments,
                'Employee': MockEmployee,
                'Client': MockClient
            };
            createController = function() {
                $injector.get('$controller')("AssignmentsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'facilitymgmtApp:assignmentsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
