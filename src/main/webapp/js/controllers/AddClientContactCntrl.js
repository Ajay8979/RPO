app.controller('ClientContactController',function($scope,$location,$timeout,$routeParams, clientContactService,$window,$location ,$http){

	$scope.clientName = $window.localStorage.getItem("addclientname");
    $scope.client_id1 = $window.localStorage.getItem("addrcandiclient_id");
   
    $scope.clientId = $window.localStorage.getItem("addrcandiclient_id");
   
    var param = $routeParams.id;
 
    if(param !=undefined){
    	
    	//contact
    	//rest/Contact_address_map/readContactAddressDetails/351
    	
    $http.get('rest/addClientContact/'+param).then(function(response) {
    	$scope.updAddress1=false;
		$scope.svaeupdate=false;
		$scope.routebtn1=true;
		$scope.routbtn2=false;
      	  if(response.data.status=="StatusSuccess"){
      		 
        	$scope.contact_Name=response.data.result.contact_Name;
		    $scope.phone=response.data.result.phone;
			$scope.mobile=response.data.result.mobile;
			$scope.email=response.data.result.email;
		    $scope.bday=response.data.result.dob;
			$scope.Aday=response.data.result.anniversary;
			$scope.BusinessLocation=response.data.result.location;
			$scope.clientName=response.data.result.client.clientName;
			
			var id = response.data.result.client.id;	
		
			  var n = id.toString();
				$scope.clientId = n;
				$scope.updatebtnContact=true;
				$scope.saveBtncontact=false;
				
      	  }
    })
    
  
    }
    
    if(param!=undefined){
    	
    	 $http.get('rest/Contact_address_map/readContactAddressDetails/'+param).then(function(response) {
        	$scope.updatedList=response.data.result;
 			 	 
		})
    
    }
	
    
    
    
    
  
   
    
	$scope.getDetails=function(){
		var promise=clientContactService.clientDisplay();
		promise.then(function(response){
			console.log("Client Controller");
			debugger;
			$scope.clientdata=response.data.result;
			console.log($scope.clientdata);
			$scope.client_id="Choose Client";
		})
	}
		
	$scope.getaddressType=function(){
		var promise=clientContactService.getaddressType();
		promise.then(function(response){
			console.log("Client Controller");
			$scope.addressType=response.data.result;
			console.log($scope.clientdata);
			$scope.client_id="Choose Client";
		})
	}
	    $scope.saveAddress=true;
	    $scope.updAddress=false;
		$scope.saveMsg=false;
		$scope.btnName="Save";
		$scope.ErrorMessage=false;
		 $scope.ok=false;
		 $scope.flag=true;
		 $scope.cid;
		 $scope.saveBtncontact=true;
		 $scope.updatebtnContact=false;
	$scope.addContactSave=function(){
		debugger;
		
		var value={
				
				contact_Name : $scope.contact_Name,
				phone : $scope.phone,
				mobile : $scope.mobile,
				email : $scope.email,
			
				dob:$scope.bday,
				anniversary:$scope.Aday,
				location:$scope.BusinessLocation,
		        
				"client": {
					id :$scope.clientId,
	            }
	               
			
				
		};
		
		
		
		  if( value.client.id=='undefined' || value.email=='undefined' || value.phone=='undefined' || value.location=='undefined' || value.contact_Name=='undefined')
				  {						  
						  $scope.errorMsg=true;
						  $timeout(function () {
							  $scope.errorMsg=false;
						  }, 2000);
						
						  return;
						  
					  }
					                                  
					         $http.post('rest/addClientContact',value).then(function(response) {
                            //  console.log(response);
							  if(response.data.status=="StatusSuccess"){
								$scope.cid=response.data.result.id;
								if($scope.cid==undefined){
									$scope.flag=true;
								}
								else{
									$scope.flag=false;
									 $scope.saveBtncontact=false;
									 $scope.updatebtnContact=true;
								}
								  $scope.btnName="Update";
								  $scope.saveMsg=true;
								  $timeout(function () {
									  $scope.saveMsg=false;
								  }, 2000);
							//	$scope.responseMes="Contact Person Added Successfully";	
                              //   $scope.ok=true;								
							  }
							  else{
								  $scope.responseMes="Internal Server Error"
								  $scope.ok=true;
							  }
						  
                          
                             }); 
					  
		// console.log(value);
		// clientContactService.sendData(value);
		//$location.path('/getclient');
	};
	$scope.updateMsg=false;
	debugger;
	$scope.addContactUpdate=function(){
		debugger;
		var idss=null;
		if($scope.cid==undefined){
			idss =param
		}else{
			idss =$scope.cid
		}
var value={
		        id:idss,  
				contact_Name : $scope.contact_Name,
				phone : $scope.phone,
				mobile : $scope.mobile,
				email : $scope.email,
			
				dob:$scope.bday,
				anniversary:$scope.Aday,
				location:$scope.BusinessLocation,
		        
				"client": {
					id :$scope.clientId,
	            }
             
			
				
		};
		
		
		
		  if(value.anniversary==undefined || value.client.id=='undefined' || value.email=='undefined' || value.phone=='undefined'|| value.mobile=='undefined' || value.location=='undefined' ||value.dob=='undefined'|| value.contact_Name=='undefined')
				  {
	
						  
						  $scope.errorMsg=true;
						  $timeout(function () {
							  $scope.errorMsg=false;
						  }, 2000);
						
						  return;
						  
					  }
					                                  
					         $http.post('rest/addClientContact',value).then(function(response) {
                            //  console.log(response);
							  if(response.data.status=="StatusSuccess"){
								$scope.cid=response.data.result.id;
								if($scope.cid==undefined){
									$scope.flag=true;
								}
								else{
									$scope.flag=false;
									 $scope.saveBtncontact=false;
									 $scope.updatebtnContact=true;
								}
								  $scope.btnName="Update";
								  $scope.updateMsg=true;
								  $timeout(function () {
									  $scope.updateMsg=false;
								  }, 2000);
							//	$scope.responseMes="Contact Person Added Successfully";	
                              //   $scope.ok=true;								
							  }
							  else{
								  $scope.responseMes="Internal Server Error"
								  $scope.ok=true;
							  }
						  $location.path('/viewClientContact')
                          
                             }); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	$scope.typeId;
	$scope.fun=function(id){
		$scope.typeId=id;
	}
	
	
	$scope.addAddress=function(){
		debugger;
var value={
				
	          	typeOfAddress :{
	          		id:$scope.id,
	          	} ,      
	          	addressLane1 : $scope.address1,
	          	addressLane2:  $scope.address2,
		        state : $scope.clientState,
				country : $scope.clientCountry,
				pincode:$scope.pincode,
				cid:$scope.clientId,
				               
			
				
		};
if(value.typeOfAddress.id==undefined || value.state==undefined || value.country==undefined || value.pincode==undefined|| value.addressLane2==undefined || value.addressLane1==undefined)
{
	 $scope.errorMsg=true;
	  $timeout(function () {
		  $scope.errorMsg=false;
	  }, 2000);
	
		  return;  
	  }                          
	         $http.post('rest/Contact_address_map',value).then(function(response) {
         	  if(response.data.status=="StatusSuccess"){
         		 $scope.getUpdatedAddress();
         		 $scope.saveAddress=false;
         	    $scope.updAddress=true;
         		 $scope.saveMsg=true;
				  $timeout(function () {
					  $scope.saveMsg=false;
				  }, 2000);
							
			  }
			  else{
			alert('response error')
			  }
		  
        
           }); 

	         /*
	        	$scope.addressId='';    
	           $scope.address1='';
	           $scope.address2='';
		         $scope.clientState='';
				 $scope.clientCountry='';
				$scope.pincode='';
			    $scope.cid='';
	     	*/
          
	      
	}
	
	
	
	$scope.updateAddress1=false;
	$scope.svaeupdate=true;
	$scope.updateAddress=function(){
		debugger;
var value={
	     	  typeOfAddress :{
      	     	id:$scope.id,
            	} ,  
	          	addressLane1 : $scope.address1,
	          	addressLane2:  $scope.address2,
		        state : $scope.clientState,
				country : $scope.clientCountry,
				pincode:$scope.pincode,
				id:$scope.idupdate,
				cid:$scope.cid,
				               
			
				
		};
if(value.typeOfAddress.id==undefined || value.state==undefined || value.country==undefined || value.pincode==undefined|| value.addressLane2==undefined || value.addressLane1==undefined)
{
	 $scope.errorMsg=true;
	  $timeout(function () {
		  $scope.errorMsg=false;
	  }, 2000);
	
		  return;  
	  }                          
	         $http.post('rest/Contact_address_map',value).then(function(response) {
         	  if(response.data.status=="StatusSuccess"){
         		
         			if(param !=undefined){
         				$scope.updAddress1=true;
        				$scope.svaeupdate=false;
         			}
         			if(param ==undefined){
         				$scope.updAddress1=false;
        				$scope.svaeupdate=true;
         			}
         			
         	   $scope.getUpdatedAddress();
         		 $scope.saveMsg=true;
				  $timeout(function () {
					  $scope.saveMsg=false;
				  }, 2000);
							
			  }
			  else{
			alert('response error')
			  }
		  
        
           }); 

		
	}
	
	
	
	
	
	
	
	$scope.redirect=function(){
		
		$location.path("/getclient");
		$window.location.reload();
	}
	
	
	$scope.getUpdatedAddress=function(){
		debugger;
		var promise=clientContactService.getUpdatedAddress($scope.clientId);
		promise.then(function(response){
			console.log("Client Controller");
			$scope.updatedList=response.data.result;
			console.log($scope.clientdata);
			$scope.client_id="Choose Client";
		})
	}
	$scope.updAddress1=false;
	$scope.idupdate
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
	
	
	
	
	
	$scope.getDetails();
	$scope.getaddressType();
	
	
	
	
});
	
