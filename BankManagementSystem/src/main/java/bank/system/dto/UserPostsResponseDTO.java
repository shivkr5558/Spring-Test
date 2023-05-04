package bank.system.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPostsResponseDTO {
	
	private Integer postId;
	private Integer documentId;
	private String title;
	private String message;
	private List<String> comments;

}
