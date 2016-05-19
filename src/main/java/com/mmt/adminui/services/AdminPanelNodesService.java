package com.mmt.adminui.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmt.adminui.pojo.CopyOrMoveRequest;
import com.mmt.adminui.pojo.CreateNodeRequest;
import com.mmt.adminui.pojo.DeleteRequest;
import com.mmt.adminui.pojo.NodeOperationResponse;
import com.mmt.adminui.pojo.RenameNodeRequest;
import com.mmt.entity.Directory;
import com.mmt.entity.File;
import com.mmt.entity.FileSystemObject;
import com.mmt.entity.Property;
import com.mmt.repositories.DirectoryRepository;
import com.mmt.repositories.FileRepository;
import com.mmt.repositories.FileSystemRepository;
import com.mmt.repositories.PropertyRepository;

@Service
public class AdminPanelNodesService {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private FileSystemRepository fileSystemRepository;
	@Autowired
	private DirectoryRepository directoryRepository;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private PropertyRepository propertyRepository;

	public List<FileSystemObject> getChildren(String nodeID) {
		List<FileSystemObject> fileSystemObjects;
		if (nodeID != null && !nodeID.isEmpty()) {
			if(nodeID.equals("#")){
				  fileSystemObjects=new ArrayList<>();
				  fileSystemObjects.add(returnRootNode());
				}
			else{
			 fileSystemObjects = fileSystemRepository.findByParentDir(directoryRepository.findOneById(Integer.parseInt(nodeID)));
			}
		
		} else {
			fileSystemObjects = fileSystemRepository.findByParentDir(directoryRepository.findOneByParentDirIsNull());
		}
		return fileSystemObjects;
	}
	private FileSystemObject returnRootNode(){
		Directory root= directoryRepository.findOneByParentDirIsNull();
		HashMap<String,Boolean> stateMap=new HashMap<>();
		stateMap.put("opened",true);
		stateMap.put("disabled",true);
		root.setStateMap(stateMap);
		return root;
		
	}
	
  
  @Transactional
  public NodeOperationResponse addNode(CreateNodeRequest nodeCreateRequest){
	  NodeOperationResponse resp=new NodeOperationResponse();
	  Directory parentDir=directoryRepository.findOneById(nodeCreateRequest.getParentid());
	  if(parentDir==null){
		  resp.setError("Parent Directory Cannot be null");
		  return resp;
		  }
	  switch(nodeCreateRequest.getType()){
	  case folder:
		  Directory newDir= new Directory();
		  newDir.setSelfName(nodeCreateRequest.getSelfName());
		  newDir.setParentDir(parentDir);
		  newDir=directoryRepository.save(newDir);
		  resp.setId(newDir.getId());
		  break;
	  case file:
		  File newFile=new File();
		  String name=nodeCreateRequest.getSelfName();
		  newFile.setSelfName(name);
		  newFile.setParentDir(parentDir);
		  newFile=fileRepository.save(newFile);
		  resp.setId(newFile.getId());
		  break;
	  
	  }
	  return resp;
  }
  @Transactional
  public NodeOperationResponse renameNode(RenameNodeRequest request){
	  NodeOperationResponse resp=new NodeOperationResponse();
	  FileSystemObject fileOrDir=fileSystemRepository.findOneById(request.getId());
	  String name=request.getNewName();
	  fileOrDir.setSelfName(name);
	  fileOrDir=fileSystemRepository.save(fileOrDir);
	  resp.setId(fileOrDir.getId());
	  return resp;
  }
  @Transactional
  public NodeOperationResponse moveNode(CopyOrMoveRequest request){
	 NodeOperationResponse resp=new NodeOperationResponse();
	 FileSystemObject fileOrDir=fileSystemRepository.findOneById(request.getId());
	 Directory parentDir=directoryRepository.findOneById(request.getParent());
	 fileOrDir.setParentDir(parentDir);
	 fileOrDir=fileSystemRepository.save(fileOrDir);
	 resp.setId(fileOrDir.getId());
	 return resp;
  }
  @Transactional
  public NodeOperationResponse copyNode(CopyOrMoveRequest request){
	     NodeOperationResponse resp=new NodeOperationResponse();
		 FileSystemObject fileOrDir=fileSystemRepository.findOneById(request.getId());
		 Directory parentDir=directoryRepository.findOneById(request.getParent());
		 if(parentDir.getId()==fileOrDir.getParentDir().getId()){
			 resp.setError("Cannot copy inside same parent");
			 return resp;
			 }
		 FileSystemObject newObject = null;
		 if(fileOrDir instanceof File){
			 newObject=copyFile((File)fileOrDir, parentDir);
		 }else if(fileOrDir instanceof Directory){
			 newObject=copyDirectory((Directory)fileOrDir, parentDir);
		 }
		 resp.setId(newObject.getId());
		 return resp;
  }
  private File copyFile(File file,Directory parentDir){
		 List<Property> properties=file.getProperties();
		// below list is created because the above properties list
		// contains proxy objects
		 List<Property> copiedProps=new ArrayList<>();
		 //initialize the collection
		 properties.size();
		 entityManager.detach(file);
		 file.setId(0);
		 file.setParentDir(parentDir);
		 for(Property property:properties){
			 entityManager.detach(property);
			 property.setId(0);
			 copiedProps.add(property);
			 propertyRepository.save(property);
		 }
         file.setProperties(copiedProps);
		 return fileRepository.save(file);
  }
  private Directory copyDirectory(Directory directory,Directory parentDir){
	  List<FileSystemObject> subObjects=directory.getSubObjects();
	  Directory directoryCopy=new Directory();
	  directoryCopy.setParentDir(parentDir);
	  directoryCopy.setSelfName(directory.getSelfName());
	  directoryCopy=directoryRepository.save(directoryCopy);
	  for(FileSystemObject subObject:subObjects){
		  if(subObject instanceof File){
			  copyFile((File)subObject, directoryCopy);
		  }else if(subObject instanceof Directory){
		      copyDirectory((Directory)subObject, directoryCopy);
		  }
	  }
	 return directoryCopy;
	  
  }
  @Transactional
  public NodeOperationResponse deleteNode(DeleteRequest request){
	   NodeOperationResponse resp=new NodeOperationResponse();
	   FileSystemObject fileorDir=fileSystemRepository.findOneById(request.getId());
	   fileSystemRepository.delete(fileorDir);
	   return resp;
  }
}
