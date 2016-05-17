(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('KlausurDetailController', KlausurDetailController);

    KlausurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Klausur', 'Dozent', 'Fach'];

    function KlausurDetailController($scope, $rootScope, $stateParams, entity, Klausur, Dozent, Fach) {
        var vm = this;
        vm.klausur = entity;
        
        var unsubscribe = $rootScope.$on('examadminApp:klausurUpdate', function(event, result) {
            vm.klausur = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
