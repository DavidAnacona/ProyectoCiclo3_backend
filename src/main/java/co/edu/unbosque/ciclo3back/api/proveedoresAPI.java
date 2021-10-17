package co.edu.unbosque.ciclo3back.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.ciclo3back.dao.mensaje;
import co.edu.unbosque.ciclo3back.dao.proveedoresDAO;
import co.edu.unbosque.ciclo3back.model.Usuarios;
import co.edu.unbosque.ciclo3back.model.proveedores;


@RestController 
@RequestMapping("proveedores")
public class proveedoresAPI {
	@Autowired 
	private proveedoresDAO proveedoresDAO;

	@GetMapping("/listar")
	public List<proveedores> listar() {
		return proveedoresDAO.findAll();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar( @RequestBody proveedores proveedor, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(proveedoresDAO.existsById(proveedor.getNitproveedor()))
            return new ResponseEntity(new mensaje("Ya existe un proveedor con el nit ingresado"), HttpStatus.BAD_REQUEST);
        proveedoresDAO.save(proveedor);
        return new ResponseEntity(new mensaje("Proveedor agregado con exito"), HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizar(@RequestBody proveedores proveedor, BindingResult bindingResult, @PathVariable("id") Long id) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(!proveedoresDAO.existsById(id))
            return new ResponseEntity(new mensaje("No existe el proveedor a actualizar"), HttpStatus.NOT_FOUND);
        proveedores proveedorActualizar = proveedoresDAO.findById(id).get();
        proveedorActualizar.setNombre_proveedor(proveedor.getNombre_proveedor());
        proveedorActualizar.setCiudad_proveedor(proveedor.getCiudad_proveedor());
        proveedorActualizar.setDireccion_proveedor(proveedor.getDireccion_proveedor());
        proveedorActualizar.setTelefono_proveedor(proveedor.getTelefono_proveedor());
        proveedoresDAO.save(proveedorActualizar);
        return new ResponseEntity(new mensaje("Proveedor actualizado"), HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<proveedores> consultar(@PathVariable("id") Long id){
		if(!proveedoresDAO.existsById(id)) 
			return new ResponseEntity(new mensaje("No existe un proveedor"), HttpStatus.NOT_FOUND);
		proveedores proveedor = proveedoresDAO.findById(id).get();
		return new ResponseEntity(proveedor, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
		if(!proveedoresDAO.existsById(id))
			return new ResponseEntity(new mensaje("No existe un proveedor"), HttpStatus.NOT_FOUND);
		proveedoresDAO.deleteById(id);
		return new ResponseEntity(new mensaje("Proveedor eliminado"), HttpStatus.OK);
	}

}
