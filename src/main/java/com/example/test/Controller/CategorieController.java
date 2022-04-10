package com.example.test.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.test.Modeles.Categorie;
import com.example.test.Repository.CategorieRepository;
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class CategorieController {
	
	@Autowired
	private CategorieRepository categorieRepository;
	 
	@GetMapping("/categorie")
	public List<Categorie> getAllCategories(){
		return categorieRepository.findAll();
		}
	 
	 @GetMapping("/categorie/{id}")
	    public ResponseEntity<Categorie> getcategorieById(@PathVariable(value = "id") Integer categorieId)
	            throws com.example.test.exception.ResourceNotFoundException {
	        Categorie categorie = categorieRepository.findById(categorieId)
	                .orElseThrow(() -> new com.example.test.exception.ResourceNotFoundException("Categorie not found for this id :: " + categorieId));
	        return ResponseEntity.ok().body(categorie);
	    }
	 @PostMapping("/categorie")
	    public Categorie createCategorie(@Validated @RequestBody Categorie categorie) {
	        return categorieRepository.save(categorie);
	    }
	 
	 @PutMapping("/categorie/{id}")
	    public ResponseEntity<Categorie> updateCategorie(@PathVariable(value = "id") Integer categorieId,
	                                                  @Validated @RequestBody Categorie categorieDetails) throws com.example.test.exception.ResourceNotFoundException {
	        Categorie categorie = categorieRepository.findById(categorieId)
	                .orElseThrow(() -> new com.example.test.exception.ResourceNotFoundException("categorie not found for this id :: " + categorieId));

	        categorie.setNomcat(categorieDetails.getNomcat());
	        categorie.setDescription(categorieDetails.getDescription());

	        final Categorie updatecategorie = categorieRepository.save(categorie);
	        return ResponseEntity.ok(updatecategorie);
	    }
	 
	 @DeleteMapping("/categorie/{id}")
	    public Map<String, Boolean> deleteCategorie(@PathVariable(value = "id")Integer categorieId)
	            throws com.example.test.exception.ResourceNotFoundException {
	        Categorie categorie = categorieRepository.findById(categorieId)
	                .orElseThrow(() -> new com.example.test.exception.ResourceNotFoundException("categorie not found for this id :: " + categorieId));

	        categorieRepository.delete(categorie);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }
}
