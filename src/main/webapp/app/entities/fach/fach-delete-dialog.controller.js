(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('FachDeleteController',FachDeleteController);

    FachDeleteController.$inject = ['$uibModalInstance', 'entity', 'Fach'];

    function FachDeleteController($uibModalInstance, entity, Fach) {
        var vm = this;
        vm.fach = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Fach.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
