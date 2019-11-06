app.service("incentivesService",function($http,$q,$rootScope){

var deferred=$q.defer();
debugger;
	this.incentivesData =function(pageIndex,pageSizeSelected,sortingOrder,sortingField,searchtext,searchcategory)
	{
		var role=sessionStorage.getItem('userRole');
		var id=sessionStorage.getItem('UserId');			
	debugger;	
	var result = $http.get('rest/incentive/getIncentivesList/'+role+'/'+id+'/'+pageIndex+'/'+pageSizeSelected+'?sortingOrder='+sortingOrder+'&sortingField='+sortingField+'&searchText='+searchtext+'&searchField='+searchcategory)
		.then(function(data){		
		deferred.resolve(data);
		console.log(data);
		return deferred.promise;
	});
		return result
	}

})