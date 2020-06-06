package co.edu.usbcali.bank.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Users;
import co.edu.usbcali.bank.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	Validator validator;

	
	@Override
	@Transactional(readOnly = true)
	public List<Users> findAll() {
		return usersRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Users> finById(String id) {
		return usersRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Users save(Users entity) throws Exception {
		validate(entity);
		if (usersRepository.findById(entity.getUserEmail()).isPresent()) {
			throw new Exception("el client con id: "+ entity.getUserEmail() + " ya existe");
		}
		return usersRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Users update(Users entity) throws Exception {
		validate(entity);
		return usersRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Users entity) throws Exception {
		if (entity == null) {
			throw new Exception("el usuario esta nulo");
		}
		Optional<Users> optionalUser = usersRepository.findById(entity.getUserEmail());

		if (optionalUser.isPresent() == false) {
			throw new Exception("el usuario:" + entity.getName() + "no existe");
		}

		entity = optionalUser.get();

		if (entity.getTransactions().size() > 0) {
			throw new Exception("el usuario :" + entity.getName() + " tiene transacciones asociadas");
		}
		usersRepository.delete(entity);
		
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		Optional<Users> optionalUser = usersRepository.findById(id);

		if (optionalUser.isPresent() == false) {
			throw new Exception("el usuario:" + id + "no existe");
		}

		Users entity = optionalUser.get();

		delete(entity);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {	
		return usersRepository.count();
	}

	@Override
	public void validate(Users user) throws Exception {

		if (user == null) {
			throw new Exception("El usuario esta nulo");
		}
		Set<ConstraintViolation<Users>> constraintViolations = validator.validate(user);

		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<Users> constraintViolation : constraintViolations) {
				strMessage.append(constraintViolation.getPropertyPath().toString());
				strMessage.append(" - ");
				strMessage.append(constraintViolation.getMessage());
				strMessage.append(". \n");
			}

			throw new Exception(strMessage.toString());
		}
		
	}

}
