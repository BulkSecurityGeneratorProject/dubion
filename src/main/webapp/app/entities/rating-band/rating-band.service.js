(function() {
    'use strict';
    angular
        .module('dubionApp')
        .factory('RatingBand', RatingBand);

    RatingBand.$inject = ['$resource', 'DateUtils'];

    function RatingBand ($resource, DateUtils) {
        var resourceUrl =  'api/rating-bands/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
