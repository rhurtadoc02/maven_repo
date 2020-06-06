package co.edu.usbcali.bank.repository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.DocumentType;

@SpringBootTest
@Rollback(false)
class DocumentTypeRepositoryTest {

	final static Logger log= LoggerFactory.getLogger(DocumentTypeRepositoryTest.class);
	
	@Autowired
	DocumentTypeRepository documentTypeRepository;

	@Test
	@Transactional(readOnly = false)
	void test() {
		DocumentType documentType = new DocumentType();
		
		documentType.setDotyId(null);
		documentType.setName("Test");
		documentType.setEnable("S");
		
		
		documentType = documentTypeRepository.save(documentType);
		
		log.info("id:{}", documentType.getDotyId());
		
	}

}
