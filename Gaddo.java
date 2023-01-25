package Project;
import java.sql.Connection; //db연결
import java.sql.DriverManager; // db drive 연동
import java.sql.ResultSet;
import java.sql.Statement; // db 쿼리 명령문
import java.util.Scanner;
import java.util.TimeZone;
//현재 날짜사용
import java.time.*; //period
import java.util.Calendar; //현지 시간가져오기


public class Gaddo extends Enter_Information {
	
	
	public static void ConnectDB(int check) {
	
		Enter_Information e1 = new Enter_Information();	
		information i1 = new information();
		try {
			//데이터베이스 연결
			Class.forName("com.mysql.cj.jdbc.Driver");
			i1.con = DriverManager.getConnection(i1.url, i1.user, i1.passwd);
			e1.stmt = e1.con.createStatement();
			
			switch(check) {
			case 1:
				e1.createTable();	
				break;
			case 2:
				e1.insertData();
				break;
			case 3:
				e1.removeData();
				break;
			case 4:
				e1.viewData();	
				break;
			}
		}
		catch(Exception e) { //예외처리
			System.out.println(e.toString());
		}
		// try의 exception 유무와 상관없이 무조건 실행
		finally {
			try {
				e1.stmt.close();
				e1.con.close();				
			} catch(Exception e) {
				System.out.println(e.toString());
			}
		}
	}//ConnectDB
	
	public static void main(String args[]){
		System.out.println("    *****       *      ****      ****       *****     ");	
		System.out.println("   *     *     * *     *    *    *    *    *     *    ");	
		System.out.println("   *          *   *    *     *   *     *   *     *      ");	
		System.out.println("   *   ***   *******   *     *   *     *   *     *   ");	 
		System.out.println("   *     *   *     *   *    *    *    *    *     *   ");	
		System.out.println("    *****    *     *   ****      ****       ***** ");
		getMode();
		
	}//main화면
	
	static void getMode() {
		Scanner scan = new Scanner(System.in);
		Enter_Information e1 = new Enter_Information();	
		System.out.println();
		System.out.println("  ┏  -  -  -  -  -  -  -  ┓");
		System.out.println("     [ 사용자 모드 ]는 0");
		System.out.println("     [ 관리자 모드 ]는 1  ");
		System.out.println("  ┗  -  -  -  -  -  -  -  ┛");
		System.out.println();
		System.out.print("   사용하실 모드를 선택해 주세요  >> ");
		int modechoice = scan.nextInt();
		
		switch(modechoice){
		case 1 : getPassword(modechoice,e1); break; //관리자 비밀번호 입력창 메서드
		case 0 : getList(e1); break; //menu 가기
		
		default : System.out.print("  제공하지 않는 서비스 입니다.");
		}
		
	}

	

	public static void getPassword(int modechoice, Enter_Information e1 ) {

		System.out.println("  ┏  -  -  -  -  -  -  -  -  -  -  ┓");
		System.out.println("      관리자 비밀번호를 입력하세요   ");
		System.out.println("  ┗  -  -  -  -  -  -  -  -  -  -  ┛");

		Scanner scan = new Scanner(System.in);
		String password = "entermanager";

		//3번이상 틀리면 강종되게 만들기 
		for(int i = 3; i>=1; i--){
			System.out.print("  비밀번호 : "); 
			String pw = scan.next();

			//if(pw.equals(password)){ //문자열 같음을 표현할 때는 .equals(변수) 로 표현한다. 
			if(pw.equals(password)) {
				System.out.println("  관리자 모드로 로그인 되었습니다.");  //비밀번호가 맞으면 빠져나간다. 
				System.out.println();
				System.out.println("┏  -  -  -  -  -  -  -  -  -  ┓");
				System.out.println("         MANAGER MODE");
				System.out.println("┗  -  -  -  -  -  -  -  -  -  ┛");

				e1.menu(); //관리자 모드로 가기
			}
			else{
				System.out.printf("  비밀번호가 틀렸습니다. 다시 입력해 주세요. (기회 %d번 남음)\n", i-1);
			}
		}
		
	}//getPassword

	public static int getList(Enter_Information e1) {
		System.out.println("              ┏  -  -  -  -  ┓");
		System.out.println("                SURVICE LIST");
		System.out.println("              ┗  -  -  -  -  ┛");
		System.out.println(" ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
		System.out.println("    1. 현재 투어 위치는 어디인가요?     ");
		System.out.println("    2. 투어 종료까지 며칠이나 남았나요?     ");
		System.out.println("    3. 지금 현지 시간은 몇시인가요?    ");	
		System.out.println("    4. 전체일정표를 보고싶어요!      ");	
		System.out.println("    5. 프로그램을 종료할래요!      ");
		System.out.println(" ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
		Scanner scan = new Scanner(System.in);
		System.out.print("원하는 서비스의 번호를 입력해 주세요 >> ");
		int choice = scan.nextInt();
		System.out.println();
		String artist="투모로우바이투게더";

		//번호에 맞춰 각 서비스 메서드로 따라가기
		switch(choice)
		{
		case 1 : getLocation(artist); break;
		case 2 : getDday(e1); break;
		case 3 : getTime(e1); break;
		case 4 : showSchedule(e1); break;
		case 5 : getMenu(e1); break;
		
		default : System.out.println("다시 입력해 주세요");
				getList(e1);
		}
		
		return choice;
	}//getList


	public static void getLocation(String artist) {
		System.out.println("");
		
		Enter_Information e1 = new Enter_Information();
		e1.getColumn(artist);
		
	}//getLocation

	public static void getDday(Enter_Information e1) {
		String artist = "투모로우바이투게더";
		
	
		//날짜 차이 구하기 (https://java119.tistory.com/52) (https://developer-talk.tistory.com/408) 참고
		LocalDate startDate = LocalDate.now(); //현재 시간은 계속 흐르기 때문에 now로 지정
		LocalDate endDate = LocalDate.of(2022,10,23); //종료시간을 정하기 때문에 of로 지정


		Period period = Period.between(startDate, endDate); //시작일과 종료일 사이 기간

		period.getMonths(); //개월 차이
		period.getDays(); //일 차이
		
		if(period.getDays() < 0) {
			System.out.println("  ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.println("      " + artist + "의 투어가 종료되었습니다. ");
			System.out.println("      종료일 : " + endDate);
			System.out.println("  ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
		}
		else {
			System.out.println(artist + "가 투어 종료까지 " + period.getMonths() + "개월 " + period.getDays() + "일 남았습니다. ");
		}
		
		getMenu(e1);
		
	}//getDday
	
	


	public static void getTime(Enter_Information e1) {
		Scanner scan = new Scanner(System.in);
		//현재시간 출력 
		//https://moonong.tistory.com/10  => Calendar class 이론 참고
		Calendar cal = Calendar.getInstance();
		LocalDate startDate = LocalDate.now();
		
		
		System.out.println("          ┏  -  -  -  -  -  -  -  ┓");
		System.out.println("             오늘 날짜 : " + startDate);
		System.out.println("          ┗  -  -  -  -  -  -  -  ┛");
		System.out.println();
		
		System.out.println("          ┏  -  -  -  -  -  -  -  ┓");
		System.out.println("\t      " + "1. 대한민국");
		System.out.println("\t      " + "2. 일본");
		System.out.println("\t      " + "3. 미국(LA)");
		System.out.println("\t      " + "4. 미국(샌프란치스코)");
		System.out.println("\t      " + "5. 미국(시카고)");
		System.out.println("\t      " + "6. 미국(애틀랜타)");
		System.out.println("\t      " + "7. 미국(달라스)");
		System.out.println("\t      " + "8. 미국(뉴욕)");
		System.out.println("\t      " + "9. 미국(휴스턴)");
		System.out.println("\t      " + "10. 인도네시아");
		System.out.println("\t      " + "11. 필리핀");
		System.out.println("\t      " + "12. 태국");
		System.out.println("\t      " + "13. 대만");
		
		System.out.println("          ┗  -  -  -  -  -  -  -  ┛");
		System.out.println();
		System.out.print("  시간을 알고 싶은 나라의 번호를 입력해 주세요 >> ");
		int num = scan.nextInt();
		switch(num) {
		case 1: //대한민국
				TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
				cal.setTimeZone(zone);
				
				System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
				System.out.print("          대한민국 현재 시각 : ");
				getAM_PM(cal);
				System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
				System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
				break;
				
		case 2: //일본
			zone = TimeZone.getTimeZone("Asia/Tokyo");
			cal.setTimeZone(zone);
			//일본과 대한민국 시간기준이 같다.
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("            일본 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 3: //미국 LA
			zone = TimeZone.getTimeZone("America/Los_Angeles");
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("            미국(LA) 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 4: //미국 샌프란치스코
			zone = TimeZone.getTimeZone("America/Los_Angeles"); //LA와 시차가 같다. 
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("           미국(샌프란치스코) 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 5://미국 시카고
			zone = TimeZone.getTimeZone("America/Chicago");
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("          미국(시카고) 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 6://미국 애틀란타
			zone = TimeZone.getTimeZone("America/New_York"); //뉴욕과 시차가 같다.
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("         미국(애틀란타) 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 7://미국 달라스
			zone = TimeZone.getTimeZone("America/Chicago"); //시카고와 시차가 같다.
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("          미국(달라스) 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 8://미국 뉴욕
			zone = TimeZone.getTimeZone("America/New_York");
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("         미국(뉴욕) 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 9://미국 휴스턴
			zone = TimeZone.getTimeZone("America/Chicago"); //시카고와 시차가 같다.
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("          미국(휴스턴) 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 10://인도네시아
			zone = TimeZone.getTimeZone("Asia/Jakarta");
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("          인도네시아 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 11://필리핀
			zone = TimeZone.getTimeZone("Asia/Manila");
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("           필리핀 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 12://태국
			zone = TimeZone.getTimeZone("Asia/Bangkok");
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.println("            태국 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
			break;
			
		case 13://대만
			zone = TimeZone.getTimeZone("Asia/Taipei");
			cal.setTimeZone(zone);
			System.out.println("        ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
			System.out.print("            대만 현재 시각 : ");
			getAM_PM(cal);
			System.out.println(" " + cal.get(Calendar.HOUR) + "시 "+ cal.get(Calendar.MINUTE) + "분");
			System.out.println("        ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛"); 
			break;
			
		
		}
		
		
		//메뉴 돌아가기
		getMenu(e1);
	
	}//getTime
	
	public static int getAM_PM(Calendar cal) {
		//오전 오후 판별
		if( cal.get(Calendar.AM_PM) == 1) {
			System.out.print("오후");
		}
		else if( cal.get(Calendar.AM_PM) == 0) {
			System.out.print("오전");
		}
		
		return cal.get(Calendar.AM_PM);
	}//getAM_PM

	public static void showSchedule( Enter_Information e1) {
		System.out.println("전체스케줄표");
		//ConnectDB 메소드의 매개변수를 받아 실행한다. 
		ConnectDB(4);
		
		getMenu(e1);
	}//showSchedule
	
	public static void getMenu( Enter_Information e1) {
		Scanner scan = new Scanner(System.in);
		
		//menu돌아가기
		System.out.println("  ┏  -  -  -  -  -  -  -  -  -  -  -  -  -  ┓");
		System.out.println("    서비스 리스트 화면으로 돌아가시려면 1을 ");
		System.out.println("    프로그램을 종료하시려면 0을 눌러주세요.");
		System.out.println("  ┗  -  -  -  -  -  -  -  -  -  -  -  -  -  ┛");
		System.out.print(">>");
		int menu = scan.nextInt();
	
		switch(menu) {
		case 0 : System.out.println("프로그램이 종료되었습니다."); break;
		case 1 : getList(e1); break;
		
		default : System.out.println("입력 오류입니다. 다시 입력해 주세요.");
				getMenu(e1);
		}
	}//getMenu

}//Gaddo







