(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('FachDialogController', FachDialogController);

    FachDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Fach', 'Klausur', 'Dozent'];

    function FachDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Fach, Klausur, Dozent) {
        var vm = this;
        vm.fach = entity;
        vm.klausurs = Klausur.query();
        vm.dozents = Dozent.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('examadminApp:fachUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.fach.id !== null) {
                Fach.update(vm.fach, onSaveSuccess, onSaveError);
            } else {
                Fach.save(vm.fach, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
