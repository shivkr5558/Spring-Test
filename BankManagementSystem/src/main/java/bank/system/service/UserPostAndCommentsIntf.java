package bank.system.service;

import bank.system.dto.UserCommentsRequestDTO;
import bank.system.dto.UserPostsRequestDTO;
import bank.system.entity.UserPosts;

public interface UserPostAndCommentsIntf {

	public UserPosts createPost(UserPostsRequestDTO postsRequest);
	
	public UserPosts createComments(UserCommentsRequestDTO postsRequest);
	
	public UserPosts getPostByDocumentId(Integer id);
	
	public UserPosts getPostById(Integer id);
}
