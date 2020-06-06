package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.domain.Users;

@SpringBootTest
@Rollback(false)
class UsersRepositoryTest {
	
	
	private static final Logger log = LoggerFactory.getLogger(UsersRepositoryTest.class);

	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	UserTypeRepository userTypeRepository;
	
	String email="rhc@gmail.com";

	@Test
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void atest() {
		
		log.info("se ejecuto el Save");
		Optional<Users>  userOptional= usersRepository.findById(email);
		assertFalse(userOptional.isPresent(),"El usuario ya existe");
		
		
		Optional<UserType> optionalUser= userTypeRepository.findById(1L);
		
		Users user= new Users();
		user.setName("Rodrigo");
		user.setEnable("S");
		user.setUserEmail(email);
		optionalUser.ifPresent(userType->{user.setUserType(userType);});
		
		usersRepository.save(user);
		
	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = true)
	void btest() {
		log.info("se ejecuto el findById");
		Optional<Users>  userOptional= usersRepository.findById(email);
		assertTrue(userOptional.isPresent(),"El usuario ya existe");

	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false)
	void ctest() {
		log.info("se ejecuto el update");
		Optional<Users>  userOptional= usersRepository.findById(email);
		assertTrue(userOptional.isPresent(),"El usuario ya existe");
		
		userOptional.ifPresent(user->{
			user.setName("Rodrigo H");
			usersRepository.save(user);
		});
		
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false)
	void dtest() {
		log.info("se ejecuto el delete");
		Optional<Users>  userOptional= usersRepository.findById(email);
		assertTrue(userOptional.isPresent(),"El usuario existe");
		
		userOptional.ifPresent(user->{
			usersRepository.delete(user);
		});
		
	}

}
