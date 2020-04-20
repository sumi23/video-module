package com.example.video.dao;

import java.util.List;

import com.example.video.exception.DBException;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.ReferenceUrl;
import com.example.video.model.User;
import com.example.video.model.Video;
import com.example.video.model.VideoContent;

interface IVideoDAO {

	List<Video> getAllVideos();

	Video getVideoById(int id);

	void addVideo(Video video);

	void deleteVideoById(int videoId);

	List<Video> getDeactivatedVideos();

	List<Video> getActivatedVideos();

	void updateVideo(Video video);

	void deleteReferenceUrlById(int videoId);

	void toggleStatus(int videoId);

	List<Level> getAllLevels();

	List<Category> getAllCategories();

	ReferenceUrl getReferenceUrlById(int id);

	VideoContent getVideoContentById(int id) throws DBException;

	void deleteVideoContentById(int id) throws DBException;

	VideoContent getReferenceArtifactById(int id) throws DBException;

	void deleteReferenceArtifactById(int id) throws DBException;

	VideoContent getSampleProgramById(int id) throws DBException;

	void deleteSampleProgramById(int Id) throws DBException;

	User findUserByName(String name) throws DBException;

	List<Video> getAllVideos(int pageNo,int pageSize) throws DBException;

	int getSize() throws DBException;
}
