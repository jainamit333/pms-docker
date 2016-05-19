package com.mmt.adminui.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmt.adminui.pojo.OperationResponse;
import com.mmt.adminui.pojo.PropertyChangeRequest;
import com.mmt.adminui.pojo.PropertyOperationResponse;
import com.mmt.entity.File;
import com.mmt.entity.Property;
import com.mmt.repositories.FileRepository;
import com.mmt.repositories.PropertyRepository;

@Service
public class AdminPanelPropertiesService {
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private PropertyRepository propRepository;
	
    @Transactional
	public List<Property> getPropertiesFromFile(int id){
		File file=fileRepository.findOneById(id);
		List<Property> properties=file.getProperties();
		//initialize a lazy loading collection by calling size
		properties.size();
		return properties;
	}
    
    @Transactional
    public OperationResponse addProperty(PropertyChangeRequest request){
    	File propFile=fileRepository.findOneById(request.getFileID());
    	Property p=request.getProperty();
    	p.setFileID(propFile);
    	propRepository.save(p);
    	PropertyOperationResponse resp=new PropertyOperationResponse();
    	resp.setProperty(p);
    	return resp;
    }
    @Transactional
    public OperationResponse deleteProperty(PropertyChangeRequest request){
    	File propFile=fileRepository.findOneById(request.getFileID());
    	Property p=request.getProperty();
    	p.setFileID(propFile);
        propRepository.delete(p);
    	PropertyOperationResponse resp=new PropertyOperationResponse();
    	resp.setProperty(p);
    	return resp;
    }
    @Transactional
    public OperationResponse changeProperty(PropertyChangeRequest request){
    	File propFile=fileRepository.findOneById(request.getFileID());
    	Property p=request.getProperty();
    	p.setFileID(propFile);
    	/*we can call save directory since property will have id set from ui*/
        propRepository.save(p);
    	PropertyOperationResponse resp=new PropertyOperationResponse();
    	resp.setProperty(p);
    	return resp;
    }
}
