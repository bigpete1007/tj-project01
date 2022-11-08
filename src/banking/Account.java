package banking;

import java.io.Serializable;
import java.util.Objects;

abstract class Account implements Serializable {

	String account; // 계좌번호
	String name; // 고객명
	//String grade; //등급
	int balance; // 잔고
	//int rate;//이자
	
	public Account() {
	}

	// 매개변수 메소드
	public Account(String account, String name, int balance) {
		this.account = account;
		this.name = name;
		this.balance = balance;
		
	}

	
//	void deposit(int amount) {
//		this.balance += amount;
//	}
	
//	void withdraw(int amount) {
//		this.balance -= amount;
//	}
	public String getAccNum() {
		return account;	
	}
	
	public String getName() {
		return this.name;
	}
	
	public void showAccInfo() {
		System.out.println("--------------");
		System.out.println("계좌번호:" + account);
		System.out.println("고객이름:" + name);
		System.out.println("잔고:" + balance);
	}

//	public void interest() {
//	}

	public int getAccMoney() {
		return balance;
	}
	
	public void interest(int deposit) {
		
	}
	
	@Override
	public int hashCode(){
		/* Objects.hash 메소드로 residentNumber의 해쉬값 반환 */
		return Objects.hash(account);
	}
	
	@Override
	public boolean equals(Object o){
		Account a = (Account)o;
		return (a.account.equals(this.account));
	}

	
	//toString 메서드 오버라이딩
	@Override
	public String toString() {
		System.out.println("Account toString 실행");

		return "Account [account=" + account + ", name=" + name + ", balance=" + balance + "]";
	}
	
	
	
	
}
