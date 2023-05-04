package bank.system.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.system.dto.UserCommentsRequestDTO;
import bank.system.dto.UserPostsRequestDTO;
import bank.system.dto.UserPostsResponseDTO;
import bank.system.entity.UserDocuments;
import bank.system.entity.UserPosts;
import bank.system.service.UserDocumentServiceIntf;
import bank.system.service.UserPostAndCommentsIntf;
import bank.system.webservicecall.Post;
import bank.system.webservicecall.WebServiceCall;

@RestController
@RequestMapping("/post")
public class UserPostsController {

	@Autowired
	private UserPostAndCommentsIntf postAndCommentsIntf;
	
	@Autowired
	private UserDocumentServiceIntf documentServiceIntf;
	
	@Autowired
	private WebServiceCall serviceCall;

	@PostMapping
	public ResponseEntity<?> createPost(@Valid @RequestBody UserPostsRequestDTO requestDTO) {

		ResponseEntity<?> resposeBody = ResponseEntity.noContent().build();

		try {
			System.out.println("createPost ......");
			System.out.println(requestDTO.toString());
			
			System.out.println("Web Service Call for POST API.... ");
			serviceCall.callPostApi(new Post(requestDTO.getDocumentId(), requestDTO.getTitle(), requestDTO.getMessage()));
			
			UserDocuments doc = documentServiceIntf.getDocumentById(requestDTO.getDocumentId());
			if(doc  != null) {
				
				UserPosts post = postAndCommentsIntf.createPost(requestDTO);
				if(post != null) {
					UserPostsResponseDTO responseDTO = new UserPostsResponseDTO();
					responseDTO.setDocumentId(post.getDocumentId());
					responseDTO.setPostId(post.getPostId());
					responseDTO.setTitle(post.getTitle());
					responseDTO.setMessage(post.getMessage());
					if(post.getComments()!= null) {
						
						responseDTO.setComments(post.getComments().stream().
								map(cc -> cc.getComments()).collect(Collectors.toList()));
					}
					
					resposeBody = ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
				}
				else 
				{
					resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("Posts Creation Failed .. . Please try again! ");
					
				}
			}
			else {
				resposeBody = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document Not Found With Document ID:" + requestDTO.getDocumentId());
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Posts Creation Failed .. . Please try again! ");
		}

		System.out.println("Returning response .....");
		return resposeBody;
	}
	
	@PostMapping("/comments")
	public ResponseEntity<?> createComments(@Valid @RequestBody UserCommentsRequestDTO requestDTO) {

		ResponseEntity<?> resposeBody = ResponseEntity.noContent().build();

		try {
			System.out.println("createComments ......");
			System.out.println(requestDTO.toString());
			
			UserPosts post = postAndCommentsIntf.createComments(requestDTO);
			if(post != null) {
				UserPostsResponseDTO responseDTO = new UserPostsResponseDTO();
				responseDTO.setDocumentId(post.getDocumentId());
				responseDTO.setPostId(post.getPostId());
				responseDTO.setTitle(post.getTitle());
				responseDTO.setMessage(post.getMessage());
				responseDTO.setComments(post.getComments().stream().
											map(cc -> cc.getComments()).collect(Collectors.toList()));
				resposeBody = ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
			}
			else 
			{
				resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Comments Creation Failed .. . Please try again! ");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Comments Creation Failed .. . Please try again! ");
		}

		System.out.println("Returning response .....");
		return resposeBody;
	}

	@GetMapping("/{documentId}")
	public ResponseEntity<?> getPosts(@PathVariable("documentId") Integer documentId) {

		System.out.println("getPosts by Document ID :"+documentId);
		ResponseEntity<?> resposeBody = ResponseEntity.noContent().build();
		try {
			UserPosts post = postAndCommentsIntf.getPostByDocumentId(documentId);
			if(post != null) {
				UserPostsResponseDTO responseDTO = new UserPostsResponseDTO();
				responseDTO.setDocumentId(post.getDocumentId());
				responseDTO.setPostId(post.getPostId());
				responseDTO.setTitle(post.getTitle());
				responseDTO.setMessage(post.getMessage());
				responseDTO.setComments(post.getComments().stream().
											map(cc -> cc.getComments()).collect(Collectors.toList()));
				resposeBody = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
			}
			else 
			{
				resposeBody = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post Not Found With Document ID:" + documentId);	
			}
			
		} catch (Exception e) {
			resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			e.printStackTrace();
		}
		return resposeBody;
	}

}
