app.controller("assignListCtrl", function($scope, $window, assignListwrkSer,$timeout,
		updateAssignService, clientService, bdmService, userlistServices,$http,
		bdmReqService,deleteAssignService) {

	console.log("myctrl");
	$scope.example16settings = {styleActive: true, enableSearch: true, showSelectAll: true, keyboardControls: true ,showEnableSearchButton: true, scrollableHeight: '300px', scrollable: true}; 

	//$scope.example17settings = {styleActive: true, enableSearch: true, showSelectAll: true, keyboardControls: true ,showEnableSearchButton: true, scrollableHeight: '300px', scrollable: true}; 

	$scope.role=$window.localStorage.getItem("role");
	$scope.loginId =$window.localStorage.getItem("usid");
	
	$scope.req = [];
	
	
	$scope.pageSize='10';
	
	$scope.selected = {};
	//$scope.record = assignListwrkSer.query();
	
	var promise = userlistServices.getUserList();
		
	promise.then(function(data) {
		$scope.users = data.data.result;
		console.log($scope.users);
		$scope.recruiters = [];
		angular.forEach($scope.users, function(key, values) {
			console.log(key.role);
			if (key.role === "recruiter") {
				$scope.recruiters.push(key);
				console.log($scope.recruiters);
			}
			console.log($scope.recruiters);
		})
	})
	
	
	
	
	
	
	var promise = assignListwrkSer.assignfunservice();
	promise.then(function(response) {
		debugger;
		$scope.records = response.data;
		$scope.record = $scope.records.result;
		console.log($scope.record.date);
		console.log($scope.record);
	});
	


	$scope.pushassign = function(list) {
		 var fun1=function() {
		var promise = clientService.getCa();

	promise.then(function(data) {
		$scope.client = data.data.result;
		console.log($scope.client);
	});

		
	}
	fun1();
	$timeout(fun1);
	
		$scope.requirements = bdmService.getClientNames();
		
		
		
		
		console.log($scope.requirements);
		
		$scope.getRequirement = function(id) {
			console.log(id);
			var promise = bdmReqService.getClientRequirement(id);
			promise.then(function(data) {
				console.log(data);
				$scope.requirement = data;
			});
		}

		

		console.log(list);
		$scope.selected = list;
		sessionStorage.setItem('list', JSON.stringify($scope.selected));
		$scope.selectedList = sessionStorage.getItem('list');
		
		console.log($scope.selectedList);
		$scope.data = JSON.parse($scope.selectedList);
		
		console.log($scope.data);
		
		
		
		
		var editRecValue=$scope.data;
		var userarr=editRecValue.users;
		console.log(userarr);
		var userarr1=[];
		
		angular.forEach(editRecValue.users, function(key) {
			console.log(key.id);
			userarr1.push({
				"id" : key.id
			});
		})
		

		
		$scope.userarray=userarr1;
		console.log($scope.userarray);
		
		//$scope.recruiter = $scope.data.users.firstName;

	}

	
	$scope.ok=false;
	$scope.editAssignments = function() {
		$scope.responseMes="Proceesing...";
		console.log($scope.recruiter);
		var usersList = [];
		console.log($scope.recruiter);
		
		console.log(usersList);

		updateList = {
			bdmReq : {
				"id" : $scope.data.bdmReq.id
			},
			client : {
				"id" :  $scope.data.client.id
			},
			users : $scope.userarray

		};
		
		  if(updateList==undefined)
					  {
						  
						  $scope.mes1="Please Fill All Details";
						  alert( $scope.mes1);
						  return;
						  
					  }                            
	  $http.post('rest/allocaterequirment/'+$scope.data.id,updateList) .then(function(response) {
            console.log(response);
		if(response.data.status=="StatusSuccess"){
	           $scope.responseMes="Updated Successfully";	
                $scope.ok=true;								
					 }
					 else{
							 $scope.responseMes="Internal Server Error"
							 $scope.ok=true;
						 }
                          
                        }); 

		
		
		// console.log(updateList);
		// updateAssignService.editAssignments($scope.data.id, updateList);
	}
	$scope.deleteAssignments=function(){
		debugger;
		  $http.delete('rest/allocaterequirment/delete/'+$scope.data.id) .then(function(response) {
	            console.log(response);
			if(response.data.status=="StatusSuccess"){
		           $scope.responseMes="Deleted Successfully";	
		           alert( $scope.responseMes)
	                $scope.ok=true;								
						 }
						 else{
								 $scope.responseMes="Internal Server Error"
								 $scope.ok=true;
							 }	                          
	                        }
		  ); 
		}

	$scope.viewInfo = function(list) {

		$scope.allReqData = list;
		console.log($scope.allReqData);
	}

});