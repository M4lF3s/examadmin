'use strict';

describe('Controller Tests', function() {

    describe('Klausur Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKlausur, MockDozent, MockFach;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKlausur = jasmine.createSpy('MockKlausur');
            MockDozent = jasmine.createSpy('MockDozent');
            MockFach = jasmine.createSpy('MockFach');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Klausur': MockKlausur,
                'Dozent': MockDozent,
                'Fach': MockFach
            };
            createController = function() {
                $injector.get('$controller')("KlausurDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'examadminApp:klausurUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
