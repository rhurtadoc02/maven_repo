package co.edu.usbcali.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.bank.domain.DocumentType;
import co.edu.usbcali.bank.dto.DocumentTypeDTO;
import co.edu.usbcali.bank.mapper.DocumentTypeMapper;
import co.edu.usbcali.bank.service.DocumentTypeService;

@RestController
@RequestMapping("/api/documentType/")
@CrossOrigin("*")
public class DocumentTypeController {

	@Autowired
	DocumentTypeService documentTypeService;
	
	@Autowired
	DocumentTypeMapper documentTypeMapper;
	
	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		List<DocumentType> documentTypes= documentTypeService.findAll();
		
		List<DocumentTypeDTO> documentTypeDTOs=documentTypeMapper.toDocumentTypeDTOs(documentTypes);
		
		return ResponseEntity.ok(documentTypeDTOs);
		
	}
}
