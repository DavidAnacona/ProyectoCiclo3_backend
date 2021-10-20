package co.edu.unbosque.ciclo3back.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.ciclo3back.dao.clientesDAO;
import co.edu.unbosque.ciclo3back.dao.mensaje;
import co.edu.unbosque.ciclo3back.model.clientes;


@RestController 
@RequestMapping("clientes")
public class clientesAPI {
	@Autowired 
	private clientesDAO clientesDAO;
	
	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@GetMapping("/listar")
	public List<clientes> listar() {
		return clientesDAO.findAll();
	}

	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar( @RequestBody clientes cliente, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(clientesDAO.existsById(cliente.getCedula_cliente()))
            return new ResponseEntity(new mensaje("Ya existe un cliente con la cedula ingresada"), HttpStatus.BAD_REQUEST);
        clientesDAO.save(cliente);
        return new ResponseEntity(new mensaje("Cliente agregado con exito"), HttpStatus.CREATED);
	}
	
	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizar(@RequestBody clientes cliente, BindingResult bindingResult, @PathVariable("id") Long id) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(!clientesDAO.existsById(id))
            return new ResponseEntity(new mensaje("No existe el cliente a actualizar"), HttpStatus.NOT_FOUND);
        clientes clienteActualizar = clientesDAO.findById(id).get();
        clienteActualizar.setNombre_cliente(cliente.getNombre_cliente());
        clienteActualizar.setDireccion_cliente(cliente.getDireccion_cliente());
        clienteActualizar.setEmail_cliente(cliente.getEmail_cliente());
        clienteActualizar.setTelefono_cliente(cliente.getTelefono_cliente());
        clientesDAO.save(clienteActualizar);
        return new ResponseEntity(new mensaje("Cliente actualizado"), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<clientes> consultar(@PathVariable("id") Long id){
		if(!clientesDAO.existsById(id)) 
			return new ResponseEntity(new mensaje("No existe un cliente"), HttpStatus.NOT_FOUND);
		clientes cliente = clientesDAO.findById(id).get();
		return new ResponseEntity(cliente, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
		if(!clientesDAO.existsById(id))
			return new ResponseEntity(new mensaje("No existe un cliente"), HttpStatus.NOT_FOUND);
		clientesDAO.deleteById(id);
		return new ResponseEntity(new mensaje("Cliente eliminado"), HttpStatus.OK);
	}
}
