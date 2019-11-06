app.controller('canreqDetailsCtrl',function($scope,$http,$timeout,$rootScope,rpoFactory,$window,$routeParams,$location) {	
		var rId = $routeParams.rId;
		var cId = $routeParams.cId;
		debugger;
		rpoFactory.canreqGetByiD(rId).then(function(res) {
			debugger;
			$scope.reqDetails = res.result;
			$scope.skills = $scope.reqDetails.skills;
			$scope.qualifications = $scope.reqDetails.qualifications;
			$scope.qualifname =$scope.qualifications[0].qualificationName;
			console.log($scope.qualifname);
			$scope.designations = $scope.reqDetails.designations;
		})
        

	
			rpoFactory.canreqdidatesByRole(cId).then(function(res) {
		      $scope.candidateData = res.result;
				})
				
				
				$scope.viewResume=function(cId){
			 // $scope.url='images/sample.pdf'
				$scope.docUrl='../Resumes/'+$routeParams.cId+'.pdf';
		         }
		
		
		$scope.acceptedAndrejected=function(status){
			if(status=="Accepted By Lead"){
			$http.post('rest/addCandidate/updatingstatusbylead/'+cId+'/'+status).then(function(response){
				$scope.res=response.data;
				if($scope.res.status=='StatusSuccess'){
					$timeout(function () {
						$location.path('/CandidateList1');
					  }, 1000);
					
				}
				});	
			}
			else{
				   $http.post('rest/addCandidate/updatingstatusbylead/'+cId+'/'+status).then(function(response){
					$scope.res=response.data;
					if($scope.res.status=='StatusSuccess'){
						$timeout(function () {
							$location.path('/CandidateList1');
						  }, 1000);
					}
					});	
			}
		}
});