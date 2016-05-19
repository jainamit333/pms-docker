
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Title</title>
<meta name="viewport" content="width=device-width" />
<link rel="stylesheet" href="resources/themes/default/style.min.css" />
<link rel="stylesheet" href="resources/css/directorytree.css" />
<link rel="stylesheet" href="resources/css/propertypanel.css" />
<link rel="stylesheet" href="resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="resources/css/font-awesome.min.css" />
<link rel="stylesheet" href="resources/css/toaster.min.css" />
<link rel="stylesheet" href="resources/css/select.min.css" />
<link rel="stylesheet" href="resources/css/sc-select.min.css" />
<link rel="stylesheet" href="resources/css/select2-bootstrap.min.css" />

<script src="resources/js/angular.min.js"></script>
</head>
<body ng-app="mainModule">
	<div class="row">
		<div class="col-xs-3">
			<div id="tree"></div>
		</div>
		<div class="col-xs-9" ng-controller="propertypanelCtrl">
			<toaster-container toaster-options="{'time-out': 2000}"></toaster-container>
			<div class="text-center text-info" ng-show="!nodeid">
				<h3>Please select a file from left pane.</h3>
			</div>
			<div ng-show="nodeid">
				<div class="row">
					<h3>
						File Name: {{fileNamefromPath()}}
						</h1>
						<h3>
							ID: {{nodeid}}
							</h1>
				</div>
				<div class="row">
					<!-- <div class="btn-group btn-group-xs"> -->
						<button type="button" ng-click="addProperty()"
							class="btn btn-xs btn-primary">Add Property</button>
						<button type="button" ng-click="showSubs()"
							class="btn btn-xs btn-primary">Show Subscriptions</button>
						<button type="button" ng-click="showVariants()"
							class="btn btn-xs btn-primary">Show Variants</button>
							<button type="button" ng-click="createVariant()"
							class="btn btn-xs btn-success">Create Variant</button>
					<!-- </div> -->
				</div>

				<div class="row">
					<hr />
					<div class="row">
						<div class="col-xs-4">
							<h3>Key</h3>
						</div>
						<div class="col-xs-4">
							<h3>Value</h3>
						</div>
					</div>
					<div class="row" ng-repeat="prop in props"
						ng-mouseenter="item.showEditDelete = true"
						ng-mouseleave="item.showEditDelete = false" ng-class-odd="'odd'"
						ng-class-even="'even'">
						<div class="col-xs-4">
							<h5>{{prop.key}}</h5>
						</div>
						<div class="col-xs-4">
							<h5>{{prop.value}}</h5>
						</div>
						<div class="col-xs-4" ng-show="item.showEditDelete"
							class="btn-group btn-group-sm">
							<button type="button" class="btn btn-default"
								ng-click="editProperty(prop)">
								<span class="glyphicon glyphicon-pencil"></span>
							</button>
							<button type="button" class="btn btn-default"
								ng-click="deleteProperty(prop)">
								<span class="glyphicon glyphicon-remove"></span>
							</button>
						</div>
					</div>

					<script type="text/ng-template" id="modalEditForm"></script>
					<script type="text/ng-template" id="modalShowSubsView"></script>
					<script type="text/ng-template" id="modalCreateVariantForm"></script>
					<script type="text/ng-template" id="modalShowVariant"></script>
					<script type="text/ng-template" id="modalNotificationFailure"></script>
				</div>
			</div>
		</div>
	</div>

	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/jstree.min.js"></script>
	<script src="resources/js/angularmodules/angular-animate.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/angularmodules/toaster.min.js"></script>
	<script src="resources/js/angularmodules/angular-touch.min.js"></script>
	<script src="resources/js/angularmodules/ui-bootstrap-tpls.js"></script>
	<script src="resources/js/angularmodules/angular-sanitize.min.js"></script>
	<script src="resources/js/angularmodules/select.min.js"></script>
	<script src="resources/js/angularmodules/sc-select.min.js"></script>
	<script src="resources/js/changenotifier.js"></script>
	<script src="resources/js/angularmodules/mainModule.js"></script>
	<script src="resources/js/angularcontrollers/propertypanelctrl.js"></script>
	<script src="resources/js/directorytree.js"></script>
	<script src="resources/js/angularcontrollers/modaleditformctrl.js"></script>
	<script src="resources/js/angularcontrollers/modalShowSubsViewCtrl.js"></script>
	<script src="resources/js/angularcontrollers/modalCreateVariantForm.js"></script>
	<script src="resources/js/angularcontrollers/modalShowVariantCtrl.js"></script>
	<script src="resources/js/angularcontrollers/modalNotificationFailedCtrl.js"></script>
</body>
</html>