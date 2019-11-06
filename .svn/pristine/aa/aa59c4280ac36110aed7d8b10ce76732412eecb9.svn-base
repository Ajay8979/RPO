app.controller('matchCandidateCtrl',function($scope,$rootScope,rpoFactory,$window,$routeParams,$location) {
	
$scope.getReqByReqId = function(){
		var reqid = $routeParams.id;
		rpoFactory.reqGetByiD(reqid).then(function(res) {
			$scope.reqDetails = res.result;
			$scope.skills = $scope.reqDetails.skills;
			$scope.qualifications = $scope.reqDetails.qualifications;
			$scope.designations = $scope.reqDetails.designations;
		})
}
$scope.getCandidatesByRole = function(){
	$scope.userId = $window.localStorage.getItem("usid");
	$scope.role = $window.localStorage.getItem("role");
	$scope.status = undefined;
	rpoFactory.candidatesByRole($scope.role,$scope.status,$scope.userId).then(function(res) {
		$scope.candidateData = res.result;
		})
	}
$scope.canDetails=function(canDetails){
	$scope.datails=canDetails;
	
}
$scope.mapToRequirement =function(candidateDetails) {
	var reqObj = {
	"bdmReq":{
		"id":$routeParams.id
	},
	"candidate":{
		"id":candidateDetails.id
	}
	}
//	if(candidateDetails.candidateStatus == "Created" || candidateDetails.candidateStatus == "Hold" || candidateDetails.candidateStatus == "Rejected"){
	rpoFactory.candidateMapping(reqObj).then(function(response) {
		$scope.candidateReqMap = response;
		if(response.status == "StatusSuccess"){
			$location.path("/CandidateList1");
		}
	})	
}
});