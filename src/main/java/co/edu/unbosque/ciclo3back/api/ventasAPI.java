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

import co.edu.unbosque.ciclo3back.dao.mensaje;
import co.edu.unbosque.ciclo3back.dao.ventasDAO;
import co.edu.unbosque.ciclo3back.model.productos;
import co.edu.unbosque.ciclo3back.model.ventas;


@RestController 
@RequestMapping("ventas")
public class ventasAPI {
	@Autowired 
	private ventasDAO ventasDAO;

	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@GetMapping("/listar")
	public List<ventas> listar() {
		return ventasDAO.findAll();
	}
	
	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar( @RequestBody ventas venta, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(ventasDAO.existsById(venta.getCodigo_venta()))
            return new ResponseEntity(new mensaje("Ya existe una venta con el codigo ingresado"), HttpStatus.BAD_REQUEST);
        
        double valor_venta = Double.parseDouble(venta.getValor_venta().toString());
        BigDecimal iva = new BigDecimal(valor_venta * 0.19);
        iva = iva.setScale(3, RoundingMode.HALF_UP);
        venta.setIvaventa(String.valueOf(iva));
        BigDecimal total = BigDecimal.valueOf(valor_venta - iva.doubleValue());
        total = total.setScale(3, RoundingMode.HALF_UP);
        venta.setTotal_venta(String.valueOf(total));
        ventasDAO.save(venta);
        return new ResponseEntity(new mensaje("Venta agregado con exito"), HttpStatus.CREATED);
	}
	
	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizar(@RequestBody ventas venta, BindingResult bindingResult, @PathVariable("id") Long id) {
		if(bindingResult.hasErrors())
            return new ResponseEntity(new mensaje("Datos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(!ventasDAO.existsById(id))
            return new ResponseEntity(new mensaje("No existe la venta a actualizar"), HttpStatus.NOT_FOUND);
        ventas ventaActualizar = ventasDAO.findById(id).get();
        double valor_venta = Double.parseDouble(venta.getValor_venta().toString());
        BigDecimal iva = new BigDecimal(valor_venta * 0.19);
        iva = iva.setScale(3, RoundingMode.HALF_UP);
        BigDecimal total = BigDecimal.valueOf(valor_venta - iva.doubleValue());
        total = total.setScale(3, RoundingMode.HALF_UP);
        ventaActualizar.setCodigo_venta(venta.getCodigo_venta());
        ventaActualizar.setCedula_cliente(venta.getCedula_cliente());
        ventaActualizar.setCedula_usuario(venta.getCedula_usuario());
        ventaActualizar.setIvaventa(String.valueOf(iva));
        ventaActualizar.setTotal_venta(String.valueOf(total));
        ventaActualizar.setValor_venta(venta.getValor_venta());
        ventasDAO.save(ventaActualizar);
        return new ResponseEntity(new mensaje("Venta actualizada"), HttpStatus.OK);
	}

	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/detalle/{id}")
	public ResponseEntity<productos> consultar(@PathVariable("id") Long id){
		if(!ventasDAO.existsById(id)) 
			return new ResponseEntity(new mensaje("No existe la venta"), HttpStatus.NOT_FOUND);
		ventas venta = ventasDAO.findById(id).get();
		return new ResponseEntity(venta, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = {"http://localhost:3000", "https://ciclo3-mintic-front.herokuapp.com"})
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
		if(!ventasDAO.existsById(id))
			return new ResponseEntity(new mensaje("No existe la venta"), HttpStatus.NOT_FOUND);
		ventasDAO.deleteById(id);
		return new ResponseEntity(new mensaje("Venta eliminada"), HttpStatus.OK);
	}
}
