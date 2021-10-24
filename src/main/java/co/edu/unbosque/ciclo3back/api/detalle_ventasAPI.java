package co.edu.unbosque.ciclo3back.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import co.edu.unbosque.ciclo3back.dao.detalle_ventasDAO;
import co.edu.unbosque.ciclo3back.dao.mensaje;
import co.edu.unbosque.ciclo3back.model.detalle_ventas;
import co.edu.unbosque.ciclo3back.model.productos;



@RestController 
@RequestMapping("detalleVenta")
public class detalle_ventasAPI {
	@Autowired 
	private detalle_ventasDAO detalle_ventasDAO;

	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@GetMapping("/listar")
	public List<detalle_ventas> listar() {
		return detalle_ventasDAO.findAll();
	}
	
	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar( @RequestBody detalle_ventas detalle_venta, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(detalle_ventasDAO.existsById(detalle_venta.getCodigo_detalle_venta()))
            return new ResponseEntity(new mensaje("Ya existe un detalle de venta con el codigo ingresado"), HttpStatus.BAD_REQUEST);
        double valor_venta = Double.parseDouble(detalle_venta.getValor_venta().toString());
        int iva = (int) (valor_venta * 0.19);
        detalle_venta.setValoriva(String.valueOf(iva));
        int total = (int) (valor_venta - iva) ;
        detalle_venta.setValor_total(String.valueOf(total));
        detalle_ventasDAO.save(detalle_venta);
        return new ResponseEntity(new mensaje("Detalle venta agregada con exito"), HttpStatus.CREATED);
	}
	
	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizar(@RequestBody detalle_ventas detalle_venta, BindingResult bindingResult, @PathVariable("id") Long id) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(!detalle_ventasDAO.existsById(id))
            return new ResponseEntity(new mensaje("No existe el detalle de venta a actualizar"), HttpStatus.NOT_FOUND);
        detalle_ventas detalleActualizar = detalle_ventasDAO.findById(id).get();
        double valor_venta = Double.parseDouble(detalle_venta.getValor_venta().toString());
        int iva = (int) (valor_venta * 0.19);
        int total = (int) (valor_venta - iva);
        detalleActualizar.setCodigo_detalle_venta(detalle_venta.getCodigo_detalle_venta());
        detalleActualizar.setCantidad_producto(detalle_venta.getCantidad_producto());
        detalleActualizar.setCodigo_producto(detalle_venta.getCodigo_producto());
        detalleActualizar.setCodigo_venta(detalle_venta.getCodigo_venta());
        detalleActualizar.setValor_total(String.valueOf(total));
        detalleActualizar.setValor_venta(detalle_venta.getValor_venta());
        detalleActualizar.setValoriva(String.valueOf(iva));
        detalle_ventasDAO.save(detalleActualizar);
        return new ResponseEntity(new mensaje("Detalle venta actualizada"), HttpStatus.OK);
	}

	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<productos> consultar(@PathVariable("id") Long id){
		if(!detalle_ventasDAO.existsById(id)) 
			return new ResponseEntity(new mensaje("No existe el detalle venta"), HttpStatus.NOT_FOUND);
		detalle_ventas detalle = detalle_ventasDAO.findById(id).get();
		return new ResponseEntity(detalle, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
		if(!detalle_ventasDAO.existsById(id))
			return new ResponseEntity(new mensaje("No existe el detalle venta"), HttpStatus.NOT_FOUND);
		detalle_ventasDAO.deleteById(id);
		return new ResponseEntity(new mensaje("Detalle venta eliminada"), HttpStatus.OK);
	}
}
