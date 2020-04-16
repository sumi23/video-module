package com.example.video.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.example.video.dao.VideoDAOImpl;
import com.example.video.exception.DBException;
import com.example.video.exception.ServiceException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.ReferenceUrl;
import com.example.video.model.Video;

class VideoServiceImplTest {

	@InjectMocks
	VideoServiceImpl videoService;

	@Mock
	VideoDAOImpl videodao;
	
	List<Level> levelList = new ArrayList<Level>();

	List<Category> categoryList = new ArrayList<Category>();

	List<Video> videoList = new ArrayList<Video>();

	Video video = new Video();

	private int id = 1;
	String fileName;

	@Captor
	private ArgumentCaptor<String> fileArg;

	@Captor
	private ArgumentCaptor<Integer> arg;

	@Captor
	private ArgumentCaptor<Video> videoArg;

	ReferenceUrl refUrl = new ReferenceUrl();

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		levelList = getLevelList();
		categoryList = getCategoryList();
		videoList = getVideoList();
		video = getVideo();

	}

	@Test
	void testGetAllVideos() throws ServiceException {

		when(videodao.getAllVideos()).thenReturn(videoList);
		assertNotNull(videoList);
		assertEquals(videoService.getAllVideos(), getVideoList());
		verify(videodao, times(1)).getAllVideos();
	}

	@Test
	void testGetAllVideosExpectFailure() throws DBException, ServiceException {
		List<Video> videoList = new ArrayList<>();
		when(videodao.getAllVideos()).thenReturn(videoList);
		doThrow(DBException.class).when(videodao).getAllVideos();
		assertThrows(ServiceException.class, () -> videoService.getAllVideos());
	}

	@Test
	void testGetAllLevels() throws ServiceException {

		when(videodao.getAllLevels()).thenReturn(levelList);
		assertNotNull(levelList);
		assertEquals(videoService.getAllLevels(), getLevelList());
		verify(videodao, times(1)).getAllLevels();
	}

	@Test
	void testGetAllLevelsExpectFailure() throws DBException, ServiceException {
		assertTrue(videodao.getAllLevels().isEmpty());
		doThrow(DBException.class).when(videodao).getAllLevels();
		assertThrows(ServiceException.class, () ->videoService.getAllLevels());

	}

	@Test
	void testGetAllCategories() throws ServiceException {
		when(videodao.getAllCategories()).thenReturn(categoryList);
		assertTrue(categoryList.size()>0);
		assertEquals(videoService.getAllCategories(), getCategoryList());
		verify(videodao, times(1)).getAllCategories();
	}

	@Test
	void testGetAllCategoriesExpectFailure() throws DBException, ServiceException {
		assertTrue(videodao.getAllCategories().isEmpty());
		doThrow(DBException.class).when(videodao).getAllCategories();
		assertThrows(ServiceException.class, () ->videoService.getAllCategories());

	}

	@Test
	void testGetVideoById() throws ServiceException {
		when(videodao.getVideoById(id)).thenReturn(video);
		assertNotNull(video);
		assertEquals(videoService.getVideoById(id), getVideo());
		verify(videodao, times(1)).getVideoById(id);
	}

	@Test
	void testGetVideoByIdExpectFailure() throws DBException, ServiceException {
		int id = 0;
		when(videodao.getVideoById(id)).thenReturn(null);
		assertNull(videodao.getVideoById(id));
		doThrow(DBException.class).when(videodao).getVideoById(id);
		assertThrows(ServiceException.class, () ->videoService.getVideoById(id));
	}

	@Test
	void testGetActivatedVideos() throws ServiceException {
		when(videodao.getActivatedVideos()).thenReturn(videoList);
		assertNotNull(videoList);
		assertEquals(videoService.getActivatedVideos(), getVideoList());
		verify(videodao, times(1)).getActivatedVideos();
	}

	@Test
	void testGetActivatedVideosExpectFailure() throws DBException, ServiceException {
		List<Video> videoList = new ArrayList<>();
		when(videodao.getActivatedVideos()).thenReturn(videoList);
		assertTrue(videoList.isEmpty());
		doThrow(DBException.class).when(videodao).getActivatedVideos();
		assertThrows(ServiceException.class, () -> videoService.getActivatedVideos());
	}

	@Test
	void testGetDeactivatedVideos() throws ServiceException {
		when(videodao.getDeactivatedVideos()).thenReturn(videoList);
		assertNotNull(videoList);
		assertEquals(videoService.getDeactivatedVideos(), getVideoList());
		verify(videodao, times(1)).getDeactivatedVideos();
	}

	@Test
	void testGetDeactivatedExpectFailure() throws DBException, ServiceException {
		List<Video> videoList = new ArrayList<>();
		when(videodao.getDeactivatedVideos()).thenReturn(videoList);
		assertTrue(videoList.isEmpty());
		doThrow(DBException.class).when(videodao).getDeactivatedVideos();
		assertThrows(ServiceException.class, () -> videoService.getDeactivatedVideos());
	}

	@Test
	void testToggleStatus() throws ServiceException {
		when(videodao.getVideoById(id)).thenReturn(video);
		assertNotNull(video);
		videoService.toggleStatus(id);
		verify(videodao, times(1)).toggleStatus(arg.capture());
		assertEquals(id, arg.getValue());
	}
	
	@Test
	void testToggleStatusExpectFailure() throws ServiceException {
		when(videodao.getVideoById(id)).thenReturn(null);
		doThrow(DBException.class).when(videodao).toggleStatus(id);
		assertThrows(ServiceException.class, () -> videoService.toggleStatus(id));
	}

	@Test
	void testDeleteVideoById() throws ServiceException {
		when(videodao.getVideoById(id)).thenReturn(video);
		assertNotNull(video);
		doNothing().when(videodao).deleteVideoById(id);
		videoService.deleteVideoById(id);
		verify(videodao, times(1)).deleteVideoById(arg.capture());
		assertEquals(id, arg.getValue());
	}

	@Test
	void testDeleteVideoByIdExpectFailure() throws ServiceException {
		when(videodao.getVideoById(id)).thenReturn(null);
		assertNull(videodao.getVideoById(id));
		doThrow(DBException.class).when(videodao).deleteVideoById(id);
		assertThrows(ServiceException.class,()->videoService.deleteVideoById(id));
	}
	
//	@Test
//	void testDeleteReferenceArtifactById() throws ServiceException {
//		when(videodao.getReferenceArtifactById(id)).thenReturn(refArt);
//		assertNotNull(refArt);
//		doNothing().when(videodao).deleteReferenceArtifactById(id);
//		videoService.deleteReferenceArtifactById(id);
//		verify(videodao, times(1)).deleteReferenceArtifactById(arg.capture());
//		assertEquals(id, arg.getValue());
//	}
//	
//	@Test
//	void testDeleteReferenceArtifactByIdExpectFailure() throws ServiceException {
//		when(videodao.getReferenceArtifactById(anyInt())).thenReturn(null);
//		assertNull(videodao.getReferenceArtifactById(anyInt()));
//		doThrow(DBException.class).when(videodao).deleteReferenceArtifactById(anyInt());
//		assertThrows(ServiceException.class,()->videoService.deleteReferenceArtifactById(id));
//	}
	
//	@Test
//	void testDeleteSampleProgramById() throws ServiceException {
//		when(videodao.getSampleProgramById(anyInt())).thenReturn(samProg);
//		assertNotNull(samProg);
//		doNothing().when(videodao).deleteSampleProgramById(anyInt());
//		videoService.deleteSampleProgramById(anyInt());
//		verify(videodao, times(1)).deleteSampleProgramById(arg.capture());
//		assertEquals(anyInt(), arg.getValue());
//	}
//	
//	@Test
//	void testDeleteSampleProgramByIdExpectFailure() throws ServiceException {
//		when(videodao.getSampleProgramById(id)).thenReturn(null);
//		assertNull(videodao.getSampleProgramById(id));
//		doThrow(DBException.class).when(videodao).deleteSampleProgramById(id);
//		assertThrows(ServiceException.class,()->videoService.deleteSampleProgramById(id));
//	}
	@Test
	void testDeleteReferenceUrlById() throws ServiceException {
		when(videodao.getReferenceUrlById(id)).thenReturn(refUrl);
		assertNotNull(refUrl);
		doNothing().when(videodao).deleteReferenceUrlById(id);
		videoService.deleteReferenceUrlById(id);
		verify(videodao, times(1)).deleteReferenceUrlById(arg.capture());
		assertEquals(id, arg.getValue());
	}

	@Test
	void testDeleteReferenceUrlByIdExpectFailure() throws ServiceException {
		when(videodao.getReferenceUrlById(id)).thenReturn(null);
		assertNull(videodao.getReferenceUrlById(id));
		doThrow(DBException.class).when(videodao).deleteReferenceUrlById(id);
		assertThrows(ServiceException.class,()->videoService.deleteReferenceUrlById(id));
	}
	@Test
	void testAddVideo() throws ServiceException {
		videoService.addVideo(video);
		verify(videodao, times(1)).addVideo(videoArg.capture());
		assertNotNull(videoArg);
	}

	@Test
	void testAddVideoExpectFailure() throws ServiceException {
		doThrow(DBException.class).when(videodao).addVideo(video);
		assertThrows(ServiceException.class, () -> videoService.addVideo(video));
	}

	@Test
	void testUpdateVideo() throws ServiceException {

		videoService.updateVideo(video);
		verify(videodao, times(1)).updateVideo(videoArg.capture());
		assertNotNull(videoArg);
	}
	
	@Test
	void testUpdateVideoExpectFailure() throws ServiceException {
		doThrow(DBException.class).when(videodao).updateVideo(video);
		assertThrows(ServiceException.class, () -> videoService.updateVideo(video));
	}
	

	@Test
	void testDownloadFileFromLocal() throws ServiceException, IOException {
		String fileName="example.txt";
		videoService.downloadFileFromLocal(fileName);
		
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
		video.setCreatedBy("Subhalakshmi");
		Timestamp createTimestamp = new Timestamp(System.currentTimeMillis());
		video.setCreatedOn(createTimestamp);
		video.setModifiedBy("Subhalakshmi");
		Timestamp modifyTimestamp = new Timestamp(System.currentTimeMillis());
		video.setModifiedOn(modifyTimestamp);
		video.setDescription("This is a java video");
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
