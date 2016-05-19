mainModule.controller('modalEditFormCtrl',
		function($scope, $uibModalInstance,property,nodeid,purpose,filename,$http,$uibModal,toaster) {
		   $scope.property = property;
           $scope.nodeid=nodeid;
           $scope.input={};
           $scope.input.immidiate=true;
           $scope.input.key=property.key;
           $scope.input.value=property.value;
	       if(purpose == 'ADD'){
	        	 $scope.title = 'Enter New Property';
	        	 $scope.keyentryenabled=true;
	        	 $scope.ok=function(){
	        	  if(!$scope.input || !$scope.input.key || !$scope.input.value){
	       			  toaster.pop('error', "Empty Fields", "Please Enter Input Values");
	       	      }else{
	       		  var property={'key':$scope.input.key,'value':$scope.input.value};
	       		  var data={'fileid':$scope.nodeid,'property': property};
	       		  $http.post("adminui/addProperty", JSON.stringify(data)).success(function(data) {
	       		    if(data.error){
	       		    	toaster.pop('error', "Error in adding Property",data.error);
	       		    }else{
	       		    	$scope.property.key=$scope.input.key;
	       		    	$scope.property.value=$scope.input.value;
	       		    	$uibModalInstance.close(data.property);
	       		    }
	       		  });
	       		 notifyObservers($http,$scope.nodeid,filename,toaster);
	        	 }
	        	 
	        }
	       }
	        else{
	        $scope.title = "Enter New Value";
	        $scope.keyentryenabled=false;
			$scope.ok = function() {
				$scope.property.value=$scope.input.value;
				  var data={'fileid':$scope.nodeid,'property': $scope.property}
				  $http.post("adminui/changeProperty", JSON.stringify(data)).success(function(data) {
				    if(data.error){
				    	toaster.pop('error', "Error in deleting Property",data.error);
				    	 $uibModalInstance.close();
				    }else{
				    	$scope.property.value=$scope.input.value;
				    	$uibModalInstance.close(data.property); 
				    }
				  });
				  notifyObservers($http,$scope.nodeid,$uibModal,filename,toaster);
			};
			}
	       $scope.cancel = function() {
				$uibModalInstance.dismiss('cancel');
			};
		});