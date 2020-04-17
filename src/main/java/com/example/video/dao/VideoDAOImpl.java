package com.example.video.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.video.constants.DataConstant;
import com.example.video.exception.DBException;
import com.example.video.model.Video;
import com.example.video.model.VideoContent;
import com.example.video.model.User;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.ReferenceUrl;

@Repository
public class VideoDAOImpl implements IVideoDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public User findUserByName(String name) throws DBException {
		User video;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			
			Query<User> query= session.createQuery("FROM User u where u.userName=:name",User.class);
			query.setParameter("name",name);
			video=query.getSingleResult();
		} catch (Exception e) {
			throw new DBException("user not record");
		}
		return video;
	}
	
	@Override
	public List<Video> getAllVideos(int pageNo) throws DBException {
		List<Video> videoList;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM Video";
			Query<Video> query = session.createQuery(hql, Video.class);
			query.setFirstResult(pageNo);
			query.setMaxResults(3);
			videoList = query.getResultList();
		} catch (Exception e) {
			throw new DBException("Error in fetching video records");
		}
		return videoList;
	}
	
	
	@Override
	public List<Video> getAllVideos() throws DBException {
		List<Video> videoList;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM Video";
			Query<Video> query = session.createQuery(hql, Video.class);
			videoList = query.getResultList();
		} catch (Exception e) {
			throw new DBException("Error in fetching video records");
		}
		return videoList;
	}

	@Override
	public List<Level> getAllLevels() throws DBException {
		List<Level> levelList;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM Level";
			Query<Level> query = session.createQuery(hql, Level.class);
			levelList = query.getResultList();
		} catch (Exception e) {
			throw new DBException("Error in fetching level records");
		}
		return levelList;
	}

	@Override
	public List<Category> getAllCategories() throws DBException {
		List<Category> categoryList;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM Category";
			Query<Category> query = session.createQuery(hql, Category.class);
			categoryList = query.getResultList();
		} catch (Exception e) {
			throw new DBException("Error in fetching category records");
		}
		return categoryList;
	}

	@Override
	public Video getVideoById(int videoId) throws DBException {
		Video video;
		try {
			Session session = sessionFactory.getCurrentSession();
			video = session.get(Video.class, videoId);
		} catch (Exception e) {
			throw new DBException("Error in fetching video record");
		}
		return video;
	}

	@Override
	public List<Video> getActivatedVideos() throws DBException {

		List<Video> videos = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "From Video v where v.status=true";
			Query<Video> query = session.createQuery(hql, Video.class);
			videos = query.getResultList();

		} catch (Exception e) {
			throw new DBException("Error in fetching activated video records");
		}
		return videos;
	}

	@Override
	public void toggleStatus(int videoId) throws DBException {
		Video video;
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql = "FROM Video v where v.id = :videoId";
			Query<Video> query = session.createQuery(hql, Video.class);
			query.setParameter("videoId", videoId);
			video = query.getSingleResult();
			video.setStatus(!video.getStatus());
			session.update(video);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DBException("Error in fetching activated video records");
		}
	}

	@Override
	public List<Video> getDeactivatedVideos() throws DBException {
		List<Video> videos = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql ="From Video v where v.status=false";
			Query<Video> query = session.createQuery(hql, Video.class);
			videos = query.getResultList();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DBException("Error in fetching activated video records");
		}
		return videos;
	}

	@Override
	public void deleteVideoById(int videoId) throws DBException {
		Video video;
		try {

			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			video = session.get(Video.class, videoId);
			session.delete(video);
			transaction.commit();
			session.close();
		}

		catch (Exception e) {
			throw new DBException("Error in deleting records");
		}

	}
	@Override
	public VideoContent getReferenceArtifactById(int id) throws DBException {
		VideoContent refArt;
		String type="referenceartifact";
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "From VideoContent s where s.id=:Id and s.type=:type";
			Query<VideoContent> query = session.createQuery(hql);
			query.setParameter("Id", id);
			query.setParameter("type",type);
			refArt=query.getSingleResult();
		} catch (Exception e) {
			throw new DBException("Error in fetching records");
		}
		return refArt;
	}

	@Override
	public void deleteReferenceArtifactById(int id) throws DBException {
		try {
			String type="referenceartifact";
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql = "delete from VideoContent r where r.id=:id and r.type=:type";
			Query<VideoContent> query = session.createQuery(hql);
			query.setParameter("id", id);
			query.executeUpdate();
			transaction.commit();
			session.close();
		}

		catch (Exception e) {
			throw new DBException("Error in deleting reference artifact records");
		}
	}

	@Override
	public VideoContent getSampleProgramById(int id) throws DBException {
		VideoContent samProg;
		VideoContent samProgr=null;
		String type="sampleprogram";
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "From VideoContent s where s.id=:Id and s.type=:type";
			Query<VideoContent> query = session.createQuery(hql);
			query.setParameter("Id", id);
			query.setParameter("type",type);
			samProg=query.getSingleResult();
		} catch (Exception e) {
			throw new DBException("Error in fetching records");
		}
		return samProg;
	}

	@Override
	public void deleteSampleProgramById(int Id) throws DBException {
		try {
			String type="sampleprogram";
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql = "delete from VideoContent s where s.id=:Id and s.type=:type";
			Query<VideoContent> query = session.createQuery(hql);
			query.setParameter("Id", Id);
			query.setParameter("type",type);
			query.executeUpdate();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DBException("Error in deleting sample program records");
		}
	}
	@Override
	public VideoContent getVideoContentById(int id) throws DBException {
		VideoContent refArt;
		try {
			Session session = sessionFactory.getCurrentSession();
			refArt = session.get(VideoContent.class, id);
		} catch (Exception e) {
			throw new DBException("Error in fetching records");
		}
		return refArt;
	}

	@Override
	public void deleteVideoContentById(int id) throws DBException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql = "delete from VideoContent v where v.id=:id";
			Query<VideoContent> query = session.createQuery(hql);
			query.setParameter("id", id);
			query.executeUpdate();
			transaction.commit();
			session.close();
		}

		catch (Exception e) {
			throw new DBException("Error in deleting reference artifact records");
		}
	}

	@Override
	public ReferenceUrl getReferenceUrlById(int id) throws DBException {
		ReferenceUrl refUrl;
		try {
			Session session = sessionFactory.getCurrentSession();
			refUrl = session.get(ReferenceUrl.class, id);
		} catch (Exception e) {
			throw new DBException("Error in fetching records");
		}
		return refUrl;
	}

	@Override
	public void deleteReferenceUrlById(int Id) throws DBException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql = "delete from ReferenceUrl r where r.id=:Id";
			Query<ReferenceUrl> query = session.createQuery(hql);
			query.setParameter("Id", Id);
			query.executeUpdate();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DBException("Error in deleting reference url records");
		}
	}

	public String getFilePath(String file) {
		String fileName = file.substring(file.lastIndexOf("\\") + 1);
		String fileStorePath = DataConstant.UPLOAD_DIRECTORY.concat(fileName);
		return fileStorePath;
	}

	@Override
	public void addVideo(Video video) throws DBException {
		Transaction transaction = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			List<VideoContent> referenceArtifactList = video.getVideoContent();
			VideoContent refArt[] = new VideoContent[referenceArtifactList.size()];
			if (!referenceArtifactList.isEmpty()) {
				for (int i = 0; i < referenceArtifactList.size(); i++) {

					refArt[i] = referenceArtifactList.get(i);
					refArt[i].setFile(getFilePath(refArt[i].getFile()));
					referenceArtifactList.set(i, refArt[i]);
					referenceArtifactList.get(i).setVideo(video);
				}

				video.setVideoContent(referenceArtifactList);
			}
			List<ReferenceUrl> referenceurlList = video.getReferenceUrl();
			if (!referenceurlList.isEmpty()) {
				for (int i = 0; i < referenceurlList.size(); i++) {
					referenceurlList.get(i).setVideo(video);
				}

				video.setReferenceUrl(referenceurlList);
			}
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			video.setStatus(true);
			video.setCreatedOn(timeStamp);
			video.setCreatedBy("subhalakshmi");
			session.save(video);
			transaction.commit();
			session.close();
		}

		catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new DBException("Video already exists");
			}
			throw new DBException("Unable to insert video records");
		}

	}

	@Override
	public void updateVideo(Video video) throws DBException {
		try {

			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			List<VideoContent> referenceArtifactList = video.getVideoContent();
			VideoContent refArt[] = new VideoContent[referenceArtifactList.size()];
			if (!referenceArtifactList.isEmpty()) {
				for (int i = 0; i < referenceArtifactList.size(); i++) {

					refArt[i] = referenceArtifactList.get(i);
					refArt[i].setFile(getFilePath(refArt[i].getFile()));
					referenceArtifactList.set(i, refArt[i]);
					referenceArtifactList.get(i).setVideo(video);
				}

				video.setVideoContent(referenceArtifactList);
			}
			List<ReferenceUrl> referenceurlList = video.getReferenceUrl();
			if (!referenceurlList.isEmpty()) {
				for (int i = 0; i < referenceurlList.size(); i++) {
					referenceurlList.get(i).setVideo(video);
				}

				video.setReferenceUrl(referenceurlList);
			}
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			video.setCreatedOn(video.getCreatedOn());
			video.setCreatedBy(video.getCreatedBy());
			video.setModifiedOn(timeStamp);
			video.setModifiedBy("subhalakshmi");
			session.saveOrUpdate(video);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new DBException("Video already exists");
			}
			throw new DBException("Unable to update video records");
		}
	}

}