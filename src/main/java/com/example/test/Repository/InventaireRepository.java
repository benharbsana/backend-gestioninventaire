package com.example.test.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.test.Modeles.Inventaire;



public interface InventaireRepository extends JpaRepository<Inventaire, Integer> {
	Inventaire findById( int inventaireid);
	void deleteById(int inventaireid);

	@Query(value = "SELECT * FROM inventaire WHERE user_id = :userid", nativeQuery = true)
    public List<Inventaire> findBygetuser(@Param("userid") Integer userid);

}
