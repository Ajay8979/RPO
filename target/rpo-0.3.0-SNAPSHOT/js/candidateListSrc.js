app.service('CandidatelistServices', function($http,$q) {
	this.getCandidateData=function(){
		var deffered3=$q.defer();
		$http.get("rest/addCandidate").then(function(data){
			deffered3.resolve(data);
		});
		return deffered3.promise;
	}
	
});
app.service('selectedDetailsService',function($http,$q){
	this.selectedCandidate=function(id){
		var deffered=$q.defer();
		$http.get("rest/addCandidate/"+id).then(function(data){
			deffered.resolve(data);
		});
		return deffered.promise;
	}
})
app.service('updateCandidatesrvService',function($http,$q){
	this.updateCandidateList=function(id,list)
	{
		console.log("jyothi");
	var deferred1=$q.defer();

	$http.post("rest/addCandidate/"+id,list).then(function(data){
		deferred1.resolve(data);
	});
	}
});