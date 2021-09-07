package BookManagement;

import java.util.List;


public interface Controller {
	public abstract int insert(String name, int price, String memo);
	public abstract List<MyBookModel> selectAll();
	public abstract MyBookModel selectByUid(int uid);
	public abstract int update(int uid, String name, int price, String memo);
	public abstract int delete(int uid);
}
