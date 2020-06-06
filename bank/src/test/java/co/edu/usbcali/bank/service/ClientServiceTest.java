package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.DocumentType;


@SpringBootTest
@Rollback(false)
class ClientServiceTest {

	
	private static final Logger log = LoggerFactory.getLogger(ClientServiceTest.class);

	Long clieId= 6060L;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	DocumentTypeService documentTypeService;
	
	@Test
	void fail() {
		log.info("fail");
		assertThrows(Exception.class, ()->{
			Client client = new Client();
			client.setAdress("CRA");
			client.setClieId(clieId);
			client.setDocumentType(null);
			client.setEmail("rhc@gmail");
			client.setEnable("SI");
			client.setName("");
			client.setPhone("");
			clientService.save(client);
		} );
	}
	
	/*@BeforeAll
	void beforeAll() {
		assertNotNull(clientService,"The clientService is Null");
	}*/
	
	
	@Test
	@DisplayName("Save")
	void atest() {
		Optional<Client> optionalClient= clientService.finById(clieId);
		assertFalse(optionalClient.isPresent(),"Ya existe el cliente");
		
		Client client = new Client();
		client.setAdress("calle 70 25");
		client.setClieId(clieId);
		client.setDocumentType(null);
		client.setEmail("rhc@gmail.com");
		client.setEnable("S");
		client.setName("Rodrigo H");
		client.setPhone("+57 311 548 47");
		
		Optional<DocumentType> optionalDocumentType = documentTypeService.finById(1L);
		assertTrue(optionalDocumentType.isPresent(),"No existe el tipo de documento");
		
		
		client.setDocumentType(optionalDocumentType.get());
		
		try {
			clientService.save(client);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
		
	}
	
	
	
	@Test
	@DisplayName("findbyId")
	void btest() {
		
		Optional<Client> optionalClient= clientService.finById(clieId);
		assertTrue(optionalClient.isPresent(),"No existe el cliente");
	}	
	
	@Test
	@DisplayName("update")
	void ctest() {
		
		Optional<Client> optionalClient= clientService.finById(clieId);
		assertTrue(optionalClient.isPresent(),"No existe el cliente");
		
		Client client = optionalClient.get();
		client.setEnable("N");	
		try {
			clientService.update(client);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
		
	}
	
	@Test
	@DisplayName("delete")
	void dtest() {
		
		Optional<Client> optionalClient= clientService.finById(clieId);
		assertTrue(optionalClient.isPresent(),"No existe el cliente");
		
		Client client=optionalClient.get();
		
		try {
			clientService.delete(client);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
		
		
	}
	
	
	
	
}
