package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.RegisteredAccount;

@SpringBootTest
@Rollback(false)
class RegisteredAccountRepositoryTest {

	@Autowired
	RegisteredAccountRepository registeredAccountRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Test
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void atest() {
		
		RegisteredAccount registeredAccount = new RegisteredAccount();
		
		Optional<Account> accountOptional = accountRepository.findById("4640-0341-9387-5781");
		accountOptional.ifPresent(account->{registeredAccount.setAccount(account);});
		
		Optional<Client> clientOptional= clientRepository.findById(1L);
		clientOptional.ifPresent(client->{registeredAccount.setClient(client);});
		
		registeredAccount.setEnable("S");
		registeredAccount.setReacId(0L);
		
		
		registeredAccountRepository.save(registeredAccount);
		
	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = true)
	void btest() {
		
		Optional<RegisteredAccount> registeredAccount = registeredAccountRepository.findById(1L);
		assertTrue(registeredAccount.isPresent(),"la cuenta esta registrada");
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false)
	void ctest() {
		Optional<RegisteredAccount> registeredAccount = registeredAccountRepository.findById(5L);
		assertTrue(registeredAccount.isPresent(),"la cuenta esta registrada");
		
		registeredAccount.ifPresent(registeredAcc->{
			registeredAcc.setEnable("N");
			registeredAccountRepository.save(registeredAcc);
		});
	}
	

	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false)
	void dtest() {
		Optional<RegisteredAccount> registeredAccount = registeredAccountRepository.findById(6L);
		//assertTrue(registeredAccount.isPresent(),"la cuenta esta registrada");
		
		registeredAccount.ifPresent(registeredAcc->{
			registeredAccountRepository.delete(registeredAcc);
		});
		
	}
	

}
