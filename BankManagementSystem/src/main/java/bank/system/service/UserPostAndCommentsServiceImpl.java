package bank.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bank.system.dto.UserCommentsRequestDTO;
import bank.system.dto.UserPostsRequestDTO;
import bank.system.entity.UserComments;
import bank.system.entity.UserPosts;
import bank.system.repository.UserPostsRepository;

@Service
public class UserPostAndCommentsServiceImpl implements UserPostAndCommentsIntf {

	@Autowired
	private UserPostsRepository postsRepository;
	
	public UserPosts createPost(UserPostsRequestDTO postsRequest) {
		System.out.println("createPost .... ");
		UserPosts savePost = null;
		try {
			UserPosts userPosts = new UserPosts();
			userPosts.setTitle(postsRequest.getTitle());
			userPosts.setDocumentId(postsRequest.getDocumentId());
			userPosts.setMessage(postsRequest.getMessage());
			savePost = postsRepository.save(userPosts);
			savePost = getPostById(savePost.getPostId());
			System.out.println("createPost completed ....");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return savePost;
	}

	public UserPosts createComments(UserCommentsRequestDTO commentsRequestDTO) {
		System.out.println("createComments .... ");
		UserPosts savePost = null;
		try {
			savePost = getPostById(commentsRequestDTO.getPostId());
			if(savePost != null) {
				
				UserComments comments = new UserComments();
				comments.setComments(commentsRequestDTO.getComments());
				comments.setUserPost(savePost);
				savePost.getComments().add(comments);
				postsRepository.save(savePost);
				savePost = getPostById(commentsRequestDTO.getPostId());
			}
			
			System.out.println("createComments completed ....");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return savePost;
	}

	public UserPosts getPostByDocumentId(Integer id) {
		System.out.println("getPostByDocumentId ..."+id);
		return postsRepository.findByDocumentId(id);
	}

	public UserPosts getPostById(Integer id) {
		System.out.println("getPostById ..."+id);
		return postsRepository.findById(id).orElse(null);
	}

}
