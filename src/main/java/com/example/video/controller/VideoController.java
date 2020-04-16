package com.example.video.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.video.exception.ServiceException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.Video;
import com.example.video.service.VideoServiceImpl;
import com.example.video.constants.RESTUriConstant;
import com.example.video.util.HTTPStatusResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class VideoController {
	@Autowired
	private VideoServiceImpl videoService;

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doGetAllVideos()  {
		List<Video> videos;
		try {
			videos = videoService.getAllVideos();
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_RETRIVAL_SUCCESS, videos),
				HttpStatus.OK);
	}

	@GetMapping(value = "/listLevels", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doGetAllLevels()  {
		List<Level> videos;
		try {
			videos = videoService.getAllLevels();
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_RETRIVAL_SUCCESS, videos),
				HttpStatus.OK);

	}

	@GetMapping(value = "/listCategories", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doGetAllCategories()  {
		List<Category> categories;
		try {
			categories = videoService.getAllCategories();
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_RETRIVAL_SUCCESS, categories),
				HttpStatus.OK);
	}

	@GetMapping(value = "/listById/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doGetVideoById(@PathVariable int videoId) {

		Video video;
		try {
			video = videoService.getVideoById(videoId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_RETRIVAL_SUCCESS, video),
				HttpStatus.OK);
	}

	@GetMapping(value = "/listActive", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doGetActivatedVideos()  {
		List<Video> videos;
		try {
			videos = videoService.getActivatedVideos();
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_RETRIVAL_SUCCESS, videos),
				HttpStatus.OK);
	}

	@GetMapping(value = "/listDeactive", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doGetDeactivatedVideos()  {
		List<Video> videos;
		try {
			videos = videoService.getDeactivatedVideos();
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_RETRIVAL_SUCCESS, videos),
				HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteById/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doDeleteVideoById(@PathVariable int videoId)  {

		try {
			videoService.deleteVideoById(videoId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_DELETE_SUCCESS),
				HttpStatus.OK);

	}
	
	@DeleteMapping(value = "/deleteVideoContentById/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> dodeleteVideoContentById(@PathVariable int videoId)
			 {

		try {
			videoService.deleteVideoContentById(videoId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_DELETE_SUCCESS),
				HttpStatus.OK);

	}

	@DeleteMapping(value = "/deleteReferenceArtifactById/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doDeleteReferenceArtifactById(@PathVariable int videoId)
			 {

		try {
			videoService.deleteReferenceArtifactById(videoId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_DELETE_SUCCESS),
				HttpStatus.OK);

	}

	@DeleteMapping(value = "/deleteSampleProgramById/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doDeleteSampleProgramById(@PathVariable int videoId)  {

		try {
			videoService.deleteSampleProgramById(videoId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_DELETE_SUCCESS),
				HttpStatus.OK);

	}

	@DeleteMapping(value = "/deleteReferenceUrlById/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doDeleteReferenceUrlById(@PathVariable int videoId) {

		try {
			videoService.deleteReferenceUrlById(videoId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_DELETE_SUCCESS),
				HttpStatus.OK);

	}

	@GetMapping(value = "/toggleStatus/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doToggleStatus(@PathVariable int videoId)  {

		try {
			videoService.toggleStatus(videoId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_UPDATE_SUCCESS),
				HttpStatus.OK);

	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doaddVideos(@RequestBody Video video)  {

		try {

			videoService.addVideo(video);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
					HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_INSERT_SUCCESS),
				HttpStatus.OK);

	}

	@PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> doEditVideos(@RequestBody Video video) {

		try {

			videoService.updateVideo(video);
		}catch (ServiceException e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
					HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.DATA_UPDATE_SUCCESS),
				HttpStatus.OK);

	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HTTPStatusResponse> uploadToLocalFileSystem(@RequestParam("file") MultipartFile file)
		 {
		BufferedOutputStream stream;
		try {
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//			String fileExtension=fileName.substring(fileName.lastIndexOf("."));
//			fileName=fileName.substring(0,fileName.lastIndexOf("."));
//			System.out.println("filename is "+fileName);
//			
//			System.out.println("file extension is"+fileExtension);
//			String ts=timeStamp.toString();
//			fileName=" "+fileName+ts+fileExtension;
//			System.out.println("filename to be  is "+fileName);
			byte[] bytes = file.getBytes();
			stream = new BufferedOutputStream(
					new FileOutputStream(new File(RESTUriConstant.UPLOAD_DIRECTORY + File.separator + fileName)));
			stream.write(bytes);
			stream.flush();
			stream.close();

		} catch (Exception e) {
			return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.BAD_REQUEST.value(), "File upload error"),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new HTTPStatusResponse(HttpStatus.OK.value(), RESTUriConstant.FILE_UPLOAD_SUCCESS),
				HttpStatus.OK);
	}

	@GetMapping(value = "/download/{fileName:.+}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> doDownloadFileFromLocal(@PathVariable String fileName) throws Exception {
		String response;
		String contentType = "text/plain";
		try {
			response = videoService.downloadFileFromLocal(fileName);

		} catch (ServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(response);
	}

}