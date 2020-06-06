package co.edu.usbcali.bank.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Rollback(false)
class ClientTest {
	
	@PersistenceContext
	EntityManager entityManager;

	Long clieId=4040L;
	
	@Test
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void test() {
		assertNotNull(entityManager);
		Client client = entityManager.find(Client.class,clieId);
		
		assertNull(client,"El cliente ya existe");
		
		client = new Client();
		
		client.setClieId(clieId);
		client.setAdress("avenida siempre viva 123");
		client.setEmail("jhdfs@gmail.com");
		client.setEnable("S");
		client.setName("Homero j Simpsion");
		client.setPhone("+551 31311544");
		
		DocumentType documentType = entityManager.find(DocumentType.class, 1L);
		
		assertNotNull(documentType,"El documentType no existe");
		client.setDocumentType(documentType);
		
		entityManager.persist(client);
	}
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = true)
	void btest() {
		assertNotNull(entityManager);
		Client client = entityManager.find(Client.class,clieId);
		
		assertNotNull(client,"El cliente ya existe");
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false)
	void ctest() {
		assertNotNull(entityManager);
		Client client = entityManager.find(Client.class,clieId);
		
		assertNotNull(client,"El cliente ya existe");
		
		client.setName("Diego Gomez");
		
		entityManager.merge(client);
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false)
	void dtest() {
		assertNotNull(entityManager);
		Client client = entityManager.find(Client.class,clieId);
		
		assertNotNull(client,"El cliente ya existe");
	
		entityManager.remove(client);
	}

}
