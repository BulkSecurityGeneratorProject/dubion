(function() {
    'use strict';
    angular
        .module('dubionApp')
        .factory('MainSearch', MainSearch);

    MainSearch.$inject = ['$resource'];

    function MainSearch ($resource) {
        var resourceUrl =  'api/artists/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'queryAlbumByName': { method: 'GET', isArray: true, url: 'api/albums/search/:name'},
            'queryArtistByName': {method: 'GET', isArray: true, url: 'api/artist/search/:name'},
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
