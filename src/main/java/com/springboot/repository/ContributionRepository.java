package com.springboot.repository;

import com.springboot.model.Contribution;
import com.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface ContributionRepository extends JpaRepository<Contribution, Long> {

 
	List<Contribution> findAllByUser(User user);


}
