package com.mmt.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmt.entity.Directory;
@Repository
public interface DirectoryRepository extends JpaRepository<Directory,Integer> {
    public Directory findOneById(Integer id);
    public Directory findOneByParentDirIsNull();
}
