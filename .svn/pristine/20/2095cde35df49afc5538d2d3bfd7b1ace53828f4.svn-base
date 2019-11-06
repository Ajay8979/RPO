app.controller('CustomerTypeCtrl', function($scope, CustomerTypeService,$timeout, $window,
		listCustomerTypeServices, updateCustomerTypeService) {

	$scope.addCustomerType = function(customerTypeDetails) {
		var customerTypeDetails = {
			customerType : $scope.customerType,
			dropdown1 : $scope.dropdown1,
			amount : $scope.amount,
			dropdown2 : $scope.dropdown2,
			commissionRate : $scope.commissionRate
		}

		CustomerTypeService.addCustomerTypeData(customerTypeDetails);
		//$location.path('/ListOfCustomerType');

	}

	
	
	
	
	//$scope.CustomerTypelist = listCustomerTypeServices.query();
	
	
		var fun1=function(){
	     	var promise=listCustomerTypeServices.getCustomerTypelist();
	    	promise.then(function(response){
	    		console.log(response.data);
	    		$scope.CustomerTypelist=response.data.result;
	    		
	    		
	    		console.log($scope.CustomerTypelist);
	    	});
	     	
	 }
	
	fun1();
	$timeout(fun1);   
	
	
	
	
	
	
	
	
	
	$scope.newCustomerTypelist = {};
	$scope.customerTypeDetails = function(list) {
		$scope.newCustomerTypelist = list;
	}

	$scope.updateCustomerTypelist = function() {
		var updateList = {
			
			customerType : $scope.newCustomerTypelist.customerType,
			dropdown1 : $scope.newCustomerTypelist.dropdown1,
			amount : $scope.newCustomerTypelist.amount,
			dropdown2 : $scope.newCustomerTypelist.dropdown2,
			commissionRate : $scope.newCustomerTypelist.commissionRate

		};
		updateCustomerTypeService.updateCustomerTypelist(
				$scope.newCustomerTypelist.id, updateList);
                //$window.location.reload();	
				fun1();
	}

});











app.directive('validNumber', function() {
    return {
      require: '?ngModel',
      link: function(scope, element, attrs, ngModelCtrl) {
        if(!ngModelCtrl) {
          return; 
        }

        ngModelCtrl.$parsers.push(function(val) {
          if (angular.isUndefined(val)) {
              var val = '';
          }
          
          var clean = val.replace(/[^-0-9\.]/g, '');
          var negativeCheck = clean.split('-');
			var decimalCheck = clean.split('.');
          if(!angular.isUndefined(negativeCheck[1])) {
              negativeCheck[1] = negativeCheck[1].slice(0, negativeCheck[1].length);
              clean =negativeCheck[0] + '-' + negativeCheck[1];
              if(negativeCheck[0].length > 0) {
              	clean =negativeCheck[0];
              }
              
          }
            
          if(!angular.isUndefined(decimalCheck[1])) {
              decimalCheck[1] = decimalCheck[1].slice(0,2);
              clean =decimalCheck[0] + '.' + decimalCheck[1];
          }

          if (val !== clean) {
            ngModelCtrl.$setViewValue(clean);
            ngModelCtrl.$render();
          }
          return clean;
        });

        element.bind('keypress', function(event) {
          if(event.keyCode === 32) {
            event.preventDefault();
          }
        });
      }
    };
  });