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
import co.edu.unbosque.ciclo3back.dao.clientesDAO;
import co.edu.unbosque.ciclo3back.model.clientes;


@RestController 
@RequestMapping("clientes")
public class clientesAPI {
	@Autowired 
	private clientesDAO clientesDAO;

	@PostMapping("/guardar")
	public void guardar(@RequestBody clientes clientes) {
		clientesDAO.save(clientes);
	}

	@GetMapping("/listar")
	public List<clientes> listar() {
		return clientesDAO.findAll();
	}

	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable("id") Integer id) {
		clientesDAO.deleteById(id);
	}

	@PutMapping("/actualizar")
	public void actualizar(@RequestBody clientes clientes) {
		clientesDAO.save(clientes);
	}
}
