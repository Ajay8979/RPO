app.service("ListReqSer",function($http,$q,$rootScope){

var deferred=$q.defer();
	

	this.getListReqData=function(pageIndex,pageSizeSelected)
	{
		var role=sessionStorage.getItem('userRole');
		var id=sessionStorage.getItem('UserId');
			
	debugger;	
		$http.get('rest/Bdmrequire/getBdmReqByRole/'+id+'/'+role+'/'+pageIndex+'/'+pageSizeSelected).then(function(data){
		
		deferred.resolve(data);
		//console.log(data);
		 $rootScope.ListReqData=data.data.result;
		
	});
		return deferred.promise;
	}
	
})