app.controller('candidatelistController',function($scope,listofCandidateservice,$location,$window) {
   
    debugger;
//    console.log("candidate List Controller");
//	$scope.clickedUser={};
//    $scope.getcandidatelist = ;
//   
//    console.log($scope.candidatelist);
//    $scope.roleName= $scope.rolelists.roleName;
//    
//    $scope.id;
//    $scope.pushrole=function(roles){
//    	$scope.clickedUser=roles;
//    }
	
	
//    $scope.editSkill12=function(){
//    	console.log($scope.clickedUser);
//    	editrole={
//    			roleName:$scope.clickedUser.roleName
//    	}
//    
//    console.log($scope.clickedUser.id);
//    	editroleService.editrole($scope.clickedUser.id,editrole);
//   
//    }

//var CandidateList=function(){
	debugger;
//	$scope.logid = $window.localStorage.usid;
	//	$scope.id = $window.localStorage.getItem("usid");
//	console.log($scope.logid);
	var promise=null;
	var promise = listofCandidateservice.getCandidateList();
	promise.then(function(response) {
		debugger;
//			var len = response.data.result.length;
////				$scope.candidatelist =response.data.data.result;
//				for(var i=0;i>len;i++){
////					$scope.candidatelist=response.data.result[i];
//					console.log(response.data.result[i]);
//				}
		$scope.candidatelist = response.data.result;
		
			});
	$scope.updatedata = function(data){
		console.log(data);
		var reqId = data;
		$location.path("/matchData/"+reqId);
	}
	
});






