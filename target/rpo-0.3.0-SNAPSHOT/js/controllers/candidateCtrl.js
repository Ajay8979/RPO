app.controller("candidateCtrl", function($scope, $window, candidateSer,$location,$timeout, $rootScope,rpoFactory, clientService, Upload, $rootScope, bdmService,bdmReqService,$q,$http) {
			$scope.date = new Date();		
						
							   $scope.open1 = function() {
								$scope.popup1.opened = true;
							  };

							  $scope.popup1 = {
								opened: false
							  };
							  
							  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
							  $scope.format = $scope.formats[0];
							  
							  
			$scope.difference=function(totalExperience,relevantExperience){		
				if(relevantExperience>totalExperience){
				$scope.message="relevant experience can't be greater than total experience";	
				$scope.relevantExperience = null;
			}
				else{
					$scope.message="";
				}
			}
	
	
	$scope.reqshow=true;		
	$scope.education= [];
	$scope.skillarray3 = [];
	$scope.certiarray=[];
	$scope.skillarray=[];
	$scope.skillarray1=[];
	
	if($window.localStorage.getItem("addreqcandiclientname")!==null)
	{
	$scope.clientshow=true;
	}
	else{
		$scope.clientshow=false;
	}
		$scope.reqId = $window.localStorage.getItem("addreqcandireqid");
		$scope.reqId1 = $window.localStorage.getItem("addreqcandireqname");
		$scope.clientName = $window.localStorage.getItem("addreqcandiclientname");
		$scope.client_id = $window.localStorage.getItem("addreqcandiclient_id");
       // alert($scope.clientName);
	
	
	$scope.example16settings = {styleActive: true, enableSearch: true, showSelectAll: true, keyboardControls: true ,showEnableSearchButton: true, scrollableHeight: '300px', scrollable: true}; 
			
			
	$scope.candidateStatus = "Submit to Lead";
	console.log("myctrl");
	var promise = candidateSer.getCandidateData();

	promise.then(function(data) {
		console.log("varunaaaaaa");
		$scope.candidateData = data.data[0];
		console.log($scope.candidateData);
	});

	candidateSer.getCandidateData();

	/*===========================================================================*/
	$scope.show = false;
	
	$("#msgss").hide();
//candidate register starts here
	$scope.register = function() {
		debugger;
		$scope.date = new Date();		
		$scope.skillarray3 = $scope.skillarray.concat($scope.skillarray1);
		$scope.id = $window.localStorage.getItem("usid");
		if($scope.resume == undefined){
			$scope.message1="Please Select a File";
			return;
		}
		else{
	   	if($scope.resume!=undefined){
  		 var ext = $scope.resume.name.split('.').pop();
  		 console.log(ext);
  		 if( ext=='docx' || ext=='pdf'){
  			$scope.resumeFile = $scope.resume;
  			$scope.resumeFileName = "resume";
  			 $scope.message="";
  			 /*$scope.UploadResume();*/
  		 }
  		 else{
  			 $scope.message="Please Select only docx & Pdf Files  !";
				  		return;
  		 }
  		}
		}
		var CandidateReqObj = {
				'user':{
					'id':$scope.id
				},
				'title':$scope.title,
				'candidateStatus':$scope.candidateStatus,
				'firstName':$scope.firstName,
				'lastName':$scope.lastName,
				'mobile':$scope.mobile,
				'alternateMobile':$scope.alternateMobile,
				'street1':$scope.street1,
				'street2':$scope.street2,
				'gender':$scope.gender,
				'email':$scope.email,
				'altenateEmail':$scope.altenateEmail,
				'candidateSource':$scope.candidateSource,
				'education' : $scope.education,
				'totalExperience' : $scope.totalExperience,
				'relevantExperience' : $scope.relevantExperience,
				'currentCTC' : $scope.currentCTC,
				'expectedCTC' : $scope.expectedCTC,
				'salaryNegotiable' : $scope.salaryNegotiable,
				'noticePeriod' : $scope.noticePeriod,
				'willingtoRelocate' : $scope.willingtoRelocate,
				'skypeID' : $scope.skypeID,
				'currentJobType' : $scope.currentJobType,
				'payRollCompanyName' : $scope.payRollCompanyName,
				'currentCompanyName' : $scope.currentCompanyName,
				'pancardNumber' : $scope.pancardnumber,
				'certificationscheck' : $scope.certifications,
				'skills' : $scope.skillarray3,
				'certificates' : $scope.certiarray,
				'filename' : $scope.inputParams.name,
				'pincode' : $scope.pincode,
				'city' : $scope.city,
				'country':$scope.country,
				'state':$scope.state
,		}
	console.log("CandidateReqObj",CandidateReqObj);
		rpoFactory.cadidateAdd(CandidateReqObj).then(function(response) {
			console.log("Cccccccccddddddddd",response.status);
			$scope.candidateRes = response.result;
			$scope.candidateResponse = response.status;
			$scope.resMessage = response.res;
			
			if(response.status==="StatusSuccess"){
				$scope.candidateId = $scope.candidateRes.id;
				$window.sessionStorage.setItem("candidateid",$scope.candidateId);
				$rootScope.candidateId = $scope.candidateId;
				$("#newModal").modal("toggle",{
					backdrop: 'static'
				});
				$("#msgss").show();
				$("#msgss").css("opacity", "4");
				/*$('#openMapReq').modal();*/
				$scope.UploadResume();
				
			}
			else{
				alert("Duplicate Email/Mobile Number/PAN Number found")			}
				window.setTimeout(function() {
					 $("#msgss").fadeTo(500, 0).slideUp(500, function(){
					        $(this).hide(); 
					    });
				}, 8000);
			
		});
		$scope.yesCandidateMap = function () {
			debugger;
			$('#newModal').modal('hide');
			$location.path("/matchData/"+$rootScope.candidateId);
			//$("#openMapReq").modal("toggle");
		}
		$scope.noCandidateRedirection = function (){
			$('#newModal').modal('hide');
			$location.path("/listOfCandidate");
		}
		
		
		
		
/*		$scope.show = true;
		console.log($scope.qualiarray);
		console.log($scope.certiarray);
		
		//date 
		$scope.date = new Date();		
		$scope.skillarray3 = $scope.skillarray.concat($scope.skillarray1);
		
		
		console.log($scope.skillarray3);

		$scope.id = $window.localStorage.getItem("usid");
		if($scope.resume == undefined){
			$scope.message="Please Select a File";
			return;
		}
		else{
	   	if($scope.resume!=undefined){
  		 var ext = $scope.resume.name.split('.').pop();
  		 console.log(ext);
  		 if(ext=='doc' || ext=='docx'){
  			$scope.resumeFile = $scope.resume;
  			$scope.resumeFileName = "resume";
  			 $scope.message="";
  			 $scope.UploadResume();
  		 }
  		 else{
  			 $scope.message="Please Select only doc & docx Files !";
				  		return;
  		 }
  		}
	}
		var candidateData = {
			client : {
				id : $scope.client_id
			},
			bdmReq : {
				id : $scope.reqId
			},
			user : {
				id : $scope.id
			},
			title : $scope.title,
			candidateStatus : $scope.candidateStatus,
			candidateSource : $scope.candidateSource,
			firstName : $scope.firstName,
			lastName : $scope.lastName,
			mobile : $scope.mobile,
			altenateEmail  : $scope.alteremail,
			alternateMobile  : $scope.altermobile,
			submittionDate : $scope.date,
			email : $scope.email,
			gender : $scope.gender,
			education : $scope.qualiarray,
			totalExperience : $scope.totalExperience,
			relevantExperience : $scope.relevantExperience,
			currentCTC : $scope.currentCTC,
			expectedCTC : $scope.expectedCTC,
			salaryNegotiable : $scope.salaryNegotiable,
			noticePeriod : $scope.noticePeriod,
			pincode : $scope.pincode,
			country : $scope.client[1].Country,
			state : $scope.client[1].State,
			city : $scope.client[1].City,
			address : $scope.address,
			willingtoRelocate : $scope.willingtoRelocate,
			skypeID : $scope.skypeID,
			currentJobType : $scope.currentJobType,
			payRollCompanyName : $scope.payRollCompanyName,
			currentCompanyName : $scope.currentCompanyName,
			pancardNumber : $scope.pancardnumber,
			certificationscheck : $scope.certifications,
			skills : $scope.skillarray3,
			certificates : $scope.certiarray,
			filename : $scope.inputParams.name,
			//resume : $scope.inputParams.file
		}

		console.log(candidateData);
		console.log(JSON.stringify(candidateData));

		var promise = candidateSer.setCandidateData(candidateData);
		
		

		promise.then(function(resp) {
			 console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
			console.log("dataSave");
			console.log(resp.data.status);
			
		if(resp.data.status==="StatusSuccess"){
			$scope.UploadResume();
			 $('#myModal').modal('show');
			//alert("Registerd Successfully"
			//+"profile sumbited at"+ $scope.date);
			 //$location.path("/CandidateList1");
		}
		
		else{
			
			$('#myModal1').modal('show');
		}
		
		})*/
	}
	
	$scope.UploadResume = function() {
		var file = $scope.resume;
			
		
		
		var cid = $window.sessionStorage.getItem("candidateid");
		  var deferred = $q.defer();
		  var hd = {
				  	transformRequest: angular.identity,
			       headers: {'Content-Type': undefined }
			}
		  var formData = new FormData();
		  formData.append('file', file);
		  $http.post("rest/addCandidate/uploadResume/"+cid,formData,hd).success(function(response){ 
			 console.log(response);
		  
			  //alert(response.res);
//			 $scope.uploadS1=true;
//			 $scope.uploadE1=false; 
//			 $scope.mes2="";
		
//		  else{
//			   $scope.uploadS2=false;
//			   $scope.uploadE2=true; 
//		  }
		  });
	}
$scope.getClientDropDown = function(){
	$scope.Userid = $window.localStorage.getItem("usid");
	$scope.status = "Open";
	rpoFactory.getClientAll($scope.Userid,$scope.status).then(function(response) {
		debugger;
		$scope.clientRes = response.result;
		console.log($scope.clientRes);
	})
}
$scope.getReq = function (){
	$scope.Userid = $window.localStorage.getItem("usid");
	$scope.clientType = $scope.clientType;
	$scope.status = "Open";
	rpoFactory.getReqByClient($scope.Userid,$scope.clientType,$scope.status).then(function(response) {
		$scope.reqList = response.result;
	})
}
$scope.candidateMap =function () {
	var cid = $window.sessionStorage.getItem("candidateid");
	var reqObj = {
	"bdmReq":{
		"id":$scope.requirement
	},
	"candidate":{
		"id":cid
	}
	}
	rpoFactory.candidateMapping(reqObj).then(function(response) {
		$scope.candidateMappings = response;
	})
	
}
	
	
	
//			$rootScope.resumeFile;
//			$rootScope.resumeFileName;
//     $scope.uploadFile = function(){
//   	  if($scope.resume == undefined){
//       		$scope.mes1="Please Select a File";
//       		return;
//       	}
//   	  else{
//   	if($scope.resume!=undefined){
//   		 var ext = $scope._10thMarksMemo.name.split('.').pop();
//   		 console.log(ext);
//   		 if(ext=='doc' || ext=='docx'){
//   			resumeFile = $scope.resume;
//   			resumeFileName = "resume";
//   			 //$scope.mes1="";
//   			 $scope.UploadResume();
//   		 }
//   		 else{
//   			 $scope.mes1="Please Select only Pdf & jpg Files !";
//				  		return;
//   		 }
//   		}
//   	  }
//   	 
//     }

	
	
	
	/*===========================================================================*/
	$scope.getDetails = function() {
		var pin = $scope.pincode;

		var promise = candidateSer.getDetails(pin);
		promise.then(function(response) {
			console.log("conty data...");
			$scope.client = response.Data;
			console.log($scope.client);
		});

		
	}
	
	
	
	$scope.popclosefun = function(){
		$location.path("/CandidateList1");
	}
	

	
	
	
	//$scope.client = clientService.query();

	//console.log($scope.client);
	var fun1 = function() {
		var promise = clientService.getCa();

		promise.then(function(data) {
			$scope.clientl = data.data.result;
			console.log($scope.clientl);
		});

	}
	fun1();
	$timeout(fun1);

	//$scope.requirements = bdmService.query();

	//console.log($scope.requirements);
	var fun2 = function() {
		var promise = bdmService.reqdertails();

		promise.then(function(data) {
			$scope.requirements = data.data.result;
			console.log($scope.requirements);
		});

	}
	fun2();
	$timeout(fun2);

	$scope.getRequirement = function(id) {
		
		
		if($scope.clientName!=undefined){
				$scope.reqshow=false;
			}
			else{
				$scope.reqshow=true;
			}
			
			
		var promise = bdmReqService.getClientRelatedRequirements(id);
		promise.then(function(data) {

			$scope.requirement = data.data.result;
			console.log($scope.requirement);
		});
	}

	/*==============================================================================*/
	/*$scope.viewInfo = function(lists) {
	$scope.candidatelist = lists;
	console.log("hai");
	console.log($scope.candidatelist);
	}
	 */

	/*==============================================================================*/

	var promise = candidateSer.getContactName();

	promise.then(function(data) {
		$scope.contactNames = data.data.result;
		console.log($scope.contactNames);

	});
	candidateSer.getContactName();

	/*==============================================================================	*/
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
		console.log("$scope.primary",$scope.primary);
		console.log($scope.secondary);
		
	});
	candidateSer.getPrimarySkill();
	/*==============================================================================	*/
	var promise = candidateSer.getCertificateNames();
	promise.then(function(data) {
		$scope.certificatname = data.data.result;
		console.log($scope.certificatname);

	});
	candidateSer.getCertificateNames();

	/*==============================================================================	*/
	var promise = candidateSer.getContactType();

	promise.then(function(data) {
		$scope.currentJobTypes = data.data.result;
		console.log($scope.currentJobTypes);

	});
	candidateSer.getContactType();

	/*==============================================================================	*/
	var promise = candidateSer.getEducation();

	promise.then(function(data) {
		debugger;
		$scope.educationdetails = data.data.result;
		console.log($scope.educationdetails);

	});
	candidateSer.getEducation();

	/*==============================================================================	*/
	var promise = candidateSer.getNoticePeriod();

	promise.then(function(data) {
		$scope.noticePeriodes = data.data.result;
		console.log($scope.noticePeriodes);

	});
	candidateSer.getNoticePeriod();

	/*==============================================================================	*/
	$scope.inputParams = {};
	$scope.generate = function() {
		console.log("Name ->" + $scope.inputParams.name);
		console.log("File data ->" + $scope.inputParams.file);
	}
});

app.directive("fileread", [ function() {
	return {
		scope : {
			fileread : "="
		},
		link : function(scope, element, attributes) {
			element.bind("change", function(changeEvent) {
				var reader = new FileReader();
				reader.onload = function(loadEvent) {
					scope.$apply(function() {
						scope.fileread = loadEvent.target.result;
					});
				}
				reader.readAsDataURL(changeEvent.target.files[0]);
				//reader.readAsArrayBuffer(changeEvent.target.files[0]);
				//reader.readAsText(changeEvent.target.files[0]);
				//reader.readAsBinaryString(changeEvent.target.files[0])
			});
		}
	}
} ]);

app.filter('unique', function() {
	return function(collection, keyname) {
		var output = [], keys = [];
		angular.forEach(collection, function(item) {
			var key = item[keyname];
			if (keys.indexOf(key) === -1) {
				keys.push(key);
				output.push(item);
			}
		});
		return output;
	};
});

app.directive('fileModel', ['$parse', function ($parse) {
    debugger;
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
          
          element.bind('change', function() {
             scope.$apply(function() {
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
 }]);

