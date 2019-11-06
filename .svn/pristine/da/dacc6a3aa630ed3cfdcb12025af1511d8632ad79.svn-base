app.controller("incentivesCtrl",function($scope,$window,$timeout,$http,$q,userlistServices,incentivesService){	
	$scope.getRecruiters = function() {
debugger;
		var promise = userlistServices.getUserList();
		promise.then(function(data) {
			$scope.users = data.data.result;
			console.log($scope.users);
			$scope.recruiters = [];			

			angular.forEach($scope.users, function(key, values) {
				if (key.role === "recruiter") {
					$scope.recruiters.push(key);
					console.log($scope.recruiters);
				}
				console.log($scope.recruiters);
/*				console.log($scope.recruiters)
*/				
			})
		});
	}
	$scope.getRecruiters();
	
/*	  $scope.getfun11 = function()
	  {
		  var rr = $window.localStorage.getItem("role");
		  if(rr=="BDM")
		  {
			$scope.homeB=true;  
			$scope.homeA=false;
		  }
		  else if(rr="bdmlead")
		  {
			  $scope.homeB=false;  
			$scope.homeA=true;
		  }
		  
	  }*/
/*	 $scope.getfun11();*/
	  
	/*  $timeout(function () {
            $scope.getfun11();   
          }, 1000);
*/
	 
	//pagination
		$scope.pageSize=10;
	 	$scope.selected = {};
	 	$scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.	     
	    
	 $scope.getfun1 = function(){
		 debugger;
		  var promise= incentivesService.incentivesData($scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory);
	 
	      promise.then(function(response){
	    // console.log("varu99999999naaaaaa");
		console.log(response);
	    $scope.incentivesData=response.data.result;
	     $scope.totalCount = response.data.totalRecords;  
	     vData=$scope.ListReqData;
		 $scope.nodata=response.data.res;		
		console.log($scope.incentivesData);		 
	 }); 
	 }
	 $scope.getfun1();
//	 $scope.sortorderad = function(order){
//		 debugger;
//		 $scope.getfun1();   
//	 }
	/* $scope.sortby = function(by){
		 $scope.getfun1();   
	 }
	 $scope.searchBy = function(searchcategory){
		 debugger;
		 $scope.getfun1();   
	 }*/
	 $scope.searchText = function(){
		 $scope.getfun1();   
	 }
	$scope.clearText=function(){
		$scope.by="",
		$scope.order="",
		$scope.searchtext="",
		$scope.searchcategory=""
		$scope.getfun1(); 
		}
//	 $scope.search = function(searchitem){
//		 $scope.getfun1();   
//	 }
	/* $scope.getfun1(); */
//	 $scope.search=function(searchitem) {
//			
//			debugger;
//			$scope.searchtext=searchitem.searchtext;
//			$scope.searchcategory=searchitem.searchcategory;
//			$scope.getrole = $window.localStorage.getItem("role");
//			$scope.getuserid = $window.localStorage.getItem("usid");
//			$http.get("rest/Bdmrequire/searchRequirements/"+$scope.getrole+"/"+$scope.getuserid+"/"+$scope.searchtext+"/"+$scope.searchcategory+"/"+$scope.pageIndex+"/"+$scope.pageSizeSelected).success(function(response){
//				 $scope.totalCount = response.totalRecords;  
//				 $scope.ListReqData=response.result;
//				 $scope.nodata=response.res;				 
//				 console.log("eee",$scope.ListReqData);
//			});			
//		}
	
	  
	 $timeout(function () {
            $scope.getfun1();   
          }, 1000);
	 $timeout(function () {
             $scope.getfun1();   
         }, 3000);
  
	   $scope.pageChanged = function() { 
	/*	          alert('Page changed to: ' + $scope.pageIndex);
		          alert('Page changed to: ' + $scope.pageSizeSelected);*/
				   $scope.getfun1();
		  };
	 
	 
	 
		  /*   $scope.showSkills=function(id){
	        console.log(id);	
		    console.log(vData[id].skills);		
	    	$scope.skillsData=vData[id].skills;						
			$scope.popUPData=vData[id];
			console.log($scope.popUPData);
			
			   $scope.c2h=false;
			   $scope.permanent=false;
			    $scope.contract=false;						
			   if($scope.popUPData.typeOfHiring==="Contract to Hire")
	           {
		           $scope.c2h=true;
				   $scope.permanent1=false;
				   $scope.permanent2=false;
				   $scope.contract=false;
	           }
			    if($scope.popUPData.typeOfHiring==="permanent")
	           {
		           $scope.c2h=false;
				   $scope.contract=false;
				  
					
					 if($scope.popUPData.permanent==="fixed")
					 {
						$scope.permanentAlias="Percentage";
                         $scope.permanent1=true;
                         $scope.permanent2=false;						 
					 }
					 if($scope.popUPData.permanent==="slab")
					 {
						 $scope.permanentAlias="Slab";
                         $scope.permanent2=true;
                         $scope.permanent1=false;						 
					 }
				   
	           }
			   
			   
			   
			   
			   if($scope.popUPData.typeOfHiring==="contract")
	           {
		           $scope.c2h=false;
				   $scope.permanent=false;
				   $scope.contract=true;
	           }
			   
	    }
	  */
	 
	 /* $scope.showDesignation=function(id){
		    
	 
	        console.log(id);
			
		    console.log(vData[id].designations);
		
	    	$scope.designationsData=vData[id].designations;
	    }*/
		
		
//		 $scope.showQualifications=function(id){
//		    
//	 
//	        console.log(id);
//			
//		    console.log(vData[id].qualifications);
//		
//	    	$scope.qualificationsData=vData[id].qualifications;
//	    }
//		
		
		/*$scope.editFun=function(recruiterId){
			debugger;
			$scope.urlForEdit="#!/editBdmreqdtls/"+list.id;
			$scope.getfun1();
		}*/
		  $scope.pushassign = function(list) {
			  debugger;
			  $scope.recruiterId =list.id;
			/*	 var fun1=function() {
				var promise = clientService.getCa();

			promise.then(function(data) {
				$scope.client = data.data.result;
				console.log($scope.client);
			});				
			}*/
				 }
		$scope.calculateInc=function(){
			debugger;
			var forall='no';
		    $http.get("rest/incentive/"+$scope.recruiterId+'/'+forall).then(function(response){
		    	$scope.incentivelist1 =  response.data.result;
		    	$scope.status =  response.data.status;
		    	console.log($scope.incentivelist1);
		    	if ($scope.status=="StatusSuccess"){
		    	alert('response Success')
		    	}
		    	$scope.getRecruiters();
				$window.location.reload();
				$scope.getRecruiters();
	       	});
		}
		$scope.calcAll=function(){
			debugger;
			var id= undefined;
			var forall='yes';
		    $http.get("rest/incentive/"+id+'/'+forall).then(function(response){
		    	$scope.incentivelistforall =  response.data.result;
		    	$scope.status =  response.data.status;
		    	console.log($scope.incentivelistforall)
	       	});
		}
		
	/*	var promise = userlistServices.getUserList();
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
	});*/
	
     //$scope.recarray=[];
	 
	 
	$scope.example16settings = {styleActive: true, enableSearch: true, showSelectAll: true, keyboardControls: true ,showEnableSearchButton: true, scrollableHeight: '300px', scrollable: true}; 
	
	
	$scope.ok=false;
	$scope.Assignwrk = function(list) {
	$scope.responseMes="Proceesing...";
	
	console.log(list.client.id);
		var assignwrk = {
			
			
			"client" : {
				"id" : list.client.id
			},
			"bdmReq" : {
				"id" : list.id
			},
			 //"users" :list.recrutier
			"users" :$scope.userarray
		}
		
		console.log(assignwrk);
		  if(assignwrk==undefined)
					  {
						  
						  $scope.mes1="Please Fill All Details";
						  alert( $scope.mes1);
						  return;
						  
					  }
					                                  
					         $http.post('rest/allocaterequirment',assignwrk) .then(function(response) {
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
		
		// console.log(assignwrk);
		// assignService.addWrk(assignwrk);
	}	
	
	
		
		
		
	var getRecrutierdetails = function() {
		debugger;
	var roleName="RECRUITER";
	
	 $http.get('rest/Reg/assign/'+roleName) .then(function(response) {
                   
		 $scope.recrutier1 = response.data;
			 console.log($scope.recrutier1);
	 var userarr1=[];
		
		
 angular.forEach($scope.recrutier1, function(key) {
			console.log(key.id);
			 userarr1.push({
			"id" : key.id
		 });
 })
		

 $scope.userarray=userarr1;
	});
	 }
	
	 getRecrutierdetails();
	 $timeout(getRecrutierdetails);
	
	
	/* $scope.getdetails = function(id){
		   var promise=AMServices.getData(id);
		  
	        promise.then(function(data){
	  console.log("hello jyothi");
		        $scope.list=data.data;
		 console.log($scope.list);
	          });
	      }*/
		  //getfun1();
		  
		  // $timeout(function ()
        // {
		  // getfun1();
        // }, 2000);
		
		
	/*	$scope.assignFun = function(list){
			debugger;
		 $scope.clientname =list.clientName;
		 $scope.client_id =list.clientId;
		 $scope.requirementName=list.nameOfRequirement;
         $scope.requirement_id=list.id;	   
	 
	 
	 $window.localStorage.setItem("reqclientname",$scope.clientname);
	 $window.localStorage.setItem("reqclient_id",$scope.client_id);
	 $window.localStorage.setItem("reqrequirementName",$scope.requirementName);
	 $window.localStorage.setItem("reqrequirement_id",$scope.requirement_id);
	 }*/
	
}) 



