package com.mmt.adminui.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mmt.adminui.pojo.OperationResponse;
import com.mmt.adminui.pojo.PropertyChangeRequest;
import com.mmt.entity.File;
import com.mmt.entity.Property;
import com.mmt.repositories.FileRepository;
import com.mmt.repositories.PropertyRepository;
@RunWith(MockitoJUnitRunner.class)
public class AdminPanelPropertiesServiceTest {
	@Mock
	private FileRepository fileRepository;
	@Mock
	private PropertyRepository propRepository;
	@InjectMocks
	private AdminPanelPropertiesService adminPanelPropService;

	@Test
	public void testGetPropertiesFromFile() {
		File f=new File();
		f.setId(2);
		f.setSelfName("file1");
		List<Property> mockProps=new ArrayList<>();
		Property p=new Property();
		p.setId(30);
		p.setFileID(f);
		p.setKey("key");
		p.setValue("value");
		mockProps.add(p);
		f.setProperties(mockProps);
		when(fileRepository.findOneById(2)).thenReturn(f);
		List<Property>props=adminPanelPropService.getPropertiesFromFile(2);
		assertNotNull(props);
        assertEquals(30,props.get(0).getId());
        assertEquals(f, p.getFileID());
        assertEquals(2, props.get(0).getFileID().getId());
        assertEquals("file1", props.get(0).getFileID().getSelfName());
        assertEquals("key", props.get(0).getKey());
        assertEquals("value", props.get(0).getValue());
	}

	@Test
	public void testAddProperty() {
		File f=new File();
		f.setId(2);
		f.setSelfName("file1");
		Property p=new Property();
		p.setId(30);
		p.setKey("key");
		p.setValue("value");
		when(fileRepository.findOneById(2)).thenReturn(f);
		when(propRepository.save(any(Property.class))).thenReturn(p);
		PropertyChangeRequest request=new PropertyChangeRequest();
		request.setFileID(2);
		request.setProperty(p);
		OperationResponse resp=adminPanelPropService.addProperty(request);
		assertNotNull(resp);
		assertNull(resp.getError());
		assertEquals(f, p.getFileID());
	}

	@Test
	public void testDeleteProperty() {
		File f=new File();
		f.setId(2);
		f.setSelfName("file1");
		Property p=new Property();
		p.setId(30);
		p.setKey("key");
		p.setValue("value");
		when(fileRepository.findOneById(2)).thenReturn(f);
		doAnswer((invo)->null).when(propRepository).delete(30);
		PropertyChangeRequest request=new PropertyChangeRequest();
		request.setFileID(2);
		request.setProperty(p);
		OperationResponse resp=adminPanelPropService.deleteProperty(request);
		assertNotNull(resp);
		assertNull(resp.getError());
	}

	@Test
	public void testChangeProperty() {
		File f=new File();
		f.setId(2);
		f.setSelfName("file1");
		Property p=new Property();
		p.setId(30);
		p.setKey("key");
		p.setValue("value");
		when(fileRepository.findOneById(2)).thenReturn(f);
		when(propRepository.save(any(Property.class))).thenReturn(p);
		PropertyChangeRequest request=new PropertyChangeRequest();
		request.setFileID(2);
		request.setProperty(p);
		OperationResponse resp=adminPanelPropService.changeProperty(request);
		assertNotNull(resp);
		assertNull(resp.getError());
		assertEquals(f, p.getFileID());
	}

}
