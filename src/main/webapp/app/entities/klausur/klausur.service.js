(function() {
    'use strict';
    angular
        .module('examadminApp')
        .factory('Klausur', Klausur);

    Klausur.$inject = ['$resource'];

    function Klausur ($resource) {
        var resourceUrl =  'api/klausurs/:id';

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
