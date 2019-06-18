package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents one comment on a blog entry.
 * 
 * @author Zvonimir Šimunović
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/** ID of the comment. */
	private Long id;
	/** {@link BlogEntry} object this comment belongs to. */
	private BlogEntry blogEntry;
	/** Email of the user that commented. */
	private String usersEMail;
	/** Message in the comment. */
	private String message;
	/** Date of the posting of the comment. */
	private Date postedOn;

	/**
	 * @return id of the comment.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Set this comments ID.
	 * 
	 * @param id of the comment to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return {@link BlogEntry} this comment belongs to.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Set the parent {@link BlogEntry} this comment belongs to.
	 * 
	 * @param blogEntry {@link BlogEntry} this comment belongs to.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Get the email of the user that posted the comment.
	 * 
	 * @return the email of the poster.
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the email of the user that posted the comment.
	 * 
	 * @param usersEMail email to set to.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * @return the message of the comment.
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message of the comment.
	 * 
	 * @param message message to set to.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * 
	 * @return Date of the posting of the comment.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Set the Date of the posting of the comment.
	 * 
	 * @param postedOn Date to be set to.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}