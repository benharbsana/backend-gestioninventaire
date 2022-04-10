package com.example.test.Controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.test.Modeles.Inventaire;
import com.example.test.Modeles.QRcode;
import com.example.test.Modeles.User;
import com.example.test.Repository.InventaireRepository;
import com.example.test.Repository.UserRepository;
import com.example.test.exception.InventaireCustomException;

import com.example.test.message.InventaireResponse;
import com.example.test.util.validator;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")

public class InventaireController {
	@Autowired
	private InventaireRepository inventaireRepository;
	private UserRepository userRepository;
	
	 public InventaireController() {
		 super();
	 }
	 
	 
	 @PostMapping("/addinventaire")
	 public ResponseEntity<InventaireResponse> addinventaire(
			 @RequestParam(name="file") MultipartFile qrcode,
				@RequestParam(name ="quantite") String quantite,
				@RequestParam(name ="iduser")Integer iduser,
				
				@RequestParam(name ="dateinv") String dateinv) throws IOException {
		 InventaireResponse inres = new InventaireResponse();
		 if (validator.isStringEmpty(quantite)    ) {
				inres.setStatus("400");
				inres.setMessage("BAD_REQUEST");
				return new ResponseEntity<InventaireResponse>(inres, HttpStatus.NOT_ACCEPTABLE);
			} 
		 else {
				try {
					User user1 = userRepository.getById(iduser);
					Inventaire inv = new Inventaire();
					inv.setQuantite(quantite);
					inv.setDateinv(dateinv);
					inv.setUser(user1);
					if (qrcode != null) {
						inv.setQrcode(qrcode.getBytes());	
					}
					inventaireRepository.save(inv);
					inres.setStatus("200");
					inres.setMessage("SUCCESS");
				    inres.setObinv(inventaireRepository.findAll());
				} catch (Exception i) {
					throw new InventaireCustomException(i.getMessage());
				}
			}
	     	return new ResponseEntity<InventaireResponse>(inres, HttpStatus.OK);
		}
	 
	 @PutMapping("/updateinventaire")
		public ResponseEntity<com.example.test.message.ServerResponse> updateinventaire(
				
				 @RequestParam(name="file") MultipartFile qrcode,
					@RequestParam(name ="quantite") String quantite,
					@RequestParam(name ="dateinv") String dateinv,
					@RequestParam(name ="id") String inventaireid)throws IOException {
		 com.example.test.message.ServerResponse inres = new com.example.test.message.ServerResponse();
			 if (validator.isStringEmpty(quantite)    ) {
					inres.setStatus("400");
					inres.setMessage("BAD_REQUEST");
					return new ResponseEntity<com.example.test.message.ServerResponse> (inres ,HttpStatus.NOT_ACCEPTABLE);
			 }
			 else {
				 try {
					 if (qrcode!=null) {
						  Inventaire inventaire = new Inventaire (Integer.parseInt(inventaireid),quantite,
								  qrcode.getBytes(),dateinv);
								  inventaireRepository.save(inventaire);
					 }
					 else {
						 
						
						Inventaire inventaireorg = inventaireRepository.findById(Integer.parseInt(inventaireid));
						 
						
						Inventaire inventaire1 =new Inventaire (Integer.parseInt(inventaireid),quantite, 
								  qrcode.getBytes(),dateinv);
						 inventaireRepository.save(inventaire1);
						 inres.setStatus("200");
						 inres.setMessage("SUCCESS");
						}
				 } catch(Exception i) {
						throw new InventaireCustomException("Unable to update inventaire details, please try again");
					}
				}
				return new ResponseEntity<com.example.test.message.ServerResponse>(inres, HttpStatus.OK);
			}
				
	 @DeleteMapping("/deleteinventaire")
		public ResponseEntity<InventaireResponse> deleteinventaire (@RequestParam(name = "id") String inventaireid )
				throws IOException {
			InventaireResponse inres = new InventaireResponse();
			if (validator.isStringEmpty(inventaireid)) {
				inres.setStatus("400");
				inres.setMessage("BAD_REQUEST");
				return new ResponseEntity<InventaireResponse>(inres , HttpStatus.NOT_ACCEPTABLE);
			}
			 else {
					try {
						inventaireRepository.deleteById(Integer.parseInt(inventaireid));
						inres.setStatus("200");
						inres.setMessage("SUCCESS");
					} catch (Exception i) {
						throw new InventaireCustomException(i.getMessage());
					
				}
					return new ResponseEntity<InventaireResponse>(inres,HttpStatus.OK);
			 }}
	 
	 
	 @GetMapping("/getinventaire")
		public ResponseEntity<InventaireResponse> getinventaire() throws IOException {
			InventaireResponse inv = new InventaireResponse();
			
			try {
				inv.setStatus(com.example.test.message.ResponseCode.SUCCESS_CODE);
				inv.setMessage(com.example.test.message.ResponseCode.LIST_SUCCESS_MESSAGE);
				inv.setObinv(inventaireRepository.findAll());
			} catch (Exception i) {
				throw new InventaireCustomException("Unable to retrieve inventaire, please try again");
			}
			return new ResponseEntity<InventaireResponse>(inv, HttpStatus.OK);
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
