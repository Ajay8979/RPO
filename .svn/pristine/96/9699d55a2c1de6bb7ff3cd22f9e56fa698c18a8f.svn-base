app.controller('listNoticePeriodController', function($scope,$window,
		listNoticePeriodService, editNoticePeriodService) {
	$scope.noticePeriodNew = {};
	$scope.clickedUser = {};
	//$scope.noticePeriods = listNoticePeriodService.query();
	
	var notice=function(){
	var promise = listNoticePeriodService.getnoticeperiod();
	promise.then(function(response) {
				console.log(response.data);
				$scope.noticePeriods = response.data.result;
			});
	}
	notice();
	notice();
			
			
			
			
			

	//$scope.noticePeriod = $scope.noticePeriods.noticePeriod;

	$scope.id;
	$scope.pushnoticePeriod = function(items) {
		$scope.clickedUser = items;
	}

	$scope.editNoticePeriod = function() {
		var editNP = {
			noticePeriod : $scope.clickedUser.noticePeriod

		}

		editNoticePeriodService.editNP($scope.clickedUser.id, editNP);
		
		// $window.location.reload();
		notice();

	}

});
