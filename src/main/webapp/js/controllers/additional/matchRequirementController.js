app.controller('matchRequirementCtrl',function($scope,$rootScope,rpoFactory,$window,$location,$routeParams,$timeout,$http) {
	
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
		debugger;
		/*$scope.ReqDetails=x;*/
		$http.get("rest/Bdmrequire/"+x.id).then(function(response){
			$scope.ReqDetails=response.data.result;
			   });
	}
	  //pagination
//  $scope.pageSize=20;
	$scope.selected = {};
	  $scope.maxSize = 2;  
//Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.	     
	    $scope.searchtext= $scope.searchtext;
		$scope.searchcategory = $scope.searchcategory;	
		
	$scope.getRequirementDetails = function(){
		debugger;
		$scope.userId = $window.localStorage.getItem("usid");
		$scope.status = "Open";
		rpoFactory.requirementById($scope.userId,$scope.status,$scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory).then(function(res) {
			debugger;
			$scope.requirementDetails = res.result;
			$scope.noData = res.res;
			$scope.totalCount = res.totalRecords;
			/*$scope.Rskills = $scope.requirementDetails.skills;
			alert($scope.Rskills)*/
			//$scope.length = $scope.requirementDetails;
			/*$scope.education = $scope.candidateDetails.education;
			$scope.certificates = $scope.candidateDetails.certificates;
			$scope.skills = $scope.candidateDetails.skills;*/
			console.log($scope.requirementDetails);
			
		});
	}
	$scope.getRequirementDetails();
	 $scope.searchText = function(){
		 debugger;
		 $scope.getRequirementDetails();   
	 }
		$scope.clearText=function(){
			$scope.by="",
			$scope.order="",
			$scope.searchtext="",
			$scope.searchcategory="",
			$scope.getRequirementDetails(); 
		}
		$scope.pageChanged = function() {
			debugger;
			 $scope.getRequirementDetails(); 
			 console.log('Page changed to: ' + $scope.pageIndex);
			  };
		$timeout(function () {
	            $scope.getRequirementDetails();   
	          }, 1000);
		$timeout(function () {
	             $scope.getRequirementDetails();   
	         }, 3000);
	  

});