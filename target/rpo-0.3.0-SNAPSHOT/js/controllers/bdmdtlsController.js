app.controller('bdmCtrl',function($scope,$routeParams,$rootScope,$location,$http, bdmService,reqEditSer,listSkillService,designationService,listqualificationservice,listNoticePeriodService,addModeService,getHiringModeService,$rootScope, $location,$window) {
	
	
    $scope.requirementStartdate = new Date();
  $scope.statusList=[{'status':'Open'},{'status':'Hold'},{'status':'Closed'},]

  var reqObj;
  var paramid = $routeParams.id;
  if(paramid !=undefined){
		$scope.addContact1=$rootScope.SPOCName;
		$scope.clientName=$rootScope.clientId;
		var promise=reqEditSer.getEditableData(paramid);
		 
		 promise.then(function(data){
			 $scope.ListReqData=data;
			 reqObj=$scope.ListReqData.data.result;	
		  $scope.clientName=reqObj.client.id;
		   $scope.contact_Name1=reqObj.addContact.contact_Name; 
		})
  }
  
  
    $http.get("rest/location") .then(function(response) {
        $scope.locationList=response.data;
       
 })
	
			$scope.difference=function(totalExperience,relavantExperience){		
				if(relavantExperience>totalExperience){
				$scope.messege="relevant experience can't be greater than total experience";	
				$scope.relavantExperience = null;
			}
				else{
					$scope.messege="";
				}
			}
	
	$scope.open1 = function() {
		
    $scope.popup1.opened = true;
  };
  $scope.open3 = function() {
		
	    $scope.popup3.opened = true;
	  };
  
	$scope.open2 = function() {

	    $scope.popup2.opened = true;
	  };
  $scope.popup1 = {
    opened: false
  };
  $scope.popup2 = {
		    opened: false
		  };
  $scope.popup3 = {
		    opened: false
		  };
  
  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
  $scope.format = $scope.formats[0];
  
  $scope.difference=function(maxExperience,minExperience){		
			if(minExperience>maxExperience){
			$scope.messege="min experience can't be greater than max experience";	
			$scope.maxExperience = null;
		}
			 else{
				$scope.messege="";
			}
		}	
		
		$scope.difference1=function(maxExperience,relavantExperience){		
			if(relavantExperience>maxExperience){
			$scope.messege="relevant experience can't be greater than total experience";	
			$scope.relavantExperience = null;
		}
			else{
				$scope.messege="";
			}
		}
  
  
  
  
//$scope.save0button =true;
    $scope.desigarray = [];
	$scope.skillarray= [];
	$scope.certiarray= [];
	$scope.qualiarray= [];
	$scope.locationarray= [];
	$scope.interviewmodearray=[];
	$scope.example16settings = {styleActive: true, enableSearch: true, showSelectAll: true, keyboardControls: true ,showEnableSearchButton: true, scrollableHeight: '300px', scrollable: true}; 
	  
	  
	  //$scope.example1model = {selectionLimit: 5}
	   
	//start date and end date validation
	$scope.checkErr = function(requirementEndDate) {
	var requirementStartdate = new Date();
	console.log("startdate::::::::::"+(new Date(requirementEndDate) - new Date(requirementStartdate)));
    $scope.errMessage = '';
    // var curDate = new Date();

    if((new Date(requirementStartdate)-new Date(requirementEndDate))> 86400000)
	{
      $scope.errMessage = 'End Date should be greater than start date';
	  $scope.requirementEndDate = null;
    }
	else if(new Date(requirementEndDate) == new Date(requirementStartdate)){
    //  $scope.errMessage = 'End Date should be greater than start date';
alert("hg");
    }
};
//end validation

	
	 var promiseq = listNoticePeriodService.getnoticeperiod();
	 promiseq.then(function(response){

 	 		$scope.noticePeriods=response.data.result;
 	 		console.log($scope.noticePeriods);
 	 	});
     
 	$scope.getRequirement = function(id) {
 		console.log(id);
 		console.log("inside getrequirement function");	
 		 var promise=bdmService.clientcontactm(id);
 	     
 	     promise.then(function(response){
 	 		console.log("hiringmode dataget");
 	 		$scope.chiru=response.data.result;
 	 		console.log($scope.chiru);
 	 	});
 	
 	}
	$scope.getActiveContacts = function(id) {
 		console.log(id);
 		console.log("status active");		 		
 		 var promise=bdmService.clientcontactactive(id); 	     
 	     promise.then(function(response){
 	 		console.log("status");
 	 		$scope.chiru=response.data.result;
 	 		console.log($scope.chiru);
 	 	});
 	
 	}
 	
 	$scope.getRequirementContact = function(addContact1) {
 		console.log(addContact1);		

 	 		$scope.val=$scope.addContact1;
 	 		console.log($scope.val);
 	 		var jhansi = JSON.parse($scope.val);
 	 		console.log(jhansi);
 	 		$scope.contactPerson=jhansi.contactPerson;
 	 		$scope.email=jhansi.email;
 	 		$scope.mobile=jhansi.mobile;
 	 		$scope.contact=jhansi.id;
 	
 	}
     
  // $scope.billRate="IncludeTax";
  // $scope.newtype="Retrospective";
  // $scope.salaryBand="pm";

  // $scope.permanent="fixed";
 //  $scope.showSlab= $scope.permanent;
   
   //   $scope.slab = [
	 //       {
	        	
	           
	 //       }];
      
      $scope.show1=true;
	  $scope.show2=false;
      $scope.showFun1=function(){
    	  console.log("fun1");
    	  $scope.show1=true;
    	  $scope.show2=false;
    	  console.log("show1"+ $scope.show1);
    	  console.log("show2"+ $scope.show2);
      }
      $scope.showFun2=function(){
    	  console.log("fun2");
    	  $scope.show1=false;
    	  $scope.show2=true;
    	  console.log( "show1"+$scope.show1);
    	  console.log("show2"+ $scope.show1);
      }
     var promise=getHiringModeService.getModeDetails();
     
     promise.then(function(response){
 		console.log("hiringmode dataget");
 		$scope.hiremode=response.data.result;
 		console.log($scope.hiremode);
 	});
     
	$scope.getskillsfun = function(){
	var promise1=listSkillService.getskill();
	promise1.then(function(data){  
		$scope.skillbdm=data.data.result;
		console.log($scope.skillbdm);
		
	});
	}
	$scope.getskillsfun();	
	var promise1=addModeService.getModeData();
	promise1.then(function(data){  
		$scope.interviewbdm=data.data.result;
		console.log($scope.interviewbdm);
		
	});
	 $scope.addNew = function(slab){
		 console.log("hhhhhhhhhhhhhh");
            $scope.slab.push({ 
               
            });
        };
    
        $scope.remove = function(){
            var newDataList=[];
            $scope.selectedAll = false;
            angular.forEach($scope.slab, function(selected){
                if(!selected.selected){
                    newDataList.push(selected);
                }
            }); 
            $scope.s = newDataList;
        };
		$scope.removeRaw = function(slabIndex){
		 console.log("hhhhhhhhhhhhhh");
		 console.log(slabIndex);
         $scope.slab.splice(slabIndex, 1);
        };
	
	
	
	var promise=designationService.getDesignationList();
	
	promise.then(function(data){  
		$scope.desigbdm=data.data.result;
		console.log($scope.desigbdm);
		
	});
//GET Qulaifications

	$scope.getquali = function(){			
	var promise=listqualificationservice.getqualification();
	promise.then(function(data){  
		$scope.qualificbdm=data.data.result;
		console.log($scope.qualificbdm);
		
	});
	}
	$scope.getquali();
//GET Locations

	$scope.getmultipleLocations = function(){	
		var promise=listqualificationservice.getmultiLocations();
		promise.then(function(result){  
			$scope.locationList=result.data;
			console.log($scope.locationList);			
		});
		}
	$scope.getmultipleLocations();
	$scope.getlocatn = function(){
		debugger;
		 $http.get("rest/location").then(function(response) { 
			 $scope.locatnbdm=[];
			
			 angular.forEach(response.data, function (value, index) {
		            $scope.locatnbdm.push({ id: value.id, label: value.locationName });
		            
		        });
			 console.log($scope.locatnbdm);
//			 angular.forEach($scope.locatnbdm, function (value, index) {
//		            $scope.locationarray.push({ id: value.id, label: value.locationName });
//		            console.log($scope.locationarray);
//		        })
			 $scope.myDropdownModel=[];
//			 angular.forEach($scope.myDropdownModel, function (value, index) {
//				 $scope.locationarray.push(value.id);
//		        });
//			 console.log($scope.myDropdownModel);
//			  $scope.myDropdownSettings = {
//			    styleActive: true,
//			    checkBoxes: true,
//			    smartButtonTextProvider(selectionArray) {
//			    	console.log(selectionArray)
//			      if (selectionArray.length === 1) {
//			        return selectionArray[0].locationName;
//			      } else {
//			        return selectionArray.length + ' Selected';
//			      }
//			    }
//			  };
//			  console.log($scope.myDropdownSettings);
		});
		}
	$scope.getlocatn();
	
	
	console.log("hiqualifications");
	
	console.log($scope.qualificbdm);
	
	var promise=bdmService.getCertificateNames();
	promise.then(function(data){  
		$scope.certificatebdm=data.data.result;
		console.log($scope.certificatebdm);
		
	});
		
	var promise=bdmService.getClientNames555();
	promise.then(function(data){  
		console.log("inside the geeting client names from service");
		$scope.clientnamesbdm=data.data.result;
		console.log($scope.clientnamesbdm);
		
	});
	
	//alert($scope.minimumContract!=undefined);
	//alert($scope.minimumContract!=null);
	
	
	$scope.ok=false;
     $scope.bdmreqdtls1=function(reqdtls){
		 $scope.responseMes="Proceesing...";
    	 $scope.bdmSkill=$scope.skill;
		 		var jobstring = $scope.jobLocation;
				$scope.jobLocation = jobstring.toString();
		console.log('joblocation::::::::::::::::',jobstring.toString());
		console.log('joblocation::::::::::::::::',$scope.jobLocation);
		 		 		 
		 //alert($scope.minimumContract==undefined);
    	

     	var temparrccmapping=[];
     	angular.forEach($scope.contact,function(id,value){
     		console.log($scope.contact)
     		temparrccmapping.push({"id":id});
 		 });
     	   	
    	var  temparrslab=[];
    	angular.forEach($scope.slab,function(id,value){
    		temparrslab.push({"id":id});
		 });
  
    	var temparr=[];
    	angular.forEach($scope.bdmSkill,function(id,value){
			 
			temparr.push({"id":id});
		 });
    	
    	//$scope.totalExperience = $scope.maxExperience+"-"+$scope.maxExperience;
		
    	
    	$scope.requirementType="Niche";
    	
    	$scope.id=$window.localStorage.getItem("usid");
    	console.log("user_id"+$scope.id);
		$scope.reqdtls= {     			
			client:{
    				 id:$scope.clientName
    			 },
    			 addContact:{
    				 id:$scope.contact
    			 },
    			 user:{
    				 id:$scope.id
    			 },	
    			 requirementStartdate:$scope.requirementStartdate,
				 requirementEndDate:$scope.requirementEndDate,
    			 nameOfRequirement:$scope.nameOfRequirement,
    			 minimumContract:$scope.minimumContract,			 
    			 requirementType:$scope.requirementType,
    			 skills:$scope. skillarray,
    			 certificates:$scope.certiarray,
    			 designations: $scope.desigarray,
    			 qualifications: $scope.qualiarray,
    			 interviewMode:$scope.interviewmodearray,
    			 jobLocation:$scope.jobLocation,
    			 noticePeriod:$scope.noticePeriod,
    			 typeOfHiring:$scope.typeOfHiring,
    			 salaryBand:$scope.salaryBand,
    			 conversionFee:$scope.conversionFee,
    			 requirementDescription:$scope.requirementDescription,
    			 totalExperience:$scope.totalExperience,
    			 relavantExperience:$scope.relavantExperience,    			
    			 numberOfPositions:$scope.numberOfPositions,
				 //target:$scope.target,
    			 numberOfRounds:$scope.numberOfRounds,
    			 gross_Amount:$scope.gross_Amount,    //// /job des
    			 netPeriod:$scope.netPeriod ,  //budget
				 budgetrate:$scope.budgetrate
				 
				 
    			 
    		
}
		if($scope.typeOfHiring === "permanent"){
			return jobLocation = $scope.locationarray;
		 }else{
			 return jobLocation;
		 }

     console.log($scope.reqdtls);
    	 console.log("bdm going to services"); 
		 console.log();
		  if($scope.minimumContract==undefined || $scope.conversionFee==undefined){
			 $scope.message="this is mandatory field";
			 alert($scope.message);
			return ;
			
		 }
		 else{
			 
			 
			  if($scope.reqdtls==undefined)
					  {
						  
						  $scope.mes1="Please Fill All Details";
						  alert( $scope.mes1);
						  return;
						  
					  }
					                                  
					         $http.post("rest/Bdmrequire", $scope.reqdtls) .then(function(response) {
                              console.log(response);
							  if(response.data.status=="StatusSuccess"){
								$scope.responseMes="Requirement Added Successfully";	
                                 $scope.ok=true;								
							  }
							  else{
								  $scope.responseMes="Internal Server Error"
								  $scope.ok=true;
							  }
						  
                          
                             }); 
			 
			 
			
			 
		 }
    	
    
	
}
  
    
     

      $scope.redirect=function(){
		
		$location.path("/viewBdmreqdtls");
		$window.location.reload();
	     }





///////////////////////////////////////
    $scope.bdmreqdtls21=function(reqdtls){
		
		 $scope.responseMes="Proceesing...";
    	 $scope.bdmSkill=$scope.skill;
    	  		 var jobstring = $scope.jobLocation;
				$scope.jobLocation = jobstring.toString();

    	 
     	var temparrccmapping=[];
     	angular.forEach($scope.contact,function(id,value){
     		console.log($scope.contact)
     		temparrccmapping.push({"id":id});
 		 });
     	
     	
     	
    
    	var  temparrslab=[];
    	angular.forEach($scope.slab,function(id,value){
    		temparrslab.push({"id":id});
		 });
    	
    	//$scope.totalExperience = $scope.maxExperience+"-"+$scope.maxExperience;
 
    	
    	
    	$scope.requirementType="Niche";
    	
    	$scope.id=$window.localStorage.getItem("usid");
    	console.log("user_id"+$scope.id);
$scope.reqdtls= {
		
     			
			client:{
    				 id:$scope.clientName
    			 },
    			 addContact:{
    				 id:$scope.contact
    			 },
    			 user:{
    				 id:$scope.id
    			 },
    			 requirementStartdate:$scope.requirementStartdate,
				 requirementEndDate:$scope.requirementEndDate,
    			 newtype:$scope.newtype,
    			 nameOfRequirement:$scope.nameOfRequirement,
    			 permanent:$scope.permanent,
    			// slab:$scope.slab,
    			 samount:$scope.samount,
    			 famount:$scope.famount,   			 
    			 requirementType:$scope.requirementType,
    			 skills:$scope. skillarray,
    			 certificates:$scope.certiarray,
    			 designations: $scope.desigarray,
    			 qualifications: $scope.qualiarray,
    			 interviewMode:$scope.interviewmodearray,
    			 jobLocation:$scope.jobLocation,
    			 noticePeriod:$scope.noticePeriod,
    			 typeOfHiring:$scope.typeOfHiring,
    			 requirementDescription:$scope.requirementDescription,
    			 totalExperience:$scope.totalExperience,
    			 relavantExperience:$scope.relavantExperience,
    			
    			 numberOfPositions:$scope.numberOfPositions,
				// target:$scope.target,
    			 numberOfRounds:$scope.numberOfRounds,
    			 gross_Amount:$scope.gross_Amount,
    			 netPeriod:$scope.netPeriod,
    			 budgetrate:$scope.budgetrate
    		
}


     console.log($scope.reqdtls);
    	 console.log($scope.slab[0].amount); 
		 
		

		 if($scope.famount==undefined && ($scope.slab[0].amount==undefined || $scope.slab[0].startEnd==undefined || $scope.slab[0].startFrom==undefined)){
			 $scope.message="this is mandatory field";
			 $scope.message1="All fiels in the table is mandatory";
			 alert($scope.message1);
			
			return;
		 }
		
		 else{
			 
			  if($scope.reqdtls==undefined)
					  {
						  
						  $scope.mes1="Please Fill All Details";
						  alert( $scope.mes1);
						  return;
						  
					  }
					                                  
					         $http.post("rest/Bdmrequire", $scope.reqdtls) .then(function(response) {
                              console.log(response);
							  if(response.data.status=="StatusSuccess"){
								$scope.responseMes="Requirement Added Successfully";	
                                 $scope.ok=true;								
							  }
							  else{
								  $scope.responseMes="Internal Server Error"
								  $scope.ok=true;
							  }
						  
                          
                             }); 
			 
			 
			 // bdmService.addBdmdtls($scope.reqdtls);
			 // alert("Requirement Saved Successfully");
			 // $location.path("/viewBdmreqdtls");
		 }
		 
		
    

}

//////////////////////////////////////////////////////////////////////////


  

 $scope.bdmreqdtls=function(){
	$scope.conctactid= JSON.parse($scope.addContact1)
	 $scope.value= {
	startDate: $scope.startDate,
	endDate: $scope.endDate,
	minBudgetRate: $scope.minBudget,
	maxBudgetRate:$scope.maxBudget,
	minBudget:$scope.minBudgetContract,
	maxBudget:$scope.maxBudgetContract,
	
	noticePeriod:$scope.noticePeriod,
	
	typeOfHiring:$scope.typeOfHiring,
	requirementDescription:$scope.requirementDescription,
     permanent:$scope.permanent,
	billRate:$scope.billRate,
	newtype:$scope.newType,
    //amountType:$scope.value,
    relavantExperience:$scope.relavantExperience,	
	famount:$scope.famount,
	status:$scope.status,
	location:{id:$scope.locationId},
	requirementType:$scope.requirementType,
	nameOfRequirement:$scope.nameOfRequirement,
	numberOfPositions:$scope.numberOfPositions,
	jobDes:$scope.jobDescription,
	skills:$scope.skillarray,
    designations:$scope.desigarray,
	qualifications:$scope.qualiarray,
	certificates:$scope.certiarray,
	client: {id:$scope.clientName},
	addContact:{id:$scope.conctactid.id}
	//id:$rootScope.id,
	}
	  $http.post("rest/Bdmrequire", $scope.value) .then(function(response) {
          console.log(response);
		  if(response.data.status=="StatusSuccess"){
			alert("requirement added successfully")
		   $location.path("/bdmlead");
		  }
		  else{
			  $scope.responseMes="Internal Server Error"
			  $scope.ok=true;
		  }
	  })
	
	

}
////////////////////////////////////////////////////////////













    $scope.bdmreqdtls3=function(reqdtls){
		
		  $scope.responseMes="Proceesing...";
    	 $scope.bdmSkill=$scope.skill;
    	 		 		var jobstring = $scope.jobLocation;
				$scope.jobLocation = jobstring.toString();
    	
     	var temparrccmapping=[];
     	angular.forEach($scope.contact,function(id,value){
     		console.log($scope.contact)
     		temparrccmapping.push({"id":id});
 		 });
     	
	//$scope.totalExperience = $scope.maxExperience+"-"+$scope.maxExperience;
    	var  temparrslab=[];
    	angular.forEach($scope.slab,function(id,value){
    		temparrslab.push({"id":id});
		 });
    	
 
    	$scope.id=$window.localStorage.getItem("usid");
    	console.log("user_id"+$scope.id);
$scope.reqdtls= {
		
     			
			client:{
    				 id:$scope.clientName
    			 },
    			 addContact:{
    				 id:$scope.contact
    			 },
    			 user:{
    				 id:$scope.id
    			 },
    			 requirementStartdate:$scope.requirementStartdate,
				 requirementEndDate:$scope.requirementEndDate,
    			 nameOfRequirement:$scope.nameOfRequirement,
    			 billRate:$scope.billRate,
    			 tax:$scope.tax, 
				 tax_amount_type:$scope.taxCostType	,			 
    			 requirementType:$scope.requirementType,
    			 skills:$scope. skillarray,
    			 certificates:$scope.certiarray,
    			 designations: $scope.desigarray,
    			 qualifications: $scope.qualiarray,
    			 interviewMode:$scope.interviewmodearray,
    			 jobLocation:$scope.jobLocation,
    			 noticePeriod:$scope.noticePeriod,
    			 typeOfHiring:$scope.typeOfHiring,
    			 requirementDescription:$scope.requirementDescription,
    			 totalExperience:$scope.totalExperience,
    			 relavantExperience:$scope.relavantExperience,	
    			 numberOfPositions:$scope.numberOfPositions,
				 //target:$scope.target,
    			 numberOfRounds:$scope.numberOfRounds,
    			 gross_Amount:$scope.gross_Amount,
    			 netPeriod:$scope.netPeriod,
				 budgetrate:$scope.budgetrate
    			 
    		
}
     console.log($scope.tax==undefined );
	if($scope.tax==undefined ){
			 $scope.message="this is mandatory field";
			 alert($scope.message);
			return;
		 }
		
		 else{
			 //bdmService.addBdmdtls($scope.reqdtls);
			 console.log("bdm going to services");
			 
			  if($scope.reqdtls==undefined)
					  {
						  
						  $scope.mes1="Please Fill All Details";
						  alert( $scope.mes1);
						  return;
						  
					  }
					                                  
					         $http.post("rest/Bdmrequire", $scope.reqdtls) .then(function(response) {
                              console.log(response);
							  if(response.data.status=="StatusSuccess"){
								$scope.responseMes="Requirement Added Successfully";	
                                 $scope.ok=true;								
							  }
							  else{
								  $scope.responseMes="Internal Server Error"
								  $scope.ok=true;
							  }
						  
                          
                             }); 
		 }


    	
    
	
}
	
});
app.filter('unique', function() {
	   return function(collection, keyname) {
	      var output = [], 
	          keys = [];
	      angular.forEach(collection, function(item) {
	          var key = item[keyname];
	          if(keys.indexOf(key) === -1) {
	              keys.push(key); 
	              output.push(item);
	          }
	      });
	      return output;
	   };
	});


