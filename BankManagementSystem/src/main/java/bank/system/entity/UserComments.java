package bank.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserComments {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer commentId;
	private String comments;
	@JoinColumn(name="post",nullable=false)
	@ManyToOne
	private UserPosts userPost;
	
}
