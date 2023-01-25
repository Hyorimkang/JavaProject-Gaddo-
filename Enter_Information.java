package Project;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

class information {
	static Connection con = null;
	static Statement stmt = null;
	// 학교 : 10.96.121.41:3306  노트북 내 : localhost
	String url = "jdbc:mysql://10.96.121.41:3306/information?serverTimezone=Asia/Seoul";
	String user = "Gaddo";
	String passwd = "1234";
}// information

public class Enter_Information extends information {

	public static void main(String[] args) {
		System.out.println("  ┏  -  -  -  -  -  -  -  -  -  ┓");
		System.out.println("           MANAGER MODE");
		System.out.println("  ┗  -  -  -  -  -  -  -  -  -  ┛");

		menu();
	}

	public static void menu() {

		// 데이터베이스 관련 코드는 try-catch문으로 예외 처리를 꼭 해주어야 한다.
		// 데이터베이스 연결
		try {

			information i1 = new information();
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(i1.url, i1.user, i1.passwd);
			stmt = con.createStatement();

			 
			// 메뉴 리스트
			System.out.println("     ┏  -  -  -  -  -  ┓");
			System.out.println("       1. 테이블 생성");
			System.out.println("       2. 데이터 추가");
			System.out.println("       3. 데이터 삭제");
			System.out.println("       4. 데이터 조회");
			System.out.println("       5. 프로그램 종료");
			System.out.println("     ┗  -  -  -  -  -  ┛");

			System.out.print("   번호 입력 >> ");
			Scanner s = new Scanner(System.in);
			int num = s.nextInt();
			switch (num) {
			case 1:
				createTable(); // 테이블 생성
				break;
			case 2:
				insertData(); // 데이터 삽입
				break;
			case 3:
				removeData(); // 데이터 삭제
				break;
			case 4:
				viewData(); // 데이터 조회
				break;
			case 5 : 
				out(); // 프로그램 종료
				break;
				
			}
		} catch (Exception e) { //예외처리
			System.out.println(e.toString());

		}
	}// menu
	
	static void out() {
		Scanner scan = new Scanner(System.in);
		
		//menu돌아가기
		System.out.println("  ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
		System.out.println("     프로그램을 종료하시려면 0을 눌러주세요.");
		System.out.println("  ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
		System.out.print(">>");
		int menu = scan.nextInt();
		while(true) {
			if(menu == 0) {
			System.out.println("프로그램이 종료되었습니다."); break;
			}
			else System.out.println("입력 오류입니다. 다시 입력해 주세요.");
		}
		
		
	}

	static void createTable() {
		try {
			String createStr = "CREATE TABLE concert (month int not null, date int not null,"
					+ " country varchar(30) not null, area varchar(30) not null)";
			stmt.execute(createStr);
			System.out.println("테이블 생성 성공!");
		} catch (Exception e) { //예외처리
			System.out.println("테이블 생성 실패 이유 : " + e.toString());
		}
	}//createTable

	// 삽입
	static void insertData() {

		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("  ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.println("  삽입하려는 달, 일, 나라, 지역 순으로 입력해 주세요. ");
			System.out.println("  ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			System.out.print("     달 >>");
			int insertmonth = scan.nextInt();

			System.out.print("     일 >>");
			int insertdate = scan.nextInt();

			System.out.print("     나라 >>");
			String insertcountry = scan.next();

			System.out.print("     지역 >>");
			String insertarea = scan.next();

			String insertStr = "INSERT INTO concert VALUES(" + insertmonth + ", " + insertdate + ", '" + insertcountry
					+ "','" + insertarea + "')";
			stmt.executeUpdate(insertStr);
			System.out.println("데이터 추가 성공!");

			System.out.println("  ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.println("     데이터를 더 추가하려면 2 ");
			System.out.println("     메뉴로 돌아가시려면 3을 눌러주세요.");
			System.out.println("  ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			System.out.print(">>");

			int choice = scan.nextInt();
			if (choice == 2) {
				insertData();
			} else if (choice == 3)
				menu();

		} catch (Exception e) { //예외처리
			System.out.println("데이터 추가 실패 이유 : " + e.toString());
		}

	}//insertData

	// 삭제
	static void removeData() {
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("  ┏  -  -  -  -  -  -  -  ┓");
			System.out.println("    번호를 입력하세요 ");
			System.out.println("    (1) 자료를 삭제하기");
			System.out.println("    (2) 메뉴로 돌아가기  ");
			System.out.println("  ┗  -  -  -  -  -  -  -  ┛");
			System.out.print("   >>");
			int del = scan.nextInt(); // 삭제하고 싶은 컬럼 입력

			if (del == 1) {
				System.out.println("  ┏  -  -  -  -  -  -  -  ┓");
				System.out.println("  삭제하려는 달을 입력해주세요   ");
				System.out.println("  ┗  -  -  -  -  -  -  -  ┛");
				System.out.print("   >>");
				int delmonth = scan.nextInt(); // 삭제하려는 달 입력
				String removeStr = "DELETE FROM schedule where month=" + delmonth;
				System.out.println("  ┏  -  -  -  -  -  -  -  ┓");
				System.out.println("  삭제하려는 일을 입력해주세요   ");
				System.out.println("  ┗  -  -  -  -  -  -  -  ┛");
				System.out.print("   >>");
				int deldate = scan.nextInt(); // 삭제하려는 일 입력

				removeStr = "DELETE FROM concert where date=" + deldate;
				stmt.executeUpdate(removeStr);
				System.out.println("데이터 삭제 성공!");
			} else {
				System.out.println("번호를 다시 입력해 주세요");
				removeData();
			}

			System.out.println("  ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.println("     데이터를 더 삭제하려면 2 ");
			System.out.println("     메뉴로 돌아가시려면 3을 눌러주세요.");
			System.out.println("  ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			System.out.print(">>");

			int choice = scan.nextInt();
			if (choice == 2) {
				insertData();
			} else if (choice == 3)
				menu();

		} catch (Exception e) { //예외처리
			System.out.println("데이터 삭제 실패 이유 : " + e.toString());
		}

	}//removeData

	// 조회
	static void viewData() {
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("     ┏  -  -  -  -  - ┓");
			System.out.println("       전체 스케줄표 ");
			System.out.println("     ┗  -  -  -  -  - ┛");
			System.out.println(" 월       일        나라         지역  ");
			System.out.println();
			String viewStr1 = "SELECT * FROM information.concert ORDER BY month ASC, date ASC"; // 월 정렬후 일 정렬

			// table 에서 데이터를 검색하기 위해 stmt.executeQuery() 메소드 사용
			// ResultSet을 사용해 결괏값을 불러온다.
			ResultSet result1 = stmt.executeQuery(viewStr1);
			int cnt1 = 0;
			while (result1.next()) {
				System.out.printf("%2d월   %2d일 %8s %10s\n",result1.getInt("month"),result1.getInt("date") , result1.getString("country") ,result1.getString("area"));
				cnt1++;
			}

			Gaddo g1 = new Gaddo();
			g1.getMode();
			
		} catch (Exception e) { //예외처리
			System.out.println("데이터 조회 실패 이유 : " + e.toString());
		}

	}//viewData
	
	

	static void getColumn(String artist) {
		Scanner scan = new Scanner(System.in);

		try {

			information i1 = new information();
			con = DriverManager.getConnection(i1.url, i1.user, i1.passwd);
			stmt = con.createStatement();

			// 월체크
			System.out.println("  ┏  -  -  -  -  -  -  ┓");
			System.out.println("     월을 입력해 주세요  ");
			System.out.println("  ┗  -  -  -  -  -  -  ┛");
			System.out.print("  >>");
			int inputmonth = scan.nextInt();
			String viewColumn = "select * from information.concert where month = " + inputmonth;
			//ResultSet은 executeQuery를 사용해 db에 명령을 내리고 반환받은 값
			ResultSet result2 = stmt.executeQuery(viewColumn);
			
			
			// 일체크
			System.out.println("  ┏  -  -  -  -  -  -  ┓");
			System.out.println("     일을 입력해 주세요  ");
			System.out.println("  ┗  -  -  -  -  -  -  ┛");
			System.out.print("  >>");
			int inputdate = scan.nextInt();
			
			switch(inputmonth) {
			case 1 : viewColumn = "select * from information.concert where month = 1 and date = "+ inputdate; break;
			case 2 : viewColumn = "select * from information.concert where month = 2 and date = "+ inputdate; break;
			case 3 : viewColumn = "select * from information.concert where month = 3 and date = "+ inputdate; break;
			case 4 : viewColumn = "select * from information.concert where month = 4 and date = "+ inputdate; break;
			case 5 : viewColumn = "select * from information.concert where month = 5 and date = "+ inputdate; break;
			case 6 : viewColumn = "select * from information.concert where month = 6 and date = "+ inputdate; break;
			case 7 : viewColumn = "select * from information.concert where month = 7 and date = "+ inputdate; break;
			case 8 : viewColumn = "select * from information.concert where month = 8 and date = "+ inputdate; break;
			case 9 : viewColumn = "select * from information.concert where month = 9 and date = "+ inputdate; break;
			case 10 : viewColumn = "select * from information.concert where month = 10 and date = "+ inputdate; break;
			case 11 : viewColumn = "select * from information.concert where month = 11 and date = "+ inputdate; break;
			case 12 : viewColumn = "select * from information.concert where month = 12 and date = "+ inputdate; break;
		
			}
			
			result2 = stmt.executeQuery(viewColumn);
			
			
			int cnt2 = 0;
			while (result2.next()) {
				// 현재 위치하고 있는 국가
				System.out.println("  ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
				System.out.print("     " +result2.getInt("month") + "월    " + result2.getInt("date") + "일    "
						+ result2.getString("country") + "    " + result2.getString("area") + "에 있습니다. " + "\n");
				System.out.println("  ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
				cnt2++;
			}
			System.out.println();
			if(cnt2 == 0) System.out.println("  해당하는 날짜의 위치가 존재하지 않습니다. 다시 확인해 주세요.");
			System.out.println();
			Enter_Information e1 = new Enter_Information();
			Gaddo g1 = new Gaddo();
			g1.getMenu(e1);

		} catch (Exception e) { //예외처리
			System.out.println("데이터 조회 실패 이유 : " + e.toString());
		}

	}//getColumn
	

}//Enter_Information

