

angular.module('reportServer2').controller('EditReportParamsController', function($scope, $routeParams, $location, ReportParamsResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.reportParams = new ReportParamsResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/ReportParams");
        };
        ReportParamsResource.get({ReportParamsId:$routeParams.ReportParamsId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.reportParams);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.reportParams.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/ReportParams");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/ReportParams");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.reportParams.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});