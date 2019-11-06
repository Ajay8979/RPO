app.service('CustomerTypeService', function($http,$q) {
	this.addCustomerTypeData=function(customerTypeDetails)
	{
	var deferred=$q.defer();
	console.log(customerTypeDetails);
	$http.post("rest/customerType",customerTypeDetails).then(function(data){
		deferred.resolve(data);
	});
	}
});
	app.service('listCustomerTypeServices', function($http,$q,$resource) {
		//return $resource('/rpo-0.3.0-SNAPSHOT/rest/customerType', {}

			
	this.getCustomerTypelist=function()
	{
		console.log("varun");
		var deferred11=$q.defer();
	    

	    $http.get("rest/customerType").then(function(data){
		deferred11.resolve(data);
		console.log(data.data)
		
	});
	
     return deferred11.promise;
	
}
	
		
		
		
		
		
	});
	
	
	
	
	
	
	

	app.service('updateCustomerTypeService',function($http,$q){
		this.updateCustomerTypelist=function(id,list)
		{
			console.log("jyothi");
			console.log(list);
		var deferred1=$q.defer();

		$http.post("rest/customerType/"+id,list).then(function(data){
			deferred1.resolve(data);
		});

		
		}
	});
