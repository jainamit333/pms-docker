mainModule.controller('modalNotificationFailedCtrl', function($scope,$uibModalInstance, $http,nodeid,errordata,filename,toaster) {
	$scope.filename=filename;
    $scope.errorMap=errordata.obErrorMap;
	$scope.cancel = function() {
		$uibModalInstance.dismiss('dismiss');
	};
	$scope.ok=function(){
	    var errorObs=Object.keys($scope.errorMap);
		var notifydata={'fileid':nodeid,'oblist':errorObs};
		 $http.post("adminui/notifyObservers", JSON.stringify(notifydata)).success(function(data) {
			   if(data.error  ||  !isEmpty(data)){
				   $scope.errorMap=data.obErrorMap;
				   toaster.pop("error","Error","Notification to mentioned Observers failed Again");
			   }else{
				   toaster.pop("success","Success","All Observers notified succesfully");
			   }
			  });
	}
});