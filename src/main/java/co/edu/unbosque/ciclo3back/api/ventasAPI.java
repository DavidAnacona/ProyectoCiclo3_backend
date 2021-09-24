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
import co.edu.unbosque.ciclo3back.dao.ventasDAO;
import co.edu.unbosque.ciclo3back.model.ventas;


@RestController 
@RequestMapping("ventas")
public class ventasAPI {
	@Autowired 
	private ventasDAO ventasDAO;

	@PostMapping("/guardar")
	public void guardar(@RequestBody ventas ventas) {
		ventasDAO.save(ventas);
	}

	@GetMapping("/listar")
	public List<ventas> listar() {
		return ventasDAO.findAll();
	}

	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable("id") Integer id) {
		ventasDAO.deleteById(id);
	}

	@PutMapping("/actualizar")
	public void actualizar(@RequestBody ventas ventas) {
		ventasDAO.save(ventas);
	}
}
