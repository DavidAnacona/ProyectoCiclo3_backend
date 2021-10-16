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
import co.edu.unbosque.ciclo3back.dao.productosDAO;
import co.edu.unbosque.ciclo3back.model.Usuarios;
import co.edu.unbosque.ciclo3back.model.productos;


@RestController 
@RequestMapping("productos")
public class productosAPI {
	@Autowired 
	private productosDAO productosDAO;

	@GetMapping("/listar")
	public List<productos> listar() {
		return productosDAO.findAll();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar( @RequestBody productos producto, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(productosDAO.existsById(producto.getCodigo_producto()))
            return new ResponseEntity(new mensaje("Ya existe un producto con el codigo ingresado"), HttpStatus.BAD_REQUEST);
        productosDAO.save(producto);
        return new ResponseEntity(new mensaje("Producto agregado con exito"), HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizar(@RequestBody productos producto, BindingResult bindingResult, @PathVariable("id") Long id) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(!productosDAO.existsById(id))
            return new ResponseEntity(new mensaje("No existe el producto a actualizar"), HttpStatus.NOT_FOUND);
        productos productoActualizar = productosDAO.findById(id).get();
        productoActualizar.setIvacompra(producto.getIvacompra());
        productoActualizar.setNitproveedor(producto.getNitproveedor());
        productoActualizar.setNombre_producto(producto.getNombre_producto());
        productoActualizar.setPrecio_compra(producto.getPrecio_compra());
        productoActualizar.setPrecio_venta(producto.getPrecio_venta());
        productosDAO.save(productoActualizar);
        return new ResponseEntity(new mensaje("Producto actualizado"), HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<productos> consultar(@PathVariable("id") Long id){
		if(!productosDAO.existsById(id)) 
			return new ResponseEntity(new mensaje("No existe el producto"), HttpStatus.NOT_FOUND);
		productos producto = productosDAO.findById(id).get();
		return new ResponseEntity(producto, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
		if(!productosDAO.existsById(id))
			return new ResponseEntity(new mensaje("No existe el producto"), HttpStatus.NOT_FOUND);
		productosDAO.deleteById(id);
		return new ResponseEntity(new mensaje("Producto eliminado"), HttpStatus.OK);
	}
}
