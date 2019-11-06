app.controller('listSkillControllers',function($scope, listSkillService,editSkillService,$location,$timeout,$window) {
    console.log("list skills Controller");
	$scope.skillNew={};
	$scope.clickedUser={};
	var promise = null;
    var promise = listSkillService.getskill();
		promise.then(function(response){
	    		console.log(response.data,"ggggggggggggggggggggggggggggggggggggg");
	    		$scope.skillNames=response.data.result;
	    		
	    		
	    		console.log($scope.skillNames);
	    	});
	
	var skillfun=function(){
		

		var promise = listSkillService.getskill();
		promise.then(function(response){
	    		console.log(response.data);
	    		$scope.skillNames=response.data.result;
	    		
	    		//$window.location.reload();
	    		console.log($scope.skillNames);
	    	});
		
		//skillfun();
	}
	

   
    console.log($scope.skillNames);
    $scope.skillName= $scope.skillNames;
    
    $scope.id;
    $scope.pushSkill=function(skills){
    	$scope.clickedUser=skills;
//    	$window.location.reload();
    	
    }

	
	
    $scope.editSkill12=function(){
    	promise;
    	console.log($scope.clickedUser);
    	editSkill={
    			skillName:$scope.clickedUser.skillName,
				flag:$scope.clickedUser.flag
    			
    	}
    console.log(editSkill);
    console.log($scope.clickedUser.id);
    editSkillService.editSkill($scope.clickedUser.id,editSkill);
    			
	skillfun();
	//editSkill12();
	//$window.location.reload();
	//$location.path('/listSkill')
    }
    $scope.changeStatus=function(id,skill){
    	  editSkillService.statusChange(id,skill);
    }
});
	
