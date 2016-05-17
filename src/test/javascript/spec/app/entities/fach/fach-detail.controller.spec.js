'use strict';

describe('Controller Tests', function() {

    describe('Fach Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFach, MockKlausur, MockDozent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFach = jasmine.createSpy('MockFach');
            MockKlausur = jasmine.createSpy('MockKlausur');
            MockDozent = jasmine.createSpy('MockDozent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Fach': MockFach,
                'Klausur': MockKlausur,
                'Dozent': MockDozent
            };
            createController = function() {
                $injector.get('$controller')("FachDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'examadminApp:fachUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
