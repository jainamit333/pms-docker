mainModule.controller("propertypanelCtrl", function($scope,$http,toaster,$uibModal) {
	$scope.props = [];
	$scope.immidiate=true;
	$scope.setTableDataFromOutSide = function(props,nodeid,filename) {
		$scope.props = props;
		$scope.nodeid=nodeid;
		$scope.filename=filename;
		$scope.$apply();
	};
	$scope.addProperty=function(){
		$scope.purpose='ADD';
	    var modalInstance = $uibModal.open({
	        templateUrl: 'resources/template/modalEditForm.html',
	        controller: 'modalEditFormCtrl',
	        size: 'lg',
	        resolve: {
	          property: function () {
	            return {'key':'','value':''};
	          },
	          nodeid: function(){
	        	  return $scope.nodeid;
	          },
	          purpose: function(){
	        	  return $scope.purpose;
	          },
	          filename: function(){
	        	  return $scope.filename;
	          },
	          toaster : function(){
	        	  return toaster;
	          }
	        }
	      });

	      modalInstance.result.then(function (property) {
	    	  if(property){
	    	    $scope.props.push(property);
	    	  }
	      }, function () {
	        //$log.info('Modal dismissed at: ' + new Date());
	      });
	};
	$scope.deleteProperty=function(property){
		if(!property.deletionInProgress){
	    var index = $scope.props.indexOf(property);
		property.deletionInProgress=true;
		  var data={'fileid':$scope.nodeid,'property': property}
		  $http.post("adminui/deleteProperty", JSON.stringify(data)).success(function(data) {
			delete property.deletionInProgress;
		    if(data.error){
		    	toaster.pop('error', "Error in deleting Property",data.error);
		    }else{
		    	 $scope.props.splice(index, 1); 
		    	
		    }
		    notifyObservers($http,$scope.nodeid,$uibModal,$scope.filename,toaster);
		  });
		}else{
			toaster.pop('error', "Please Wait","deletion in progress please wait");
		}
	}
	$scope.editProperty=function(property){
		$scope.purpose='EDIT';
	    var modalInstance = $uibModal.open({
	        templateUrl: 'resources/template/modalEditForm.html',
	        controller: 'modalEditFormCtrl',
	        size: 'lg',
	        resolve: {
	          property: function () {
	            return property;
	          },
	          nodeid: function(){
	        	  return $scope.nodeid;
	          },
	          purpose: function(){
	        	  return $scope.purpose;
	          },
	          filename: function(){
	        	  return $scope.filename;
	          },
	          toaster : function(){
	        	  return toaster;
	          }
	        }
	      });

	      modalInstance.result.then(function (property) {
	    	  if(property){
	    	    var index = $scope.props.indexOf(property);
	    	    $scope.props[index]=property;
	    	  }
	      }, function () {
	        //$log.info('Modal dismissed at: ' + new Date());
	      });
	};
	$scope.showSubs= function(){
		var modalInstance = $uibModal.open({
	        templateUrl: 'resources/template/modalShowSubsView.html',
	        controller: 'modalShowSubsViewCtrl',
	        size: 'lg',
	        resolve: {
	        	nodeid : function(){
	        		return $scope.nodeid;
	        	},
		        filename : function(){
		        	return $scope.filename;
		        },
		        toaster: function(){
		        	return toaster;
		        }
	        }
	      });
	}
	$scope.showVariants=function(){
		var modalInstance = $uibModal.open({
	        templateUrl: 'resources/template/modalShowVariant.html',
	        controller: 'modalShowVariantCtrl',
	        size: 'lg',
	        resolve: {
	        	nodeid : function(){
	        		return $scope.nodeid;
	        	},
		        filename : function(){
		        	return $scope.filename;
		        },
		        toaster: function(){
		        	return toaster;
		        }
	        }
	      });
	}
	$scope.createVariant=function(){
		var modalInstance = $uibModal.open({
	        templateUrl: 'resources/template/modalCreateVariantForm.html',
	        controller: 'modalCreateVariantFormCtrl',
	        size: 'lg',
	        resolve: {
	        	nodeid : function(){
	        		return $scope.nodeid;
	        	},
		        filename : function(){
		        	return $scope.filename;
		        },
		        toaster: function(){
		        	return toaster;
		        },
		        props : function(){
		        	return JSON.parse(JSON.stringify($scope.props));
		        }
	        }
	      });
	}
});