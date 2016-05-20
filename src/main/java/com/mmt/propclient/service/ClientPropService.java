package com.mmt.propclient.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class ClientPropService {
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private ObserverRepository observerRepository;
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private VariantRepository variantRepo;
    @Transactional
	public String getPropertyFile(Integer id,String observerIP) {
		    File file=fileRepository.findOneById(id);
		    if(file==null){
		    	throw new RuntimeException("Property File not present");
		    }
		    
		    
		    Variant variant = null;
		    variant=variantRepo.findOneByfileIDAndObserversIp(file, observerIP);
			StringBuilder sb = new StringBuilder();
			for (Property prop : variant==null?file.getProperties():variant.getProperties()) {
				sb.append(prop.getKey()).append('=').append(prop.getValue()).append('\n');
			}
			return sb.toString();
		
	}

	@Transactional
	public ClientPropsOPResponse createSubscription(String observerIp, String prefix, String suffix, String qualifier,
			Integer[] fileIDs) {
		ClientPropsOPResponse clientResponse = new ClientPropsOPResponse();
		Observer obr = observerRepository.findOneByIp(observerIp);
		if (obr == null){
		    obr=new Observer();
		    obr.setIp(observerIp);
			obr = observerRepository.save(obr);
		}
		ArrayList<File> fList = new ArrayList<>(fileIDs.length);
		for (Integer id : fileIDs) {
			
				File file =  fileRepository.findOneById(id);
				fList.add(file);
				if(file==null){
				clientResponse.setError("File "+id + " Does Not exist");
				return clientResponse;
				}
		}
		Subscription subs=subscriptionRepository.findOneBySubsQualifierAndObserverIp(qualifier, observerIp);
		if(subs==null)
			subs=new Subscription();
		subs.setObserver(obr);
		subs.setSubsQualifier(qualifier);
		subs.setPrefix(prefix);
		subs.setSuffix(suffix);
		subs.setFiles(fList);
		subscriptionRepository.save(subs);
		return clientResponse;
	}

	@Transactional
	public ClientPropsOPResponse unSubscribe(String qualifier, String observerIP) {
		ClientPropsOPResponse opResp=new ClientPropsOPResponse();
		Subscription subs=subscriptionRepository.findOneBySubsQualifierAndObserverIp(qualifier, observerIP);
		if(subs==null)
			opResp.setError("No Subscription found for given qualifier "+qualifier);
		subscriptionRepository.delete(subs);
		return opResp;
	}
}
