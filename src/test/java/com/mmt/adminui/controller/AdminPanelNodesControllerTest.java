package com.mmt.adminui.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mmt.adminui.pojo.CopyOrMoveRequest;
import com.mmt.adminui.pojo.CreateNodeRequest;
import com.mmt.adminui.pojo.DeleteRequest;
import com.mmt.adminui.pojo.NodeOperationResponse;
import com.mmt.adminui.pojo.RenameNodeRequest;
import com.mmt.adminui.services.AdminPanelNodesService;
import com.mmt.adminui.util.NodeIcon;
import com.mmt.entity.Directory;
import com.mmt.entity.File;
import com.mmt.entity.FileSystemObject;
@RunWith(MockitoJUnitRunner.class)
public class AdminPanelNodesControllerTest {
	@Mock
	private AdminPanelNodesService adminPanelNodesService;
	@InjectMocks
	private AdminPanelNodesController adminPanelNodesController;
	
    
	@Test
	public void testGetChildren() {
		List<FileSystemObject> flist=new ArrayList<>();
		Directory dir=new Directory();
		dir.setId(1);
		dir.setSelfName("/");
		File file=new File();
		file.setId(2);
		file.setSelfName("file1");
		flist.add(dir);
		flist.add(file);
		when(adminPanelNodesService.getChildren(any())).thenReturn(flist);
		List<FileSystemObject> returned=adminPanelNodesController.getChildren("#");
		assertNotNull(returned);
		assertTrue(returned.size()>0);
		assertEquals("/",returned.get(0).getSelfName());
		assertEquals("file1",returned.get(1).getSelfName());
	}

	@Test
	public void testAddNode() {
		CreateNodeRequest request=new CreateNodeRequest();
		request.setParentid(1);
		request.setSelfName("file2");
		request.setType(NodeIcon.file);
		NodeOperationResponse mockResp=new NodeOperationResponse();
		mockResp.setId(3);
		when(adminPanelNodesService.addNode(any())).thenReturn(mockResp);
		NodeOperationResponse resp=adminPanelNodesController.addNode(request);
		assertNotNull(resp);
		assertNull(resp.getError());
		assertEquals(3, resp.getId());
		
		request.setType(NodeIcon.folder);
		resp=adminPanelNodesController.addNode(request);
		assertNotNull(resp);
		assertNull(resp.getError());
		assertEquals(3, resp.getId());
		mockResp.setError("Error Occured");
		mockResp.setId(0);
		resp=adminPanelNodesController.addNode(request);
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("Error Occured",resp.getError());
		assertEquals(0, resp.getId());
	}

	@Test
	public void testRenameNode() {
		NodeOperationResponse mockResp=new NodeOperationResponse();
		mockResp.setId(4);
		when(adminPanelNodesService.renameNode(any())).thenReturn(mockResp);
		RenameNodeRequest request=new RenameNodeRequest();
		request.setId(2);
		request.setNewName("file2");
		NodeOperationResponse resp=adminPanelNodesController.renameNode(request);
		assertNotNull(resp);
		assertNull(resp.getError());
		assertEquals(4, resp.getId());
		mockResp.setError("Error Occured");
		mockResp.setId(0);
		resp=adminPanelNodesController.renameNode(request);
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("Error Occured",resp.getError());
		assertEquals(0, resp.getId());
	}

	@Test
	public void testMoveNode() {
		NodeOperationResponse mockResp=new NodeOperationResponse();
		mockResp.setId(11);
		when(adminPanelNodesService.moveNode(any())).thenReturn(mockResp);
		CopyOrMoveRequest request=new CopyOrMoveRequest();
		request.setId(11);
		request.setParent(1);
		NodeOperationResponse resp=adminPanelNodesController.moveNode(request);
		assertNotNull(resp);
		assertNull(resp.getError());
		assertEquals(11,resp.getId());
		mockResp.setError("Error Occured");
		mockResp.setId(0);
		resp=adminPanelNodesController.moveNode(request);
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("Error Occured",resp.getError());
		assertEquals(0, resp.getId());
		
	}

	@Test
	public void testCopyNode() {
		NodeOperationResponse mockResp=new NodeOperationResponse();
		mockResp.setId(11);
		when(adminPanelNodesService.copyNode(any())).thenReturn(mockResp);
		CopyOrMoveRequest request=new CopyOrMoveRequest();
		request.setId(11);
		request.setParent(1);
		NodeOperationResponse resp=adminPanelNodesController.copyNode(request);
		assertNotNull(resp);
		assertNull(resp.getError());
		assertEquals(11,resp.getId());
		mockResp.setError("Error Occured");
		mockResp.setId(0);
		resp=adminPanelNodesController.copyNode(request);
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("Error Occured",resp.getError());
		assertEquals(0, resp.getId());
	}

	@Test
	public void testDeleteNode() {
		NodeOperationResponse mockResp=new NodeOperationResponse();
		mockResp.setId(11);
		when(adminPanelNodesService.deleteNode(any())).thenReturn(mockResp);
		DeleteRequest request=new DeleteRequest();
		request.setId(11);
		NodeOperationResponse resp=adminPanelNodesController.deleteNode(request);
		assertNotNull(resp);
		assertNull(resp.getError());
		assertEquals(11,resp.getId());
		mockResp.setError("Error Occured");
		mockResp.setId(0);
		resp=adminPanelNodesController.deleteNode(request);
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("Error Occured",resp.getError());
		assertEquals(0, resp.getId());
	}

}
