package com.example.video.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import com.example.video.constants.BusinessConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.video.dao.VideoDAOImpl;
import com.example.video.exception.DBException;
import com.example.video.exception.ServiceException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.Video;
import com.example.video.model.User;

@Service
public class VideoServiceImpl implements IVideoService {

	@Autowired
	private VideoDAOImpl videoDAOImpl;

	
	@Override
	public User getUser(String name) throws ServiceException {
		User user;
		try {
			user = videoDAOImpl.findUserByName(name);
			if (user==null)
				throw new ServiceException("user not found");
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return user;
	}
	
	@Override
	public List<Video> getAllVideos(int pageNo) throws ServiceException {
		List<Video> videos;
		try {
			System.out.println("page no in service"+pageNo);
			videos = videoDAOImpl.getAllVideos(pageNo);
			if (videos.isEmpty())
				throw new ServiceException(BusinessConstant.VIDEOS_NOT_FOUND);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return videos;
	
	}
	
	@Override
	public List<Video> getAllVideos() throws ServiceException {
		List<Video> videos;
		try {
			videos = videoDAOImpl.getAllVideos();
			if (videos.isEmpty())
				throw new ServiceException(BusinessConstant.VIDEOS_NOT_FOUND);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return videos;
	}

	@Override
	public List<Level> getAllLevels() throws ServiceException {
		List<Level> levels;
		try {
			levels = videoDAOImpl.getAllLevels();
			if (levels.isEmpty()) {
				throw new ServiceException(BusinessConstant.LEVELS_NOT_FOUND);
			}
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return levels;
	}

	@Override
	public List<Category> getAllCategories() throws ServiceException {
		List<Category> categories;
		try {
			categories = videoDAOImpl.getAllCategories();
			if (categories.isEmpty()) {
				throw new ServiceException(BusinessConstant.CATEGORIES_NOT_FOUND);
			}
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return categories;
	}

	@Override
	public Video getVideoById(int videoId) throws ServiceException {
		Video video;
		try {
			video = videoDAOImpl.getVideoById(videoId);
			if (video == null) {
				throw new ServiceException(BusinessConstant.VIDEO_NOT_FOUND);
			}
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return video;
	}

	@Override
	public List<Video> getActivatedVideos() throws ServiceException {
		List<Video> videos;
		try {
			videos = videoDAOImpl.getActivatedVideos();
			if (videos.isEmpty()) {
				throw new ServiceException(BusinessConstant.VIDEOS_NOT_FOUND);
			}
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return videos;
	}

	@Override
	public List<Video> getDeactivatedVideos() throws ServiceException {
		List<Video> videos;
		try {
			videos = videoDAOImpl.getDeactivatedVideos();
			if (videos.isEmpty()) {
				throw new ServiceException(BusinessConstant.VIDEOS_NOT_FOUND);
			}
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
		return videos;
	}

	@Override
	public void toggleStatus(int videoId) throws ServiceException {
		try {
			if (videoDAOImpl.getVideoById(videoId) != null)
				videoDAOImpl.toggleStatus(videoId);
			else
				throw new ServiceException(BusinessConstant.VIDEO_NOT_FOUND);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void deleteVideoById(int videoId) throws ServiceException {
		try {
			if (videoDAOImpl.getVideoById(videoId) != null)
				videoDAOImpl.deleteVideoById(videoId);
			else
				throw new ServiceException(BusinessConstant.VIDEOS_NOT_FOUND);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public void deleteVideoContentById(int videoId) throws ServiceException {

		try {
			if (videoDAOImpl.getVideoContentById(videoId) != null)
				videoDAOImpl.deleteVideoContentById(videoId);
			else
				throw new ServiceException(BusinessConstant.VIDEO_CONTENT_NOT_FOUND);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}

	}
	
	@Override
	public void deleteReferenceArtifactById(int videoId) throws ServiceException {

		try {
			if (videoDAOImpl.getReferenceArtifactById(videoId) != null)
				videoDAOImpl.deleteReferenceArtifactById(videoId);
			else
				throw new ServiceException(BusinessConstant.REFERENCE_ARTIFACT_NOT_FOUND);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public void deleteSampleProgramById(int videoId) throws ServiceException {

		try {
			if (videoDAOImpl.getSampleProgramById(videoId) != null)
				videoDAOImpl.deleteSampleProgramById(videoId);
			else
				throw new ServiceException(BusinessConstant.SAMPLE_PROGRAM_NOT_FOUND);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public void deleteReferenceUrlById(int videoId) throws ServiceException {

		try {
			if (videoDAOImpl.getReferenceUrlById(videoId) != null)
				videoDAOImpl.deleteReferenceUrlById(videoId);
			else
				throw new ServiceException(BusinessConstant.REFERENCE_URL_NOT_FOUND);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public void addVideo(Video video) throws ServiceException {
		try {
			videoDAOImpl.addVideo(video);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public void updateVideo(Video video) throws ServiceException {
		try {
			videoDAOImpl.updateVideo(video);
		} catch (DBException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public String downloadFileFromLocal(String fileName) throws ServiceException {
		String encodedString;
		try {
			Path path = Paths.get(BusinessConstant.UPLOAD_DIRECTORY + fileName);
			byte[] bytes = Files.readAllBytes(path);
			Base64.Encoder encoder = Base64.getEncoder();
			byte[] encoded = encoder.encode(bytes);
			encodedString = new String(encoded);
		} catch (IOException e) {
			throw new ServiceException(BusinessConstant.FILE_DOWNLOAD_ERROR);

		}
		return encodedString;
	}
}