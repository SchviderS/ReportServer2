
angular.module('reportServer2').controller('NewReportParamsController', function ($scope, $location, locationParser, ReportParamsResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.reportParams = $scope.reportParams || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/ReportParams/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ReportParamsResource.save($scope.reportParams, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/ReportParams");
    };
});