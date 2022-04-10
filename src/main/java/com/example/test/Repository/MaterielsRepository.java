package com.example.test.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.Modeles.Materiels;

public interface MaterielsRepository extends JpaRepository<Materiels, Integer> {
	
	Materiels findByid( int materielid);
	void deleteByid(int materielid);

}
