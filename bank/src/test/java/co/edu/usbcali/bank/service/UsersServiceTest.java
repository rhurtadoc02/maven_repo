package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.domain.Users;


@SpringBootTest
@Rollback(false)
class UsersServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(UsersServiceTest.class);

	
	@Autowired
	UserTypeService  userTypeService;
	
	@Autowired
	UsersService usersService;
	
	
	String email="rhc@gmail.com";
	
	@Test
	void fail() {
		log.info("fail");
		assertThrows(Exception.class, ()->{
			Users user = new Users();
			user.setName("");
			user.setEnable("SI");
			usersService.save(user);
			
		} );
	}
	
	@Test
	@DisplayName("Save")
	void atest() {
		
		
		Optional<Users> optionalUser= usersService.finById(email);
		assertFalse(optionalUser.isPresent(),"El usuario ya existe");
		
		
		Optional<UserType> userType= userTypeService.finById(1L); 
		assertTrue(userType.isPresent(),"No existe el tipo de usuario");
		
		Users user = new Users();
		
		user.setEnable("S");
		user.setName("Rodrigo H");
		user.setUserEmail("rhc@gmail.com");
		user.setUserType(userType.get());
		
		try {
			usersService.save(user);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
	}
	
	@Test
	@DisplayName("findById")
	void btest() {	
		Optional<Users> optionalUser= usersService.finById(email);
		assertTrue(optionalUser.isPresent(),"El usuario no existe");

	}
	
	@Test
	@DisplayName("Update")
	void ctest() {
		
		
		Optional<Users> optionalUser= usersService.finById(email);
		assertTrue(optionalUser.isPresent(),"El usuario no existe");
		
		Users user = optionalUser.get();
		
		user.setEnable("N");
		
		try {
			usersService.update(user);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
	}
	
	@Test
	@DisplayName("Delete")
	void dtest() {
		
		
		Optional<Users> optionalUser= usersService.finById(email);
		assertTrue(optionalUser.isPresent(),"El usuario no existe");
		
		Users user = optionalUser.get();
		
		try {
			usersService.delete(user);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
	}
	
	
	
	

}
