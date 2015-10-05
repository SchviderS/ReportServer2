'use strict';

angular.module('reportServer2',['ngRoute','ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/ReportParams',{templateUrl:'views/ReportParams/search.html',controller:'SearchReportParamsController'})
      .when('/ReportParams/new',{templateUrl:'views/ReportParams/detail.html',controller:'NewReportParamsController'})
      .when('/ReportParams/edit/:ReportParamsId',{templateUrl:'views/ReportParams/detail.html',controller:'EditReportParamsController'})
      .otherwise({
        redirectTo: '/'
      });
  }])
  .controller('LandingPageController', function LandingPageController() {
  })
  .controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
  });
