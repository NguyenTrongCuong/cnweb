package com.example.demo.domain.service.crud;

import com.example.demo.domain.entity.Profile;
import com.example.demo.infrastructure.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("profileService")
@Transactional
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile saveOrUpdate(Profile profile) {
        return this.profileRepository.save(profile);
    }

    public List<Profile> saveOrUpdateAll(Collection<Profile> profiles) {
        return this.profileRepository.saveAll(profiles);
    }

    public Optional<Profile> findByAccountPhoneNumber(String phoneNumber) {
        return this.profileRepository.findByAccountPhoneNumber(phoneNumber);
    }

    public Optional<Profile> findByAccountId(Long id) {
        return this.profileRepository.findByAccountId(id);
    }

}
