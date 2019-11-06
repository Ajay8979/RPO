
app.service('clientService1',function($http,$q,$rootScope){
	this.clientDisplay=function(pageIndex,pageSizeSelected){
		var role=sessionStorage.getItem('userRole');
		var id=sessionStorage.getItem('UserId');
		var deferred=$q.defer();
		var config={
				headers:{
				'content type':'application/json',
				}
		}
		debugger;
		var result=$http.get('rest/client/getAllClientsByRole/'+id+'/'+role+'/'+pageIndex+'/'+pageSizeSelected).then(function(response){
			deferred.resolve(response);
			 console.log(response.data);
			 $rootScope.client1=response.data.result;
			 return deferred.promise;
		});
		return result;
	}
	
	
	this.search=function(client)
	{
		console.log("hello");
	var deffer=$q.defer();
	var config={
			headers:{
			'content type':'application/json',
			}
	}
	var result=$http.get("rest/client/"+client).then(function(data){
		defer.resolve(data);
		return defer.promise;
	})
	return result;
	console.log(result);
	}
});
