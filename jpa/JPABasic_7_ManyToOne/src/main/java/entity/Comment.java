package entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

// 답글- 게시글 M:1
// @manyToOne 표기 -> ownership을 가짐
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String content;
	
//	@ManyToOne // Join column 설정이 없으면 필드이름+_id로 컬럼 생성됨
//	private Post post;

//	@ManyToOne(cascade = CascadeType.PERSIST) // Join column 설정이 없으면 필드이름+_id로 컬럼 생성됨
//	private Post post;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) // Join column 설정이 없으면 필드이름+_id로 컬럼 생성됨
	private Post post;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", post=" + post + "]";
	}

}
