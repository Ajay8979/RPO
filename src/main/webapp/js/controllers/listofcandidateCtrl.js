app.controller('candidatelistController',function($scope,listofCandidateservice,$location,$window,$timeout,$http,$q) {
   
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
  //pagination
    $scope.pageSize=20;
 	$scope.selected = {};
	  $scope.maxSize = 2;  
// Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.	     
	  /*  $scope.searchtext= $scope.searchtext;
		$scope.searchcategory = $scope.searchcategory;		*/

    $scope.candidateList=function(){
	debugger;
//	$scope.logid = $window.localStorage.usid;
	//	$scope.id = $window.localStorage.getItem("usid");
//	console.log($scope.logid);
//	var promise=null;
	$scope.userId = $window.localStorage.getItem("usid");
	$scope.role = $window.localStorage.getItem("role");
	var promise = listofCandidateservice.getCandidateList($scope.userId,$scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory);
	promise.then(function(response) {
		debugger;
//			var len = response.data.result.length;
////				$scope.candidatelist =response.data.data.result;
//				for(var i=0;i>len;i++){
////					$scope.candidatelist=response.data.result[i];
//					console.log(response.data.result[i]);
//				}
		$scope.candidatelist = response.data.result;
        $scope.totalCount = response.data.totalRecords; 
        $scope.noData = response.data.res;  
	/*	$scope.candidateName= candidatelist.firstName+""+candidatelist.lastName*/
			});
	$scope.pageChanged = function() {
		 $scope.candidateList(); 
		 console.log('Page changed to: ' + $scope.pageIndex);
		  };
    }
//    $scope.candidateList(); 
	 $scope.searchText = function(){
		 debugger;
		 $scope.candidateList();   
	 }
		$scope.clearText=function(){
			$scope.by="",
			$scope.order="",
			$scope.searchtext="",
			$scope.searchcategory="",
			$scope.candidateList(); 
		}
		$timeout(function () {
	            $scope.candidateList();   
	          }, 1000);
		$timeout(function () {
	             $scope.candidateList();   
	         }, 3000);
	  
		
	$scope.updatedata = function(data){
		console.log(data);
		var reqId = data;
		$location.path("/matchData/"+reqId);
	}
	 $scope.pageSelected=function(id){
		 for(var i=0;i<=$scope.options.length;i++){
			 if($scope.options[i].id==id){
				  $scope.pageSize=$scope.options[i].pageSize;
			 }
		 }
	 }
	
	 $scope.viewInfo = function(candidateId) {
			debugger;
			    $http.get("rest/addCandidate/"+candidateId).then(function(response){
			    	$scope.candidatelist1 =  response.data.result;
		       	});
		}
	 
});






