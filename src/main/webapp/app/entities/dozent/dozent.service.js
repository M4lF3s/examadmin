(function() {
    'use strict';
    angular
        .module('examadminApp')
        .factory('Dozent', Dozent);

    Dozent.$inject = ['$resource'];

    function Dozent ($resource) {
        var resourceUrl =  'api/dozents/:id';

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
