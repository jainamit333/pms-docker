mainModule.controller('modalCreateVariantFormCtrl', function($scope,
		$uibModalInstance, $http, $anchorScroll, $location, nodeid, filename,
		toaster, props, $uibModal) {
	$scope.filename=filename;
	$scope.ipregex='((([0-9]{1,3})\\.){3}[0-9]{1,3})';
    $scope.enteredvalues=[];
    $scope.enteredprops=[];
    $scope.errors=[];
    $scope.addMoreObv=function(){
    	var entryObv={ip:''};
    	$scope.enteredvalues.push(entryObv);
    }
    $scope.addMoreProps=function(){
    	var entryProp={key:'',value:''};
    	$scope.enteredprops.push(entryProp)
    }
    if(props.length==0){
    	$scope.addMoreProps();
    }else{
    for(var prop in props){
    	props[prop].id=0;
    	$scope.enteredprops.push(props[prop]);	
     }
    }
	$uibModalInstance.opened.then(function() {
		$http.get("adminui/getallObservers").success(
				function(data) {
					$scope.obvs = data;
				}).error(function(data){
					toaster.pop('error', "Error in fetching Observers","Please try again");
				});
	});
	$scope.removeObEntry=function(obentryindx){
		$scope.enteredvalues.splice(obentryindx,1);
	}
	$scope.removePropEntry=function(propentryindx){
		$scope.enteredprops.splice(propentryindx,1);
	}
    $scope.validate=function(oblist,proplist){
      $scope.errors=[];
      var errorfound=false;
      if(oblist.length==0){
    	  $scope.errors.push("Enter at least one observer");
    	  errorfound=true;
    	  }
      if(proplist.length==0){
    	  $scope.errors.push("Enter at least one property");
    	  errorfound=true;
      }
      else
    	  return !errorfound;
    }
	$scope.ok = function() {
		$anchorScroll($location.hash('modaltop'));
		var obList=[];
		for(var i in $scope.selectedobvs){
			if($scope.selectedobvs[i])
			 obList.push({'ip':$scope.selectedobvs[i]});
		}
		for(var i in $scope.enteredvalues){
			if($scope.enteredvalues[i].ip)
			 obList.push($scope.enteredvalues[i])
		}
		var propList=[];
		for(var i in $scope.enteredprops){
		    var prop=$scope.enteredprops[i];
			if(prop.key && prop.value){
				propList.push(prop);
			}
		}
		if($scope.validate(obList,propList)){
		var createrequest={'fileid':nodeid,'oblist':obList,'props':propList};
		$http.post("adminui/createvariant", JSON.stringify(createrequest)).success(function(data) {
   		    if(data.error){
   		    	toaster.pop('error', "Error in creating variant",data.error);
   		    }else{
   		    	toaster.pop('success','Success','Variant Added Successfully')
   		    	$uibModalInstance.close();
   		    }
   		  }).error(function(){
   			toaster.pop('error', "Error in creating variant","Please try again");
   		  });
		 notifyObservers($http,nodeid,$uibModal,$scope.filename,toaster);
		}
	};
	 $scope.cancel = function() {
			$uibModalInstance.dismiss('cancel');
	};
});