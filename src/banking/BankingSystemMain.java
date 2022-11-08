package banking;

import java.util.Scanner;//입력함수 수입

public class BankingSystemMain implements ICustomDefine {//뱅킹시스템메인은 아이커스텀디파인의 일종이다

	static void ShowMenu() {//메뉴를 보여주는함수를 정의

		Scanner scan = new Scanner(System.in);
		
		System.out.println("****************************");
		System.out.println("-----Menu-----");
		System.out.println("1.계좌개설 ");
		System.out.println("2.입 금");
		System.out.println("3.출 금");
		System.out.println("4.계좌정보출력 ");
		System.out.println("5.저장옵션 ");
		System.out.println("6.프로그램종료");
		System.out.println("****************************");
		System.out.print("메뉴선택>>>");
	}

	public static void main(String[] args) {
		
		
		AccountManager manager = new AccountManager();
		//Account accounts = new Account();abstract이라 하위값인 high으로 받아야한다
		manager.LoadFile();
				
		AutoSaverRunnable autosaverRun = new AutoSaverRunnable(manager);
		Thread autosaver = new Thread(autosaverRun);
//		autosaver.setDaemon(true);
//		autosaver.start();
		
		while (true) {
			
			// 메뉴를 출력한다.
			ShowMenu();
			
			Scanner scan = new Scanner(System.in);
			int choice = 0;
			try
			{
				// 사용자는 수행할 기능의 메뉴를 선택한다.
				choice = scan.nextInt();
				if (choice < 1 || 6 < choice)
				{
					System.out.println("1~5의 수로 입력해주십시오.");
					continue;
				}
			}
			catch(Exception e)
			{

				System.out.println("정수로 입력해주십시오.");
				continue;
			}

			// 선택한 번호에 따라 switch문으로 각 메서드를 호출한다.
			switch (choice) {
			case ICustomDefine.MAKE:
				manager.makeAccount();
				break;
			case ICustomDefine.DEPOSIT:
				manager.depositMoney();
				break;
			case ICustomDefine.WITHDRAW:
				manager.withdrawMoney();
				break;
			case ICustomDefine.INQUIRE:
				manager.showAccInfo();
				break;
			case ICustomDefine.SAVE_SETTING:
				{
					System.out.println("저장설정");
					System.out.println("1.자동저장On, 2.자동저장Off");
					Scanner scan2 = new Scanner(System.in);
					int choice_autosave = scan.nextInt();
					switch(choice_autosave)
					{
					case 1:
						{
							if (!autosaver.isInterrupted())//자동저장 쓰레드가 interrupt 상태인지? 
							{

								System.out.println("자동저장 시작");
								autosaverRun = new AutoSaverRunnable(manager);
								autosaver = new Thread(autosaverRun);
								autosaver.setDaemon(true);
								autosaver.start();
								
							}
							else
							{
								System.out.println("이미 자동저장이 실행중입니다.");
							}
						}
						break;
					case 2:
						{
							autosaver.interrupt();
						}
						break;
					}
				}
				break;
			case ICustomDefine.EXIT:
				System.out.println("프로그램 종료");
				manager.SaveFile();
				return;
			}
		}
	}	
}
