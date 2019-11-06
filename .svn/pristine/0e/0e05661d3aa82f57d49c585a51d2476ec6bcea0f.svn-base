app.controller('locationListController',function($scope,locationService,$location,listqualificationservice) {
     console.log("locationListController");
    /* $scope.addLocation=function(jobLocation){
        console.log(jobLocation);
        console.log("Locations"); 
        locationService.addLocation(jobLocation);		
		$location.path('/joblocations');
	}
     */
 	$scope.locationlist=function() {
		debugger;
		var promise = listqualificationservice.getmultiLocations();

	promise.then(function(response) {
		
		$scope.locationsList=response.data;
		
//		
//		for(i =0 ; i<result.length; i++){
//		
//			$scope.nameOfRound=data.data.result[i].nameOfRound;
//			console.log("mmmmmmmmmmmmmmmmmmmmmmmmmm",$scope.nameOfRound);
//		}
		//console.log(data.data.result($index),"ccccccccccccc");
//		
		console.log("Location Data",data);
	});

		
	}

  });