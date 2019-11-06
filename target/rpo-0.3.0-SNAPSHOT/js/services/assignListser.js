app.service('assignListwrkSer', function($http, $q) {
	this.assignfunservice = function(id) {
       var UserId=sessionStorage.getItem('UserId');
       var userRole=sessionStorage.getItem('userRole');
		var deferred11 = $q.defer();

		$http.get('rest/allocaterequirment/getAssigenByBdmId/'+UserId+'/'+userRole).then(
				function(data) {

					deferred11.resolve(data);
					console.log("inside clientcontactmcriteria")
					console.log(data.data);

				});

		return deferred11.promise;

	}
	
	//return $resource('rest/allocaterequirment', {})

});
app.service('updateAssignService', function($http, $q) {
	this.editAssignments = function(id, list) {
		console.log("mounika");
		console.log(list);
		var deferred1 = $q.defer();

		$http.post("rest/allocaterequirment/"+id,list)
				.then(function(data) {
					deferred1.resolve(data);
				});
	}
});
app.service('deleteAssignService', function($http, $q) {
	this.deleteAssignments = function(id, list) {
		console.log("deleted");
		console.log(list);
		var deferred1 = $q.defer();
		$http.delete("rest/allocaterequirment/"+id,list)
				.then(function(data) {
					deferred1.resolve(data);
				});
	}
});
