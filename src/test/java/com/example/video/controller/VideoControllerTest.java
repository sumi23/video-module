package com.example.video.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.video.exception.ServiceException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.ReferenceUrl;
import com.example.video.model.Video;
import com.example.video.service.VideoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs()
@WebAppConfiguration
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
class VideoControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	VideoController videoController;

	@Mock
	VideoServiceImpl videoService;

	List<Level> levelList = new ArrayList<Level>();

	List<Category> categoryList = new ArrayList<Category>();

	List<Video> videoList = new ArrayList<Video>();

	Video video = new Video();

	private ObjectMapper objectmapper;

	@BeforeEach
	public void init(RestDocumentationContextProvider restDocumentation) {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(videoController)
				.apply(documentationConfiguration(restDocumentation)).build();
		levelList = getLevelList();
		categoryList = getCategoryList();
		videoList = getVideoList();
		video = getVideo();
		objectmapper = new ObjectMapper();

	}

	@Test
	void testDoGetAllVideos() throws Exception {
		when(videoService.getAllVideos()).thenReturn(videoList);
		this.mockMvc.perform(get("/list")).andExpect(status().isOk()).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
	}

	@Test
	void testDoGetAllVideosExpectFailure() throws Exception {
		doThrow(ServiceException.class).when(videoService).getAllVideos();
		this.mockMvc.perform(get("/list")).andExpect(status().isNotFound());
	}

	@Test
	void testDoGetAllLevels() throws Exception {
		when(videoService.getAllLevels()).thenReturn(levelList);
		this.mockMvc.perform(get("/listLevels")).andExpect(status().isOk());

	}

	@Test
	void testDoGetAllLevelsExpectFailure() throws Exception {

		doThrow(ServiceException.class).when(videoService).getAllLevels();
		this.mockMvc.perform(get("/listLevels")).andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testDoGetAllCategories() throws Exception {
		when(videoService.getAllCategories()).thenReturn(categoryList);
		this.mockMvc.perform(get("/listCategories")).andExpect(status().isOk());
	}

	@Test
	void testDoGetAllCategoriesExpectFailure() throws Exception {
		doThrow(ServiceException.class).when(videoService).getAllCategories();
		this.mockMvc.perform(get("/listCategories")).andExpect(status().isNotFound());
	}

	@Test
	void testDoGetActivatedVideos() throws ServiceException, Exception {
		when(videoService.getActivatedVideos()).thenReturn(videoList);
		this.mockMvc.perform(get("/listActive")).andExpect(status().isOk());
	}

	@Test
	void testDoGetActivatedVideosExpectFailure() throws ServiceException, Exception {
		doThrow(ServiceException.class).when(videoService).getActivatedVideos();
		this.mockMvc.perform(get("/listActive")).andExpect(status().isNotFound());
	}

	@Test
	void testDoGetDeactivatedVideos() throws Exception {
		when(videoService.getDeactivatedVideos()).thenReturn(videoList);
		this.mockMvc.perform(get("/listDeactive")).andExpect(status().isOk());
	}

	@Test
	void testDoGetDeactivatedVideosExpectFailure() throws Exception {

		doThrow(ServiceException.class).when(videoService).getDeactivatedVideos();
		this.mockMvc.perform(get("/listDeactive")).andExpect(status().isNotFound());
	}

	@Test
	void testDoGetVideoById() throws Exception {
		when(videoService.getVideoById(anyInt())).thenReturn(video);
		this.mockMvc.perform(get("/listById/{id}", anyInt())).andExpect(status().isOk());
		verify(videoService, times(1)).getVideoById(anyInt());
	}

	@Test
	void testDoGetVideoByIdExpectFailure() throws Exception {
		doThrow(ServiceException.class).when(videoService).getVideoById(anyInt());
		this.mockMvc.perform(get("/listById/{id}", anyInt())).andExpect(status().isNotFound()).andDo(print());
		verify(videoService, times(1)).getVideoById(anyInt());
	}

	@Test
	void testDoDeleteVideoById() throws Exception {
		doNothing().when(videoService).deleteVideoById(anyInt());
		this.mockMvc.perform(delete("/deleteById/{id}", anyInt())).andExpect(status().isOk());
	}

	@Test
	void testDoDeleteVideoByIdExpectFailure() throws Exception {
		doThrow(ServiceException.class).when(videoService).deleteVideoById(anyInt());
		this.mockMvc.perform(delete("/deleteById/{id}", anyInt())).andExpect(status().isNotFound());
	}

	@Test
	void testDoDeleteReferenceArtifactById() throws Exception {
		doNothing().when(videoService).deleteReferenceArtifactById(anyInt());
		this.mockMvc.perform(delete("/deleteReferenceArtifactById/{id}", anyInt())).andExpect(status().isOk());
	}

	@Test
	void testDoDeleteReferenceArtifactByIdExpectFailure() throws Exception {
		doThrow(ServiceException.class).when(videoService).deleteReferenceArtifactById(anyInt());
		this.mockMvc.perform(delete("/deleteReferenceArtifactById/{id}", anyInt())).andExpect(status().isNotFound());
	}

	@Test
	void testDoDeleteSampleProgramById() throws Exception {
		doNothing().when(videoService).deleteSampleProgramById(anyInt());
		this.mockMvc.perform(delete("/deleteSampleProgramById/{id}", anyInt())).andExpect(status().isOk());
	}

	@Test
	void testDoDeleteSampleProgramByIdExpectFailure() throws Exception {
		doThrow(ServiceException.class).when(videoService).deleteSampleProgramById(anyInt());
		this.mockMvc.perform(delete("/deleteSampleProgramById/{id}", anyInt())).andExpect(status().isNotFound());
	}

	@Test
	void testDoDeleteReferenceUrlById() throws Exception {
		doNothing().when(videoService).deleteReferenceUrlById(anyInt());
		this.mockMvc.perform(delete("/deleteReferenceUrlById/{id}", anyInt())).andExpect(status().isOk());
	}

	@Test
	void testDoDeleteReferenceUrlByIdExpectFailure() throws Exception {
		doThrow(ServiceException.class).when(videoService).deleteReferenceUrlById(anyInt());
		this.mockMvc.perform(delete("/deleteReferenceUrlById/{id}", anyInt())).andExpect(status().isNotFound());
	}

	@Test
	void testDoToggleStatus() throws Exception {
		doNothing().when(videoService).toggleStatus(anyInt());
		MvcResult mvcResult = this.mockMvc.perform(get("/toggleStatus/{id}", 1)).andExpect(status().isOk()).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	void testDoToggleStatusExpectFailure() throws Exception {
		doThrow(ServiceException.class).when(videoService).toggleStatus(anyInt());
		this.mockMvc.perform(get("/toggleStatus/{id}", anyInt())).andExpect(status().isNotFound());
	}

	@Test
	void testDoaddVideos() throws Exception {

		doNothing().when(videoService).addVideo(video);
		String videoJson = objectmapper.writeValueAsString(video);
		this.mockMvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON_VALUE).content(videoJson))
				.andExpect(status().isOk());
	}

	@Test
	void testDoAddVideosExpectFailure() throws Exception {
		String videoJson = objectmapper.writeValueAsString(video);
		doThrow(ServiceException.class).when(videoService).addVideo(any());
		this.mockMvc.perform(post("/add").content(videoJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest()).andDo(print()).andReturn();
	}

	@Test
	void testDoEditVideos() throws Exception {

		doNothing().when(videoService).updateVideo(video);
		String videoJson = objectmapper.writeValueAsString(video);
		this.mockMvc.perform(put("/edit").contentType(MediaType.APPLICATION_JSON_VALUE).content(videoJson))
				.andExpect(status().isOk());
	}

	@Test
	void testDoEditVideosExpectFailure() throws Exception {
		String videoJson = objectmapper.writeValueAsString(video);
		doThrow(ServiceException.class).when(videoService).updateVideo(any());
		this.mockMvc.perform(put("/edit").content(videoJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testUploadToLocalFileSystem() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", MediaType.TEXT_PLAIN_VALUE,
				"some file content".getBytes());
		this.mockMvc.perform(multipart("/upload").file(firstFile)).andExpect(status().isOk());
	}

	@Test
	void testUploadToLocalFileSystemExpectFailure() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("filename.txt", "content".getBytes());
		this.mockMvc.perform(multipart("/upload").file(firstFile)).andExpect(status().isBadRequest());
	}

	@Test
	void testDoDownloadFileFromLocal() throws Exception {
		String fileName = "file.txt";
		String encodedFileContent = "ZmlsZSBjb250ZW50";
		when(videoService.downloadFileFromLocal(fileName)).thenReturn(encodedFileContent);
		this.mockMvc.perform(get("/download/{fileName}", fileName)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE));
		verify(videoService, times(1)).downloadFileFromLocal(fileName);
	}

	@Test
	void testDoDownloadFileFromLocalExpectFailure() throws Exception {
		String fileName = "file.txt";
		doThrow(ServiceException.class).when(videoService).downloadFileFromLocal(anyString());
		this.mockMvc.perform(get("/download/{fileName}",fileName)).andExpect(status().isNotFound());
	}

	public List<Level> getLevelList() {

		Level level = new Level();
		level.setId(1);
		level.setName("level 1");
		levelList.add(level);
		return levelList;
	}

	public List<Category> getCategoryList() {

		Category category = new Category();
		category.setId(1);
		category.setName("java");
		categoryList.add(category);
		return categoryList;
	}

	public Video getVideo() {

//		ReferenceArtifact referenceArtifact1 = new ReferenceArtifact();
//		referenceArtifact1.setId(1);
//		referenceArtifact1.setName("reference artifact example");
//		referenceArtifact1.setFile("java.txt");
//		referenceArtifact1.setDescription("This is a reference artifact");
//		SampleProgram sampleProgram1 = new SampleProgram();
//		sampleProgram1.setId(1);
//		sampleProgram1.setName("sample program example");
//		sampleProgram1.setFile("sample.txt");
//		sampleProgram1.setDescription("This is a sample program");
		ReferenceUrl referenceUrl1 = new ReferenceUrl();
		referenceUrl1.setId(1);
		referenceUrl1.setName("reference url example");
		referenceUrl1.setUrl("http://www.javase.com");
		referenceUrl1.setDescription("This is a reference url");
//		List<ReferenceArtifact> refArtList = new ArrayList<ReferenceArtifact>();
//		refArtList.add(referenceArtifact1);
//		List<SampleProgram> samProgList = new ArrayList<SampleProgram>();
//		samProgList.add(sampleProgram1);
		List<ReferenceUrl> refUrlList = new ArrayList<ReferenceUrl>();
		refUrlList.add(referenceUrl1);
		Level level = new Level();
		level.setId(1);
		level.setName("Level 1");
		Category category = new Category();
		category.setId(1);
		category.setName("java");
		video.setId(1);
		video.setName("java");
		video.setDisplayName("java");
		video.setUrl("https://www.javase.com");
		Time time = Time.valueOf("01:20:09");
		video.setDuration(time);
		video.setTags("java");
		video.setCreatedBy("Subhalakshmi");
		Timestamp createTimestamp = new Timestamp(System.currentTimeMillis());
		video.setCreatedOn(createTimestamp);
		video.setModifiedBy("Subhalakshmi");
		Timestamp modifyTimestamp = new Timestamp(System.currentTimeMillis());
		video.setModifiedOn(modifyTimestamp);
		video.setDescription("This is a java video");
		video.setTranscript("file.txt");
		video.setStatus(true);
		video.setLevel(level);
		video.setCategory(category);
//		video.setReferenceArtifact(refArtList);
//		video.setSampleProgram(samProgList);
		video.setReferenceUrl(refUrlList);
		return video;

	}

	public List<Video> getVideoList() {
		videoList.add(video);
		return videoList;
	}

}
