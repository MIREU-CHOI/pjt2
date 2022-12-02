package net.e4net.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.e4net.demo.entity.Goods;
import net.e4net.demo.entity.Member;
import net.e4net.demo.entity.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
	
	
}
