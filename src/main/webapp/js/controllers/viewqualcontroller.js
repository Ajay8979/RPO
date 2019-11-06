app.controller('listqualificationController', function($scope,
		listqualificationservice, editqualiService, $location,$window) {
	$scope.clickedUser = {};
	var fun1=function(){
	var promise = listqualificationservice.getqualification();
	promise.then(function(response){
		console.log(response.data.result);
		$scope.qualificationlists=response.data.result;
    	});
	}
	fun1();
	var promise = listqualificationservice.getqualification();
	promise.then(function(response){
		console.log(response.data.result);
		$scope.qualificationlists=response.data.result;
    	});
	
	//$scope.qualificationlists = listqualificationservice.query();
	console.log($scope.qualificationlists);
	//$scope.qualificationName = $scope.qualificationlists.qualificationName;
	$scope.id;
	$scope.pushqualification = function(qualification) {
		$scope.clickedUser = qualification;
		console.log($scope.clickedUser);
	}

	$scope.editqualification = function() {
	
		editqualifications = {
			qualificationName : $scope.clickedUser.qualificationName
		}
	
		editqualiService.editqualification($scope.clickedUser.id,
				editqualifications);

			//$window.location.reload();	
			fun1();	
	}
	$scope.inactive=function(id){
     	var promise=inactiveQualServices.inactiveStatus(id);
    	promise.then(function(response){
    		console.log(response.data);
    		$scope.clickedUser=response.data;
    		$scope.qualificationlists.push($scope.clickedUser);
    		$scope.qualificationlists.splice(this.id,1);
    		
    		console.log($scope.clickedUser.status);
    	});
     	
  }
});


