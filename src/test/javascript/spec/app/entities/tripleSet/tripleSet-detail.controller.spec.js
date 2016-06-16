'use strict';

describe('Controller Tests', function() {

    describe('TripleSet Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTripleSet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTripleSet = jasmine.createSpy('MockTripleSet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TripleSet': MockTripleSet
            };
            createController = function() {
                $injector.get('$controller')("TripleSetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'proj2App:tripleSetUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
