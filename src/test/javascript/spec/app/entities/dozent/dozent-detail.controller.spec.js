'use strict';

describe('Controller Tests', function() {

    describe('Dozent Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDozent, MockKlausur, MockFach;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDozent = jasmine.createSpy('MockDozent');
            MockKlausur = jasmine.createSpy('MockKlausur');
            MockFach = jasmine.createSpy('MockFach');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Dozent': MockDozent,
                'Klausur': MockKlausur,
                'Fach': MockFach
            };
            createController = function() {
                $injector.get('$controller')("DozentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'examadminApp:dozentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
