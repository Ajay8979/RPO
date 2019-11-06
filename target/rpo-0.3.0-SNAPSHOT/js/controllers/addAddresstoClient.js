
app.controller('AddClientAddressController',function($scope,$location,$window,$rootScope, clientContactService,addressService, clientService) {
	
	$scope.view1=true;
	$scope.view2=false;
	$scope.hide1=false;
	
	$scope.pageSize='10';
//get client contacts
$scope.getDetails=function(){
	var promise=clientContactService.getData();
	promise.then(function(response){
		$scope.clientdata=response.data.result;
		console.log($scope.clientdata);
	})
}
$scope.getDetails();

//carrying client details
$scope.getContactData=function(value){
	$window.localStorage.setItem("cname",value.contact_Name);
	$window.localStorage.setItem("cid",value.id);
	console.log(value);	
}
	$scope.xyz = $window.localStorage.getItem("cname");
	$scope.xyz1 = $window.localStorage.getItem("cid");
	console.log($window.localStorage.getItem("cname"));
	console.log($window.localStorage.getItem("cid"));

//get address types
/*$scope.getList = function() {
	AddressTypeList={}
	var promise = addressService.getAddressType();
	promise.then(function(response) {
		$scope.AddressTypeList = response.data;
		console.log($scope.AddressTypeList);
		
	});
};	
$scope.getList();*/

$scope.idfun=function(idtype1){
	var val=JSON.parse(idtype1);
	$scope.typeAddress=val.typeOfAddress;
	$scope.idtype=val.id;
}	

//get address API
$scope.getcountryDetails=function(){
	var pin=$scope.pincode;	
	var promise=clientService.getDetails(pin);
	promise.then(function(response){
		console.log("conty data...");
		$scope.client=response.Data;
			console.log($scope.client);
		console.log($scope.client[1].Country);
	})
}
$scope.getcountryDetails1=function(){
	var pin=$scope.pincode1;	
	var promise=clientService.getDetails(pin);
	promise.then(function(response){
		console.log("conty data...");
		$scope.client1=response.Data;
			console.log($scope.client1);
		console.log($scope.client1[1].Country);
	})
}



//adding address
$scope.addAddress1 = function() {
	$scope.hide1=true;
	$scope.view1=true;
	$scope.view2=true;
	address={
			pincode:$scope.pincode,
			country:$scope.client[1].Country,
			state:$scope.client[1].State,
			city:$scope.client[1].City,
			addressLane1:$scope.Address,
			addressLane2:$scope.addressLane2,
			typeOfAddress:{
				id:"1"
			},
				addContact:{
					id:$scope.xyz1
					}
		};
	console.log(address);
	addressService.addAddressValues(address);
	
	
}

$scope.addAddress2= function() {

	address={
			pincode:$scope.pincode1,
			country:$scope.client1[1].Country,
			state:$scope.client1[1].State,
			city:$scope.client1[1].City,
			addressLane1:$scope.Address1,
			addressLane2:$scope.addressLane21,
			typeOfAddress:{
				id:"2"
			},
				addContact:{
					id:$scope.xyz1
					}
		};
	console.log(address);
	addressService.addAddressValues(address);
	$location.path('/viewClientContact');
}



//getting address
$scope.getListAddress = function(id) {
	$scope.getidURL="#!/addClientContact/"+id;
	console.log($scope.getidURL);		
};




//dashboard redirecting
$scope.redirect=function(){
		var change = $window.localStorage.getItem("role");
		if(change=="BDM"){
			$location.path('/bdm');
		}
		else if(change=="bdmlead"){
			$location.path('/bdmlead');
		}		
	}
});

