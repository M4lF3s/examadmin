(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('FachDetailController', FachDetailController);

    FachDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Fach', 'Klausur', 'Dozent'];

    function FachDetailController($scope, $rootScope, $stateParams, entity, Fach, Klausur, Dozent) {
        var vm = this;
        vm.fach = entity;
        
        var unsubscribe = $rootScope.$on('examadminApp:fachUpdate', function(event, result) {
            vm.fach = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
