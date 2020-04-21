package com.example.video.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.video.exception.DBException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.ReferenceUrl;
import com.example.video.model.Video;

class VideoDAOImplTest {

	@InjectMocks
	VideoDAOImpl videodao;

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	Query<Level> mockLevelQuery;

	@Mock
	Query<Category> mockCategoryQuery;

	@Mock
	Query<Video> mockVideoQuery;

//	@Mock
//	Query<ReferenceArtifact> mockRefArtQuery;
//
//	@Mock
//	Query<SampleProgram> mockSamProgQuery;

	@Mock
	Query<ReferenceUrl> mockRefUrlQuery;

	@Mock
	Transaction transaction;

	@Captor
	private ArgumentCaptor<Integer> arg;

	@Captor
	private ArgumentCaptor<Video> videoArg;

	List<Level> levelList = new ArrayList<Level>();

	List<Category> categoryList = new ArrayList<Category>();

	List<Video> videoList = new ArrayList<Video>();

	Video video = new Video();
	ReferenceUrl refUrl = new ReferenceUrl();

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		videoList = getVideoList();
		video = getVideo();
		levelList = getLevelList();
		categoryList = getCategoryList();
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.beginTransaction()).thenReturn(transaction);
	}

	@AfterEach
	void tearDown() throws Exception {
		doNothing().when(transaction).commit();
		doNothing().when(session).close();
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
		video.setStatus(true);
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

	@Test
	void testGetAllVideos() throws DBException {
		doReturn(mockVideoQuery).when(session).createQuery(Mockito.anyString(), Mockito.any());
		when(mockVideoQuery.getResultList()).thenReturn(videoList);
		assertNotNull(videodao.getAllVideos());
		assertEquals(videodao.getAllVideos(), videoList);
	}

	@Test
	void testGetAllVideosExpectFailure() throws DBException {
		assertThrows(DBException.class, () -> videodao.getAllVideos());
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

	@Test
	void testGetAllLevels() {
		doReturn(mockLevelQuery).when(session).createQuery(Mockito.anyString(), Mockito.any());
		when(mockLevelQuery.getResultList()).thenReturn(levelList);
		assertNotNull(videodao.getAllLevels());
		assertEquals(videodao.getAllLevels(),levelList);
	}

	@Test
	void testGetAllLevelsExpectFailure() throws DBException {
		assertThrows(DBException.class, () -> videodao.getAllLevels());
	}

	@Test
	void testGetAllCategories() {
		doReturn(mockCategoryQuery).when(session).createQuery(Mockito.anyString(), Mockito.any());
		when(mockCategoryQuery.getResultList()).thenReturn(categoryList);
		List<Category> category = new ArrayList<>();
		category = videodao.getAllCategories();
		assertNotNull(category);
		assertEquals(videodao.getAllLevels(), categoryList);
	}

	@Test
	void testGetAllCategoriesExpectFailure() throws DBException {
		assertThrows(DBException.class, () -> videodao.getAllCategories());
	}

	@Test
	void testGetVideoById() {
		int id = 0;
		doReturn(video).when(session).get(Video.class, id);
		assertNotNull(video);
		assertNotNull(videodao.getVideoById(id));
		assertEquals(videodao.getVideoById(id), video);
	}

	@Test
	void testGetVideoByIdExpectFailure() throws DBException {
		assertThrows(DBException.class, () -> videodao.getVideoById(Mockito.anyInt()));
	}

//	@Test
//	public void getReferenceArtifactById() throws DBException {
//		int id = 0;
//		doReturn(refArt).when(session).get(ReferenceArtifact.class, id);
//		assertNotNull(refArt);
//		assertEquals(videodao.getReferenceArtifactById(id), refArt);
//	}
//
//	@Test
//	public void getReferenceArtifactByIdExpectFailure() throws DBException {
//		assertThrows(DBException.class, () -> videodao.getReferenceArtifactById(Mockito.anyInt()));
//	}
//
//	@Test
//	public void getSampleProgramById() throws DBException {
//		int id = 0;
//		doReturn(samProg).when(session).get(SampleProgram.class, id);
//		assertNotNull(samProg);
//		assertEquals(videodao.getSampleProgramById(id), samProg);
//	}
//
//	@Test
//	public void getSampleProgramByIdExpectFailure() throws DBException {
//		assertThrows(DBException.class, () -> videodao.getSampleProgramById(Mockito.anyInt()));
//	}

	@Test
	public void getReferenceUrlById() throws DBException {
		int id = 0;
		doReturn(refUrl).when(session).get(ReferenceUrl.class, id);
		assertNotNull(refUrl);
		assertEquals(videodao.getReferenceUrlById(id), refUrl);
	}

	@Test
	public void getReferenceUrlByIdExpectFailure() throws DBException {
		assertThrows(DBException.class, () -> videodao.getReferenceUrlById(Mockito.anyInt()));
	}

	@Test
	void testToggleStatus() {
		Boolean status = true;
		int id = 1;
		doReturn(mockVideoQuery).when(session).createQuery(Mockito.anyString(), Mockito.any());
		when(mockVideoQuery.getSingleResult()).thenReturn(video);
		assertNotNull(video);
		video.setStatus(status);
		doNothing().when(session).update(video);
		videodao.toggleStatus(id);
	}

	@Test
	void testToggleStatusExpectFailure() throws DBException {
		int id = 0;
		when(session.createQuery(Mockito.anyString(), Mockito.any())).thenThrow(DBException.class);
		assertThrows(DBException.class, () -> videodao.toggleStatus(id));
	}

	@Test
	void testGetActivatedVideos() {
		doReturn(mockVideoQuery).when(session).createQuery(Mockito.anyString(), Mockito.any());
		when(mockVideoQuery.getResultList()).thenReturn(videoList);
		assertNotNull(videodao.getActivatedVideos());
		assertEquals(videodao.getActivatedVideos(), videoList);
	}

	@Test
	void testGetActivatedVideosExpectFailure() throws DBException {
		assertThrows(DBException.class, () -> videodao.getActivatedVideos());
	}

	@Test
	void testGetDeactivatedVideos() {
		doReturn(mockVideoQuery).when(session).createQuery(Mockito.anyString(), Mockito.any());
		when(mockVideoQuery.getResultList()).thenReturn(videoList);
		assertNotNull(videodao.getDeactivatedVideos());
		assertEquals(videodao.getDeactivatedVideos(), videoList);
	}

	@Test
	void testGetDeactivatedVideosExpectFailure() throws DBException {
		assertThrows(DBException.class, () -> videodao.getDeactivatedVideos());
	}

	@Test
	void testDeleteVideoById() {
		int id = 0;
		doReturn(video).when(session).get(Video.class, id);
		assertNotNull(video);
		doNothing().when(session).delete(video);
		videodao.deleteVideoById(id);
	}

	@Test
	void testDeleteVideoByIdExpectFailure() throws DBException {
		assertThrows(DBException.class, () -> videodao.deleteVideoById(Mockito.anyInt()));
	}

//	@Test
//	void testDeleteReferenceArtifactById() {
//		int id = 0;
//		int result = 0;
//		doReturn(mockRefArtQuery).when(session).createQuery(Mockito.anyString());
//		when(mockRefArtQuery.setParameter(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockRefArtQuery);
//		when(mockRefArtQuery.executeUpdate()).thenReturn(result);
//		videodao.deleteReferenceArtifactById(id);
//		verify(mockRefArtQuery, times(1)).setParameter(Mockito.anyString(), arg.capture());
//		assertEquals(id, arg.getValue().intValue());
//
//	}
//
//	@Test
//	void testDeleteReferenceArtifactByIdExpectFailure() throws DBException {
//		int id = 0;
//		assertThrows(DBException.class, () -> videodao.deleteReferenceArtifactById(id));
//	}
//
//	@Test
//	void testDeleteSampleProgramById() {
//		int id = 0;
//		int result = 0;
//		doReturn(mockSamProgQuery).when(session).createQuery(Mockito.anyString());
//		when(mockSamProgQuery.setParameter(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockSamProgQuery);
//		when(mockSamProgQuery.executeUpdate()).thenReturn(result);
//		videodao.deleteSampleProgramById(id);
//		verify(mockSamProgQuery, times(1)).setParameter(Mockito.anyString(), arg.capture());
//		assertEquals(id, arg.getValue().intValue());
//
//	}

	@Test
	void testDeleteSampleProgramByIdExpectFailure() {
		int id = 0;
		assertThrows(DBException.class, () -> videodao.deleteSampleProgramById(id));
	}

	@Test
	void testDeleteReferenceUrlById() {
		int result = 0;
		int id = 0;
		doReturn(mockRefUrlQuery).when(session).createQuery(Mockito.anyString());
		when(mockRefUrlQuery.setParameter(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockRefUrlQuery);
		when(mockRefUrlQuery.executeUpdate()).thenReturn(result);
		videodao.deleteReferenceUrlById(id);
		verify(mockRefUrlQuery, times(1)).setParameter(Mockito.anyString(), arg.capture());
		assertEquals(id, arg.getValue().intValue());

	}

	@Test
	void testDeleteReferenceUrlByIdExpectFailure() throws DBException {
		int id = 0;
		assertThrows(DBException.class, () -> videodao.deleteReferenceUrlById(id));
	}

//	@Test
//	void testAddVideo() {
//		int id = 0;
//		doReturn(id).when(session).save(video);
//		videodao.addVideo(video);
//		verify(session, times(1)).save(videoArg.capture());
//		assertEquals(video, videoArg.getValue());
//	}

	@Test
	void testAddVideoExpectFailure() throws DBException {
		assertThrows(DBException.class, () -> videodao.addVideo(Mockito.any()));
	}

//	@Test
//	void testUpdateVideo() {
//		doNothing().when(session).update(video);
//		videodao.updateVideo(video);
//		verify(session, times(1)).update(videoArg.capture());
//		assertEquals(video, videoArg.getValue());
//
//	}

	@Test
	void testUpdateVideoExpectFailure() throws DBException {
		assertThrows(DBException.class, () -> videodao.updateVideo(Mockito.any()));
	}
}
