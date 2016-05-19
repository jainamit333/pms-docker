package com.mmt.adminui.services;

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
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mmt.adminui.pojo.CreateVariantRequest;
import com.mmt.adminui.pojo.DeleteRequest;
import com.mmt.adminui.pojo.NotificationRequest;
import com.mmt.adminui.pojo.OperationResponse;
import com.mmt.adminui.pojo.SingleObNotificationResponse;
import com.mmt.entity.Directory;
import com.mmt.entity.File;
import com.mmt.entity.Observer;
import com.mmt.entity.Property;
import com.mmt.entity.Subscription;
import com.mmt.entity.Variant;
import com.mmt.propclient.notifier.PropertyChangeNotifier;
import com.mmt.repositories.FileRepository;
import com.mmt.repositories.ObserverRepository;
import com.mmt.repositories.PropertyRepository;
import com.mmt.repositories.SubscriptionRepository;
import com.mmt.repositories.VariantRepository;

import rx.Observable;
@RunWith(MockitoJUnitRunner.class)
public class AdminPanelMiscServiceTest {
	@Mock
	private SubscriptionRepository subsRepository;
	@Mock
	private FileRepository fileRepository;
	@Mock
	private ObserverRepository observerRepository;
	@Mock
	private VariantRepository variantRepository;
	@Mock
	private PropertyRepository propertyRepository;
	@Mock
	private PropertyChangeNotifier propertyChangeNotifier;
	@InjectMocks
	private AdminPanelMiscService adminPanelService;

	@Test
	public void testGetSubscriptionsForFile() {
		Observer ob=new Observer();
		ob.setId(34);
		ob.setIp("10.34.56.98");
		List<Subscription> subsList=new ArrayList<>();
		Subscription subs=new Subscription();
		subs.setObserver(ob);
		subs.setPrefix("http://");
		subs.setSuffix(":9090/pmsclient/notifypropchange");
		subs.setSubsQualifier("spicejet.properties");
		subsList.add(subs);
		when(subsRepository.findByFilesId(any())).thenReturn(subsList);
		List<Subscription> returned=adminPanelService.getSubscriptionsForFile(11);
		assertNotNull(returned);
		assertTrue(returned.size()==1);
		assertEquals("10.34.56.98",returned.get(0).getObserver().getIp());
		assertEquals("http://",returned.get(0).getPrefix());
		assertEquals(":9090/pmsclient/notifypropchange",returned.get(0).getSuffix());
		assertEquals("spicejet.properties",returned.get(0).getSubsQualifier());
	}

	@Test
	public void testGetAllObservers() {
		Observer ob=new Observer();
		ob.setId(34);
		ob.setIp("10.34.56.98");
		List<Observer> obList=new ArrayList<>();
		obList.add(ob);
		when(observerRepository.findAll()).thenReturn(obList);
		List<Observer> returned=adminPanelService.getAllObservers();
		assertNotNull(returned);
		assertEquals("10.34.56.98", returned.get(0).getIp());
		assertEquals(34,returned.get(0).getId());
	}

	@Test
	public void testCreateVariant() {
		when(variantRepository.isDuplicate(any(),any())).thenReturn(false);
		Directory root=new Directory();
		root.setId(1);
		root.setSelfName("/");
		File file=new File();
		file.setSelfName("file1");
		file.setId(10);
		file.setParentDir(root);
		Observer ob=new Observer();
		ob.setId(34);
		ob.setIp("10.34.56.98");
		List<Observer> obList=new ArrayList<>();
		obList.add(ob);
		Variant v=new Variant();
		v.setFileID(file);
		v.setId(40);
		v.setObservers(obList);
		Property p=new Property();
		p.setVariantID(v);
		p.setKey("testkey");
		p.setValue("testvalue");
		when(observerRepository.findOneByIp(any())).thenReturn(ob);
		when(fileRepository.findOneById(any())).thenReturn(file);
		when(variantRepository.save(any(Variant.class))).thenReturn(v);
		when(propertyRepository.save(any(Property.class))).thenReturn(p);
		CreateVariantRequest request=new CreateVariantRequest();
		request.setFileID(10);
		request.setObserverList(obList);
		ArrayList<Property> propList=new ArrayList<>();
		propList.add(p);
		request.setProperties(propList);
		OperationResponse resp=adminPanelService.createVariant(request);
		assertNotNull(resp);
		assertNull(resp.getError());
		request.setObserverList(new ArrayList<>());
		resp=adminPanelService.createVariant(request);
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("Variant Cannot be created without an observer", resp.getError());
		request.setObserverList(obList);
		when(variantRepository.isDuplicate(any(),any())).thenReturn(true);
		resp=adminPanelService.createVariant(request);
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("variant for file already exists for given observer(s)", resp.getError());
		when(variantRepository.isDuplicate(any(),any())).thenReturn(false);
		when(observerRepository.findOneByIp(any())).thenReturn(null);
		when(observerRepository.save(any(Observer.class))).thenReturn(ob);
		resp=adminPanelService.createVariant(request);
		assertNotNull(resp);
		assertNull(resp.getError());
		
		
	}

	@Test
	public void testGetVariantsForFile() {
		Directory root=new Directory();
		root.setId(1);
		root.setSelfName("/");
		File file=new File();
		file.setSelfName("file1");
		file.setId(10);
		file.setParentDir(root);
		when(fileRepository.findOneById(any())).thenReturn(file);
		Observer ob=new Observer();
		ob.setId(34);
		ob.setIp("10.34.56.98");
		List<Observer> obList=new ArrayList<>();
		obList.add(ob);
		Variant v=new Variant();
		v.setFileID(file);
		v.setId(40);
		v.setObservers(obList);
		Property p=new Property();
		p.setVariantID(v);
		p.setKey("testkey");
		p.setValue("testvalue");
		List<Property> pList=new ArrayList<>();
		pList.add(p);
		v.setProperties(pList);
		List<Variant> vList=new ArrayList<>();
		vList.add(v);
		when(variantRepository.findByfileID(any())).thenReturn(vList);
		List<Variant> returned=adminPanelService.getVariantsForFile(11);
		assertNotNull(returned);
		assertEquals(10, returned.get(0).getFileID().getId());
		assertEquals(40, returned.get(0).getId());
		assertEquals("10.34.56.98", returned.get(0).getObservers().get(0).getIp());
		assertEquals(34, returned.get(0).getObservers().get(0).getId());
	}

	@Test
	public void testDeleteVariant() {
		Directory root=new Directory();
		root.setId(1);
		root.setSelfName("/");
		File file=new File();
		file.setSelfName("file1");
		file.setId(10);
		file.setParentDir(root);
		when(fileRepository.findOneById(any())).thenReturn(file);
		Observer ob=new Observer();
		ob.setId(34);
		ob.setIp("10.34.56.98");
		List<Observer> obList=new ArrayList<>();
		obList.add(ob);
		Variant v=new Variant();
		v.setFileID(file);
		v.setId(40);
		v.setObservers(obList);
		Property p=new Property();
		p.setVariantID(v);
		p.setKey("testkey");
		p.setValue("testvalue");
		List<Property> pList=new ArrayList<>();
		pList.add(p);
		v.setProperties(pList);
		when(variantRepository.findOneById(any())).thenReturn(v);
		Mockito.doAnswer((invo)->null).when(variantRepository).delete(any(Integer.class));
		DeleteRequest deleteRequest=new DeleteRequest();
		deleteRequest.setId(40);
		OperationResponse resp=adminPanelService.deleteVariant(deleteRequest);
		assertNotNull(resp);
		assertNull(resp.getError());
	}

	@Test
	public void testNotifyObservers() {
		when(propertyChangeNotifier.notifyObservers(any(), any()))
				.thenReturn(Observable.just(new SingleObNotificationResponse()));
		NotificationRequest request = new NotificationRequest();
		request.setFileID(11);
		Observable<SingleObNotificationResponse> obresp = adminPanelService.notifyObservers(request);
		assertNotNull(obresp);
	}

}
