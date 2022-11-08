package banking;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;

public class AccountManager {
	String account, name, grade;
	int balance, rate;

	// Account[] accounts = new Account[50];
	HashSet<Account> accounts;
	// int numOfAccounts = 0;

	public AccountManager() {
		accounts = new HashSet<Account>();
	}
//
//	public AccountManager(int num) {
//		// accounts = new Account[num];
//		accounts = new HashSet<>();
//		numOfAccounts = 0;
//	}
//
//	public HashSet<Account> getAccounts() {
//		return accounts;
//	}
//
//	public void setAccounts(HashSet<Account> accounts) {
//		this.accounts = accounts;
//	}

	public void SaveFile() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("src/banking/AccountInfo.obj"));

			for (Account a : accounts) {

				oos.writeObject(a);

			}
			oos.close();
			System.out.println("저장 완료");
		} catch (IOException e) {
			System.out.println("저장에 실패했습니다.");
		}
	}

	public void SaveFileTXT() {
//		ObjectOutputStream oos = null;
//		try {
//			oos = new ObjectOutputStream(new FileOutputStream("src/banking/AccountInfo.txt"));
//			oos.writeObject(accounts);		
//			oos.close();
//			System.out.println("저장 완료");
//		} catch (IOException e) {
//			System.out.println("저장에 실패했습니다.");
//		}

		PrintWriter pw = null;

		try {
			pw = new PrintWriter(new FileWriter("src/banking/AccountInfo.txt"));

			for (Account a : accounts) {

				pw.write(a.toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pw.close();
		}

	}

	public void LoadFile() {
		ObjectInputStream stream = null;

		try {
			stream = new ObjectInputStream(new FileInputStream("src/banking/AccountInfo.obj"));

			while (true) {
				accounts.add((Account) stream.readObject());
			}
			// stream.close();
		} catch (EOFException e) {
			System.out.println("파일 로드 완료했습니다.");
//			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
//
//	public AccountManager(HashSet<Account> accounts, int numOfAccounts) {
//		super();
//		this.accounts = accounts;
//		this.numOfAccounts = numOfAccounts;
//	}
//
//	// 필요없음
//	public int getNumOfAccounts() {
//		return numOfAccounts;
//	}
//
//	// 필요없음.
//	public void setNumOfAccounts(int numOfAccounts) {
//		this.numOfAccounts = numOfAccounts;
//	}

	public void showAccInfo() {
		// Account 클래스의 showAccount()
		System.out.println("***신규계좌개설***");
		for (Account a : accounts) {
			a.showAccInfo();
		}
	}

	public void makeAccount() {

		Scanner scan = new Scanner(System.in);
		System.out.println("-----계좌선택------");
		System.out.println("1.보통계좌");
		System.out.println("2.신용신뢰계좌");
		System.out.print("메뉴선택>>>");

		int choice = scan.nextInt();
		scan.nextLine();

		if (choice == 1) {
			System.out.println("******신규계좌개설******");
			System.out.print("계좌번호: ");
			String account = scan.nextLine();
			System.out.print("고객이름: ");
			String name = scan.nextLine();
			System.out.print("잔고: ");
			int balance = scan.nextInt();
			System.out.print("기본이자%(정수형태로입력): ");
			int rate = scan.nextInt();
			scan.nextLine();

			NormalAccount nc = new NormalAccount(account, name, balance, rate);
			if (accounts.contains(nc)) {
				System.out.println("중복계좌발견됨. 덮어쓸까요?(y or n)");
				Scanner commandScan = new Scanner(System.in);
				String command = commandScan.next();
				switch (command) {
				case "y":
					accounts.remove(nc);
					accounts.add(nc);
					break;
				case "n":
					break;
				}
			} else {
				accounts.add(nc);
			}
		}

		if (choice == 2) {
			System.out.println("******신규계좌개설******");
			System.out.print("계좌번호: ");
			String account = scan.nextLine();
			System.out.print("고객이름: ");
			String name = scan.nextLine();
			System.out.print("잔고: ");
			int balance = scan.nextInt();
			System.out.print("기본이자%(정수형태로입력): ");
			int rate = scan.nextInt();
			scan.nextLine();
			System.out.print("신용등급(A,B,C등급): ");
			String grade = scan.nextLine();

			HighCreditAccount ha = new HighCreditAccount(account, name, balance, rate, grade);
			if (accounts.contains(ha)) {
				System.out.println("중복계좌발견됨. 덮어쓸까요?(y or n)");
				Scanner commandScan = new Scanner(System.in);
				String command = commandScan.next();
				switch (command) {
				case "y":
					accounts.remove(ha);
					accounts.add(ha);
					break;
				case "n":
					break;
				}
			} else {
				accounts.add(ha);
			}
		}

	}

	public void depositMoney() {
		/*
		 * 일반계좌 : 잔고 + (잔고 * 기본이자) + 입금액 Ex) 5000 + (5000 * 0.02) + 2000 = 7,100원 신용계좌 :
		 * 잔고 + (잔고 * 기본이자) + (잔고 * 추가이자) + 입금액 Ex) 5000 + (5000 * 0.02) + (5000 * 0.04)
		 * + 2000 = 7,300원
		 */
		try {
			System.out.println("-----입금-----");
			System.out.println("계좌번호와 입금할 금액을 입력하세요");
			Scanner scan = new Scanner(System.in);
			System.out.print("계좌번호: ");
			String account = scan.nextLine();
			System.out.print("입금: ");
			int deposit = scan.nextInt();

			if (deposit < 0) {
				System.out.println("잘못된 금액입니다.");
				return; // 입금액이 음수면 중지
			}
			int isValidDeposit = (deposit % 500); // 입금액이 500 단위이면 0 아니면 0이 아닌 수
			if (isValidDeposit != 0) {
				System.out.println("입금 금액 단위는 500원입니다.");
				return;
			}

		
			for (Account a : accounts) {
				if (account.equals(a.getAccNum())) {// 1234 비교 1234 1234 vs 12312
					a.interest(deposit);
					System.out.println("입금이 완료되었습니다.");
				}
			}
		} catch (Exception e) {
			System.out.println("정상적인 입력이 아닙니다.");
		}

	}

	public void withdrawMoney() {

		try {
			System.out.println("-----출금-----");
			System.out.println("계좌번호와 출금할 금액을 입력하세요");

			Scanner scan = new Scanner(System.in);
			System.out.print("계좌번호: ");
			String account = scan.nextLine();
			System.out.print("출금: ");
			int withdraw = scan.nextInt();
			if (withdraw < 0) {
				System.out.println("잘못된 금액입니다.");
				return; // 입금액이 음수면 중지
			}
			int isValidDeposit = (withdraw % 1000); // 입금액이 500 단위이면 0 아니면 0이 아닌 수
			if (isValidDeposit != 0) {
				System.out.println("출금 금액 단위는 1000원입니다.");
				return;
			}

			for (Account a : accounts) {
				if (account.equals(a.account)) { // 검색한 계좌번호가 저장된 계좌번호와 같을 때
					if (a.balance < withdraw) {
						System.out.println("출금액이 잔고보다 큽니다.");
						System.out.println("YES: 잔금 모두 출금");
						System.out.println("NO: 출금 취소");
						Scanner subScan = new Scanner(System.in);
						String input = subScan.next();
						switch (input) {
						case "YES": {
							a.balance = 0;
							System.out.println("출금되었습니다.");
						}
							break;
						case "NO": {
							System.out.println("취소되었습니다.");
						}
							break;
						}

						break;
					} else {
						a.balance -= withdraw;
						System.out.println("출금되었습니다.");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("정상적인 입력이 아닙니다.");
		}
	}
}
