package com.mmt.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmt.entity.Directory;
import com.mmt.entity.FileSystemObject;
@Repository
public interface FileSystemRepository extends JpaRepository<FileSystemObject,Integer> {
	public FileSystemObject findBySelfName(String selfName);
    public FileSystemObject findOneById(Integer id);
    public List<FileSystemObject> findByParentDir(Directory parentDir);
    public FileSystemObject findOneBySelfNameAndParentDir(String selfName,Directory parentDir);
}