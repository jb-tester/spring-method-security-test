package com.mytests.spring.security.methodsecuritytest.repositories;

import com.mytests.spring.security.methodsecuritytest.data.SampleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * *
 * <p>Created by irina on 12/16/2021.</p>
 * <p>Project: spring-method-security-test</p>
 * *
 */
public interface SampleRepository extends CrudRepository<SampleEntity,Long> {

     List<SampleEntity> findAllByVersionAndColor(Integer version, String color);
     List<SampleEntity> versionAndColor(Integer version, String color);
     SampleEntity findFirstByColor(String color);
}
