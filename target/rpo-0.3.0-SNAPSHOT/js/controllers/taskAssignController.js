app.controller("assignTaskController", function($scope,$rootScope, rpoFactory,assignTaskService, assignTaskService1, $window,rpoFactory,$location,$http) {
	$scope.record = assignTaskService.query();
	
	/*$scope.getTask = function() {
		//id=$rootScope.id;
		var id = $window.localStorage.getItem("usid");

		var promise = assignTaskService1.getTaskAssignedRequirement(id);
		promise.then(function(response) {
			$scope.resultTask = response.data;
			console.log($scope.resultTask);
		});
	}
	$scope.getTask(); */
	$scope.pageSize='10';
	$scope.popdata=function(record1){
		console.log(record1);
		$scope.popdatarecord = record1.bdmReq;
		
	}
	
	
	$scope.addreqcandifun = function(list){
		$scope.reqname = list.bdmReq.nameOfRequirement;
		$scope.reqid = list.bdmReq.id;
		$scope.clientname = list.bdmReq.client.clientName;
		$scope.client_id = list.bdmReq.client.id;
		$window.localStorage.setItem("addreqcandireqname",$scope.reqname);
		$window.localStorage.setItem("addreqcandireqid",$scope.reqid);		
		$window.localStorage.setItem("addreqcandiclientname",$scope.clientname);
		$window.localStorage.setItem("addreqcandiclient_id",$scope.client_id);
	}
	$scope.viewReqInfo = function(x){
		debugger;
		var reqid = x.id;
		rpoFactory.reqGetByiD(reqid).then(function(res) {
			$scope.reqDetails = res.result;
			$scope.skills = $scope.reqDetails.skills;
			$scope.qualifications = $scope.reqDetails.qualifications;
			$scope.designations = $scope.reqDetails.designations;
		})
		console.log($scope.reqDetails);
	}
	$scope.getRequirementDetails = function (){
		$scope.Userid = $window.localStorage.getItem("usid");
		$scope.status = "Open";
		rpoFactory.requirementById($scope.Userid,$scope.status).then(function(response) {
			debugger;
			$scope.reqListDetails = response.result;
		})
	}
	$scope.navigationToCandidate = function(list) {
		console.log(list);
		var reqId = list.id;
		$location.path("/matchCandidate/"+reqId);
	}
	
	/*$scope.pageSize=5;
	  $scope.id=1;
	 $scope.options=[{'id':1,'pageSize':5},{'id':2,'pageSize':10},{'id':3,'pageSize':15},{'id':4,'pageSize':20},{'id':5,'pageSize':25}]

	 $scope.pageSelected=function(id){
		 for(var i=0;i<=$scope.options.length;i++){
			 if($scope.options[i].id==id){
				  $scope.pageSize=$scope.options[i].pageSize;
			 }
		 }
	 }*/
	
	$scope.flag=false;
    $scope.expandSelected=function(details){
      $scope.reqListDetails.forEach(function(val){
        val.expanded=false;
      })
      details.expanded=true;
      
      
   	$http.get('rest/addCandidate/getCandiateListByRequirementId/'+details.id).then(function(response){
   	  $scope.statusres=response.data.status;
		    if($scope.statusres=='StatusSuccess'){
		    	$scope.flag=true;
		    $scope.ListReqData=response.data.result;	
		    }
		    else{
		    	   $scope.ListReqData=[];
		    		$scope.flag=false;
		    }
		
  		});	
  
      
    }
	
});

