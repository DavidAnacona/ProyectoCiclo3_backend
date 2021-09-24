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
import co.edu.unbosque.ciclo3back.dao.detalle_ventasDAO;
import co.edu.unbosque.ciclo3back.model.detalle_ventas;


@RestController 
@RequestMapping("detalle_ventas")
public class detalle_ventasAPI {
	@Autowired 
	private detalle_ventasDAO detalle_ventasDAO;

	@PostMapping("/guardar")
	public void guardar(@RequestBody detalle_ventas detalle_ventas) {
		detalle_ventasDAO.save(detalle_ventas);
	}

	@GetMapping("/listar")
	public List<detalle_ventas> listar() {
		return detalle_ventasDAO.findAll();
	}

	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable("id") Integer id) {
		detalle_ventasDAO.deleteById(id);
	}

	@PutMapping("/actualizar")
	public void actualizar(@RequestBody detalle_ventas detalle_ventas) {
		detalle_ventasDAO.save(detalle_ventas);
	}
}
