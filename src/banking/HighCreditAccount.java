package banking;


/*
 HighCreditAccount : 신용신뢰계좌 > 신용도가 높은 고객에게 개설이 허용되며 높은 이율의 계좌이다.
NormalAccount 와 마찬가지로 생성자를 통해서 이율정보(이자비율의정보)를 초기화 할수있도록 정의한다.
고객의 신용등급을 A, B, C로 나누고 계좌개설시 이 정보를 등록한다.
A,B,C 등급별로 각각 기본이율에 7%, 4%, 2%의 이율을 추가로 제공한다.
 
 */

//이자계산은 입금시에만 잔고를 대상으로 계산한다. 출금할때는 이자계산을 하지 않는다.


/*
자계산에 대해서는 다음의 규칙을 적용한다.
이자계산은 입금시에만 잔고를 대상으로 계산한다. 출금할때는 이자계산을 하지 않는다.
이자계산방식 : 잔고:5000원, 기본이자:2%, 신용등급이자:4%, 입금액:2000원이라 가정하면….
일반계좌 : 잔고 + (잔고 * 기본이자) + 입금액 
Ex) 5000 + (5000 * 0.02) + 2000 = 7,100원
신용계좌 : 잔고 + (잔고 * 기본이자) + (잔고 * 추가이자) + 입금액
Ex) 5000 + (5000 * 0.02) + (5000 * 0.04) + 2000 = 7,300원
balance
초기 계좌개설에서는 이자를 계산하지 않는다.
계좌개설 이후 입금을 할때만 이자가 발생한다.
이자의 계산과정에서 발생하는 소수점은 무시해도 된다.(무조건버림처리)
 */

public class HighCreditAccount extends Account implements ICustomDefine {
	int rate;
	String grade;
	//int extrarate;
	public HighCreditAccount (String account, String name, int balance,int rate, String grade) {
		super(account, name, balance);
		this.rate =rate;
		this.grade=grade;
	}
	
	
	@Override
	public void showAccInfo() {
		// TODO Auto-generated method stub
		super.showAccInfo();
		System.out.println("기본이자: "+ rate+"%");
		System.out.println("신용등급: "+ grade);
	}
	

//	public void interest() {
//		if(grade =="A") 
//			this.rate = (rate+7)/100;
//		else if(grade =="B") 
//			this.rate = (rate+4)/100;
//		else if(grade =="C") 
//			this.rate = (rate+2)/100;
//	}
	
	@Override
	public void interest(int deposit)
	{
		//extraRate 는 interest 메서드내부에서만 사용할 것이기 때문에 지역변수로 선언.
		int extraRate = 0;
		
		if(grade.equalsIgnoreCase("A")) {
			extraRate= RATE_A;		
		}
		else if(grade.equalsIgnoreCase("b")) {
			extraRate= RATE_B;	
		}
		else if(grade.equalsIgnoreCase("c")) {
			extraRate= RATE_C;	
		}
		balance = balance + (balance*rate/100) + (balance*extraRate/100)  + deposit;
		System.out.println("HighCreditAccount>interest호출됨");
	}


	@Override
	public String toString() {
		System.out.println("HCA toString 실행");
		//super.toString();
		//return "HighCreditAccount [rate=" + rate + ", grade=" + grade + "]";
		return String.format("[계좌번호는 %s 이고 계좌소유주이름은 %s이고 잔액은 %d, 이자율은 %d, 신용등급은 %s] %n "
				, this.getAccNum(),this.getName(),this.getAccMoney(),this.rate,this.grade);
	}
	
	
	
	
}	

