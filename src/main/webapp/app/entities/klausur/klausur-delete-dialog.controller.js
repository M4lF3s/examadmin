(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('KlausurDeleteController',KlausurDeleteController);

    KlausurDeleteController.$inject = ['$uibModalInstance', 'entity', 'Klausur'];

    function KlausurDeleteController($uibModalInstance, entity, Klausur) {
        var vm = this;
        vm.klausur = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Klausur.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
