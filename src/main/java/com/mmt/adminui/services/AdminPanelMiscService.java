package com.mmt.adminui.services;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmt.adminui.pojo.CreateVariantRequest;
import com.mmt.adminui.pojo.DeleteRequest;
import com.mmt.adminui.pojo.NotificationRequest;
import com.mmt.adminui.pojo.OperationResponse;
import com.mmt.adminui.pojo.SingleObNotificationResponse;
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

@Service
public class AdminPanelMiscService {

	@Autowired
	private SubscriptionRepository subsRepository;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private ObserverRepository observerRepository;
	@Autowired
	private VariantRepository variantRepository;
	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private PropertyChangeNotifier propertyChangeNotifier;

	public List<Subscription> getSubscriptionsForFile(Integer fileId) {
		return subsRepository.findByFilesId(fileId);
	}

	public List<Observer> getAllObservers() {
		return observerRepository.findAll();
	}

	@Transactional
	public OperationResponse createVariant(CreateVariantRequest request) {
		OperationResponse resp=new OperationResponse();
		List<Observer> obList = request.getObserverList();
		if (obList.size() <= 0){
		     resp.setError("Variant Cannot be created without an observer");
		     return resp;
			}
		List<String> obIPList = new ArrayList<String>(obList.size());
		for (Observer ob : obList)
			obIPList.add(ob.getIp());
		File file = fileRepository.findOneById(request.getFileID());
		if (variantRepository.isDuplicate(file, obIPList)){
			 resp.setError("variant for file already exists for given observer(s)");
		     return resp;
			}
		ListIterator<Observer> obListIter = obList.listIterator();
		while (obListIter.hasNext()) {
			Observer ob = obListIter.next();
			Observer obPersisted = observerRepository.findOneByIp(ob.getIp());
			if (obPersisted == null)
				obPersisted = observerRepository.save(ob);
			obListIter.set(obPersisted);
		}

		Variant v = new Variant();
		v.setFileID(file);
		v.setObservers(obList);
		variantRepository.save(v);
		for (Property prop : request.getProperties()) {
			prop.setId(0);
			prop.setVariantID(v);
			propertyRepository.save(prop);
		}
		return resp;
	}

	@Transactional
	public List<Variant> getVariantsForFile(Integer fileId) {
		File file = fileRepository.findOneById(fileId);
		List<Variant> variants = variantRepository.findByfileID(file);
		// initialize lazy loading properties
		for (Variant v : variants) {
			v.getProperties().size();
			v.getObservers().size();
		}
		return variants;
	}
    @Transactional
	public OperationResponse deleteVariant(DeleteRequest request) {
		Variant v = variantRepository.findOneById(request.getId());
		for (Observer ob : v.getObservers()) {
			if (!subsRepository.hasSubscription(ob)) {
				observerRepository.delete(ob);
			}
		}
		variantRepository.delete(request.getId());
		return new OperationResponse();
	}

	public Observable<SingleObNotificationResponse> notifyObservers(NotificationRequest request) {
		return propertyChangeNotifier.notifyObservers(request.getFileID(),request.getObserverList());
	}

}
