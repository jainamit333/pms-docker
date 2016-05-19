package com.mmt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mmt.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
	public File findOneById(Integer id);

	public List<File> findBySelfName(String name);

	public Long countBySelfName(String name);
	
    //regex is not supported by jpa query so native query is used below
	@Query(value = "select max(substring(identifier,char_length(identifier),1)) from filesystem where identifier regexp ?#{[0]+'[0-9]*'}", nativeQuery = true)
	public String findMaxIdentifierByFileName(String fileName);
}
