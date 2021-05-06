package com.sedmuc.init.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sedmuc.init.dao.AreasDAO;
import com.sedmuc.init.entitys.Area;


@RestController
@RequestMapping("Areas")
public class AreaRest {
	
	@Autowired
	private AreasDAO areaDAO;
	
	@GetMapping
	public ResponseEntity<java.util.List<Area>> getArea() {
		java.util.List<Area> area = areaDAO.findAll();
		return ResponseEntity.ok(area);
	}
	
	@RequestMapping(value="obtenerArea/{areaId}") 
	public ResponseEntity<Area> getAreaById(@PathVariable("areaId") Integer areaId) {
		Optional<Area> optionalArea = areaDAO.findById(areaId);
		if(optionalArea.isPresent()){
			return ResponseEntity.ok(optionalArea.get());
		}else{
			return ResponseEntity.noContent().build();
		}
	}
	
	@RequestMapping(value="nuevaArea", method = RequestMethod.POST) 
	public ResponseEntity<Area> createArea(@RequestBody Area area){
		Area newArea = areaDAO.save(area);
		return ResponseEntity.ok(newArea);
	}
	
	@RequestMapping(value="elimnarArea/{areaId}", method = RequestMethod.DELETE) 
	public ResponseEntity<Void> eliminarArea(@PathVariable("areaId") Integer areaId){
		areaDAO.deleteById(areaId);
		return ResponseEntity.ok(null);
	}
	
	@RequestMapping(value="modificarArea", method = RequestMethod.PUT) 
	public ResponseEntity<Area> modificarArea(@RequestBody Area area){
		Optional<Area> optionalArea = areaDAO.findById(area.getId());
		if(optionalArea.isPresent()){
			Area modifArea = optionalArea.get();
			modifArea.setDescripcion(area.getDescripcion());
			areaDAO.save(modifArea);
			return ResponseEntity.ok(modifArea);
		}else{
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	

}
