package com.example.video.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "videos", uniqueConstraints = { @UniqueConstraint(columnNames = "name", name = "unique_video_name") })

public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	int id;
	
	@Column(name = "name", nullable = false, length = 50)
	String name;
	
	@Column(name = "display_name", nullable = false, length = 50)
	String displayName;
	
	@Column(name = "url", nullable = false, length = 50)
	String url;
	
	@Column(name = "duration")
	Time duration;
	
	@Column(name = "tags", nullable = false, length = 100)
	String tags;
	
	@Column(name = "status")
	Boolean status;
	
	@Column(name = "description", columnDefinition = "text")
	String description;
	
	@Column(name = "transcript", length = 100)
	private String transcript;

	@Column(name = "created_by", nullable = false, length = 50)
	String createdBy;
	
	@Column(name = "created_on", columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false)
	private Timestamp createdOn;
	
	@Column(name = "modified_by")
	String modifiedBy;

	@Column(name = "modified_on", columnDefinition = "timestamp default null on update CURRENT_TIMESTAMP")
	private Timestamp modifiedOn;

	@OneToOne
	@JoinColumn(name = "level_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_videos_level_id"))
	private Level level;

	@OneToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_videos_category_id"))
	private Category category;

	@OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonManagedReference
	private List<VideoContent> videoContent;

	@OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonManagedReference
	private List<ReferenceUrl> referenceUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTranscript() {
		return transcript;
	}

	public void setTranscript(String transcript) {
		this.transcript = transcript;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<VideoContent> getVideoContent() {
		return videoContent;
	}

	public void setVideoContent(List<VideoContent> videoContent) {
		this.videoContent = videoContent;
	}
	
	public List<ReferenceUrl> getReferenceUrl() {
		return referenceUrl;
	}

	public void setReferenceUrl(List<ReferenceUrl> referenceUrl) {
		this.referenceUrl = referenceUrl;
	}

	public Video() {
		super();
	}

}