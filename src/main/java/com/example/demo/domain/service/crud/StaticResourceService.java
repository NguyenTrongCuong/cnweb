package com.example.demo.domain.service.crud;

import com.example.demo.domain.entity.StaticResource;
import com.example.demo.infrastructure.repository.StaticResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service("staticResourceService")
public class StaticResourceService {

    @Autowired
    private StaticResourceRepository staticResourceRepository;

    public StaticResource saveOrUpdate(StaticResource staticResource) {
        return this.staticResourceRepository.save(staticResource);
    }

    public List<StaticResource> saveOrUpdateAll(Collection<StaticResource> staticResources) {
        return this.staticResourceRepository.saveAll(staticResources);
    }

    public void deleteAll(Collection<StaticResource> staticResources) {
        this.staticResourceRepository.deleteAll(staticResources);
    }

}
