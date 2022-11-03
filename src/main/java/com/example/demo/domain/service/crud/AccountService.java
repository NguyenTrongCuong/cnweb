package com.example.demo.domain.service.crud;

import com.example.demo.domain.entity.Account;
import com.example.demo.infrastructure.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("accountService")
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account saveOrUpdate(Account account) {
        return this.accountRepository.save(account);
    }

    public List<Account> saveOrUpdateAll(Collection<Account> accounts) {
        return this.accountRepository.saveAll(accounts);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return this.accountRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean existsByUuid(String uuid) {
        return this.accountRepository.existsByUuid(uuid);
    }

    public Optional<Account> findByPhoneNumber(String phoneNumber) {
        return this.accountRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<Account> findById(Long id) {
        return this.accountRepository.findById(id);
    }

    public Optional<Account> findByIdWithLikedPostsLoadedEagerly(Long id) {
        return this.accountRepository.findByIdWithLikedPostsLoadedEagerly(id);
    }

}
