package com.mmt.adminui.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mmt.adminui.pojo.OperationResponse;
import com.mmt.adminui.pojo.PropertyChangeRequest;
import com.mmt.adminui.services.AdminPanelPropertiesService;
import com.mmt.entity.Directory;
import com.mmt.entity.File;
import com.mmt.entity.Property;
@RunWith(MockitoJUnitRunner.class)
public class AdminPanelPropertiesControllerTest {
	@Mock
	private AdminPanelPropertiesService propertiesService;
	
	@InjectMocks
	private AdminPanelPropertiesController propertiesController;

	@Test
	public void testGetPropertiesForFile() {
		List<Property> propList=new ArrayList<>();
		Directory root =new Directory();
		root.setId(1);
		root.setSelfName("/");
		File f=new File();
		f.setId(11);
		f.setSelfName("testfile");
		f.setParentDir(root);
		f.setProperties(propList);
		Property p=new Property();
		p.setFileID(f);
		p.setId(30);
		p.setKey("test");
		p.setValue("testvalue");
		propList.add(p);
		when(propertiesService.getPropertiesFromFile(11)).thenReturn(propList);
		List<Property> result=propertiesController.getPropertiesForFile(11);
		assertNotNull(result);
		assertTrue(result.size()==1);
		assertNotNull(result.get(0));
		assertEquals(30,result.get(0).getId());
		assertEquals(11, result.get(0).getFileID().getId());
		assertEquals("testfile",result.get(0).getFileID().getSelfName());
		assertEquals("test", result.get(0).getKey());
		assertEquals("testvalue", result.get(0).getValue());
	}

	@Test
	public void testAddProperty() {
		OperationResponse mockResp=new OperationResponse();
		when(propertiesService.addProperty(any())).thenReturn(mockResp);
	    PropertyChangeRequest request=new PropertyChangeRequest();
	    request.setFileID(11);
	    Property p=new Property();
	    p.setKey("testkey");
	    p.setValue("testvalue");
	    request.setProperty(p);
	    OperationResponse opresp=propertiesController.addProperty(request);
	    assertNotNull(opresp);
	    assertNull(opresp.getError());
	    mockResp.setError("Error Occured");
	    opresp=propertiesController.addProperty(request);
	    assertNotNull(opresp);
	    assertNotNull(opresp.getError());
	    assertEquals("Error Occured",opresp.getError());
	}

	@Test
	public void testDeleteProperty() {
		OperationResponse mockResp=new OperationResponse();
		when(propertiesService.deleteProperty(any())).thenReturn(mockResp);
	    PropertyChangeRequest request=new PropertyChangeRequest();
	    request.setFileID(11);
	    Property p=new Property();
	    p.setKey("testkey");
	    p.setValue("testvalue");
	    request.setProperty(p);
	    OperationResponse opresp=propertiesController.deleteProperty(request);
	    assertNotNull(opresp);
	    assertNull(opresp.getError());
	    mockResp.setError("Error Occured");
	    opresp=propertiesController.deleteProperty(request);
	    assertNotNull(opresp);
	    assertNotNull(opresp.getError());
	    assertEquals("Error Occured",opresp.getError());
	}

	@Test
	public void testChangeProperty() {
		OperationResponse mockResp=new OperationResponse();
		when(propertiesService.changeProperty(any())).thenReturn(mockResp);
	    PropertyChangeRequest request=new PropertyChangeRequest();
	    request.setFileID(11);
	    Property p=new Property();
	    p.setKey("testkey");
	    p.setValue("testvalue");
	    request.setProperty(p);
	    OperationResponse opresp=propertiesController.changeProperty(request);
	    assertNotNull(opresp);
	    assertNull(opresp.getError());
	    mockResp.setError("Error Occured");
	    opresp=propertiesController.changeProperty(request);
	    assertNotNull(opresp);
	    assertNotNull(opresp.getError());
	    assertEquals("Error Occured",opresp.getError());
	}

}
