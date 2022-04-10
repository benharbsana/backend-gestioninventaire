package com.example.test.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.test.Modeles.Categorie;
import com.example.test.Modeles.Materiels;
import com.example.test.Repository.CategorieRepository;
import com.example.test.Repository.MaterielsRepository;
import com.example.test.exception.MaterielCustomException;
import com.example.test.message.MaterielResponse;
import com.example.test.util.validator;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class MaterielsController {
	 	@Autowired
	private MaterielsRepository materielsRepository;
	 	@Autowired
	private CategorieRepository categorieRepository;
	 	
	 public MaterielsController() {
		 super();

	 }
	 @CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
	 @PostMapping("/addmateriel")
	 public ResponseEntity<MaterielResponse> addmateriel(
			 @RequestParam(name="file") MultipartFile materielimage,
				@RequestParam(name ="designation") String designation,
				@RequestParam(name ="etat" ) String  etat,
				@RequestParam(name ="disponible") String disponible,
				@RequestParam(name ="idcategorie") Integer idcategorie,
				@RequestParam(name ="origine") String origine,
				@RequestParam(name ="date") String date) throws IOException {
		 MaterielResponse matres = new MaterielResponse();
		 if (validator.isStringEmpty(designation) || validator.isStringEmpty(etat)
					|| validator.isStringEmpty(disponible) ||  validator.isStringEmpty(origine)   ) {
				matres.setStatus("400");
				matres.setMessage("BAD_REQUEST");
				return new ResponseEntity<MaterielResponse>(matres, HttpStatus.NOT_ACCEPTABLE);
			} 
		 else {
				try {
					Categorie cate= categorieRepository.getById(idcategorie);
					Materiels mat = new Materiels();
					mat.setDesignation(designation);
					mat.setCategorie(cate);
					mat.setEtat(etat);
					mat.setDisponible(disponible);
					mat.setOrigine(origine);
					mat.setDate(date);
					if (materielimage != null) {
						mat.setMaterielimage(materielimage.getBytes());
					}
					materielsRepository.save(mat);
					matres.setStatus("200");
					matres.setMessage("SUCCESS");
				    matres.setOblist(materielsRepository.findAll());
				} catch (Exception m) {
					throw new MaterielCustomException(m.getMessage());
				}
			}
	     	return new ResponseEntity<MaterielResponse>(matres, HttpStatus.OK);
		}
	 
	 
	 @CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
		@PutMapping("/updatemateriel")
		public ResponseEntity<com.example.test.message.ServerResponse> updatemateriel(
				
				 @RequestParam(name="file") MultipartFile materielimage,
					@RequestParam(name ="designation") String designation,
					@RequestParam(name ="etat" ) String  etat,
					@RequestParam(name ="disponible") String disponible,
					@RequestParam(name ="origine") String origine,
					@RequestParam(name ="date") String date,
					@RequestParam(name ="id") String materielid)throws IOException {
			com.example.test.message.ServerResponse matres = new com.example.test.message.ServerResponse();
			 if (validator.isStringEmpty(designation) || validator.isStringEmpty(etat)
						|| validator.isStringEmpty(disponible) ||  validator.isStringEmpty(origine)   ) {
					matres.setStatus("400");
					matres.setMessage("BAD_REQUEST");
					return new ResponseEntity<com.example.test.message.ServerResponse> (matres ,HttpStatus.NOT_ACCEPTABLE);
			 }
			 else {
				 try {
					 if (materielimage!=null) {
						  Materiels materiel = new Materiels (Integer.parseInt (materielid),designation, etat,disponible,origine,
								  materielimage.getBytes(),date);
								  materielsRepository.save(materiel);
					 }
					 else {
						 
						 
		
						Materiels materielorg = materielsRepository.findByid(Integer.parseInt(materielid));
						
						Materiels materiel1 =new Materiels (Integer.parseInt(materielid),designation, etat,disponible,origine,
								  materielimage.getBytes(),date);
						 materielsRepository.save(materiel1);
						 matres.setStatus("200");
						 matres.setMessage("SUCCESS");
						}
				 } catch(Exception m) {
						throw new MaterielCustomException("Unable to update Materiel details, please try again");
					}
				}
				return new ResponseEntity<com.example.test.message.ServerResponse>(matres, HttpStatus.OK);
			}
				
		
	 @CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
		@DeleteMapping("/deletemateriel")
		public ResponseEntity<MaterielResponse> deletemateriel (@RequestParam(name = "id") String materielid )
				throws IOException {
			MaterielResponse matres = new MaterielResponse();
			if (validator.isStringEmpty(materielid)) {
				matres.setStatus("400");
				matres.setMessage("BAD_REQUEST");
				return new ResponseEntity<MaterielResponse>(matres , HttpStatus.NOT_ACCEPTABLE);
			}
			 else {
					try {
						materielsRepository.deleteById(Integer.parseInt(materielid));
						matres.setStatus("200");
						matres.setMessage("SUCCESS");
					} catch (Exception m) {
						throw new MaterielCustomException(m.getMessage());
					
				}
					return new ResponseEntity<MaterielResponse>(matres,HttpStatus.OK);
			 }}
	 @CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
		@GetMapping("/getmateriel")
		public ResponseEntity<MaterielResponse> getmateriel() throws IOException {
			MaterielResponse mat = new MaterielResponse();
			
			try {
				mat.setStatus(com.example.test.message.ResponseCode.SUCCESS_CODE);
				mat.setMessage(com.example.test.message.ResponseCode.LIST_SUCCESS_MESSAGE);
				mat.setOblist(materielsRepository.findAll());
			} catch (Exception m) {
				throw new MaterielCustomException("Unable to retrieve materiel, please try again");
			}
			return new ResponseEntity<MaterielResponse>(mat, HttpStatus.OK);
		}
		}
			 
				
				
				
				
	 
	 
	 
	 
	 
	 
		
	 	 
			 
			 
			 
	    
	 
	 
	 