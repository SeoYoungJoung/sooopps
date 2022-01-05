import java.util.*;

interface Phone {
	void sendCall(String callee);

	// 이 메소드는 "Made a call to 수신자_이름(callee)"라고 출력해야 하며 이 출력의 앞 또는 뒤에
	// 발신자 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다.
	void receiveCall(String caller);
	// 이 메소드는 "Received a call from 송신자_이름(caller)"라고 출력해야 하며 이 출력의 앞 또는
	// 뒤에 수신자 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다.
}

interface Calculator {
	// +, -, *, / 사칙연산만 지원하고 그 외의 연산자일 경우 "NOT supported operator" 에러 메시지 출력
	// 수식과 계산 결과 또는 에러 메시지를 출력해야 하며 이 출력의 앞 또는 뒤에
	// 계산기 소유주 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다.
	void calculate(double oprd1, String op, double oprd2); // 예: calculate(3, "+", 2.0)

	void calculate(String expr); // calculate("3 + 2"): 연산자와 피연산자는 " "로 분리되어 있어야 함

	void calculate(Scanner scanner); // 스캐너로부터 수식을 읽어 위 메소드를 호출함
}

// 위 Phone와 Calculator를 implements로 상속 받았지만 여전히 이들 인터페이스의 메소드들을 구현하지
// 않았기 때문에 SmartPhone 클래스를 추상 클래스로 선언해야 함
// 이들 메소드들은 아래 GalaxyPhone과 IPhone 클래스에서 서로 다르게 구현됨
abstract class SmartPhone implements Phone, Calculator {
	static Calendar userDate = null;
	static boolean isNotQuested = true;

	static void setDate(String line) {
		// 키보드가 아닌 문자열 line으로부터 읽어 들이는 Scanner를 생성할 수 있음
		Scanner s = new Scanner(line);
		userDate = Calendar.getInstance(); // [교재 예제 6-11] 참조
		userDate.set(s.nextInt(), s.nextInt() - 1, s.nextInt());
		// 위에서 달(month) 값 설정에 유의할 것. 출력 때는 반대로 해야 함.
		userDate.set(Calendar.HOUR_OF_DAY, s.nextInt());
		userDate.set(Calendar.MINUTE, s.nextInt());
		userDate.set(Calendar.SECOND, s.nextInt());
		s.close(); // 문자열에서 읽어 들이는 Scanner 닫음
	}

	String owner; // 스마트폰 소유주 이름
	Calendar date;

	public SmartPhone(String owner) {
		this.owner = owner;
		date = (userDate == null) ? Calendar.getInstance() : userDate;
	}

	@Override
	public String toString() {
		return this.getMaker() + " Phone\t(" + date.get(Calendar.YEAR) + "." + (date.get(Calendar.MONTH) + 1) + "."
				+ date.get(Calendar.DAY_OF_MONTH) + " " + (date.get(Calendar.AM_PM) == Calendar.AM ? "AM " : "PM ")
				+ date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND) + ")";
	}

	public abstract String getMaker();
}

// 위 SmartPhone 클래스를 상속 받아 Phone, Calculator 인터페이스의 모든 메소드들을 구현함
// 아래 GalaxyPhone과 그 아래 IPhone 클래스는 제조회사가 달라서 상속 받은 인터맨위로

// 기능은 모두 동일하게 제공하지만 구현은 서로 다르게 함
class GalaxyPhone extends SmartPhone {
	public GalaxyPhone(String owner) {
		super(owner);
	} // 슈퍼클래스 생성자 호출

	@Override
	public void sendCall(String callee) {
		System.out.println("Made a call to " + callee + "    @" + owner + "'s GalaxyPhone");
	}

	@Override
	public void receiveCall(String caller) {
		System.out.println("Received a call from " + caller + "    @" + owner + "'s GalaxyPhone");
	}

	@Override
	public void calculate(double oprd1, String op, double oprd2) {
		switch (op) {
		case "+":
			System.out.println(oprd1 + " + " + oprd2 + " = " + (oprd1 + oprd2) + "    @" + owner + "'s GalaxyPhone");
			break;
		case "-":
			System.out.println(oprd1 + " - " + oprd2 + " = " + (oprd1 - oprd2) + "    @" + owner + "'s GalaxyPhone");
			break;
		case "*":
			System.out.println(oprd1 + " * " + oprd2 + " = " + (oprd1 * oprd2) + "    @" + owner + "'s GalaxyPhone");
			break;
		case "/":
			System.out.println(oprd1 + " / " + oprd2 + " = " + (oprd1 / oprd2) + "    @" + owner + "'s GalaxyPhone");
			break;
		default:
			System.out.println(
					oprd1 + " " + op + " " + oprd2 + " = NOT supported operator" + "    @" + owner + "'s GalaxyPhone");
			break;
		}
	}

	@Override
	public void calculate(Scanner s) {
		calculate(s.nextDouble(), s.next(), s.nextDouble());
	}

	@Override
	public void calculate(String expr) {
		String e[] = expr.trim().split(" ");
		double d1 = Double.parseDouble(e[0]);
		double d2 = Double.parseDouble(e[2]);
		calculate(d1, e[1], d2);
	}

	@Override
	public String getMaker() {
		return "SAMSUNG";
	}
}

// 위 SmartPhone 클래스를 상속 받아 Phone, Calculator 인터페이스의 모든 메소드들을 구현함
// 위 GalaxyPhone과 아래 IPhone 클래스는 제조회사가 달라서 상속 받은 인터페이스의 모든 메소드들을
// 기능은 모두 동일하게 제공하지만 구현은 서로 다르게 함
class IPhone extends SmartPhone {
	String model;

	public IPhone(String owner, String model) {
		super(owner);
		this.model = model;
	} // 슈퍼클래스 생성자 호출 및 model 초기화

	@Override
	public void sendCall(String callee) {
		System.out.println(owner + "'s IPhone " + model + ": made a call to " + callee);
	}

	@Override
	public void receiveCall(String caller) {
		System.out.println(owner + "'s IPhone " + model + ": received a call from " + caller);
	}

	private double add(double oprd1, double oprd2) {
		return oprd1 + oprd2;
	}

	private double sub(double oprd1, double oprd2) {
		return oprd1 - oprd2;
	}

	private double mul(double oprd1, double oprd2) {
		return oprd1 * oprd2;
	}

	private double div(double oprd1, double oprd2) {
		return oprd1 / oprd2;
	}

	@Override
	public void calculate(double oprd1, String op, double oprd2) {
		switch (op) {
		case "+":
			System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " + " + oprd2 + " = " + (oprd1 + oprd2));
			break;
		case "-":
			System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " - " + oprd2 + " = " + (oprd1 - oprd2));
			break;
		case "*":
			System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " * " + oprd2 + " = " + (oprd1 * oprd2));
			break;
		case "/":
			System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " / " + oprd2 + " = " + (oprd1 / oprd2));
			break;
		default:
			System.out.println(owner + "'s IPhone " + model + ":" + " " + op + " = NOT supported operator");
			break;
		}
	}

	@Override
	public void calculate(Scanner s) {
		calculate(s.nextLine());
	}

	@Override
	public void calculate(String expr) {
		String oprs[] = { "+", "-", "*", "/" };
		for (int i = 0; i < oprs.length; i++) {
			if (expr.indexOf(oprs[i]) >= 0) {
				String op = expr.substring(expr.indexOf(oprs[i]), expr.indexOf(oprs[i]) + 1);
				String e[] = expr.split("\\" + oprs[i]);
				calculate(Double.parseDouble(e[0]), op, Double.parseDouble(e[1]));
				return;
			}
		}
		System.out.println(owner + "'s IPhone " + model + ": " + expr + " = NOT supported operator");
	}

	@Override
	public String getMaker() {
		return "Apple";
	}
}

class Person implements Comparable<Person> {

	protected String name; // 사람 이름
	protected int id; // Identifier
	protected double weight; // 체중
	protected SmartPhone smartPhone; // 스마트폰

	@Override
	public int compareTo(Person p) {
		int res = this.name.compareTo(p.name);
		return res;
	}

	public void set(String name, int id, double weight) {
		// 각각의 멤버 초기 값 설정;
		this.name = name;
		this.id = id;
		this.weight = weight;

		setSmartPhone((id % 2) == 1 ? new GalaxyPhone(name) : new IPhone(name, "13"));
	}

	public Person(Scanner s) {
		this(s.next(), s.nextInt(), s.nextDouble());
	}

	public Person(String name, int id, double weight) {
		// 위 set() 메소드 활용하여 초기 값 설정
		this.set(name, id, weight);
		// "Person(..." 출력
	}

	/*
	 * public void println() { print(); System.out.println(); }
	 */

	/*
	 * public void print() { System.out.print(name + ",\tID:" + id + ", W:" + weight
	 * + ", " + smartPhone.getMaker()); }
	 */

	@Override
	public String toString() {
		return this.name + ",\tID:" + this.id + ", W:" + this.weight + ", " + this.smartPhone.getMaker();
	}

	@Override
	public boolean equals(Object obj) {
		Person p = (Person) obj;
		if (this.getID() == p.getID() && this.getClass().getName() == p.getClass().getName())
			return true;
		else
			return false;
	}

	public Phone getPhone() {
		return smartPhone;
	}

	public Calculator getCalculator() {
		return smartPhone;
	}

	public void setSmartPhone(SmartPhone smartPhone) {
		this.smartPhone = smartPhone;
	}

	public String getName() {
		return this.name;
	}

	public int getID() {
		return this.id;
	}

	public double getWeight() {
		return this.weight;
	}

	public void set(int id) {
		this.id = id;
	}

	public void set(double weight) {
		this.weight = weight;
	}

	public void set(String name) {
		this.name = name;
	}

	public void whatAreYouDoing() {
		System.out.println(name + " is taking a rest.");
	}

	public boolean isSame(String name, int id) {
		return (this.name == name && this.id == id) ? true : false;
	}

	public void update(Scanner s) {
		this.set(this.name, s.nextInt(), s.nextDouble());
	}
}

class Student extends Person {
	private String department; // 학과
	private int year; // 학년
	private double GPA; // 평균평점

	public void setStudent(String department, int year, double GPA) {
		this.department = department;
		this.year = year;
		this.GPA = GPA;
	}

	public Student(Scanner scanner) {
		super(scanner);
		setStudent(scanner.next(), scanner.nextInt(), scanner.nextDouble());
	}

	public Student(String name, int id, double weight, String department, int year, double GPA) {
		super(name, id, weight);
		setStudent(department, year, GPA);
	}

	@Override
	public boolean equals(Object obj) {
		Student st = (Student) obj;
		if (super.equals(st) && this.department.equals(st.department) && this.year == st.year)
			return true;
		else
			return false;
	}

	@Override // 부모 클래스인 Person의 whatAreYouDoing() 메소드를 오버라이딩함
	public void whatAreYouDoing() {
		study();
		System.out.print(", ");
		takeClass();
		System.out.println();
	}

	// 새로 추가된 메소드
	public void study() {
		System.out.print(super.getName() + " is studying as a " + year + "-year student in " + department);
	}

	public void takeClass() {
		System.out.print("\n" + super.getName() + " took several courses and got GPA " + GPA);
	}

	// @Override // Person의 print()
	/*
	 * public void print() { super.print(); System.out.print(",\tD:" + department +
	 * ", Y:" + year + ", GPA:" + GPA); }
	 */
	@Override
	public String toString() {
		return super.toString() + ",\tD:" + this.department + ", Y:" + this.year + ", GPA:" + this.GPA;
	}

	@Override // Person의 update()
	public void update(Scanner s) {
		super.update(s);
		setStudent(s.next(), s.nextInt(), s.nextDouble());
	}
}

class Worker extends Person {
	private String company; // 회사명
	private String position; // 직급

	public void set(String company, String position) {
		this.company = company;
		this.position = position;
	}

	// 새로 추가된 메소드
	public Worker(String name, int id, double weight, String company, String position) {
		super(name, id, weight);
		set(company, position);
	}

	public Worker(Scanner s) {
		super(s);
		set(s.next(), s.next());
	}

	@Override
	public boolean equals(Object obj) {
		Worker wk = (Worker) obj;
		if (super.equals(wk) && this.company.equals(wk.company) && this.position.equals(wk.position))
			return true;
		else
			return false;
	}

	@Override
	public void whatAreYouDoing() {
		work();
		System.out.print(", \n");
		goOnVacation();
		System.out.println();
	}

	public void goOnVacation() {
		System.out.print(name + " is now enjoying his(her) vacation.");
	}

	public void work() {
		System.out.print(name + " works in " + company + " as " + position);
	}

	// @Override
	/*
	 * public void print() { super.print(); System.out.print(",\tC:" + company +
	 * ", P:" + position); }
	 */
	@Override
	public String toString() {
		return super.toString() + ",\tC:" + company + ", P:" + position;
	}

	@Override
	public void update(Scanner s) {
		super.update(s);
		set(s.next(), s.next());
	}
}

class StudWork extends Student {
	protected boolean married; // 결혼유무
	protected String career[]; // 알바경력
	protected String address; // 현근무지주소

	public void setCareer(String sCareer) {
		// ","로 구분된 하나의 문자열 즉,
		// "CU KangNam,Seven Eleven,GS Convenient Store Suwon"로 주어진
		// 경력 리스트(sCareer)를 토큰별로 쪼개어 문자열 배열 career[]에 저장
		// String의 split()을 사용해도 되지만 여기서는 StringTokenizer를 이용할 것
		StringTokenizer st = new StringTokenizer(sCareer, ",");
		// StringTokenizer를 이용해 sCareer를 "," 구분자를 이용해 토크들로 자르고,
				career = new String[st.countTokens()];
		for (int i = 0; i < career.length; i++)
			career[i] = st.nextToken();
		// 토큰의 개수만큼 String[] 배열을 할당하여 career에 저장하고,
		// for문을 이용하여 잘라진 각 토큰을 얻어 내 career[i]에 저장
	}

	public void setAddress(String sAddress) {
		// 하나의 문자열 "Gwangju city BongsunDong 12 BeonJi"로 주어진 sAddress를
		// 아래 pseudocode처럼 몇가지 정보를 수정/추가/삭제를 한 후 멤버 address에 저장
		String 번길 = "BeonGil", 번지 = "BeonJi", 남구 = "NamGu";
		StringBuffer sb = new StringBuffer(sAddress);
		// StringBuffer의 메소드를 이용하여 구현할 것, 즉 indexOf(), replace(),
		// sb.append(), delete(), insert(), toString() 활용

		if (sb.indexOf(번지) >= 0)
			sb.replace(sb.indexOf(번지), sb.indexOf(번지) + 번지.length(), 번길);
		else if (sb.indexOf(번길) < 0)
			sb.append(번길);
		if (sb.indexOf("-") >= 0)
			sb.deleteCharAt(sb.indexOf("-"));
		if (sb.indexOf(남구) < 0)
			if (sb.indexOf("city") >= 0)
				sb.insert(sb.indexOf("city") + "city".length(), " " + 남구);
			else if (sb.indexOf("city") < 0)
				sb.insert(0, 남구 + " ");
		address = sb.toString();
		// 1) 주소 중에 "BeonJi"가 있으면 "BeonGil"로 변경,
		// 예) NamGu 24 BeonJi -> NamGu 24 BeonGil
		// 2) 주소 중에 번지나 번길 없으면 주소 끝에 "BeonGil" 삽입,
		// 예) SeseokDong 24 -> SeseokDong 24 BeonGil
		// 3) 1-24 같이 중간에 "-"가 있으면 "-"를 삭제,
		// 예) 1-24 -> 124 BeonGil, 1-24 BeonJi -> 124 BeonGil
		// 4) 구 이름인 "NamGu"가 주소에 없으면 "city" 다음에 " "를 삽입한 후 그 뒤에 "NamGu"를 삽입할 것,
		// 예) Gwangju city BongsunDong 12 BeonJi ->
		// Gwangju city NamGu BongsunDong 12 BeonGil
		// 만약 "NamGu"도 없고 "city"도 없다면 맨앞에 "NamGu"+" "를 삽입,
		// 예) JinwolDong 12-2 BeonGil -> NamGu JinwolDong 122 BeonGil
		// 마지막으로 sb에 있는 문자열을 꺼내 [StringBuffer의 toString() 메소드 활용] address 멤버에 설정
	}

	public void set(String sMarried, String sCareer, String sAddress) { // Overloading
		// 문자열 매개변수 sMarried를 Boolean 값으로 변환한 후 멤버 married에 설정 [교재 예제 6-5 참조]
		this.married = Boolean.parseBoolean(sMarried);
		// 위 setCareer()와 setAddress() 호출하여 경력과 주소설정;
		setCareer(sCareer);
		setAddress(sAddress);
	}

	public StudWork(String args[], String personArgs[]) {
		super(personArgs[0], Integer.parseInt(personArgs[1]), Double.parseDouble(personArgs[2]), personArgs[3],
				Integer.parseInt(personArgs[4]), Double.parseDouble(personArgs[5]));
		this.set(args[1], args[2], args[3]);

	}

	public StudWork(String args[]) {
		this(args, args[0].split(" "));
	}

	public StudWork(String line) {
		this(line.trim().split(":"));
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" M:" + married + ",\n\tCareer:");
		for (int i = 0; i < career.length - 1; i++) {
			sb.append(career[i] + ", ");
		}
		sb.append(career[career.length - 1] + ",\n\tAddr:" + address);
		return sb.toString();
	}

	public void printStudWork(String name, String sYear, String sGPA, String sMarried) {
		System.out.println("name:" + name + ",\tyear:" + sYear + ", GPA:" + sGPA + ", M:" + sMarried);
	}

	public void printStudWork() {
		// 각각의 해당 멤버를 문자열 sYear, sGPA, sMarried로 변환한 후 [교재 예제 6-5 참조]
		String sb[] = super.toString().split(",");
		String sYear = sb[5].trim().split(":")[1];
		String sGPA = sb[6].trim().split(":")[1];
		String sMarried = Boolean.toString(married);

		printStudWork(this.name, sYear, sGPA, sMarried);
		// 반드시 위 printStudWork(name, sYear, sGPA, sMarried)를 호출하여 구현할 것
	}
}

interface Factory<T> 
{
	 T newPerson(Scanner scanner);
	// 5번 메뉴항목 입력 후 "Soon" 뒤에 ([엔터] 또는 ' ' 또는 '\t')가 있고 
	    // 그 뒤에 "사람 인적정보[엔터]" 입력되었을 때 중간에 있는 ([엔터] 또는 ' ' 또는 '\t')를
	    // 제거하고 "사람 인적정보"를 읽어 내는 메소드
	    public static String getNextLine(Scanner s) { 
	        String line = s.nextLine().trim();
	        for ( ; line.equals(""); line = s.nextLine().trim())
	            ;
	        return line;
}

}
class PersonManager<T extends Person> {

	// private final int MAX_PERSONS = 100;

	private Scanner scanner;
	//private Vector<Person> vector;
	 private LinkedList<T> list;
	// private int count; // persons[] 배열에 실제로 저장된 사람들의 수
	 private Factory<T> factory;

	PersonManager(T array[], Scanner scanner, Factory<T> factory) {
		  this.scanner = scanner;
		this.factory =factory; 
		 
		 
		  
		// MAX_PERSONS개의 원소를 가진 persons 배열을 생성
		// persons = new Person[MAX_PERSONS];
		// array의 모든 원소를 persons으로 복사
		  list = new LinkedList<T>();
		//vector = new Vector<Person>();
		for (int i = 0; i < array.length; i++)
			list.add(i,array[i]);
	
		// persons[i] = array[i];
		// scanner, count 멤버 초기화
		
		// this.count = array.length;
	}

	int findIndex(String name) {
		int i;
		for (i = 0; i < list.size(); i++) {
			if (name.equals(list.get(i).getName())) {
				return i;
			}
		}
		System.out.println(name + " is NOT found.");
		return -1;
	}

	T find(String name) {
		int index = findIndex(name);
		return (index < 0) ? null : list.get(index);
	}

	void display() {
		for (var p : list) {
			  System.out.println(p);
			//System.out.println(list.get(i).toString());
		}
		System.out.println("Person count: " + list.size());
	}

	void search() {
		// scanner.nextLine();
		System.out.print("Name to search? ");
		String name = scanner.next();
		T s = find(name);
		if (s != null) {
			System.out.println(s);
		}
	}

	void update() {
		System.out.print("Information to update? ");
		String name = scanner.next();

		if (find(name) != null) {
			find(name).update(scanner);
		} else
			scanner.nextLine();
	}

	void delete() {
		System.out.print("Name to delete? ");
		String name = scanner.next();

		int idx = findIndex(name);
		/*
		 * if (idx >= 0) { for (int i = idx; i < vector.size(); i++) { //vector.get(i) =
		 * persons[i + 1]; } // count--; }
		 */
		if (idx >= 0)
			list.remove(idx);

	}

	void insert() {
		//scanner.nextLine();
		if (list.size() > 0) {
			while (true) {
				System.out.print("Existing name to insert in front? ");
				String name = scanner.next();
				
	
					
				//System.out.println("[Person delimiter(S or W or SW)] [Person information to insert]? ");
				printNotice("", "to insert? ");
					T p = getNewPerson();
					if(findIndex(name)<0)
						break;
					
					if (p == null) 
						break;
					
					list.add(findIndex(name), p);
					
					break;
				
			}
		}

		else {
			System.out.println("[Person delimiter(S or W or SW)] [Person information to insert]? ");
			T p = getNewPerson();
			list.add(list.size(), p);
			
		}
	}
	
    public void printNotice(String preMessage, String postMessage) { 
        System.out.print(preMessage);
    	if (factory instanceof PersonFactory)
            System.out.print("[Person delimiter(S or W or SW)] ");
        System.out.println("[Person information] "+postMessage);
    }

	public T getNewPerson() {

		return factory.newPerson(scanner);
	}

	void append() {
		//System.out.println("Continuously input [S or W or SW] [person information to insert], and  input \"end\" at the end.");
		printNotice("Continuously input ", "\nto append, and input \"end\" at the end.");
				while (true) {
			if (scanner.hasNext("end")) {
				scanner.next();
				return;
			} else {
				T p = getNewPerson();
				if (p == null) {
					continue;
				}
				list.add(p);
			}
		}
	}

	void whatDoing() {
		System.out.print("Name to know about? ");
		T p = find(scanner.next());
		if (p != null)
			p.whatAreYouDoing();
	}

	public void call() {
		System.out.print("Names of phone caller and callee? ");
		String name[] = new String[2];
		Person p[] = new Person[2]; //***************
		name[0] = scanner.next();
		name[1] = scanner.next();
		p[0] = find(name[0]);
		p[1] = find(name[1]);

		if (p[0] == null || p[1] == null)
			return;
		else {
			p[0].getPhone().sendCall(name[1]);
			p[1].getPhone().receiveCall(name[0]);
		}
	}

	public void calculate() {
		System.out.print("Calculator's owner and expression? ");
		String name = scanner.next();
		if (find(name) == null) {
			scanner.nextLine();
			return;
		}
		Calculator cl = find(name).getCalculator();
		cl.calculate(scanner);
	}

	public void findPerson() {
		//System.out.println("[Delimiter(S or W or SW)] [Person information to find by using equals()]?");
		printNotice("", "to find by using equals()? ");

		T p = this.getNewPerson();
		if (p == null) {
			return;
		}
		T fp = this.find(p.name);
		if (fp != null && p.equals(fp)) {
			System.out.println(fp);
		} else
			System.out.println("can NOT find anyone equal to " + p.getName());
	} // 메뉴항목: FindPerson(equals()이용한 사람 찾기)

	public void displayPhone() {
		for (var p:list)
			if (p != null)
				System.out.println(p.getName() + ":\t" + p.getPhone());
	} // 메뉴항목: DispAllPhone(모든폰보기)

	public void changePhone() {
		if (SmartPhone.isNotQuested) { // changePhone()을 처음 실행했다면 한번만 아래 과정 실행
			scanner.nextLine(); // Menu item number? 12[엔터]: 숫자 뒤의 [엔터]를 skip
			// ex: 년 월 일 시 분 초 순서로 입력
			System.out.print("Date and time to set(ex: 2021 10 1 18 24 30)? ");
			String line = scanner.nextLine();
			if (!line.equals("")) // [년 월 일 시 분 초]를 입력한 경우
				SmartPhone.setDate(line);
			// 날짜를 입력하지 않고 그냥 엔터를 친 경우(line.equals("")) 아무 것도 설정하지 않음
			SmartPhone.isNotQuested = false;
			// 한번만 사용자에게 질의하여 위 과정을 실행하고 다음부터는 위 과정 생략하도록 하기 위해
		}

		System.out.println("Owner name and maker of phone to change(ex: Hong Samsung or Hong Apple)?");
		String fname = scanner.next();
		String fmaker = scanner.next();
		T fp = find(fname);
		if (fp == null)
			return;

		if (fmaker.equals("Samsung"))
			fp.setSmartPhone(new GalaxyPhone(fname));
		else if (fmaker.equals("Apple"))
			fp.setSmartPhone(new IPhone(fname, "13"));
		else {
			System.out.println(fmaker + ": WRONG phone's maker");
			return;
		}

		System.out.println(fp.getPhone());
	} // 메뉴항목: ChangePhone

	private Random rnd = null;

// Math.random() 대신 rnd.random()을 사용할 것
	public void changeWeight() { // 메뉴항목: ChangeWeight(자동체중변경)
		if (rnd == null) {
			System.out.print("Seed integer for random number generator? ");
			rnd = new Random(scanner.nextInt());
		}
		// 여기에 코드 추가
		for (var p:list) {
			double weight = (rnd.nextDouble() * 60 + 40);
			weight = (double) Math.round(weight);
			p.weight = weight;
		}
		display();
	}

	public void displayStudWorks() {
		for (var p:list) {
			if (p != null && p.getClass().getName().equals("StudWork")) {
				StudWork st = (StudWork) p;
				st.printStudWork();
			}
		}
	} // 메뉴항목: DispAllAlba(모든알바생들보기)

	public void sort() {
		// vector.iterator();

		Collections.sort(list);
		display();

	} // 메뉴항목: Sort(정렬)

	public void reverse() {
		Collections.reverse(list);
		display();
	} // 메뉴항목: Reverse(역순배치)

	public void binarySearch() {

		System.out.print("For binary search, it's needed to sort in advance. Name to search? ");
		String name = scanner.next();
		Person p = new Person(name, 0, 0.0);
		int idx = Collections.binarySearch(list, p);
		if (idx > -1) {
			System.out.println(list.get(idx));
		} else {
			System.out.println(name + " is NOT found.");
		}

	} // 메뉴항목: BinarySearch(이진검색)

	private final int 종료 = 0, 모두보기 = 1, 검색 = 2, 수정 = 3, 삭제 = 4, 삽입 = 5, 추가 = 6, 뭐하니 = 7, 전화 = 8, 계산 = 9, 사람찾기 = 10,
			모든폰보기 = 11, 폰변경 = 12, 자동체중변경 = 13, 알바생들보기 = 14, 정렬 = 15, 역순배치 = 16, 이진검색 = 17;

	public void run() {
		System.out.println("PersonManage::run() start");
		display();
		while (true) {
			System.out.println();
			System.out.println("Menu: 0.Exit 1.DisplayAll 2.Search 3.Update 4.Remove 5.Insert");
			System.out.println("      6.Append 7.WhatDoing? 8.PhoneCall 9.Calculator 10.FindPerson(equals())");
			System.out.println("     11.DispAllPhone 12.ChangePhone 13.ChangeWeight 14.DispAllAlba");
			System.out.println("     15.Sort 16.Reverse 17.BinarySearch");
			int idx;
			while (true) {
				System.out.print("Menu item number? ");
				try {
					idx = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Input an INTEGER.");
					scanner.nextLine();
					continue;
				}
				break;
			}
			switch (idx) {
			case 모두보기:
				display();
				break;
			case 검색:
				search();
				break;
			case 수정:
				update();
				break;
			case 삭제:
				delete();
				
				break;
			case 삽입:
				insert();
				break;
			case 추가:
				append();
				break;
			case 뭐하니:
				whatDoing();
				break;
			case 전화:
				call();
				break;
			case 계산:
				calculate();
				break;
			case 사람찾기:
				findPerson();
				break;
			case 모든폰보기:
				displayPhone();
				break;
			case 폰변경:
				changePhone();
				break;
			case 자동체중변경:
				changeWeight();
				break;
			case 알바생들보기:
				displayStudWorks();
				break;
			case 정렬:
				sort();
				break;
			case 역순배치:
				reverse();
				break;
			case 이진검색:
				binarySearch();
				break;
			case 종료:
				System.out.println("PersonManager run() returned\n");
				return;

			default:
				System.out.println("WRONG menu item");
				break;
			}
		}
	}
}

class PersonFactory implements Factory<Person> {
    // 스캐너를 통해 사용자가 입력한 사람 구분자와 사람 정보를 입력 받은 후 
    // 구분자에 따라 기존처럼 Student, Worker, StudWork 객체를 생성한 후 반환함
    @Override
    public Person newPerson(Scanner s) { 
       // 위 [문제 2-4]의 getNewPerson() 메소드 내의 주석 처리한 기존 코드를 [제거]한 후
        //여기로 [이동]시키고 주석을 제거한다.
    	
    	String tag = s.next();
		if (tag.equals("S"))
			return new Student(s);
		else if (tag.equals("W"))
			return new Worker(s);
		else if (tag.equals("SW"))
			return new StudWork(s.nextLine());
		else {
			System.out.println(tag + ": WRONG delimiter");
			s.nextLine();
			
			return  null;
		}
    	
    }
}
/*
 interface Factory<T> 
{
    // 스캐너로부터 제네릭 타입 T형의 사람 정보를 입력 받아 T형의 새로운 사람 객체를 생성하여 반환함 
    T newPerson(Scanner scanner);
    // 이 메소드는 추후 Factory<T>를 implements하는 여러 클래스에서 직접 구현해야 한다.
}
 
 */

class StudentFactory implements Factory<Student> {
    // 스캐너를 통해 사용자가 지정한 Student 정보를 입력 받은 후 Student 객체를 생성하여 반환함
    @Override
    public Student newPerson(Scanner s) { return new Student(s); }
}

// 위 두 클래스를 참고하여 아래 두 클래스를 완성하라.

class WorkerFactory implements Factory<Worker> { 
	 @Override
	    public Worker newPerson(Scanner s) { return new Worker(s); }
    // 스캐너를 통해 사용자가 지정한 Worker 정보를 입력 받은 후 Worker 객체를 생성하여 반환함
}

class StudWorkFactory implements Factory< StudWork> { 
	 @Override
	
	    public  StudWork newPerson(Scanner s) { return new StudWork(Factory.getNextLine(s));}//{s.nextLine(); return new  StudWork(s.nextLine()); }
    // 스캐너를 통해 사용자가 지정한 StudWork 정보를 입력 받은 후 StudWork 객체를 생성하여 반환함
}

public class Main {

	static void StringSpeed(String s) {
		System.out.println("String Speed");
		for (int i = 0; i < 20; i++) {
			String last = s.substring(s.length() - 1, s.length()); // 마지막 문자 저장
			String sub = s.substring(0, s.length() - 1); // 처음부터 마지막 문자 앞까지 저장
			s = sub + last; // 저장된 마지막 문자를 맨 뒤에 다시 추가하여 새로운 문자열을 생성
			System.out.print(i + " ");
		}
		System.out.println("\n");
	}

	static void StringBufferSpeed(StringBuffer sb) {
		System.out.println("StringBuffer Speed");
		for (int i = 0; i < 20; i++) {
			String last = sb.substring(sb.length() - 1, sb.length());// 마지막 문자 저장
			sb.delete(sb.length() - 1, sb.length()); // 마지막 문자 삭제
			sb.append(last); // 저장된 마지막 문자를 다시 맨 뒤에 추가
			System.out.print(i + " ");
		}
		System.out.println("\n");
	}

	static void SpeedTest() {
		String s = "This book is a 명품 Java Programming.";
		// s 문자열을 반복적으로 계속 두배로 키워 매우 긴 문자열(약 300MB)을 만든다.
		// 아래 for문의 23 대신 15, 20 등으로 변경해서 짧은 문자열일 경우의 속도도 비교해 보라.

		// 만약 자바시스템의 기본 메모리 크기가 작게 설정되어 있으면
		// 아래 for문이 메모리 부족으로 죽을 수 있다.
		// 이 경우 프로그램이 죽지 않을 때까지 23을 계속 하나씩 줄여서 테스트해라.
		// 죽지 않는 시점의 그 숫자를 23 대신 사용하라.
		for (int i = 0; i < 21; i++) {
			s += s;
			System.out.print(i + " ");
		}
		System.out.println("\ns length is " + s.length() / (1024 * 1024) + " MB\n");
		StringSpeed(s); // 아래 문장과 순서를 바꾸어도 속도 차이는 있음
		StringBufferSpeed(new StringBuffer(s));
	}

	static void StringRotation(String s) {
		String book = "book", BOOK = "BOOK", 명품 = "Masterpiece ", Java = "Java";
		System.out.println("String Rotation: " + s);
		for (int i = 0; i < 20; i++) {
			if (s.indexOf(book) >= 0)
				s = s.replace(book, BOOK);
			else if (s.indexOf(BOOK) >= 0)
				s = s.replace(BOOK, book);
			// 기존 s 문자열 내에
			// 만약 변수 book의 내용이 있으면 이를 변수 BOOK의 내용으로 대체하고 // replace() 메소드
			// 변수 book이 없고 대신에 변수 BOOK이 있으면 변수 book으로 대체한다.
			// 특정 문자열이 s에 있는지 조사는 s.indexOf(찾을문자열변수)의 리턴 값이
			// ( >= 0)면 찾은 것이고 음수면 못 찾은 것임

			if (s.indexOf(명품) >= 0) {
				String first = s.substring(0, s.indexOf(명품));
				String last = s.substring(s.indexOf(명품) + 명품.length());
				s = first + last;
			} else {
				if (s.indexOf(Java) >= 0) {
					String f = s.substring(0, s.indexOf(Java));
					String l = s.substring(s.indexOf(Java), s.length());
					s = f + 명품 + l;
				}
			}
			// 만약 변수 명품의 내용이 있으면 이를 삭제하고
			// 변수 명품의 내용이 없으면 변수 Java 단어 앞에 변수 명품의 내용을 새로 삽입하라.
			// 삭제는 삭제할 단어 앞에 있는 문자열을 뽑아내고(substring()),
			// 또한 삭제할 단어 뒤(삭제할 단어 위치+단어 길이)에 있는
			// 문자열을 뽑아낸 후 뽑아 낸 두 문자열을 서로 더해 새로운 문자열로 만듦
			// 삽입: (Java 단어 앞의 서브문자열) + 명품 +
			// (Java 단어부터 이후의 서브문자열)

			String first = s.substring(0, 1);
			String last = s.substring(1, s.length());
			s = last + first;

			// s의 첫 문자를 맨 마지막으로 옮긴 새로운 문자열을 만들어 s에 저장
			// 위 StringSpeed(String s) 참조
			// s의 첫번째 문자를 뽑아내 변수에 저장하고
			// 첫번째를 제외한 나머지 문자열 또한 뽑아내 변수에 저장하라.
			// 나머지 문자열 변수와 첫 문자 변수를 더해서 s에 저장하면 됨
			System.out.println(((i < 10) ? " " : "") + i + " " + s);
		}
		System.out.println();
	}

	static String StringBufferRotation(String s) {
		String book = "book", BOOK = "BOOK", 명품 = "Masterpiece ", Java = "Java";
		StringBuffer sb = new StringBuffer(s);
		System.out.println("StringBuffer Rotation: " + s);

		for (int i = 0; i < 20; i++) {
			if (sb.indexOf(book) >= 0)
				sb.replace(sb.indexOf(book), sb.indexOf(book) + book.length(), BOOK);
			else if (sb.indexOf(BOOK) >= 0)
				sb.replace(sb.indexOf(BOOK), sb.indexOf(BOOK) + BOOK.length(), book);

			if (sb.indexOf(명품) >= 0)
				sb.delete(sb.indexOf(명품), sb.indexOf(명품) + 명품.length());
			else if (sb.indexOf(Java) >= 0)
				sb.insert(sb.indexOf(Java), 명품);
			// 기존 sb 스트링버퍼 내에
			// 만약 "book" 문자열이 있으면 이를 "BOOK"으로 대체하고
			// "book"이 없고 대신에 "BOOK"이 있으면 "book"으로 대체한다.
			// 만약 "Masterpiece" 문자열이 있으면 이를 삭제하고
			// "Masterpiece"이 없으면 "Java" 앞에 "Masterpiece"을 새로 삽입하라.
			// indexOf(), replace(), delete(), insert()메소드 활용
			char f = sb.charAt(0);
			sb.delete(0, 1);
			sb.append(f);

			// s의 첫 문자를 맨 마지막으로 옮긴 새로운 문자열을 만들어 s에 저장
			// sb의 첫번째 문자 뽑아내 변수에 저장하고 첫번째 문자를 삭제한다.
			// sb 끝에 첫 문자를 추가한다.
			// delete(), append() 활용
			System.out.println(((i < 10) ? " " : "") + i + " " + sb);
		}
		System.out.println();
		// sb에 저장된 문자열을 빼내 반환 // toString() 활용
		return sb.toString();
	}

	static void StringAndStringBufferTest(Scanner scanner) {
		String s0 = "This book is a Masterpiece Java Programming.";
		System.out.println("If just [enter], automatic input: " + s0);
		System.out.println("Simple sentence including 3 words(book, Masterpiece, Java)? ");
		String s = scanner.nextLine();
		System.out.println();

		if (!s.equals("")) // 그냥 엔터만 치지 않고 정보를 입력한 후 엔터친 경우
			s0 = s; // 사용자가 입력한 문장열로 기존 s0 대체
		StringRotation(s0);
		s = StringBufferRotation(s0);
		System.out.println("StringBufferRotation() returns: " + s + "\n");

	}

	static void compares(String msg, Person p1, Person p2) {
		System.out.println(msg + (p1.equals(p2) ? "equals" : "NOT equal"));
	}

	static void equalsTest() {
		Person p2 = new Person("Choon", 22, 45.5);
		Student sp2 = new Student("Hong", 10, 81.5, "Computer", 3, 3.5);
		Worker wp2 = new Worker("Mong", 11, 70.3, "Samsung", "DepartmentHead");
		compares("p1 and p2 are ", p1, p2);
		p2.set(12); // ID 변경
		compares("p1 and p2 are ", p1, p2);
		System.out.println();
		compares("p1 and sp2 are ", p1, sp2);
		// compares("sp2과 p1는 ", sp2, p1); // 주석을 풀면 프로그램이 죽는 이유는?
		System.out.println();
		compares("sp1 and sp2 are ", sp1, sp2);
		sp2.setStudent("Computer", 2, 3.5);
		compares("sp1 and sp2 are ", sp1, sp2);
		System.out.println();
		compares("wp1 and wp2 are ", wp1, wp2);
		wp2.set("Samsung", "Director");
		compares("wp1 and wp2 are ", wp1, wp2);
		System.out.println();
	}

	static void StudWorkTest(Person p1, Person p2, Person p3) {
		StudWork sw1 = (StudWork) p1, sw2 = (StudWork) p2, sw3 = (StudWork) p3;
		System.out.println("StudentWorker: System.out.println()");
		System.out.println(sw1);
		System.out.println(p1);
		System.out.println(sw2);
		System.out.println(p2);
		System.out.println(sw3);
		System.out.println(p3);

		System.out.println("\nStudentWorker.printStudWork()");
		sw1.printStudWork();
		sw2.printStudWork();
		sw3.printStudWork();
	}

	static void calculatorTest() {
		Calculator gc = new GalaxyPhone(sp1.getName());
		Calculator ic = new IPhone(wp1.getName(), "13");
		// 갤럭시는 연산자와 피연산자가 [하나의 ' '로만] 분리되어 있어야 함
		gc.calculate("1 + 2");
		gc.calculate("1 - 2");
		gc.calculate("1 * 2");
		gc.calculate("1 / 2");
		gc.calculate("1 | 2");
		// gc.calculate("1+2"); // 연산자와 피연산자가 " "로 분리되어 있지 않아 주석을 풀면 프로그램이 죽는다.
		// gc.calculate("1 + 2"); // 연산자와 피연산자가 두 개의 ' '로 분리되어 있어 주석을 풀면 프로그램이 죽는다.
		// 두 개의 스페이스로 분리된 "1 +"는 String::split()에 의해 "1", "", "+"로 분리되기 때문이다.

		// IPhone의 경우 연산자와 피연산자가 ' '로 분리되지 않아도 처리할 수 있음
		ic.calculate("1 + 2");
		ic.calculate("1- 2");
		ic.calculate("1 *2");
		ic.calculate("1/2");
		ic.calculate("1&2");
	}

	// Up casting
	static Person p1 = new Person("Choon", 12, 45.5);
	static Person sp1 = new Student("Hong", 10, 71.5, "Computer", 2, 3.5);
	static Person wp1 = new Worker("Mong", 11, 75, "Samsung", "Director");

	static void toStringTest() {
		// Overriding: toString()
		System.out.println(p1);
		System.out.println(sp1);
		System.out.println(wp1);

		// Down casting
		Student s1 = (Student) sp1;
		Worker w1 = (Worker) wp1;
		System.out.println(s1 + " " + "Student: " + 1.0);
		System.out.println(w1 + "    " + "Worker:  " + true);
		System.out.println();
	}

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        Student students[] = {
            new Student("Hong",  10, 64,   "Computer",    2, 3.5), 
            new Student("Chung", 11, 46.1, "Physics",     1, 3.8), 
            new Student("Soon",  12, 88.5, "Electronics", 4, 2.5),
        };
       // var studMng = new PersonManager<Student>(students, scanner);
        var studFact = new StudentFactory();
        var studMng = new PersonManager<Student>(students, scanner, studFact);

        Worker workers[] = {
            new Worker ("Hong",  10, 64,   "Samsung", "Director"),  
            new Worker ("Chung", 11, 46.1, "LG",      "DeparmentHead"),
            new Worker ("Soon",  12, 88.5, "Naver",   "TeamLeader"),
        };
      //  var workMng = new PersonManager<Worker>(workers, scanner);
        var workFact = new WorkerFactory();
        var workMng = new PersonManager<Worker>(workers, scanner, workFact);

        StudWork studWorks[] = {
            // 중요: StudWork의 기본인적정보의("Kang 22 90.1 Computer 3 3.5") 경우 
            //      문자열 내에 필드구분을 위해 하나의 스페이스만으로 각 필드를 구분해야 함
            new StudWork("Hong 10 64 Computer 2 3.5:true:CU KangNam,Seven Eleven,GSConvenientStore Suwon:Gwangju city BongSunDong 12 BeonJi"),
            new StudWork("Chung 11 46.1 Physics 1 3.8:true:Family Mart,7 11,GS BookGu:Gwangju city NamGu 12-2"),
            new StudWork("Soon 12 88.5 Electronics 4 2.5:false:Seven Eleven:12-3 BeonGil"),
        };
       // var studWorkMng = new PersonManager<StudWork>(studWorks, scanner);
        var studWorkFact = new StudWorkFactory();
        var studWorkMng = new PersonManager<StudWork>(studWorks, scanner, studWorkFact);


        Person persons[] = {
            // 중요: StudWork의 기본인적정보의("Kang 22 90.1 Computer 3 3.5") 경우 
            //      문자열 내에 필드구분을 위해 하나의 스페이스만으로 각 필드를 구분해야 함
            new StudWork("Kang 22 90.1 Computer 3 3.5:true:CU KangNam,Seven Eleven,GSConvenientStore Suwon:Gwangju city BongSunDong 12 BeonJi"),
            new StudWork("Sham 20 81.5 Electronics 2 2.1:true:Family Mart,7 11,GS BookGu:Gwangju city NamGu 12-2"),
            new StudWork("Jang 21 70.3 Mathematics 4 3.0:false:Seven Eleven:12-3 BeonGil"),
            new Student("Hong",  10, 64,   "Computer",    2, 3.5), 
            new Worker ("Mong",  11, 75,   "Samsung",     "Director"),  
            new Worker ("Choon", 12, 45.5, "LG",          "DepartmentHead"),
            new Student("Chung", 13, 46.1, "Physics",     1, 3.8), 
            new Student("Soon",  14, 88.5, "Electronics", 4, 2.5),
        };
       // var personMng = new PersonManager<Person>(persons, scanner);
        var personFact = new PersonFactory();
        var personMng = new PersonManager<Person>(persons, scanner, personFact);
        while (true) {
            System.out.println("Menu: 0.Exit 1.Student 2.Worker 3.StudWork 4.AllKindPerson");
            System.out.print("Menu item number? ");
            int idx = scanner.nextInt();
            if (idx == 0) 
                break;
            switch (idx) {
            case 1:  System.out.println("\nPersonManager<Student>");
                     studMng.run();
                     break;
            case 2:  System.out.println("\nPersonManager<Worker>");
                     workMng.run();
                     break;
            case 3:  System.out.println("\nPersonManager<StudWork>");
                     studWorkMng.run();
                     break;
            case 4:  System.out.println("\nPersonManager<Person>");
                     personMng.run();
                     break;
            default: System.out.println("WRONG menu item\n");
                     break;
            }
        }
        scanner.close();

    }

// 위 코드에서 배열 students[]는 먼저 Student 객체 배열을 생성하여 저장하고 있다.
// 그런 후 대량의 [Student 객체만]을 보관하고 관리, 검색, 수정, 삭제 등을 할 수 있는 
// PersonManager<Student>의 객체 studMng를 생성한다. 
//      var studMng = new PersonManager<Student>(students, scanner);

// 위 main() 함수에서는 studMng, workMng, studWorkMng, personMng 등 같은
// 네 개의 PersonManager 객체가 생성되었다.
// 이들은 각각 Student, Worker, StudWork, Person 객체만을 전용으로 관리하는 관리자 객체이다.
// 예를들어, studMng 관리자는는 Student 객체만 삽입, 수정, 삭제, 정렬 등을 할 수 있고 
// 다른 클래스의 객체는 관리할 수 없다.
}