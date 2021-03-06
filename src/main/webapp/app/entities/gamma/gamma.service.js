(function() {
    'use strict';
    angular
        .module('dubionApp')
        .factory('Gamma', Gamma);

    Gamma.$inject = ['$resource'];

    function Gamma ($resource) {
        var resourceUrl =  'api/gammas/:id';

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
