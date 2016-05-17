(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('DozentController', DozentController);

    DozentController.$inject = ['$scope', '$state', 'Dozent'];

    function DozentController ($scope, $state, Dozent) {
        var vm = this;
        vm.dozents = [];
        vm.loadAll = function() {
            Dozent.query(function(result) {
                vm.dozents = result;
            });
        };

        vm.loadAll();
        
    }
})();
