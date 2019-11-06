app.controller("RecriterDashBoardCtrl",function($scope,RecuriterDashBoard, $location,$timeout) { 

		 var getfun2 = function(){
		  var promise=RecuriterDashBoard.getData1();
	 
	       promise.then(function(data){
	   
		       $scope.list=data.data;
		console.log($scope.list);
	           });
	      }
		 getfun2();
		  $timeout(function ()
        {
          getfun2();
        }, 2000);
})