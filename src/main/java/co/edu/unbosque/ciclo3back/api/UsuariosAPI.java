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

import co.edu.unbosque.ciclo3back.dao.UsuariosDAO;
import co.edu.unbosque.ciclo3back.dao.mensaje;
import co.edu.unbosque.ciclo3back.model.Usuarios;


@RestController 
@RequestMapping("usuarios")
public class UsuariosAPI {
	@Autowired 
	private UsuariosDAO usuariosDAO;

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/listar")
	public List<Usuarios> listar() {
		return usuariosDAO.findAll();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar( @RequestBody Usuarios usuarios, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(usuariosDAO.existsById(usuarios.getCedula_usuario()))
            return new ResponseEntity(new mensaje("Ya existe un usuario con la cedula ingresada"), HttpStatus.BAD_REQUEST);
        usuariosDAO.save(usuarios);
        return new ResponseEntity(new mensaje("Usuario agregado con exito"), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizar(@RequestBody Usuarios usuarios, BindingResult bindingResult, @PathVariable("id") Long id) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(!usuariosDAO.existsById(id))
            return new ResponseEntity(new mensaje("No existe el usuario a actualizar"), HttpStatus.NOT_FOUND);
        Usuarios usuario = usuariosDAO.findById(id).get();
        usuario.setNombre_usuario(usuarios.getNombre_usuario());
        usuario.setEmail_usuario(usuario.getEmail_usuario());
        usuario.setNombre_usuario(usuarios.getNombre_usuario());
        usuario.setPassword(usuarios.getPassword());
        usuario.setUsuario(usuarios.getUsuario());
        usuariosDAO.save(usuario);
        return new ResponseEntity(new mensaje("Usuario actualizado"), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Usuarios> consultar(@PathVariable("id") Long id){
		if(!usuariosDAO.existsById(id)) 
			return new ResponseEntity(new mensaje("No existe un usuario"), HttpStatus.NOT_FOUND);
		Usuarios usuario = usuariosDAO.findById(id).get();
		return new ResponseEntity(usuario, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
		if(!usuariosDAO.existsById(id))
			return new ResponseEntity(new mensaje("No existe un usuario"), HttpStatus.NOT_FOUND);
		usuariosDAO.deleteById(id);
		return new ResponseEntity(new mensaje("Usuario eliminado"), HttpStatus.OK);
	}
}
