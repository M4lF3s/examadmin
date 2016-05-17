(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('DozentDetailController', DozentDetailController);

    DozentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Dozent', 'Klausur', 'Fach'];

    function DozentDetailController($scope, $rootScope, $stateParams, entity, Dozent, Klausur, Fach) {
        var vm = this;
        vm.dozent = entity;
        
        var unsubscribe = $rootScope.$on('examadminApp:dozentUpdate', function(event, result) {
            vm.dozent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
