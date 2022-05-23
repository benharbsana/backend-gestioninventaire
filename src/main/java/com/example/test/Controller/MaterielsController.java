package com.example.test.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.test.Modeles.Categorie;
import com.example.test.Modeles.Materiels;
import com.example.test.Repository.CategorieRepository;
import com.example.test.Repository.InventaireRepository;
import com.example.test.Repository.MaterielsRepository;
import com.example.test.Repository.UserRepository;
import com.example.test.exception.MaterielCustomException;
import com.example.test.message.MaterielResponse;
import com.example.test.message.ServerResponse;
import com.example.test.util.validator;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/materiel")
public class MaterielsController {
	 	@Autowired
	private MaterielsRepository materielsRepository;
	 	@Autowired
	private UserRepository userRepository;
@Autowired
    private InventaireRepository inventaireRepository; 	 	
	 	@Autowired
	private CategorieRepository categorieRepository;
	 private static Logger logger = LoggerFactory.getLogger(MaterielsController.class);
	 	
	 public MaterielsController() {
		 super();

	 }
	 
	 @PostMapping("/addmateriel")
	 public ResponseEntity<MaterielResponse> addmateriel(
			 	@RequestParam(name="file") MultipartFile materielimage,
				@RequestParam(name ="designation") String designation,
				@RequestParam(name ="etat" ) String  etat,
				@RequestParam(name ="disponible") String disponible,
				@RequestParam(name ="idcategorie") Integer idcategorie) throws IOException {
		 MaterielResponse matres = new MaterielResponse();
		 if (validator.isStringEmpty(designation) || validator.isStringEmpty(etat)
					|| validator.isStringEmpty(disponible)    ) {
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
	 
	 
	 
		@PutMapping("/updatemateriel")
		public ResponseEntity<com.example.test.message.ServerResponse> updatemateriel(
				
				 @RequestParam(name="file") MultipartFile materielimage,
					@RequestParam(name ="designation") String designation,
					@RequestParam(name ="etat" ) String  etat,
					@RequestParam(name ="disponible") String disponible,
				
					
					@RequestParam(name ="id") String materielid)throws IOException {
			com.example.test.message.ServerResponse matres = new com.example.test.message.ServerResponse();
			try {
			Materiels mat = materielsRepository.findByid(Integer.parseInt(materielid));
			
			mat.setDesignation(designation);
			mat.setEtat(etat);
			
			mat.setDisponible(disponible);
		
			mat.setMaterielimage(materielimage.getBytes());
			materielsRepository.save(mat);
			matres.setStatus("200");
			matres.setMessage("SUCCESS");
		} 
		catch (Exception m) {
			throw new MaterielCustomException(m.getMessage());
		}
	
 	return new ResponseEntity<ServerResponse>(matres, HttpStatus.OK);

			
		}
		
				
		
	 
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
	 
	 @CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
	 @GetMapping("/getmaterielbycategorie/{id}")
	 public ResponseEntity<MaterielResponse> getmaterielbycategorie(@PathVariable(value="id")Integer catid)
	 throws IOException {
		  
		 MaterielResponse mat = new MaterielResponse();
		 try {
			 mat.setStatus(com.example.test.message.ResponseCode.SUCCESS_CODE);
			mat.setMessage(com.example.test.message.ResponseCode.LIST_SUCCESS_MESSAGE);
			mat.setOblist(materielsRepository.findBygetmaterielwithcategorieid(catid));
			
		 }
		 catch (Exception m) {
				throw new MaterielCustomException("Unable to retrieve materiel, please try again");
			}
			return new ResponseEntity<MaterielResponse>(mat, HttpStatus.OK);
		}
	 
	 
	 @GetMapping("/counts")
	 public ResponseEntity<MaterielResponse> countentities()
	 throws IOException {
		 MaterielResponse mat = new MaterielResponse();
		 List<Long> counts = new ArrayList<>(); 
		 
		
		 try {
			 
			 Long usercount= userRepository.count();
			 Long categcount= categorieRepository.count();
			 Long matcount= materielsRepository.count();
			 Long invcout= inventaireRepository.count();
			 counts.add(invcout);
			 counts.add(categcount);
			 counts.add(matcount);
			 counts.add(usercount);
			 System.out.println(counts);
			mat.setStatus(com.example.test.message.ResponseCode.SUCCESS_CODE);
			mat.setMessage(com.example.test.message.ResponseCode.LIST_SUCCESS_MESSAGE);
			mat.setOblistcount(counts);
			
		 }
		 catch (Exception m) {
				throw new MaterielCustomException("Unable to retrieve materiel, please try again");
			}
			return new ResponseEntity<MaterielResponse>(mat, HttpStatus.OK);
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 }
	 
		


				
				
				
				
	 
	 
	 
	 
	 
	 
		
	 	 
			 
			 
			 
	    
	 
	 
	 