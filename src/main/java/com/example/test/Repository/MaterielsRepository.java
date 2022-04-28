package com.example.test.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.test.Modeles.Materiels;

public interface MaterielsRepository extends JpaRepository<Materiels, Integer> {
	
	Materiels findByid( int materielid);
	void deleteByid(int materielid);
	
	
	@Query(value = "SELECT * FROM materiels WHERE categorie_id = :catid", nativeQuery = true)
    public List<Materiels> findBygetmaterielwithcategorieid(@Param("catid") Integer catid);

}
