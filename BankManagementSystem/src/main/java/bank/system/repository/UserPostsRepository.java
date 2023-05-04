package bank.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bank.system.entity.UserPosts;

@Repository
public interface UserPostsRepository extends JpaRepository<UserPosts, Integer>{
	
	public UserPosts findByDocumentId(Integer docId);
	
}
