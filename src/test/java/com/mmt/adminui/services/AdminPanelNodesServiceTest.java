package com.mmt.adminui.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

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
import com.mmt.adminui.util.NodeIcon;
import com.mmt.entity.Directory;
import com.mmt.entity.File;
import com.mmt.entity.FileSystemObject;
import com.mmt.entity.Property;
import com.mmt.repositories.DirectoryRepository;
import com.mmt.repositories.FileRepository;
import com.mmt.repositories.FileSystemRepository;
import com.mmt.repositories.PropertyRepository;
@RunWith(MockitoJUnitRunner.class)
public class AdminPanelNodesServiceTest {
	@Mock
	private EntityManager entityManager;
	@Mock
	private FileSystemRepository fileSystemRepository;
	@Mock
	private DirectoryRepository directoryRepository;
	@Mock
	private FileRepository fileRepository;
	@Mock
	private PropertyRepository propertyRepository;
	@InjectMocks
	private AdminPanelNodesService adminPanelNodesService;
	

	@Test
	public void testGetChildren() {
		List<FileSystemObject> subObjects=new ArrayList<>();
		Directory root=new Directory();
		root.setId(1);
		root.setSelfName("/");
		Directory dir=new Directory();
		dir.setId(2);
		dir.setSelfName("dir1");
		dir.setParentDir(root);
		File file=new File();
		file.setSelfName("file1");
		file.setId(4);
		file.setParentDir(root);
		subObjects.add(file);
		subObjects.add(dir);
		when(fileSystemRepository.findByParentDir(any())).thenReturn(subObjects);
		when(directoryRepository.findOneByParentDirIsNull()).thenReturn(root);
		List<FileSystemObject> returned=adminPanelNodesService.getChildren("1");
		assertNotNull(returned);
		assertEquals(4,returned.get(0).getId());
		assertEquals(2,returned.get(1).getId());
		assertEquals("file1",returned.get(0).getSelfName());
		assertEquals("dir1",returned.get(1).getSelfName());
		returned=adminPanelNodesService.getChildren("#");
		assertNotNull(returned);
		assertEquals("/", returned.get(0).getSelfName());
		assertEquals(1, returned.get(0).getId());
		returned=adminPanelNodesService.getChildren(null);
		assertNotNull(returned);
		assertEquals(4,returned.get(0).getId());
		assertEquals(2,returned.get(1).getId());
		assertEquals("file1",returned.get(0).getSelfName());
		assertEquals("dir1",returned.get(1).getSelfName());
	}
	@Test
	public void testAddNode() {
		Directory root=new Directory();
		root.setId(1);
		root.setSelfName("/");
		Directory dir=new Directory();
		dir.setId(2);
		dir.setSelfName("dir1");
		dir.setParentDir(root);
		File file=new File();
		file.setSelfName("file1");
		file.setId(4);
		file.setParentDir(root);
		when(directoryRepository.findOneById(1)).thenReturn(root);
		when(fileRepository.save(any(File.class))).thenReturn(file);
		when(directoryRepository.save(any(Directory.class))).thenReturn(dir);
		CreateNodeRequest request=new CreateNodeRequest();
		request.setParentid(1);
		request.setSelfName("dir");
		request.setType(NodeIcon.folder);
		NodeOperationResponse resp=adminPanelNodesService.addNode(request);
		assertNotNull(resp);
		assertEquals(2,resp.getId());
		request=new CreateNodeRequest();
		request.setParentid(1);
		request.setSelfName("file1");
		request.setType(NodeIcon.file);
		resp=adminPanelNodesService.addNode(request);
		assertNotNull(resp);
		assertEquals(4,resp.getId());
		when(directoryRepository.findOneById(any())).thenReturn(null);
		resp=adminPanelNodesService.addNode(request);
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("Parent Directory Cannot be null", resp.getError());
	}

	@Test
	public void testRenameNode() {
		File file=new File();
		file.setSelfName("file1");
		file.setId(4);
		when(fileSystemRepository.findOneById(any())).thenReturn(file);
		when(fileSystemRepository.save(any(File.class))).thenReturn(file);
		RenameNodeRequest request=new RenameNodeRequest();
		request.setId(4);
		request.setNewName("file2");
		NodeOperationResponse resp=adminPanelNodesService.renameNode(request);
		assertNotNull(resp);
		assertEquals(4, resp.getId());
		
	}

	@Test
	public void testMoveNode() {
		Directory dir=new Directory();
		dir.setId(5);
		dir.setSelfName("dir1");
		File file=new File();
		file.setSelfName("file1");
		file.setId(4);
		when(directoryRepository.findOneById(any())).thenReturn(dir);
		when(fileSystemRepository.findOneById(any())).thenReturn(file);
		when(fileSystemRepository.save(any(File.class))).thenReturn(file);
		CopyOrMoveRequest request=new CopyOrMoveRequest();
		request.setId(4);
		request.setParent(5);
		NodeOperationResponse resp=adminPanelNodesService.moveNode(request);
		assertNotNull(resp);
		assertEquals(4, resp.getId());
	}

	@Test
	public void testCopyNodeFile() {
		Directory root=new Directory();
		root.setId(1);
		root.setSelfName("/");
		Directory dir=new Directory();
		dir.setId(2);
		dir.setSelfName("dir1");
		dir.setParentDir(root);
		List<Property> pList=new ArrayList<>();
		File file=new File();
		file.setSelfName("file1");
		file.setId(6);
		Property p=new Property();
		p.setId(30);
		p.setFileID(file);
		p.setKey("testkey");
		p.setValue("testvalue");
		pList.add(p);
		file.setParentDir(root);
		file.setProperties(pList);
		File created =new File();
		created.setSelfName("file1");
		created.setId(10);
		when(directoryRepository.findOneById(any())).thenReturn(dir);
		when(fileSystemRepository.findOneById(any())).thenReturn(file);
		when(fileRepository.save(any(File.class))).thenReturn(created);
		when(directoryRepository.save(any(Directory.class))).thenReturn(dir);
		when(propertyRepository.save(any(Property.class))).thenReturn(new Property());
		doAnswer((invo)->null).when(entityManager).detach(any());
		CopyOrMoveRequest request=new CopyOrMoveRequest();
		request.setId(4);
		request.setParent(9);
		NodeOperationResponse resp=adminPanelNodesService.copyNode(request);
		assertNotNull(resp);
		assertEquals(10, resp.getId());
		file.setParentDir(dir);
		resp=adminPanelNodesService.copyNode(request);
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("Cannot copy inside same parent", resp.getError());
		
	}
	@Test
	public void testCopyNodeDir() {
		Directory root=new Directory();
		root.setId(1);
		root.setSelfName("/");
		Directory dir=new Directory();
		dir.setId(2);
		dir.setSelfName("dir1");
		dir.setParentDir(root);
		Directory dir2=new Directory();
		dir2.setSelfName("dir2");
		dir2.setParentDir(root);
		ArrayList<FileSystemObject> subObj=new ArrayList<>();
		dir2.setSubObjects(subObj);
		File subFile=new File();
		subFile.setId(15);
		subFile.setParentDir(dir2);
		subFile.setSelfName("subfile1");
		subFile.setProperties(new ArrayList<>());
		subObj.add(subFile);
		Directory subDir=new Directory();
		subDir.setId(20);
		subDir.setSelfName("subdir1");
		subDir.setParentDir(dir2);
		subDir.setSubObjects(new ArrayList<>());
		subObj.add(subDir);
		Directory created =new Directory();
		created.setSelfName("dir2");
		created.setId(10);
		when(directoryRepository.findOneById(any())).thenReturn(dir);
		when(fileSystemRepository.findOneById(any())).thenReturn(dir2);
		when(directoryRepository.save(any(Directory.class))).thenReturn(created);
		when(propertyRepository.save(any(Property.class))).thenReturn(new Property());
		doAnswer((invo)->null).when(entityManager).detach(any());
		CopyOrMoveRequest request=new CopyOrMoveRequest();
		request.setId(4);
		request.setParent(9);
		NodeOperationResponse resp=adminPanelNodesService.copyNode(request);
		assertNotNull(resp);
		assertEquals(10, resp.getId());
		dir2.setParentDir(dir);
		resp=adminPanelNodesService.copyNode(request);
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("Cannot copy inside same parent", resp.getError());
		
	}

	@Test
	public void testDeleteNode() {
		File file =new File();
		file.setSelfName("file1");
		file.setId(10);
		when(fileSystemRepository.findOneById(any())).thenReturn(file);
		doAnswer((invo)->null).when(fileSystemRepository).delete(any(FileSystemObject.class));
		DeleteRequest request=new DeleteRequest();
		NodeOperationResponse resp=adminPanelNodesService.deleteNode(request);
		assertNotNull(resp);
		assertNull(resp.getError());
	}

}
