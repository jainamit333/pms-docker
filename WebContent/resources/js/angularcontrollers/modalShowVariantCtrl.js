mainModule.controller('modalShowVariantCtrl', function($scope,$uibModalInstance, $http,nodeid,filename,toaster,$uibModal) {
    $scope.filename=filename;
	$uibModalInstance.opened.then(function() {
		$http.get("adminui/getvarsforfile?fileid=" + nodeid).success(
				function(data) {
					$scope.vars = data;
				}).error(function(data){
					toaster.pop('error', "Error in fetching Variants","Please try again");
				});
	});
	$scope.noVars=function(){
		return $scope.vars&&$scope.vars.length>0?true:false;
	}
	$scope.editVariant=function(){
		
	}
    $scope.deleteVariant=function(variant){
    	  var data={'id':variant.id};
		  $http.post("adminui/deletevariant", JSON.stringify(data)).success(function(data) {
		    if(data.error){
		    	toaster.pop('error', "Error in deleting Variant",data.error);
		    }else{
		    	toaster.pop('success', "Success","Variant Deleted");
		    	 index=$scope.vars.indexOf(variant)
		    	 $scope.vars.splice(index, 1); 
		    }
		  });
		  notifyObservers($http,nodeid,$uibModal,$scope.filename,toaster);
    }
	$scope.ok = function() {
		$uibModalInstance.dismiss('dismiss');
	};
});