(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('DozentDeleteController',DozentDeleteController);

    DozentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Dozent'];

    function DozentDeleteController($uibModalInstance, entity, Dozent) {
        var vm = this;
        vm.dozent = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Dozent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
