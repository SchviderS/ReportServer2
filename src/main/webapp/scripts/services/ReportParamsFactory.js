angular.module('reportServer2').factory('ReportParamsResource', function($resource){
    var resource = $resource('rest/reportparams/:ReportParamsId',{ReportParamsId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});