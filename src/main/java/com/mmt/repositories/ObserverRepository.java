package com.mmt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmt.entity.Observer;
@Repository
public interface ObserverRepository extends JpaRepository<Observer, Integer> {
    public Observer findOneById(Integer id);
    public Observer findOneByIp(String ip);
}
