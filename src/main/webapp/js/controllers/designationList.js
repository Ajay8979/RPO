app.controller("designationListController",function($scope,$location,designationService,designService,$timeout,$window){

$scope.clickedDesignation={};
var promise=designationService.getDesignationList();
	    	promise.then(function(response){
	    		console.log(response.data);
	    		$scope.existingDetails=response.data.result;
	    		
	    		
	    		console.log($scope.existingDetails);
	    	});

var fun1=function(){
	     	var promise=designationService.getDesignationList();
	    	promise.then(function(response){
	    		console.log(response.data);
	    		$scope.existingDetails=response.data.result;
	    		
	    		
	    		console.log($scope.existingDetails);
	    	});
	     	
	  }
	
	fun1();
	$timeout(fun1);









console.log($scope.existingDetails);
//$scope.updateddesignation1=$scope.existingDetails.designation;


$scope.editDesignation=function(exist){
	$scope.clickedDesignation=exist;
}

$scope.updateDesignation=function(){
console.log($scope.clickedDesignation);
	var val={
			designation:$scope.clickedDesignation.designation
	}
	console.log($scope.clickedDesignation.id);
	designService.editdesignation(val,$scope.clickedDesignation.id);
	console.log("ggggggg");
	//$window.location.reload();
     fun1();	
}

});









