app.controller('CandidatelistController', function($scope,
		CandidatelistServices, candidateSer, clientService, bdmService,rpoFactory,
		bdmReqService, updateCandidatesrvService,Upload,$rootScope,$location,$window,$routeParams,$timeout,TypeOfProcessService,$rootScope,$http) {
	
	$scope.view=function(list){
		var candId=list.candidateid;
	}
	
	$scope.viewReqInfo = function(list){
		debugger;
		var reqid = list.bdmReqId;
		rpoFactory.reqGetByiD(reqid).then(function(res) {
			$scope.reqDetails = res.result;
			$scope.skills = $scope.reqDetails.skills;
			$scope.qualifications = $scope.reqDetails.qualifications;
			$scope.designations = $scope.reqDetails.designations;
		})
		console.log($scope.reqDetails);
}
		
		$scope.pageSize='10';
		
			$scope.status = "Active";
			 $scope.open1 = function() {
								$scope.popup1.opened = true;
							  };

							  $scope.popup1 = {
								opened: false
							  };
							  
							  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
							  $scope.format = $scope.formats[0];
							  
		$scope.getrole = $window.localStorage.getItem("role");
		$scope.getuserid = $window.localStorage.getItem("usid");
		
			if($scope.getrole=="recruiter"){
			$scope.edithide=false;
			$scope.addcandidate=false;
			}

			else if($scope.getrole=="bdmlead"){
				$scope.edithide=true;
				$scope.addcandidate=true;
			}
			
			
			$scope.redirect = function(){
				if($scope.getrole=="recruiter"){
					$location.path("/recruiter");
				}
				else if($scope.getrole=="bdmlead"){
					$location.path("/bdmlead");
				}
			}
		
		$scope.difference=function(totalExperience,relevantExperience){	
				
				if(relevantExperience>totalExperience){
					
				$scope.messege="relevant experience can't be greater than total experience";	
				$scope.relevantExperience = null;
			}
				else{
					$scope.messege="";
				}
			}
	
		$scope.canReqDetails = function(list) {
			debugger;
			var reqId = list.bdmReqId;
			var canId=list.candidateid;
			$location.path('/canreqDetails/'+reqId+'/'+canId);
			
		}
		
	//$scope.candidatelist = CandidatelistServices.query();
	//console.log($scope.candidatelist); 
	var iddd;
	$scope.funnn=function(id){
		//alert(id);
		iddd=id;
		//alert(iddd);
		
	}
	
	
	var promise = candidateSer.getNoticePeriod();

	promise.then(function(data) {
		$scope.noticePeriodes = data.data.result;
		console.log($scope.noticePeriodes);
		$scope.fun1();

	});
	candidateSer.getNoticePeriod();
	
	
		var promise = candidateSer.getContactType();

	promise.then(function(data) {
		$scope.currentJobTypes = data.data.result;
		console.log($scope.currentJobTypes);
		$scope.fun1();

	});
	candidateSer.getContactType();
	
	
	
	
		$scope.downloadPDF = function (id) {	
    var dlnk = document.getElementById('dwnldLnk'); 
		var promise = candidateSer.getResume(id);
	  promise.then(function(data) 
	  {
		  console.log("Response>>>>>>>>>>>",data.data.result.resume);
		 dlnk.href =data.data.result.resume;
		 dlnk.click();	
	  });
	}
	
	$scope.fun1=function() {
		debugger;
		var promise = CandidatelistServices.getCandidateData($scope.getrole,$scope.getuserid);

	promise.then(function(data,result) {
		$rootScope.candidatelist = data.data.result;
		$scope.role=sessionStorage.getItem("userRole");
		$scope.loginId=sessionStorage.getItem("loginId");
		$scope.nameOfRound=data.data.result;
		
//		
//		for(i =0 ; i<result.length; i++){
//		
//			$scope.nameOfRound=data.data.result[i].nameOfRound;
//			console.log("mmmmmmmmmmmmmmmmmmmmmmmmmm",$scope.nameOfRound);
//		}
		//console.log(data.data.result($index),"ccccccccccccc");
//		
		console.log("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",data);
	});

		
	}
 $timeout(function ()
 {
    $scope.fun1();
 }, 2000);
	

	$scope.newUserlist = {};
	$scope.userid = function(list) {
		$scope.newUserlist = list;
		console.log($scope.newUserlist);
		console.log($scope.newUserlist.filename);
		$scope.qualiarray = $scope.newUserlist.education;
		$scope.certiarray = $scope.newUserlist.certificates;
		
			
		$scope.skillarray = $scope.newUserlist.skills;
		
		console.log($scope.skillarray);
		
		
		$scope.skillarray1 = [];
		$scope.skillarray2 = [];
		angular.forEach($scope.skillarray, function(key, value) {
			if (key.flag == true) {
				$scope.primarySkills = key;

				$scope.skillarray1.push($scope.primarySkills);
			} else {
				$scope.secondarySkills = key;
				$scope.skillarray2.push($scope.secondarySkills);
			}
		})
		console.log($scope.skillarray1);
		console.log($scope.skillarray2);
$scope.noticePeriod=$scope.newUserlist.noticePeriod;
	console.log($scope.noticePeriod);

$scope.fun1();		
	}
		
	var promise = candidateSer.getEducation();

	promise.then(function(data) {
		$scope.educationdetails = data.data.result;
		console.log($scope.educationdetails);
		$scope.education1 = [];
		angular.forEach($scope.qualificationName, function(key) {
			$scope.education1.push({
				"id" : key
			});
		})
	});
	candidateSer.getEducation();
	
	var promise = candidateSer.getCertificateNames();
	promise.then(function(data) {
		$scope.certificatname = data.data.result;
		console.log($scope.certificatname);

		$scope.certName = [];
		angular.forEach($scope.certificationName, function(key) {
			$scope.certName.push({
				"id" : key
			});
		})
	});
	candidateSer.getCertificateNames();
	
	var promise = candidateSer.getPrimarySkill();

	promise.then(function(data) {
		$scope.skills = data.data.result;

		$scope.skills2 = data.data.result;

		$scope.primary = [];
		$scope.secondary = [];
		angular.forEach($scope.skills, function(key, value) {
			if (key.flag == true) {
				$scope.primarySkills = key;

				$scope.primary.push($scope.primarySkills);
			} else {
				$scope.secondarySkills = key;
				$scope.secondary.push($scope.secondarySkills);
			}
		})
		console.log($scope.primary);
		$scope.primes = [];
		angular.forEach($scope.prime, function(key) {
			$scope.primes.push({
				"id" : key
			});
		})
		angular.forEach($scope.second, function(key) {
			$scope.primes.push({
				"id" : key
			});
		})
	});
	candidateSer.getPrimarySkill();

	$scope.updateCandidatelist = function() {
		$scope.skillarray3 = $scope.skillarray1.concat($scope.skillarray2);
		
			
			
		updateList = {
				client: {
					id:$scope.newUserlist.client.id
					},
					bdmReq:{
					id:$scope.newUserlist.bdmReq.id
					},
					user : {
						id :$scope.newUserlist.user.id
					},
			firstName : $scope.newUserlist.firstName,
			lastName : $scope.newUserlist.lastName,
			mobile : $scope.newUserlist.mobile,
			email : $scope.newUserlist.email,
			altenateEmail  : $scope.newUserlist.altenateEmail,
			alternateMobile  : $scope.newUserlist.alternateMobile,
			submittionDate : $scope.newUserlist.submittionDate,
			education : $scope.educationList,
			totalExperience : $scope.newUserlist.totalExperience,
			relevantExperience : $scope.newUserlist.relevantExperience,
			currentCTC : $scope.newUserlist.currentCTC,
			expectedCTC : $scope.newUserlist.expectedCTC,
			salaryNegotiable : $scope.newUserlist.salaryNegotiable,
			noticePeriod : $scope.newUserlist.noticePeriod,
			pincode : $scope.newUserlist.pincode,
			country : $scope.newUserlist.country,
			state : $scope.newUserlist.state,
			city : $scope.newUserlist.city,
			address : $scope.newUserlist.address,
			willingtoRelocate : $scope.newUserlist.willingtoRelocate,
			skypeID : $scope.newUserlist.skypeID,
			currentJobType : $scope.newUserlist.currentJobType,
			payRollCompanyName : $scope.newUserlist.payRollCompanyName,
			currentCompanyName : $scope.newUserlist.currentCompanyName,
			pancardNumber : $scope.newUserlist.pancardNumber,
			certificationscheck : $scope.newUserlist.certificationscheck,
			gender : $scope.newUserlist.gender,
			title : $scope.newUserlist.title,
			candidateStatus:$scope.newUserlist.candidateStatus,
			candidateSource : $scope.newUserlist.candidateSource,
			skills : $scope.skills1,
			certificates : $scope.certNamelist,
			filename : $scope.newUserlist.filename,
			resume : $scope.newUserlist.resume
		};
		console.log($scope.newUserlist.id);
		updateCandidatesrvService.updateCandidateList($scope.newUserlist.id,updateList);
		$scope.fun1();
		 alert("data updated Successfully");
		 $scope.fun1();
		$location.path('/CandidateList1');
		$scope.fun1();
		$window.location.reload();
		$scope.fun1();
			}
	
	$scope.viewInfo = function(lists) {
		debugger;
		    $http.get("rest/addCandidate/"+lists.candidateid).then(function(response){
		    	$scope.candidatelist1 =  response.data.result;
	       	});
	}
		//$scope.candidatelist = lists;
		//console.log("hai");
		//console.log($scope.candidatelist);
		//$scope.process1();
		//$scope.fun1();

	$scope.updatedata=function(list){
		
		sessionStorage.setItem('canStatus',list.candidateStatus); 
	
		sessionStorage.setItem('canStatus',list.candidateStatus); 
					var rr = $window.localStorage.getItem("role");
					
					var process = list.candidateStatus;
		console.log(list.id);
		$scope.cid=list.candidateid;
		console.log($scope.cid);
		 $window.localStorage.setItem('candidateid',$scope.cid);
		 	$scope.process1 = function() {
					
						console.log(" exis process");
                        
						var promise = TypeOfProcessService.getprocessData(rr,process);
						console.log(promise);
						promise.then(function(response) {
							console.log('257 response.data.result',response.data.result);
							$rootScope.processDetails = response.data.result;
							console.log("processDetails:::::::::::::",$rootScope.processDetails);
	                     					
						});
					}
							 $timeout(function () {
                                      $scope.process1();
	                                       }, 200);
										   
					$scope.$watch("processDetails",function handleFooChange( newValue, oldValue )
					{
                        console.log( "vm.fooCount:", newValue );
						console.log( "vm.fooCount:", oldValue );
						
                    } );
					$scope.fun1();
		
		 $scope.fun1();
		 
	 
	     $location.path("/typeofProcess/"+list.candidateid);
	}
	
	
	
	   $scope.onHolddata= function(id) {
		 //  
			var promise = TypeOfProcessService.getquesservice(id);
    console.log(promise);
     promise.then(function(response) {
	 console.log(response.data.result);
	 $scope.getques = response.data.result;
	console.log($scope.getques);	
	$window.localStorage.setItem("cname", $scope.getques.candidateiname);
	$window.localStorage.setItem("email", $scope.getques.userEmail);
	$window.localStorage.setItem("ques", $scope.getques.quryQuestion);
	$window.localStorage.setItem("candiid", $scope.getques.candidateid);
	$window.localStorage.setItem("type", $scope.getques.typeofprocess);
	$window.localStorage.setItem("ans", $scope.getques.quryAnswer); 
	
	
	
});
$location.path("/onHold");

			}
			
	$scope.details=function()
	{	
	$scope.candiname = $window.localStorage.getItem("cname");
	$scope.candiemail= $window.localStorage.getItem("email");
	$scope.candiques= $window.localStorage.getItem("ques");
	$scope.candidateid = $window.localStorage.getItem("candiid");
	$scope.candidatetype=$window.localStorage.getItem("type");
	$scope.queryAnswer=$window.localStorage.getItem("ans");
	
	
	if(	$scope.queryAnswer=$window.localStorage.getItem("ans")=="null")
	{
		$scope.queryAnswer="";
	}
	else{
			$scope.queryAnswer=$window.localStorage.getItem("ans");
	}
	
	}		
				
				
				
				
		$scope.onHolddata1= function(id) {
			var promise = TypeOfProcessService.getquesservice(id);
    console.log(promise);
     promise.then(function(response) {
	 console.log(response.data.result);
	 $scope.getques = response.data.result;
	console.log($scope.getques);	
	
	
	
	$window.localStorage.setItem("cname1", $scope.getques.candidateiname);
	$window.localStorage.setItem("email1", $scope.getques.userEmail);
	$window.localStorage.setItem("ques1", $scope.getques.quryQuestion);
	$window.localStorage.setItem("candiid1", $scope.getques.candidateid);
	$window.localStorage.setItem("type1", $scope.getques.typeofprocess);
	$window.localStorage.setItem("ans1", $scope.getques.quryAnswer); 
	
	
	
	
});
	$location.path("/onHold1");			
			}		
				
		
$scope.g=$window.localStorage.getItem("ans1");	
$scope.a=$window.localStorage.getItem("cname1");
$scope.b=$window.localStorage.getItem("email1");
$scope.c=$window.localStorage.getItem("ques1");
$scope.d=$window.localStorage.getItem("candiid1");
$scope.f=$window.localStorage.getItem("type1");
				
			console.log($scope.queryAnswer);			
			
				
					$scope.saveOnHold = function() {
							console.log($scope.getques);
							
						var amholddata = {
							candidateid : $scope.candidateid,
							candidateiname: $scope.candiname,
							userEmail:$scope.candiemail,
							quryQuestion:$scope.candiques,
							typeofprocess:$scope.candidatetype,
                            quryAnswer:$scope.queryAnswer

						};
						console.log(amholddata);

						TypeOfProcessService.sendAmholdData(amholddata,$scope.candidateid );
						

					}
					
					
					$scope.flags=false;
				    $scope.expandSelected=function(details){
				      $scope.candidatelist.forEach(function(val){
				        val.expanded=false;
				      })
				      details.expanded=true;
				      
				      
				   	$http.get('rest/addCandidate/getRequiremntListByCandidateId/'+details.candidateid).then(function(response){
				   	  $scope.statusress=response.data.status;
						    if($scope.statusress=='StatusSuccess'){
						    	$scope.flags=true;
						    $scope.ListReqDataa=response.data.result;	
						    }
						    else{
						    	   $scope.ListReqDataa=[];
						    		$scope.flags=false;
						    }
						
				  		});	
				  
				      
				    }
				    $scope.navigationToCandidate = function(list) {
						console.log(list);
						var reqId = list.id;
						$location.path("/matchCandidate/"+reqId);
					}
				    
				  
});
