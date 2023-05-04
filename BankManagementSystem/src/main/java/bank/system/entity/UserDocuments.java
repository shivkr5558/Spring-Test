package bank.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDocuments {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer docId;
	private String docName;
    private String filePath;
	private String docType;
	private String userName;
	@Column(updatable=false)
	@CreatedDate
	private Date createDate;
	@Column
	@LastModifiedDate
	private Date updateDate;
	private Integer active;
	
}
