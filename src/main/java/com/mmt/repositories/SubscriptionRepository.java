package com.mmt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mmt.entity.Observer;
import com.mmt.entity.Subscription;
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    public Subscription findOneById(Integer id);
    public List<Subscription> findByFilesId(Integer file);
    public Subscription findOneBySubsQualifier(String qualifier);
    @Query("Select count(*)>0 From Subscription subs inner join subs.observer ob where ob = :obList")
    public Boolean hasSubscription(@Param("obList")Observer ob);
    public List<Subscription> findByFilesIdAndObserverIpIn(Integer fileID,List<String> observers);
    public Subscription findOneBySubsQualifierAndObserverIp(String qualifier,String ip);
}
