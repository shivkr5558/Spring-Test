package bank.system.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bank.system.dto.UserDocumentRequestDTO;
import bank.system.dto.UserDocumentResponseDTO;
import bank.system.entity.UserDocuments;
import bank.system.service.UserDocumentServiceIntf;

@RestController
@RequestMapping("/document")
public class UserDocumentController {

	@Autowired
	private UserDocumentServiceIntf documentServiceIntf;
	
	@Value("${upload.file.path}")
	private String savefilePath;

	@PostMapping(value = "/uploadFile/{username}", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadFile(@RequestParam("username") String username,
			@RequestParam("file") MultipartFile file) {

		ResponseEntity<?> resposeBody = ResponseEntity.noContent().build();

		try {
			// Normalize file name
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			System.out.println("File Name :" + fileName);
			
			if(!file.getContentType().contains("pdf")) {
				resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Sorry! Only PDF document allowed ..");
				return resposeBody;
			}

			// Saving file to Disk ...
			String path = saveFileToDisk(file);
			if (path == null) {
				resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Sorry! Document upload Failed.. " + fileName);

			}

			else {
				UserDocumentRequestDTO requestDTO = new UserDocumentRequestDTO();
				requestDTO.setFilePath(path);
				requestDTO.setDocName(fileName);
				requestDTO.setDocType(file.getContentType());
				requestDTO.setUserName(username);

				UserDocuments doc = documentServiceIntf.createDocument(requestDTO);
				if (doc != null) {

					UserDocumentResponseDTO responseDTO = new UserDocumentResponseDTO();
					responseDTO.setDocId(doc.getDocId());
					responseDTO.setDocName(doc.getDocName());
					responseDTO.setUserName(doc.getUserName());
					responseDTO.setDocType(doc.getDocType());
					responseDTO.setCreateDate(doc.getCreateDate());
					responseDTO.setUpdateDate(doc.getUpdateDate());
					responseDTO.setDocUrl(doc.getFilePath());
					resposeBody = ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
				} else {
					resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("Could not store file  .. . Please try again! ");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Could not store file  .. . Please try again! ");
		}

		System.out.println("Returning response .....");
		return resposeBody;
	}

	@PutMapping(value = "/updateFile/{username}/docId/{id}", consumes = "multipart/form-data")
	public ResponseEntity<?> updateFile(@RequestParam("username") String username, @RequestParam("id") Integer id,
			@RequestParam("file") MultipartFile file) {

		ResponseEntity<?> resposeBody = ResponseEntity.noContent().build();

		try {
			// Normalize file name
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			System.out.println("File Name :" + fileName);

			// Saving file to Disk ...
			String path = saveFileToDisk(file);
			if (path == null) {
				resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Sorry! Document upload Failed.. " + fileName);

			} else {

				UserDocuments documents = documentServiceIntf.getDocumentById(id);
				if (documents == null) {

					resposeBody = ResponseEntity.status(HttpStatus.OK)
							.body("Sorry! Document with ID : " + id + " not found");
				} else {

					documents.setFilePath(path);
					documents.setDocName(fileName);
					documents.setDocType(file.getContentType());
					documents.setUserName(username);
					documents.setCreateDate(documents.getCreateDate());
					UserDocuments doc = documentServiceIntf.updateDocument(documents);
					if (doc != null) {

						UserDocumentResponseDTO responseDTO = new UserDocumentResponseDTO();
						responseDTO.setDocId(doc.getDocId());
						responseDTO.setDocName(doc.getDocName());
						responseDTO.setUserName(doc.getUserName());
						responseDTO.setDocType(doc.getDocType());
						responseDTO.setCreateDate(doc.getCreateDate());
						responseDTO.setUpdateDate(doc.getUpdateDate());
						responseDTO.setDocUrl(doc.getFilePath());
						resposeBody = ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
					} else {
						resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body("Could not store file  .. . Please try again! ");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Could not store file  ... Please try again! ");
		}

		System.out.println("Returning response .....");
		return resposeBody;
	}

	@DeleteMapping("/delete/{docId}")
	public ResponseEntity<?> removeDocument(@PathVariable("docId") Integer docId) {

		ResponseEntity<?> resposeBody = ResponseEntity.noContent().build();
		try {
			UserDocuments documents = documentServiceIntf.getDocumentById(docId);
			if (documents == null) {

				resposeBody = ResponseEntity.status(HttpStatus.OK)
						.body("Sorry! Document with ID : " + docId + " not found");
			} else {
				if (documentServiceIntf.deleteDocument(docId)) {
					resposeBody = ResponseEntity.status(HttpStatus.OK).body("Document Deletion Success");
				} else {
					resposeBody = ResponseEntity.status(HttpStatus.OK).body("Document Deletion Failed");
				}

			}
		} catch (Exception e) {
			resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			e.printStackTrace();
		}
		return resposeBody;
	}

	@GetMapping("/{docId}")
	public ResponseEntity<?> getDocuments(@PathVariable("docId") Integer docId) {

		System.out.println("getDocuments by ID :"+docId);
		ResponseEntity<?> resposeBody = ResponseEntity.noContent().build();
		try {
			UserDocuments doc = documentServiceIntf.getDocumentById(docId);
			if (doc != null) {
				System.out.println(doc.toString());
				UserDocumentResponseDTO responseDTO = new UserDocumentResponseDTO();
				responseDTO.setDocId(doc.getDocId());
				responseDTO.setDocName(doc.getDocName());
				responseDTO.setUserName(doc.getUserName());
				responseDTO.setDocType(doc.getDocType());
				responseDTO.setCreateDate(doc.getCreateDate());
				responseDTO.setUpdateDate(doc.getUpdateDate());
				responseDTO.setDocUrl(doc.getFilePath());
				resposeBody = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
			} else {
				resposeBody = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document Not Found With ID:" + docId);
			}
		} catch (Exception e) {
			resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			e.printStackTrace();
		}
		return resposeBody;
	}

	@GetMapping("/ByUser/{user}")
	public ResponseEntity<?> getAllDocuments(@PathVariable("user") String user) {
		System.out.println("getAllDocuments by User :"+user);
		ResponseEntity<?> resposeBody = ResponseEntity.noContent().build();
		UserDocumentResponseDTO responseDTO = null;
		List<UserDocumentResponseDTO> responseDTOs = new ArrayList<UserDocumentResponseDTO>();
		try {
			List<UserDocuments> documentsList = documentServiceIntf.getAllDocuments(user);
			System.out.println(documentsList.toString());
			for (UserDocuments doc : documentsList) {

				responseDTO = new UserDocumentResponseDTO();
				responseDTO.setDocId(doc.getDocId());
				responseDTO.setDocName(doc.getDocName());
				responseDTO.setUserName(doc.getUserName());
				responseDTO.setDocType(doc.getDocType());
				responseDTO.setCreateDate(doc.getCreateDate());
				responseDTO.setUpdateDate(doc.getUpdateDate());
				responseDTO.setDocUrl(doc.getFilePath());
				responseDTOs.add(responseDTO);
			}
			resposeBody = ResponseEntity.status(HttpStatus.OK).body(responseDTOs);
		} catch (Exception e) {
			resposeBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			e.printStackTrace();
		}
		return resposeBody;
	}

	private String saveFileToDisk(MultipartFile file) {
		System.out.println("Saving file to path :" + savefilePath);
		String filePath = null;

		try {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			File path = new File(savefilePath + fileName);
			path.createNewFile();
			FileOutputStream output = new FileOutputStream(path);
			output.write(file.getBytes());
			output.close();
			filePath = path.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("File uploaded on path :" + filePath);
		return filePath;
	}
}
