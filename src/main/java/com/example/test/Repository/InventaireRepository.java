package com.example.test.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.Modeles.Inventaire;


public interface InventaireRepository extends JpaRepository<Inventaire, Integer> {
	Inventaire findById( int inventaireid);
	void deleteById(int inventaireid);


}
