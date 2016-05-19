$(function() {
	$(window).resize(
			function() {
				var h = Math.max($(window).height() - 0, 420);
				$('#container, #data, #tree, #data .content').height(h).filter(
						'.default').css('lineHeight', h + 'px');
			}).resize();

	$('#tree')
			.jstree(
					{
						'core' : {
							'data' : {
								'url' : 'adminui/getChildren',
								'data' : function(node) {
									return {
										'id' : node.id
									};
								}
							},
							'check_callback' : function(o, n, p, i, m) {
								/*
								 * m.dnd indicates the node is drag and dropped
								 * m.pos indicates the position which node is
								 * moved a: after, b:before, i:inside
								 */
								if (m && m.dnd && m.pos !== 'i') {
									return false;
								} // prevents drag and drop for after before
								if (o === "move_node" || o === "copy_node") {
									// prevent moving in non expandable node
									if (this.get_node(p).type === 'file') {
										return false;
									}
									// prevent movid in same parent
									if (this.get_node(n).parent === this
											.get_node(p).id) {
										return false;
									}
								}
								return true;
							},
							'themes' : {
								'responsive' : false,
								'variant' : 'small',
								'stripes' : true
							}
						},
						'sort' : function(a, b) {
							return this.get_type(a) === this.get_type(b) ? (this
									.get_text(a) > this.get_text(b) ? 1 : -1)
									: (this.get_type(a) <= this.get_type(b) ? 1
											: -1);
						},
						'contextmenu' : {
							'items' : function(node) {
								var tmp = $.jstree.defaults.contextmenu.items();
								delete tmp.create.action;
								tmp.create.label = "New";
								tmp.create.submenu = {
									"create_folder" : {
										"separator_after" : true,
										"label" : "Folder",
										"action" : function(data) {
											var inst = $.jstree
													.reference(data.reference), obj = inst
													.get_node(data.reference);
											inst.create_node(obj, {
												type : "folder"
											}, "last", function(new_node) {
												setTimeout(function() {
													inst.edit(new_node);
												}, 0);
											});
										}
									},
									"create_file" : {
										"label" : "File",
										"action" : function(data) {
											var inst = $.jstree
													.reference(data.reference), obj = inst
													.get_node(data.reference);
											inst.create_node(obj, {
												type : "file"
											}, "last", function(new_node) {
												setTimeout(function() {
													inst.edit(new_node);
												}, 0);
											});
										}
									}
								};
								if (this.get_type(node) === "file") {
									delete tmp.create;
								}
								return tmp;
							}
						},
						'types' : {
							/* 'default' : { 'icon' : 'folder' }, */
							'folder' : {
								'icon' : 'folder'
							},
							'file' : {
								'valid_children' : [],
								'icon' : 'file'
							}
						},
						'unique' : {
							'duplicate' : function(name, counter) {
								return name + ' ' + counter;
							}
						},
						'plugins' : [ 'state', 'dnd', 'sort', 'types',
								'contextmenu', 'unique' ]
					})
			.on('delete_node.jstree', function(e, dat) {
				var request = {
					'id' : dat.node.id
				};
				$.ajax({
					url : "adminui/deleteNode",
					type : "POST",
					data : JSON.stringify(request),
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					success : function(d) {
						dat.instance.refresh();
					},
					error : function(exception) {
						dat.instance.refresh();
					}
				});
			})
			.on('create_node.jstree', function(e, dat) {
				var request = {
					type : dat.node.type,
					parentid : dat.node.parent,
					name : dat.node.text
				};
				$.ajax({
					url : "adminui/addNode",
					type : "POST",
					data : JSON.stringify(request),
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					success : function(d) {
						dat.instance.set_id(dat.node, d.id);
					},
					error : function(exception) {
						dat.instance.refresh();
					}
				});
			})
			.on(
					'rename_node.jstree',
					function(e, dat) {
						var request = {
							id : dat.node.id,
							name : dat.text
						};
						$.ajax({
							url : "adminui/renameNode",
							type : "POST",
							data : JSON.stringify(request),
							contentType : "application/json; charset=utf-8",
							dataType : "json",
							success : function(d) {
								dat.instance.set_id(dat.node, d.id);
							},
							error : function(exception) {
								dat.instance.refresh();
							}
						});
					}).on('move_node.jstree', function(e, dat) {
				var request = {
					id : dat.node.id,
					parent : dat.parent
				};
				$.ajax({
					url : "adminui/moveNode",
					type : "POST",
					data : JSON.stringify(request),
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					success : function(d) {
						dat.instance.refresh();
					},
					error : function(exception) {
						dat.instance.refresh();
					}
				});
			}).on('copy_node.jstree', function(e, dat) {
				var request = {
						id : dat.original.id,
						parent : dat.parent
					};
				$.ajax({
					url : "adminui/copyNode",
					type : "POST",
					data : JSON.stringify(request),
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					success : function(d) {
						dat.instance.refresh();
					},
					error : function(exception) {
						dat.instance.refresh();
					}
				});
			}).on('changed.jstree', function(e, data) {
				if (data && data.selected && data.selected.length) {
					var tbody=$("div[ng-controller='propertypanelCtrl'] tbody");
					if (data.node.type == 'file' && tbody.attr("loadedFile")!=data.node.id) {
						var entries=[];
						var angularTabscope=angular.element($( "div[ng-controller='propertypanelCtrl']" )).scope();
						angularTabscope.setTableDataFromOutSide(entries);
						$.ajax({
							url : "adminui/getPropertyForFile",
							type : "GET",
							data : "fileid="+data.node.id,
							success : function(d) {
								//udpate angular scope
								angularTabscope.setTableDataFromOutSide(d,data.node.id,data.node.text);
								tbody.attr("loadedFile",data.node.id);
							},
							error : function(exception) {
								
							}
						});
					}
				}
				/*
				 * if(data && data.selected && data.selected.length) {
				 * $.get('?operation=get_content&id=' + data.selected.join(':'),
				 * function (d) { if(d && typeof d.type !== 'undefined') {
				 * $('#data .content').hide(); switch(d.type) { case 'text':
				 * case 'txt': case 'md': case 'htaccess': case 'log': case
				 * 'sql': case 'php': case 'js': case 'json': case 'css': case
				 * 'html': $('#data .code').show(); $('#code').val(d.content);
				 * break; case 'png': case 'jpg': case 'jpeg': case 'bmp': case
				 * 'gif': $('#data .image img').one('load', function () {
				 * $(this).css({'marginTop':'-' + $(this).height()/2 +
				 * 'px','marginLeft':'-' + $(this).width()/2 + 'px'});
				 * }).attr('src',d.content); $('#data .image').show(); break;
				 * default: $('#data .default').html(d.content).show(); break; } }
				 * }); } else { $('#data .content').hide(); $('#data
				 * .default').html('Select a file from the tree.').show(); }
				 */
			});
});