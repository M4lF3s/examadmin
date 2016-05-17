(function() {
    'use strict';

    angular
        .module('examadminApp')
        .controller('DozentDialogController', DozentDialogController);

    DozentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Dozent', 'Klausur', 'Fach'];

    function DozentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Dozent, Klausur, Fach) {
        var vm = this;
        vm.dozent = entity;
        vm.klausurs = Klausur.query();
        vm.faches = Fach.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('examadminApp:dozentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.dozent.id !== null) {
                Dozent.update(vm.dozent, onSaveSuccess, onSaveError);
            } else {
                Dozent.save(vm.dozent, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
