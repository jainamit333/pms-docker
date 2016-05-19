package com.mmt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mmt.entity.File;
import com.mmt.entity.Variant;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> {
	public Variant findOneById(Integer id);
	@Query("select count(*) > 0 from Variant v inner join v.observers ob where v.fileID=:file and ob.ip in :observers")
	public Boolean isDuplicate(@Param("file")File file,@Param("observers")List<String> observersIps);
	public Variant findOneByfileIDAndObserversIp(File fileId,String ob);
	public List<Variant> findByfileID(File fileId);
}
