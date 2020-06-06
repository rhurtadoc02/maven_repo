package co.edu.usbcali.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operaciones/")
public class OperacionesMatematicas {
	
	@GetMapping("sumar/{numeroUno}/{numeroDos}")
	public Resultado sumar(@PathVariable("numeroUno") Integer n1,@PathVariable("numeroDos") Integer n2) {
		return new Resultado(n1+n2);
	}

	@GetMapping("restar/{numeroUno}/{numeroDos}")
	public Resultado restar(@PathVariable("numeroUno") Integer n1,@PathVariable("numeroDos") Integer n2) {
		return new Resultado(n1-n2);
	}
	
	@GetMapping("div/{numeroUno}/{numeroDos}")
	public ResponseEntity<?> dividir(@PathVariable("numeroUno") Integer n1,@PathVariable("numeroDos") Integer n2) {
		
		if(n2 == 0 ) {
			return ResponseEntity.badRequest().body(new DivError("Error division por cero"));	
		
	}
		return ResponseEntity.ok(new Resultado(n1/n2)) ;
	}
	
	
	@GetMapping("divDos/{numeroUno}/{numeroDos}")
	public ResponseEntity<?> dividirDos(@PathVariable("numeroUno") Integer n1,@PathVariable("numeroDos") Integer n2) {
		
		try {
			
			return ResponseEntity.ok(new Resultado(n1/n2)) ;
		}catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new DivError(e.getMessage()));
		}			
	
		
	}
	
	
}

class DivError{
	private String msg;
	
	

	public DivError(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}

class Resultado{
	
	private Integer valor;

	public Resultado(Integer valor) {
		super();
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
	
	
}