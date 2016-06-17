'use strict';

describe('Controller Tests', function() {

    describe('PaySheets Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPaySheets, MockEmployee;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPaySheets = jasmine.createSpy('MockPaySheets');
            MockEmployee = jasmine.createSpy('MockEmployee');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PaySheets': MockPaySheets,
                'Employee': MockEmployee
            };
            createController = function() {
                $injector.get('$controller')("PaySheetsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'proj2App:paySheetsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
