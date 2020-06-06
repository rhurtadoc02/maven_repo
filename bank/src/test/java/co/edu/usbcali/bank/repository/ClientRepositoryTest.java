package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.DocumentType;

@SpringBootTest
@Rollback(false)
public class ClientRepositoryTest {
	
	final static Logger log = LoggerFactory.getLogger(ClientRepositoryTest.class);
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	
	Long clieId=4040L;
	
	@BeforeEach
	void beforeEach() {
		log.info("se ejecute el beforeEach");
		assertNotNull(clientRepository, "the clientRepository is null");
		assertNotNull(documentTypeRepository, "the documentTypeRepository is null");
	}
	@AfterEach
	void afterEach() {
		log.info("se ejecute el afterEach");
	}
	
	@Test
	@DisplayName("Save")
	void atest() {
		log.info("se ejecuto el Save");
		Optional<Client> clientOptional = clientRepository.findById(clieId);
		assertFalse(clientOptional.isPresent(),"el cliente no existe");
		
        Client client = new Client();
		
		client.setClieId(clieId);
		client.setAdress("avenida siempre viva 123");
		client.setEmail("jhdfs@gmail.com");
		client.setEnable("S");
		client.setName("Homero j Simpsion");
		client.setPhone("+551 31311544");
		
		Optional<DocumentType> documentOptional = documentTypeRepository.findById(1L);
		DocumentType documentType=documentOptional.get();
		
		client.setDocumentType(documentType);
		
		clientRepository.save(client);
		
		
	}
	
	
	@Test
	@DisplayName("findById")
	void btest() {
		log.info("se ejecuto el findById");
		Optional<Client> clientOptional = clientRepository.findById(clieId);
		assertTrue(clientOptional.isPresent(),"el cliente existe");

	}
	
	
	@Test
	@DisplayName("update")
	void ctest() {
		log.info("se ejecuto el update");
		Optional<Client> clientOptional = clientRepository.findById(clieId);
		assertTrue(clientOptional.isPresent(),"el cliente existe");
		
		clientOptional.ifPresent(client->{
			client.setName("pedro perez");
			clientRepository.save(client);
			
		});

	}
	
	@Test
	@DisplayName("delete")
	void dtest() {
		log.info("se ejecuto el delete");
		Optional<Client> clientOptional = clientRepository.findById(clieId);
		assertTrue(clientOptional.isPresent(),"el cliente existe");
		
		clientOptional.ifPresent(client->{
			clientRepository.delete(client);
			
		});

	}


}
