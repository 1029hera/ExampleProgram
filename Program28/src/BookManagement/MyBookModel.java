package BookManagement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyBookModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1941877634271207928L;
	private int uid; // 도서 일련 번호
	private String name; // 도서 이름
	private int price; // 도서 가격
	private String memo; // 도서 메모
	private LocalDateTime regDate; // 도서 등록일
	
	@Override
	public String toString() {
		return String.format("%d] %s | %d원 | %s",
				uid, name, price, memo,
				regDate.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")));
	}
	
}
