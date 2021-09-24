package co.edu.unbosque.ciclo3back.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.ciclo3back.dao.proveedoresDAO;
import co.edu.unbosque.ciclo3back.model.proveedores;


@RestController 
@RequestMapping("proveedores")
public class proveedoresAPI {
	@Autowired 
	private proveedoresDAO proveedoresDAO;

	@PostMapping("/guardar")
	public void guardar(@RequestBody proveedores proveedores) {
		proveedoresDAO.save(proveedores);
	}

	@GetMapping("/listar")
	public List<proveedores> listar() {
		return proveedoresDAO.findAll();
	}

	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable("id") Integer id) {
		proveedoresDAO.deleteById(id);
	}

	@PutMapping("/actualizar")
	public void actualizar(@RequestBody proveedores proveedores) {
		proveedoresDAO.save(proveedores);
	}
}
