package com.example.test.Controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;



import com.example.test.Modeles.Inventaire;
import com.example.test.Modeles.Materiels;
import com.example.test.Modeles.QRcode;

import com.example.test.Repository.InventaireRepository;
import com.fasterxml.jackson.annotation.JsonIdentityReference;


@CrossOrigin("*")
@RestController
@RequestMapping("/inventaire")

public class InventaireController {
	@Autowired
	private InventaireRepository inventaireRepository;
	@GetMapping("/inventaire")
	public List<Inventaire> getAllInventaires(){
		return inventaireRepository.findAll();
		}
	 
	 @GetMapping("/inventaire/{id}")
	    public ResponseEntity<Inventaire> getinventaireById(@PathVariable(value = "id") Integer inventaireId)
	            throws com.example.test.exception.ResourceNotFoundException {
	        Inventaire inventaire = inventaireRepository.findById(inventaireId)
	                .orElseThrow(() -> new com.example.test.exception.ResourceNotFoundException("inventaire not found for this id :: " + inventaireId));
	        return ResponseEntity.ok().body(inventaire);
	    }
	
	 @PostMapping("/addinventaire")
	    public Inventaire createinventaire(@Validated @RequestBody Inventaire inventaire) {
		 Inventaire inventairelocale= new Inventaire();
		 Date datenow= new Date();
		 System.out.println(inventaire.getMateriels());
		 inventairelocale.setMateriels(inventaire.getMateriels());
		 inventairelocale.setUser(inventaire.getUser());
		 inventairelocale.setDateinv(datenow);
	        return inventaireRepository.save(inventairelocale);
	        
	    }
	 
	 
	 @PutMapping("/updateinventaire/{id}")
	    public ResponseEntity<Inventaire> updateinventaire(@PathVariable(value = "id") Integer inventaireId,
	    		
	                                                  @Validated @RequestBody Inventaire inventaireDetails) throws com.example.test.exception.ResourceNotFoundException {
	        Inventaire oldinventaire = inventaireRepository.findById(inventaireId)
	        		
	                .orElseThrow(() -> new com.example.test.exception.ResourceNotFoundException("inventaire not found for this id :: " + inventaireId));
	        Date datenow= new Date();
	        
	        oldinventaire.setDateinv(datenow);
	        oldinventaire.setMateriels(inventaireDetails.getMateriels());

	        final Inventaire updateinventaire = inventaireRepository.save(oldinventaire);
	        return ResponseEntity.ok(updateinventaire);
	    }
	 
	
	
	 
	 @DeleteMapping("/inventaire/{id}")
	    public Map<String, Boolean> deleteInventaire(@PathVariable(value = "id")Integer inventaireId)
	            throws com.example.test.exception.ResourceNotFoundException {
	        Inventaire inventaire = inventaireRepository.findById(inventaireId)
	                .orElseThrow(() -> new com.example.test.exception.ResourceNotFoundException("inventaire not found for this id :: " + inventaireId));

	        inventaireRepository.delete(inventaire);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }
	
	
	
	
	
	 
	
	
	 @RequestMapping(value = "qrcode/{id}", method = RequestMethod.GET)
		public void qrcode(@PathVariable("id") int id, HttpServletResponse response) throws Exception{
			Inventaire inventaire = inventaireRepository.findById(id);

			if(id == inventaire.getCodeInv()){
				response.setContentType("image/png");
				OutputStream outputStream = response.getOutputStream();

				outputStream.write(QRcode.getQRCodeImage( "http://localhost:4200/Scanner/" + id, 300, 300));

				outputStream.flush();
				outputStream.close();



			}
		 
	 
	 
	
	
	
	
}
	
	
	
}
