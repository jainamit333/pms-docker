mainModule.controller('modalShowSubsViewCtrl', function($scope,$uibModalInstance, $http,nodeid,filename,toaster) {
    $scope.fileName=filename;
	$uibModalInstance.opened.then(function() {
		$http.get("adminui/getsubsforfile?fileid=" + nodeid).success(
				function(data) {
					$scope.subs = data;
				}).error(function(data){
					toaster.pop('error', "Error in fetching subscriptions","Please try again");
				});
	});

	$scope.ok = function() {
		$uibModalInstance.dismiss('dismiss');
	};
});