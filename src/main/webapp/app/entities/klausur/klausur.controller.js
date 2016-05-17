(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('KlausurController', KlausurController);

    KlausurController.$inject = ['$scope', '$state', 'Klausur'];

    function KlausurController ($scope, $state, Klausur) {
        var vm = this;
        vm.klausurs = [];
        vm.loadAll = function() {
            Klausur.query(function(result) {
                vm.klausurs = result;
            });
        };

        vm.loadAll();
        
    }
})();
