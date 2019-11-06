app.controller('matchRequirementCtrl',function($scope,$rootScope,rpoFactory,$window,$location,$routeParams) {
	
	$scope.getCandidateDetails = function(){
		
		$scope.candidateId = $routeParams.id; 
			//$window.sessionStorage.getItem("candidateid");
		rpoFactory.candidateGetByid($scope.candidateId).then(function(response) {
			$scope.candidateDetails = response.result;
			$scope.education = $scope.candidateDetails.education;
			$scope.certificates = $scope.candidateDetails.certificates;
			$scope.skills = $scope.candidateDetails.skills;
			console.log($scope.candidateDetails);
		})
	}
	$scope.mapCandidateToReq = function () {
		
		$scope.reqId = $scope.SelectedReqId;
		var reqObj = {
		"bdmReq":{
			"id":$scope.reqId
		},
		"candidate":{
			"id":$routeParams.id
		}
		}
		rpoFactory.candidateMapping(reqObj).then(function(response) {
			$scope.candidateMappingss = response;
			if(response.status == "StatusSuccess"){
				$location.path("/CandidateList1");
			}
		})
	}
	
	$scope.reqDetails=function(x){
		$scope.ReqDetails=x;
	}
	
	$scope.getRequirementDetails = function(){
		
		$scope.userId = $window.localStorage.getItem("usid");
		$scope.status = "Open";
		rpoFactory.requirementById($scope.userId,$scope.status).then(function(res) {
			$scope.requirementDetails = res.result;
			/*$scope.Rskills = $scope.requirementDetails.skills;
			alert($scope.Rskills)*/
			//$scope.length = $scope.requirementDetails;
			/*$scope.education = $scope.candidateDetails.education;
			$scope.certificates = $scope.candidateDetails.certificates;
			$scope.skills = $scope.candidateDetails.skills;*/
			console.log($scope.requirementDetails);
			
		});
	}
	

});