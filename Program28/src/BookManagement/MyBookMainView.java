package BookManagement;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MyBookMainView implements C {
	private Scanner sc;
	private MyBookController mbCtrl;
	
	public static void main(String[] args) {
		MyBookMainView app = new MyBookMainView();
		
		app.init();
		app.run();
		app.exit();

	} // end main()

	private void init() {
		sc = new Scanner(System.in);
		mbCtrl = MyBookController.getInstance();	
	}

	private void run() {
		while(true) {
			showList();
			
			try {
				int menu = sc.nextInt();
				sc.nextLine();
				
				switch(menu) {
				case MENU_INSERT:
					insertMyBook();
					break;
				case MENU_LIST:
					listMyBook();
					break;
				case MENU_UPDATE:
					updateMyBook();
					break;
				case MENU_DELETE:
					deleteMyBook();
					break;
				case MENU_QUIT: 
					mbCtrl.close();
					System.out.println("프로그램을 종료합니다.");
					return;
				default:
					System.out.println("잘못 입력하셨습니다.");
				} // end switch	
			} catch (MyBookException ex) {
				System.out.println(ex.getMessage());
			} catch (InputMismatchException e) {
				System.out.println("잘못 입력하셨습니다.");
				sc.nextLine();
			} catch (IOException e) {
				e.printStackTrace();
			} // end try-catch
			
		} // end while
		
	} // end run

	private void insertMyBook() {
		System.out.println("-- 등록 메뉴 --");
		System.out.print("도서 이름 입력: ");
		String name = sc.nextLine();
		
		System.out.print("도서 가격 입력: ");
		int price = sc.nextInt();
		sc.nextLine();
		
		System.out.print("메모 입력: ");
		String memo = sc.nextLine();
		
		int result = mbCtrl.insert(name, price, memo);
		System.out.println(result + "개의 상품 정보 등록 성공");
		
	}

	private void listMyBook() {
		List<MyBookModel> list = mbCtrl.selectAll();
		
		System.out.println("총 " + list.size() + "개의 데이터 출력");
		for(MyBookModel m : list) {
			System.out.println(m);
		}
		
	}

	private void updateMyBook() {
		System.out.println("-- 수정 메뉴 --");
		System.out.print("수정할 상품번호 입력: ");
		int uid = sc.nextInt();
		sc.nextLine();
		
		mbCtrl.selectByUid(uid);
		
		System.out.print("도서 이름 입력: ");
		String name = sc.nextLine();
		
		System.out.print("도서 가격 입력: ");
		int price = sc.nextInt();
		sc.nextLine();
		
		System.out.print("메모 입력: ");
		String memo = sc.nextLine();
		
		int result = mbCtrl.update(uid, name, price, memo);
		System.out.println(result + " 개의 상품 수정 성공");
		
	}

	private void deleteMyBook() {
		System.out.println("--- 삭제 메뉴 ---");	
		System.out.println("삭제할 상품번호 입력:");
		int uid = sc.nextInt();
		sc.nextLine();
		
		mbCtrl.selectByUid(uid);
		
		int result = mbCtrl.delete(uid);
		System.out.println(result + " 개의 상품 삭제 성공");
	}

	public void showList() {
		System.out.println();
		System.out.println("도서관리 프로그램");
		System.out.println("---------------");
		System.out.println(" [1] 등록");
		System.out.println(" [2] 열람");
		System.out.println(" [3] 수정");
		System.out.println(" [4] 삭제");
		System.out.println(" [0] 종료");
		System.out.println("---------------");
		System.out.print("선택: ");
	}

	private void exit() {
		sc.close();
	}

}
