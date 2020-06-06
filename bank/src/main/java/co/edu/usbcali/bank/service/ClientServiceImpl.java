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

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.repository.ClientRepository;
import co.edu.usbcali.bank.repository.DocumentTypeRepository;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	DocumentTypeRepository documentTypeRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return clientRepository.findAll();

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Client> finById(Long id) {

		return clientRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Client save(Client entity) throws Exception {
		validate(entity);
		if (clientRepository.findById(entity.getClieId()).isPresent()) {
			throw new Exception("el client con id: "+ entity.getClieId() + " ya existe");
		}
		return clientRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Client update(Client entity) throws Exception {
		validate(entity);
		return clientRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Client entity) throws Exception {
		if (entity == null) {
			throw new Exception("el cliente esta nulo");
		}
		Optional<Client> optionalClient = clientRepository.findById(entity.getClieId());

		if (optionalClient.isPresent() == false) {
			throw new Exception("el cliente con id:" + entity.getClieId() + "no existe");
		}

		entity = optionalClient.get();

		if (entity.getAccounts().size() > 0) {
			throw new Exception("el cliente con id:" + entity.getClieId() + " tiene cuentas asociadas");
		}

		if (entity.getRegisteredAccounts().size() > 0) {
			throw new Exception("el cliente con id:" + entity.getClieId() + " tiene cuentas registradas");
		}

		clientRepository.delete(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		Optional<Client> optionalClient = clientRepository.findById(id);

		if (optionalClient.isPresent() == false) {
			throw new Exception("el cliente con id:" + id + "no existe");
		}

		Client entity = optionalClient.get();

		delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {

		return clientRepository.count();
	}

	@Override
	public void validate(Client client) throws Exception {

		if (client == null) {
			throw new Exception("El cliente esta nulo");
		}
		Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);

		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<Client> constraintViolation : constraintViolations) {
				strMessage.append(constraintViolation.getPropertyPath().toString());
				strMessage.append(" - ");
				strMessage.append(constraintViolation.getMessage());
				strMessage.append(". \n");
			}

			throw new Exception(strMessage.toString());
		}

	}

}
