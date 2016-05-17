(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('FachController', FachController);

    FachController.$inject = ['$scope', '$state', 'Fach'];

    function FachController ($scope, $state, Fach) {
        var vm = this;
        vm.faches = [];
        vm.loadAll = function() {
            Fach.query(function(result) {
                vm.faches = result;
            });
        };

        vm.loadAll();
        
    }
})();
