package net.e4net.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.entity.MembLoginHst;
import net.e4net.demo.entity.Member;

@Repository
public interface MembLoginHstRepository extends JpaRepository<MembLoginHst, Long> {
	
}
