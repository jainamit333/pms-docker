package com.mmt.propclient.service;

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

import com.mmt.entity.File;
import com.mmt.entity.Observer;
import com.mmt.entity.Property;
import com.mmt.entity.Subscription;
import com.mmt.entity.Variant;
import com.mmt.propclient.pojo.ClientPropsOPResponse;
import com.mmt.repositories.FileRepository;
import com.mmt.repositories.ObserverRepository;
import com.mmt.repositories.SubscriptionRepository;
import com.mmt.repositories.VariantRepository;
@RunWith(MockitoJUnitRunner.class)
public class ClientPropServiceTest {
	@Mock
	private FileRepository fileRepository;
	@Mock
	private ObserverRepository observerRepository;
	@Mock
	private SubscriptionRepository subscriptionRepository;
	@Mock
	private VariantRepository variantRepo;
	@InjectMocks
	private ClientPropService clientPropService;

	@Test
	public void testGetPropertyFile() {
		File f=new File();
		f.setId(2);
		f.setSelfName("file1");
		List<Property> propList=new ArrayList<>();
		Property p=new Property();
		p.setFileID(f);
		p.setKey("testkey");
		p.setValue("testvalue");
		propList.add(p);
		f.setProperties(propList);
		when(fileRepository.findOneById(any())).thenReturn(f);
		when(variantRepo.findOneByfileIDAndObserversIp(any(),any())).thenReturn(null);
		String propFile=clientPropService.getPropertyFile(2,"172.16.23.45");
		assertNotNull(propFile);
		assertEquals("testkey=testvalue\n",propFile);
		when(fileRepository.findOneById(any())).thenReturn(null);
		try{
		propFile=clientPropService.getPropertyFile(2,"172.16.23.45");
		}catch(RuntimeException ex){
			assertEquals("Property File not present", ex.getMessage());
		}
	}
	@Test
	public void testGetPropertyFilewithVariant(){
		File f=new File();
		f.setId(2);
		f.setSelfName("file1");
		Variant v=new Variant();
		v.setFileID(f);
		List<Property> propList=new ArrayList<>();
		Property p=new Property();
		p.setVariantID(v);
		p.setKey("testkey");
		p.setValue("testvalue");
		propList.add(p);
		v.setProperties(propList);
		when(fileRepository.findOneById(any())).thenReturn(f);
		when(variantRepo.findOneByfileIDAndObserversIp(any(),any())).thenReturn(v);
		String propFile=clientPropService.getPropertyFile(2,"172.16.23.45");
		assertNotNull(propFile);
		assertEquals("testkey=testvalue\n",propFile);
	}

	@Test
	public void testCreateSubscription() {
		File f=new File();
		f.setId(2);
		f.setSelfName("file1");
		Observer ob=new Observer();
		ob.setId(30);
		ob.setIp("172.16.23.45");
		when(fileRepository.findOneById(11)).thenReturn(f);
		when(fileRepository.findOneById(12)).thenReturn(f);
		when(observerRepository.findOneByIp("172.16.23.45")).thenReturn(ob);
		when(subscriptionRepository.save(any(Subscription.class))).thenReturn(new Subscription());
		ClientPropsOPResponse opresp=clientPropService.createSubscription("172.16.23.45", "http://",":9090/pmsclient/notifypropchange", "spiceject.properties",new Integer[]{11,12});
	    assertNotNull(opresp);
	    assertNull(opresp.getError());
	    when(observerRepository.findOneByIp("172.16.23.45")).thenReturn(null);
	    when(observerRepository.save(any(Observer.class))).thenReturn(ob);
	    opresp=clientPropService.createSubscription("172.16.23.45", "http://",":9090/pmsclient/notifypropchange", "spiceject.properties",new Integer[]{11,12});
	    assertNotNull(opresp);
	    assertNull(opresp.getError());
	    when(fileRepository.findOneById(11)).thenReturn(null);
	    opresp=clientPropService.createSubscription("172.16.23.45", "http://",":9090/pmsclient/notifypropchange", "spiceject.properties",new Integer[]{11,12});
	    assertNotNull(opresp);
	    assertNotNull(opresp.getError());
	    assertEquals("File 11 Does Not exist", opresp.getError());
	}

	@Test
	public void testUnSubscribe() {
		Subscription s=new Subscription();
		s.setId(300);
		when(subscriptionRepository.findOneBySubsQualifierAndObserverIp("spicejet.properties", "172.16.23.45")).thenReturn(s);
		doAnswer((invo)->null).when(subscriptionRepository).delete(300);
		ClientPropsOPResponse resp=clientPropService.unSubscribe("spicejet.properties", "172.16.23.45");
		assertNotNull(resp);
		assertNull(resp.getError());
		when(subscriptionRepository.findOneBySubsQualifierAndObserverIp("spicejet.properties", "172.16.23.45")).thenReturn(null);
		resp=clientPropService.unSubscribe("spicejet.properties", "172.16.23.45");
		assertNotNull(resp);
		assertNotNull(resp.getError());
		assertEquals("No Subscription found for given qualifier spicejet.properties",resp.getError());
	}

}
