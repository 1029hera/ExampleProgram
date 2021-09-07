package BookManagement;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MyBookController implements Controller, C, Closeable {
	
	private List<MyBookModel> mbList = new ArrayList<MyBookModel>();
	
	private static final String DATA_DIR = "data";
	private static final String DATA_FILE = "work.dat";
	private File mbDir;
	private File mbFile;
	
	private static MyBookController instance = null;
	private MyBookController() {
		initFile();
	}
	
	public static MyBookController getInstance() {
		if(instance == null) {
			instance = new MyBookController();
		}
		return instance;
	}
	
	private void initFile() {
		// 데이터 저장 폴더 생성 (없었다면!)
		mbDir = new File(DATA_DIR);
		if(!mbDir.exists()) {
			if(mbDir.mkdir()) {
				System.out.println("폴더 생성 성공");
			} else {
				System.out.println("폴더 생성 실패");
			}
		} else {
			System.out.println("폴더 존재: " + mbDir.getAbsolutePath());
		}
		
		// 데이터 저장 파일 생성 (없었다면)
		mbFile = new File(mbDir, DATA_FILE);
		if(!mbFile.exists()) {
			System.out.println("데이터 파일 새로 생성");
		} else {			
			System.out.println("데이터 파일 존재 : " + mbFile.getAbsolutePath());
			// 기존에 저장된 파일 데이터 읽어오기
			getDataFromFile();
		}		
	}
	
	private void getDataFromFile() {
		try(
				InputStream in = new FileInputStream(mbFile);
				ObjectInputStream oin = new ObjectInputStream(in);
				){
			mbList = (List<MyBookModel>)oin.readObject();  // 파일 -> List 읽어오기
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveDataToFile() {
		try(
				OutputStream out = new FileOutputStream(mbFile);
				ObjectOutputStream oout = new ObjectOutputStream(out);
				){
			oout.writeObject(mbList);  // List -> 파일 저장
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		saveDataToFile();
		
	}

	// 도서 등록
	@Override
	public int insert(String name, int price, String memo) {
		int result = 0; 
		
		if(name == null || name.trim().length() == 0)
			throw new MyBookException("insert() 이름 입력 오류: ", ERR_EMPTY_STRING);
		if(price < 0)
			throw new MyBookException("insert() 상품 가격 입력 오류: ", ERR_MINUS_INT);
		
		MyBookModel mb = new MyBookModel();
		mb.setUid(getMaxUid() + 1);
		mb.setName(name);
		mb.setPrice(price);
		mb.setMemo(memo);
		mb.setRegDate(LocalDateTime.now());
		
		mbList.add(mb);
		
		result = 1;
		
		return result;
	}

	@Override
	public List<MyBookModel> selectAll() {
		return mbList;
	}

	@Override
	public MyBookModel selectByUid(int uid) {
		MyBookModel mb = null;
		
		for (int i = 0; i < mbList.size(); i++) {
			mb = mbList.get(i);
			if(mb.getUid() == uid) return mb;
		}
		throw new MyBookException("존재하지 않는 상품번호: " + uid, ERR_INVALID_ID);
	}

	// 도서 정보 수정
	@Override
	public int update(int uid, String name, int price, String memo) {
		int result = ERR_GENERIC;
		
		if(uid < 1)
			throw new MyBookException("update() 도서번호 오류: " + uid, ERR_INVALID_ID);
		if (name == null || name.trim().length() == 0)
			throw new MyBookException("update() 이름입력 오류: ", ERR_EMPTY_STRING);
		if (price < 0)
			throw new MyBookException("update() 전화번호입력 오류: ", ERR_MINUS_INT);
		
		int index = findIndexByUid(uid);
		if(index < 0) 
			throw new MyBookException("update() 존재하지 않는 상품번호 : " + uid, ERR_INVALID_ID);
		
		MyBookModel mb = mbList.get(index);
		mb.setName(name);
		mb.setPrice(price);
		mb.setMemo(memo);
		result = 1;
		
		return result;
	}

	// 도서 삭제
	@Override
	public int delete(int uid) {
		int result = ERR_GENERIC; 
		
		if (uid < 1)
			throw new MyBookException("update() 상품번호 오류: " + uid, ERR_INVALID_ID);
		
		int index = findIndexByUid(uid);
		if(index < 0)
			throw new MyBookException("update() 존재하지 않는 상품번호 : " + uid, ERR_INVALID_ID);
		
		mbList.remove(index);
		result = 1;
			
		return result;	
	}
	
	private int getMaxUid() {
		if(mbList.size() == 0) return 0;
		return mbList.get(mbList.size() - 1).getUid();
	}

	private int findIndexByUid(int uid) {
		for (int i = 0; i < mbList.size(); i++) {
			if(mbList.get(i).getUid() == uid) return i;
		}
		return -1;
	}

}
