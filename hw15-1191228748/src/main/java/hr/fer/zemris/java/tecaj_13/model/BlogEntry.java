package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents one entry of the blog.
 * 
 * @author Zvonimir Šimunović
 *
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/** ID of the entry. */
	private Long id;
	/** Comments of this blog entry. */
	private List<BlogComment> comments = new ArrayList<>();
	/** Date the blog entry was created at. */
	private Date createdAt;
	/** Date when the blog entry was last modified on. */
	private Date lastModifiedAt;
	/** Title of the blog entry. */
	private String title;
	/** Text of the entry. */
	private String text;
	/** Creator of the entry. */
	private BlogUser creator;

	/**
	 * @return ID of the entry.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Set ID of the entry.
	 * 
	 * @param id id to be set to.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return list of this blog entry's comments.
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets the comments of this entry.
	 * 
	 * @param comments comments to set to.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * @return the date of the creation of the entry.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the date of the creation.
	 * 
	 * @param createdAt date to set to.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return date of the last modification.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the date of the last modification.
	 * 
	 * @param lastModifiedAt date to set to.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * @return the title of the entry.
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of this entry.
	 * 
	 * @param title title to set to.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the text of this blog entry.
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of this blog entry.
	 * 
	 * @param text text to set to.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the creator of the entry.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Sets the creator of the blog entry.
	 * 
	 * @param creator the creator to set
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}