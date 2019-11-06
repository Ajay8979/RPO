
app.controller('clientController1',function($scope,$rootScope,$http,clientService1,$location,$rootScope,clientService,$window,$timeout,clientContactService){

	////get client	
	    $scope.movetoRequirement=function(id,sName){
	    	$rootScope.SPOCName=sName;
	    	$rootScope.clientId=id;
	    	$location.path('/bdmreqdtls/'+id) 
	    	
	    }
		
		$scope.editFun=function(list){
			$scope.urlForEdit="#!/editBdmreqdtls/"+list.id;
		
		}
		$scope.assignFun = function(list){
			 $scope.clientname =list.client.clientName;
		 $scope.client_id =list.client.id;
	       $scope.requirementName=list.nameOfRequirement;
	         $scope.requirement_id=list.id;	   
		 
		 
		 $window.localStorage.setItem("reqclientname",$scope.clientname);
		 $window.localStorage.setItem("reqclient_id",$scope.client_id);
		 $window.localStorage.setItem("reqrequirementName",$scope.requirementName);
		 $window.localStorage.setItem("reqrequirement_id",$scope.requirement_id);

		 }
			 
		
		  $scope.pageSize=20;
		//pagination
		  $scope.maxSize = 2;     // Limit number for pagination display number.  
		    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
		    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
		    $scope.pageSizeSelected = 20; // Maximum number of items per page.
		    
	$scope.getCfun=function(){
		debugger;
		var promise=clientService1.clientDisplay($scope.pageIndex,$scope.pageSizeSelected);
		promise.then(function(response){
			debugger;
			$scope.client1=response.data.result;
			console.log($scope.client1);
		});
		}
		$timeout($scope.getCfun());
		
		 $timeout(function () {
           $scope.getCfun();   
          }, 1000);
		//var editdata;
		
	////view address
		$scope.getaddresspopup = function(x){
			
			$scope.addressdetails = x.addressDetails;
			console.log(x);
		}
	
		$scope.editClientfun=function(data1){
				$scope.urlForEdit="#!/editClient/"+data1.id;
		}
	
		
		$scope.getDetails=function(){
			var pin=$scope.pincode;
			var promise=clientService.getDetails(pin);
			promise.then(function(response){
				console.log("country data...");
				$scope.client=response.Data;
					console.log($scope.client);
				console.log($scope.client[1].Country);
			})		
		}
		 console.log("client search Controller");  
		 $scope.search=function(clientName){
		 	console.log("entered into function");
		 $scope.client=clientName;
		 console.log($scope.client);
		 	var promise=clientService1.search($scope.client);
		 	
		 	promise.then(function(response){
		 			$rootScope.clientdata=response.data;
		 			console.log($scope.clientdata);		
		 		});
		 }
		 
		 $scope.redirect=function(){
	var change = $window.localStorage.getItem("role");
	if(change=="BDM"){
		$location.path('/bdm');
	}
	else if(change=="bdmlead"){
		$location.path('/bdmlead');
	}
	
}
		
		  $scope.id=1;
		 $scope.options=[{'id':1,'pageSize':10},{'id':2,'pageSize':20},{'id':3,'pageSize':30},{'id':4,'pageSize':40},{'id':5,'pageSize':50}]
      
		 $scope.pageSelected=function(id){
			 for(var i=0;i<=$scope.options.length;i++){
				 if($scope.options[i].id==id){
					  $scope.pageSize=$scope.options[i].pageSize;
				 }
			 }
		 }
		 
		 
		 
		$scope.flag=false;
	       $scope.expandSelected=function(person){
	         $scope.client1.forEach(function(val){
	           val.expanded=false;
	         })
	         person.expanded=true;
	         
	         
	      	$http.get('rest/Bdmrequire/clientreq/'+person.id+'/Open').then(function(response){
	      	  $scope.statusres=response.data.status;
    		    if($scope.statusres=='StatusSuccess'){
    		    	$scope.flag=true;
    		    $scope.ListReqData=response.data.result;	
    		    }
    		    else{
    		    	   $scope.ListReqData=[];
    		    		$scope.flag=false;
    		    }
   			
	     		});	
	     
	         
	       }	
	      $scope.statusChange=function(status,id){
	    	  debugger;
		        var id =id ;
		     
		         var status = status ;
		         if(status=='Active') {
		        	 status='InActive' 
		        	 
		         }else{
		        	 status='Active'  
		         }
		      	$http.get('rest/client/updateContactStatus/'+id+'/'+status).then(function(response){
		      	  $scope.statusres=response.data.status;
	    		    if($scope.statusres=='StatusSuccess'){
	    		    alert('updated successfully')
	    		    	debugger;
	    		    
	    		    }
	  
		     		});	
		     
		         
		       }
 $scope.addclientfun = function(x){

$scope.clientname =x.clientName;
 $scope.client_id = x.id;	
 $window.localStorage.setItem("addclientname",$scope.clientname);
 $window.localStorage.setItem("addrcandiclient_id",$scope.client_id);
	 	
	 }	
 
$scope.getcontactClient = function(id){
		  var promise=clientContactService.getData1(id);
	promise.then(function(response){
		$scope.clientdata=response.data.result;
		console.log($scope.clientdata);
	})
	     }	 
	
$scope.editAddress=function(list,itemData,i){
	$scope.routebtn1=false;
	$scope.routebtn2=true;
	$scope.idupdate=itemData.id;
	
	
	
	$scope.updAddress1=true;

	$scope.address1=itemData.addressLane1;
	$scope.address2=itemData.addressLane2;
	$scope.cid=itemData.cid;
	//itemData.city;
	 $scope.clientCountry=itemData.country;
	//itemData.id;
	$scope.pincode=itemData.pincode;
	$scope.clientState=itemData.state;
	//$scope.addressId=itemData.typeOfAddress.id;
	  //$scope.typeOfAddress= [
	 //   {id: 1,typeOfAddress: "Office"},
	//     {id: 2,typeOfAddress: "Residence"}
	//  ]

	var id = itemData.typeOfAddress.id;
	
	  //$scope.id = $scope.addressType[i];
//	$scope.id = itemData.typeOfAddress.typeOfAddress;
	//$scope.id = itemData.typeOfAddress; 
	  
	  
	//  $scope.id1=list[i].typeOfAddress;
	
	  var n = id.toString();
		$scope.id = n;
}
});