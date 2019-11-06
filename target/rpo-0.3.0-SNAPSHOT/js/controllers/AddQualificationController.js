app.controller('AddqualificationController',function($scope,qualificationService,$location) {
     console.log("Addqualification Controller");
     $scope.addqualification=function(qualificationName){
        console.log(qualificationName);
        console.log("roles going to services"); 
        qualificationService.addQualification(qualificationName);
		
		$location.path('/listqualification');
	}
  });