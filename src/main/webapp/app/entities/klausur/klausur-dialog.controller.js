(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('KlausurDialogController', KlausurDialogController);

    KlausurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Klausur', 'Dozent', 'Fach'];

    function KlausurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Klausur, Dozent, Fach) {
        var vm = this;
        vm.klausur = entity;
        vm.dozents = Dozent.query();
        vm.faches = Fach.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('examadminApp:klausurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.klausur.id !== null) {
                Klausur.update(vm.klausur, onSaveSuccess, onSaveError);
            } else {
                Klausur.save(vm.klausur, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
