function notifyObservers($http,nodeid,$uibModal,filename,toaster){
	var notifydata={'fileid':nodeid,'oblist':[]};
	 $http.post("adminui/notifyObservers", JSON.stringify(notifydata)).success(function(data) {
	   if(data.error  ||  !isEmpty(data.obErrorMap)){
		   var modalInstance = $uibModal.open({
		        templateUrl: 'resources/template/modalNotificationFailure.html',
		        controller: 'modalNotificationFailedCtrl',
		        size: 'lg',
		        resolve: {
		        	nodeid : function(){
		        		return nodeid;
		        	},
		        	filename: function(){
		        		return filename;
		        	},
		        	errordata: function(){
		        		return data;
		        	},
		        	toaster:function(){
		        		return toaster;
		        	}
		        }
		      });
	   }else{
		   toaster.pop("success","Success","All Observers notified succesfully");
	   }
	  });
}

function isEmpty(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop))
            return false;
    }

    return true && JSON.stringify(obj) === JSON.stringify({});
}