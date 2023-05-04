package bank.system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bank.system.entity.UserDocuments;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocuments, Integer>{
	
	@Transactional(propagation= Propagation.REQUIRES_NEW)
	@Modifying
	@Query(value="update UserDocuments set active=0 where docId=:doc_id",countQuery="")
	public void deleteDocumentById(@Param("doc_id") Integer id);
	
	@Query(value="from UserDocuments where active=1 and userName=:user")
	public List<UserDocuments> getAllDocumentsForUser(@Param("user") String username);
	
	@Query(value="from UserDocuments where active=1 and docId=:doc_id")
	public Optional<UserDocuments> getDocumentsById(@Param("doc_id") Integer id);
	
}
