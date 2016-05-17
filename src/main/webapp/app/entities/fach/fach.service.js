(function() {
    'use strict';
    angular
        .module('examadminApp')
        .factory('Fach', Fach);

    Fach.$inject = ['$resource'];

    function Fach ($resource) {
        var resourceUrl =  'api/faches/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
