package com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.model.Projet;
import com.springboot.model.User;
@Repository

public interface ProjetRepository extends JpaRepository<Projet, Long>  {
	List<Projet> findAllByUser(User user);
}
