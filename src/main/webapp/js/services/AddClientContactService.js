app.service('clientContactService',function($http,$q){
	
	this.clientDisplay=function(){
		var UserId=sessionStorage.getItem('UserId');
		var userRole=sessionStorage.getItem('userRole');
		var deferred=$q.defer();
		var config={
				headers:{
				'content type':'application/json',		
				}
		}
		var result=$http.get('rest/client/getClientsByRole/'+UserId+'/'+userRole).then(function(response){
			console.log("Client Service");
			deferred.resolve(response);
			 console.log(response.data);
			 return deferred.promise;
		});
		return result;
	}
	
	this.getUpdatedAddress=function(cid){
		var deferred=$q.defer();
		var config={
				headers:{
				'content type':'application/json',		
				}
		}
		var result=$http.get('rest/Contact_address_map/readContactAddressDetails/'+cid).then(function(response){
			console.log("Client Service");
			deferred.resolve(response);
			 console.log(response.data);
			 return deferred.promise;
		});
		return result;
	}
	
	
	this.getaddressType=function(){
		var deferred=$q.defer();
		var config={
				headers:{
				'content type':'application/json',		
				}
		}
		var result=$http.get('rest/typeofaddress').then(function(response){
			console.log("Client Service");
			deferred.resolve(response);
			 console.log(response.data);
			 return deferred.promise;
		});
		return result;
	}
	
	
	
	
	
	this.sendData=function(value){
		var deferred=$q.defer();
		$http.post('rest/addClientContact',value).then(function(data) {
				deferred.resolve(data);
				console.log(data);
		});
	}
		
	this.getData = function(pageIndex,pageSizeSelected,sortingField,sortingOrder,searchcategory,searchtext){	
		var UserId=sessionStorage.getItem('UserId');
		var userRole=sessionStorage.getItem('userRole');
		
		var deferred=$q.defer();
		var config={
				headers:{
				'content type':'application/json',		
				}
		}
		debugger;
		var result=$http.get('rest/addClientContact/findContactByBdmId/'+UserId+'/'+userRole+'/'+pageIndex+'/'+pageSizeSelected+'?sortField='+sortingField+'&sortOrder='+sortingOrder+'&searchField='+ searchcategory+'&searchText='+searchtext).then(function(response){
			console.log("Client Service");
			deferred.resolve(response);
			 console.log(response.data);
			 return deferred.promise;
		});
		return result;
		
	}
	
	this.getData1 = function(id){	
		var deferred=$q.defer();
		var config={
				headers:{
				'content type':'application/json',		
				}
		}
		var result=$http.get('rest/addClientContact/contacs/'+id).then(function(response){
			console.log("Client Service");
			deferred.resolve(response);
			 console.log(response.data);
			 return deferred.promise;
		});
		return result;
		
	}
	
});