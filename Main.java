import java.util.*;

interface Phone {
	void sendCall(String callee);

	// �� �޼ҵ�� "Made a call to ������_�̸�(callee)"��� ����ؾ� �ϸ� �� ����� �� �Ǵ� �ڿ�
	// �߽��� �̸��� �Բ� ����ϵ� ����Ŀ�� �˾Ƽ� ������ ȸ���, �𵨸� ��� �Բ� ǥ���ϸ� �ȴ�.
	void receiveCall(String caller);
	// �� �޼ҵ�� "Received a call from �۽���_�̸�(caller)"��� ����ؾ� �ϸ� �� ����� �� �Ǵ�
	// �ڿ� ������ �̸��� �Բ� ����ϵ� ����Ŀ�� �˾Ƽ� ������ ȸ���, �𵨸� ��� �Բ� ǥ���ϸ� �ȴ�.
}

interface Calculator {
	// +, -, *, / ��Ģ���길 �����ϰ� �� ���� �������� ��� "NOT supported operator" ���� �޽��� ���
	// ���İ� ��� ��� �Ǵ� ���� �޽����� ����ؾ� �ϸ� �� ����� �� �Ǵ� �ڿ�
	// ���� ������ �̸��� �Բ� ����ϵ� ����Ŀ�� �˾Ƽ� ������ ȸ���, �𵨸� ��� �Բ� ǥ���ϸ� �ȴ�.
	void calculate(double oprd1, String op, double oprd2); // ��: calculate(3, "+", 2.0)

	void calculate(String expr); // calculate("3 + 2"): �����ڿ� �ǿ����ڴ� " "�� �и��Ǿ� �־�� ��

	void calculate(Scanner scanner); // ��ĳ�ʷκ��� ������ �о� �� �޼ҵ带 ȣ����
}

// �� Phone�� Calculator�� implements�� ��� �޾����� ������ �̵� �������̽��� �޼ҵ���� ��������
// �ʾұ� ������ SmartPhone Ŭ������ �߻� Ŭ������ �����ؾ� ��
// �̵� �޼ҵ���� �Ʒ� GalaxyPhone�� IPhone Ŭ�������� ���� �ٸ��� ������
abstract class SmartPhone implements Phone, Calculator {
	static Calendar userDate = null;
	static boolean isNotQuested = true;

	static void setDate(String line) {
		// Ű���尡 �ƴ� ���ڿ� line���κ��� �о� ���̴� Scanner�� ������ �� ����
		Scanner s = new Scanner(line);
		userDate = Calendar.getInstance(); // [���� ���� 6-11] ����
		userDate.set(s.nextInt(), s.nextInt() - 1, s.nextInt());
		// ������ ��(month) �� ������ ������ ��. ��� ���� �ݴ�� �ؾ� ��.
		userDate.set(Calendar.HOUR_OF_DAY, s.nextInt());
		userDate.set(Calendar.MINUTE, s.nextInt());
		userDate.set(Calendar.SECOND, s.nextInt());
		s.close(); // ���ڿ����� �о� ���̴� Scanner ����
	}

	String owner; // ����Ʈ�� ������ �̸�
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

// �� SmartPhone Ŭ������ ��� �޾� Phone, Calculator �������̽��� ��� �޼ҵ���� ������
// �Ʒ� GalaxyPhone�� �� �Ʒ� IPhone Ŭ������ ����ȸ�簡 �޶� ��� ���� ���͸�����

// ����� ��� �����ϰ� ���������� ������ ���� �ٸ��� ��
class GalaxyPhone extends SmartPhone {
	public GalaxyPhone(String owner) {
		super(owner);
	} // ����Ŭ���� ������ ȣ��

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

// �� SmartPhone Ŭ������ ��� �޾� Phone, Calculator �������̽��� ��� �޼ҵ���� ������
// �� GalaxyPhone�� �Ʒ� IPhone Ŭ������ ����ȸ�簡 �޶� ��� ���� �������̽��� ��� �޼ҵ����
// ����� ��� �����ϰ� ���������� ������ ���� �ٸ��� ��
class IPhone extends SmartPhone {
	String model;

	public IPhone(String owner, String model) {
		super(owner);
		this.model = model;
	} // ����Ŭ���� ������ ȣ�� �� model �ʱ�ȭ

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

	protected String name; // ��� �̸�
	protected int id; // Identifier
	protected double weight; // ü��
	protected SmartPhone smartPhone; // ����Ʈ��

	@Override
	public int compareTo(Person p) {
		int res = this.name.compareTo(p.name);
		return res;
	}

	public void set(String name, int id, double weight) {
		// ������ ��� �ʱ� �� ����;
		this.name = name;
		this.id = id;
		this.weight = weight;

		setSmartPhone((id % 2) == 1 ? new GalaxyPhone(name) : new IPhone(name, "13"));
	}

	public Person(Scanner s) {
		this(s.next(), s.nextInt(), s.nextDouble());
	}

	public Person(String name, int id, double weight) {
		// �� set() �޼ҵ� Ȱ���Ͽ� �ʱ� �� ����
		this.set(name, id, weight);
		// "Person(..." ���
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
	private String department; // �а�
	private int year; // �г�
	private double GPA; // �������

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

	@Override // �θ� Ŭ������ Person�� whatAreYouDoing() �޼ҵ带 �������̵���
	public void whatAreYouDoing() {
		study();
		System.out.print(", ");
		takeClass();
		System.out.println();
	}

	// ���� �߰��� �޼ҵ�
	public void study() {
		System.out.print(super.getName() + " is studying as a " + year + "-year student in " + department);
	}

	public void takeClass() {
		System.out.print("\n" + super.getName() + " took several courses and got GPA " + GPA);
	}

	// @Override // Person�� print()
	/*
	 * public void print() { super.print(); System.out.print(",\tD:" + department +
	 * ", Y:" + year + ", GPA:" + GPA); }
	 */
	@Override
	public String toString() {
		return super.toString() + ",\tD:" + this.department + ", Y:" + this.year + ", GPA:" + this.GPA;
	}

	@Override // Person�� update()
	public void update(Scanner s) {
		super.update(s);
		setStudent(s.next(), s.nextInt(), s.nextDouble());
	}
}

class Worker extends Person {
	private String company; // ȸ���
	private String position; // ����

	public void set(String company, String position) {
		this.company = company;
		this.position = position;
	}

	// ���� �߰��� �޼ҵ�
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
	protected boolean married; // ��ȥ����
	protected String career[]; // �˹ٰ��
	protected String address; // ���ٹ����ּ�

	public void setCareer(String sCareer) {
		// ","�� ���е� �ϳ��� ���ڿ� ��,
		// "CU KangNam,Seven Eleven,GS Convenient Store Suwon"�� �־���
		// ��� ����Ʈ(sCareer)�� ��ū���� �ɰ��� ���ڿ� �迭 career[]�� ����
		// String�� split()�� ����ص� ������ ���⼭�� StringTokenizer�� �̿��� ��
		StringTokenizer st = new StringTokenizer(sCareer, ",");
		// StringTokenizer�� �̿��� sCareer�� "," �����ڸ� �̿��� ��ũ��� �ڸ���,
				career = new String[st.countTokens()];
		for (int i = 0; i < career.length; i++)
			career[i] = st.nextToken();
		// ��ū�� ������ŭ String[] �迭�� �Ҵ��Ͽ� career�� �����ϰ�,
		// for���� �̿��Ͽ� �߶��� �� ��ū�� ��� �� career[i]�� ����
	}

	public void setAddress(String sAddress) {
		// �ϳ��� ���ڿ� "Gwangju city BongsunDong 12 BeonJi"�� �־��� sAddress��
		// �Ʒ� pseudocodeó�� ��� ������ ����/�߰�/������ �� �� ��� address�� ����
		String ���� = "BeonGil", ���� = "BeonJi", ���� = "NamGu";
		StringBuffer sb = new StringBuffer(sAddress);
		// StringBuffer�� �޼ҵ带 �̿��Ͽ� ������ ��, �� indexOf(), replace(),
		// sb.append(), delete(), insert(), toString() Ȱ��

		if (sb.indexOf(����) >= 0)
			sb.replace(sb.indexOf(����), sb.indexOf(����) + ����.length(), ����);
		else if (sb.indexOf(����) < 0)
			sb.append(����);
		if (sb.indexOf("-") >= 0)
			sb.deleteCharAt(sb.indexOf("-"));
		if (sb.indexOf(����) < 0)
			if (sb.indexOf("city") >= 0)
				sb.insert(sb.indexOf("city") + "city".length(), " " + ����);
			else if (sb.indexOf("city") < 0)
				sb.insert(0, ���� + " ");
		address = sb.toString();
		// 1) �ּ� �߿� "BeonJi"�� ������ "BeonGil"�� ����,
		// ��) NamGu 24 BeonJi -> NamGu 24 BeonGil
		// 2) �ּ� �߿� ������ ���� ������ �ּ� ���� "BeonGil" ����,
		// ��) SeseokDong 24 -> SeseokDong 24 BeonGil
		// 3) 1-24 ���� �߰��� "-"�� ������ "-"�� ����,
		// ��) 1-24 -> 124 BeonGil, 1-24 BeonJi -> 124 BeonGil
		// 4) �� �̸��� "NamGu"�� �ּҿ� ������ "city" ������ " "�� ������ �� �� �ڿ� "NamGu"�� ������ ��,
		// ��) Gwangju city BongsunDong 12 BeonJi ->
		// Gwangju city NamGu BongsunDong 12 BeonGil
		// ���� "NamGu"�� ���� "city"�� ���ٸ� �Ǿտ� "NamGu"+" "�� ����,
		// ��) JinwolDong 12-2 BeonGil -> NamGu JinwolDong 122 BeonGil
		// ���������� sb�� �ִ� ���ڿ��� ���� [StringBuffer�� toString() �޼ҵ� Ȱ��] address ����� ����
	}

	public void set(String sMarried, String sCareer, String sAddress) { // Overloading
		// ���ڿ� �Ű����� sMarried�� Boolean ������ ��ȯ�� �� ��� married�� ���� [���� ���� 6-5 ����]
		this.married = Boolean.parseBoolean(sMarried);
		// �� setCareer()�� setAddress() ȣ���Ͽ� ��°� �ּҼ���;
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
		// ������ �ش� ����� ���ڿ� sYear, sGPA, sMarried�� ��ȯ�� �� [���� ���� 6-5 ����]
		String sb[] = super.toString().split(",");
		String sYear = sb[5].trim().split(":")[1];
		String sGPA = sb[6].trim().split(":")[1];
		String sMarried = Boolean.toString(married);

		printStudWork(this.name, sYear, sGPA, sMarried);
		// �ݵ�� �� printStudWork(name, sYear, sGPA, sMarried)�� ȣ���Ͽ� ������ ��
	}
}

interface Factory<T> 
{
	 T newPerson(Scanner scanner);
	// 5�� �޴��׸� �Է� �� "Soon" �ڿ� ([����] �Ǵ� ' ' �Ǵ� '\t')�� �ְ� 
	    // �� �ڿ� "��� ��������[����]" �ԷµǾ��� �� �߰��� �ִ� ([����] �Ǵ� ' ' �Ǵ� '\t')��
	    // �����ϰ� "��� ��������"�� �о� ���� �޼ҵ�
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
	// private int count; // persons[] �迭�� ������ ����� ������� ��
	 private Factory<T> factory;

	PersonManager(T array[], Scanner scanner, Factory<T> factory) {
		  this.scanner = scanner;
		this.factory =factory; 
		 
		 
		  
		// MAX_PERSONS���� ���Ҹ� ���� persons �迭�� ����
		// persons = new Person[MAX_PERSONS];
		// array�� ��� ���Ҹ� persons���� ����
		  list = new LinkedList<T>();
		//vector = new Vector<Person>();
		for (int i = 0; i < array.length; i++)
			list.add(i,array[i]);
	
		// persons[i] = array[i];
		// scanner, count ��� �ʱ�ȭ
		
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
	} // �޴��׸�: FindPerson(equals()�̿��� ��� ã��)

	public void displayPhone() {
		for (var p:list)
			if (p != null)
				System.out.println(p.getName() + ":\t" + p.getPhone());
	} // �޴��׸�: DispAllPhone(���������)

	public void changePhone() {
		if (SmartPhone.isNotQuested) { // changePhone()�� ó�� �����ߴٸ� �ѹ��� �Ʒ� ���� ����
			scanner.nextLine(); // Menu item number? 12[����]: ���� ���� [����]�� skip
			// ex: �� �� �� �� �� �� ������ �Է�
			System.out.print("Date and time to set(ex: 2021 10 1 18 24 30)? ");
			String line = scanner.nextLine();
			if (!line.equals("")) // [�� �� �� �� �� ��]�� �Է��� ���
				SmartPhone.setDate(line);
			// ��¥�� �Է����� �ʰ� �׳� ���͸� ģ ���(line.equals("")) �ƹ� �͵� �������� ����
			SmartPhone.isNotQuested = false;
			// �ѹ��� ����ڿ��� �����Ͽ� �� ������ �����ϰ� �������ʹ� �� ���� �����ϵ��� �ϱ� ����
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
	} // �޴��׸�: ChangePhone

	private Random rnd = null;

// Math.random() ��� rnd.random()�� ����� ��
	public void changeWeight() { // �޴��׸�: ChangeWeight(�ڵ�ü�ߺ���)
		if (rnd == null) {
			System.out.print("Seed integer for random number generator? ");
			rnd = new Random(scanner.nextInt());
		}
		// ���⿡ �ڵ� �߰�
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
	} // �޴��׸�: DispAllAlba(���˹ٻ��麸��)

	public void sort() {
		// vector.iterator();

		Collections.sort(list);
		display();

	} // �޴��׸�: Sort(����)

	public void reverse() {
		Collections.reverse(list);
		display();
	} // �޴��׸�: Reverse(������ġ)

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

	} // �޴��׸�: BinarySearch(�����˻�)

	private final int ���� = 0, ��κ��� = 1, �˻� = 2, ���� = 3, ���� = 4, ���� = 5, �߰� = 6, ���ϴ� = 7, ��ȭ = 8, ��� = 9, ���ã�� = 10,
			��������� = 11, ������ = 12, �ڵ�ü�ߺ��� = 13, �˹ٻ��麸�� = 14, ���� = 15, ������ġ = 16, �����˻� = 17;

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
			case ��κ���:
				display();
				break;
			case �˻�:
				search();
				break;
			case ����:
				update();
				break;
			case ����:
				delete();
				
				break;
			case ����:
				insert();
				break;
			case �߰�:
				append();
				break;
			case ���ϴ�:
				whatDoing();
				break;
			case ��ȭ:
				call();
				break;
			case ���:
				calculate();
				break;
			case ���ã��:
				findPerson();
				break;
			case ���������:
				displayPhone();
				break;
			case ������:
				changePhone();
				break;
			case �ڵ�ü�ߺ���:
				changeWeight();
				break;
			case �˹ٻ��麸��:
				displayStudWorks();
				break;
			case ����:
				sort();
				break;
			case ������ġ:
				reverse();
				break;
			case �����˻�:
				binarySearch();
				break;
			case ����:
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
    // ��ĳ�ʸ� ���� ����ڰ� �Է��� ��� �����ڿ� ��� ������ �Է� ���� �� 
    // �����ڿ� ���� ����ó�� Student, Worker, StudWork ��ü�� ������ �� ��ȯ��
    @Override
    public Person newPerson(Scanner s) { 
       // �� [���� 2-4]�� getNewPerson() �޼ҵ� ���� �ּ� ó���� ���� �ڵ带 [����]�� ��
        //����� [�̵�]��Ű�� �ּ��� �����Ѵ�.
    	
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
    // ��ĳ�ʷκ��� ���׸� Ÿ�� T���� ��� ������ �Է� �޾� T���� ���ο� ��� ��ü�� �����Ͽ� ��ȯ�� 
    T newPerson(Scanner scanner);
    // �� �޼ҵ�� ���� Factory<T>�� implements�ϴ� ���� Ŭ�������� ���� �����ؾ� �Ѵ�.
}
 
 */

class StudentFactory implements Factory<Student> {
    // ��ĳ�ʸ� ���� ����ڰ� ������ Student ������ �Է� ���� �� Student ��ü�� �����Ͽ� ��ȯ��
    @Override
    public Student newPerson(Scanner s) { return new Student(s); }
}

// �� �� Ŭ������ �����Ͽ� �Ʒ� �� Ŭ������ �ϼ��϶�.

class WorkerFactory implements Factory<Worker> { 
	 @Override
	    public Worker newPerson(Scanner s) { return new Worker(s); }
    // ��ĳ�ʸ� ���� ����ڰ� ������ Worker ������ �Է� ���� �� Worker ��ü�� �����Ͽ� ��ȯ��
}

class StudWorkFactory implements Factory< StudWork> { 
	 @Override
	
	    public  StudWork newPerson(Scanner s) { return new StudWork(Factory.getNextLine(s));}//{s.nextLine(); return new  StudWork(s.nextLine()); }
    // ��ĳ�ʸ� ���� ����ڰ� ������ StudWork ������ �Է� ���� �� StudWork ��ü�� �����Ͽ� ��ȯ��
}

public class Main {

	static void StringSpeed(String s) {
		System.out.println("String Speed");
		for (int i = 0; i < 20; i++) {
			String last = s.substring(s.length() - 1, s.length()); // ������ ���� ����
			String sub = s.substring(0, s.length() - 1); // ó������ ������ ���� �ձ��� ����
			s = sub + last; // ����� ������ ���ڸ� �� �ڿ� �ٽ� �߰��Ͽ� ���ο� ���ڿ��� ����
			System.out.print(i + " ");
		}
		System.out.println("\n");
	}

	static void StringBufferSpeed(StringBuffer sb) {
		System.out.println("StringBuffer Speed");
		for (int i = 0; i < 20; i++) {
			String last = sb.substring(sb.length() - 1, sb.length());// ������ ���� ����
			sb.delete(sb.length() - 1, sb.length()); // ������ ���� ����
			sb.append(last); // ����� ������ ���ڸ� �ٽ� �� �ڿ� �߰�
			System.out.print(i + " ");
		}
		System.out.println("\n");
	}

	static void SpeedTest() {
		String s = "This book is a ��ǰ Java Programming.";
		// s ���ڿ��� �ݺ������� ��� �ι�� Ű�� �ſ� �� ���ڿ�(�� 300MB)�� �����.
		// �Ʒ� for���� 23 ��� 15, 20 ������ �����ؼ� ª�� ���ڿ��� ����� �ӵ��� ���� ����.

		// ���� �ڹٽý����� �⺻ �޸� ũ�Ⱑ �۰� �����Ǿ� ������
		// �Ʒ� for���� �޸� �������� ���� �� �ִ�.
		// �� ��� ���α׷��� ���� ���� ������ 23�� ��� �ϳ��� �ٿ��� �׽�Ʈ�ض�.
		// ���� �ʴ� ������ �� ���ڸ� 23 ��� ����϶�.
		for (int i = 0; i < 21; i++) {
			s += s;
			System.out.print(i + " ");
		}
		System.out.println("\ns length is " + s.length() / (1024 * 1024) + " MB\n");
		StringSpeed(s); // �Ʒ� ����� ������ �ٲپ �ӵ� ���̴� ����
		StringBufferSpeed(new StringBuffer(s));
	}

	static void StringRotation(String s) {
		String book = "book", BOOK = "BOOK", ��ǰ = "Masterpiece ", Java = "Java";
		System.out.println("String Rotation: " + s);
		for (int i = 0; i < 20; i++) {
			if (s.indexOf(book) >= 0)
				s = s.replace(book, BOOK);
			else if (s.indexOf(BOOK) >= 0)
				s = s.replace(BOOK, book);
			// ���� s ���ڿ� ����
			// ���� ���� book�� ������ ������ �̸� ���� BOOK�� �������� ��ü�ϰ� // replace() �޼ҵ�
			// ���� book�� ���� ��ſ� ���� BOOK�� ������ ���� book���� ��ü�Ѵ�.
			// Ư�� ���ڿ��� s�� �ִ��� ����� s.indexOf(ã�����ڿ�����)�� ���� ����
			// ( >= 0)�� ã�� ���̰� ������ �� ã�� ����

			if (s.indexOf(��ǰ) >= 0) {
				String first = s.substring(0, s.indexOf(��ǰ));
				String last = s.substring(s.indexOf(��ǰ) + ��ǰ.length());
				s = first + last;
			} else {
				if (s.indexOf(Java) >= 0) {
					String f = s.substring(0, s.indexOf(Java));
					String l = s.substring(s.indexOf(Java), s.length());
					s = f + ��ǰ + l;
				}
			}
			// ���� ���� ��ǰ�� ������ ������ �̸� �����ϰ�
			// ���� ��ǰ�� ������ ������ ���� Java �ܾ� �տ� ���� ��ǰ�� ������ ���� �����϶�.
			// ������ ������ �ܾ� �տ� �ִ� ���ڿ��� �̾Ƴ���(substring()),
			// ���� ������ �ܾ� ��(������ �ܾ� ��ġ+�ܾ� ����)�� �ִ�
			// ���ڿ��� �̾Ƴ� �� �̾� �� �� ���ڿ��� ���� ���� ���ο� ���ڿ��� ����
			// ����: (Java �ܾ� ���� ���깮�ڿ�) + ��ǰ +
			// (Java �ܾ���� ������ ���깮�ڿ�)

			String first = s.substring(0, 1);
			String last = s.substring(1, s.length());
			s = last + first;

			// s�� ù ���ڸ� �� ���������� �ű� ���ο� ���ڿ��� ����� s�� ����
			// �� StringSpeed(String s) ����
			// s�� ù��° ���ڸ� �̾Ƴ� ������ �����ϰ�
			// ù��°�� ������ ������ ���ڿ� ���� �̾Ƴ� ������ �����϶�.
			// ������ ���ڿ� ������ ù ���� ������ ���ؼ� s�� �����ϸ� ��
			System.out.println(((i < 10) ? " " : "") + i + " " + s);
		}
		System.out.println();
	}

	static String StringBufferRotation(String s) {
		String book = "book", BOOK = "BOOK", ��ǰ = "Masterpiece ", Java = "Java";
		StringBuffer sb = new StringBuffer(s);
		System.out.println("StringBuffer Rotation: " + s);

		for (int i = 0; i < 20; i++) {
			if (sb.indexOf(book) >= 0)
				sb.replace(sb.indexOf(book), sb.indexOf(book) + book.length(), BOOK);
			else if (sb.indexOf(BOOK) >= 0)
				sb.replace(sb.indexOf(BOOK), sb.indexOf(BOOK) + BOOK.length(), book);

			if (sb.indexOf(��ǰ) >= 0)
				sb.delete(sb.indexOf(��ǰ), sb.indexOf(��ǰ) + ��ǰ.length());
			else if (sb.indexOf(Java) >= 0)
				sb.insert(sb.indexOf(Java), ��ǰ);
			// ���� sb ��Ʈ������ ����
			// ���� "book" ���ڿ��� ������ �̸� "BOOK"���� ��ü�ϰ�
			// "book"�� ���� ��ſ� "BOOK"�� ������ "book"���� ��ü�Ѵ�.
			// ���� "Masterpiece" ���ڿ��� ������ �̸� �����ϰ�
			// "Masterpiece"�� ������ "Java" �տ� "Masterpiece"�� ���� �����϶�.
			// indexOf(), replace(), delete(), insert()�޼ҵ� Ȱ��
			char f = sb.charAt(0);
			sb.delete(0, 1);
			sb.append(f);

			// s�� ù ���ڸ� �� ���������� �ű� ���ο� ���ڿ��� ����� s�� ����
			// sb�� ù��° ���� �̾Ƴ� ������ �����ϰ� ù��° ���ڸ� �����Ѵ�.
			// sb ���� ù ���ڸ� �߰��Ѵ�.
			// delete(), append() Ȱ��
			System.out.println(((i < 10) ? " " : "") + i + " " + sb);
		}
		System.out.println();
		// sb�� ����� ���ڿ��� ���� ��ȯ // toString() Ȱ��
		return sb.toString();
	}

	static void StringAndStringBufferTest(Scanner scanner) {
		String s0 = "This book is a Masterpiece Java Programming.";
		System.out.println("If just [enter], automatic input: " + s0);
		System.out.println("Simple sentence including 3 words(book, Masterpiece, Java)? ");
		String s = scanner.nextLine();
		System.out.println();

		if (!s.equals("")) // �׳� ���͸� ġ�� �ʰ� ������ �Է��� �� ����ģ ���
			s0 = s; // ����ڰ� �Է��� ���忭�� ���� s0 ��ü
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
		p2.set(12); // ID ����
		compares("p1 and p2 are ", p1, p2);
		System.out.println();
		compares("p1 and sp2 are ", p1, sp2);
		// compares("sp2�� p1�� ", sp2, p1); // �ּ��� Ǯ�� ���α׷��� �״� ������?
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
		// �����ô� �����ڿ� �ǿ����ڰ� [�ϳ��� ' '�θ�] �и��Ǿ� �־�� ��
		gc.calculate("1 + 2");
		gc.calculate("1 - 2");
		gc.calculate("1 * 2");
		gc.calculate("1 / 2");
		gc.calculate("1 | 2");
		// gc.calculate("1+2"); // �����ڿ� �ǿ����ڰ� " "�� �и��Ǿ� ���� �ʾ� �ּ��� Ǯ�� ���α׷��� �״´�.
		// gc.calculate("1 + 2"); // �����ڿ� �ǿ����ڰ� �� ���� ' '�� �и��Ǿ� �־� �ּ��� Ǯ�� ���α׷��� �״´�.
		// �� ���� �����̽��� �и��� "1 +"�� String::split()�� ���� "1", "", "+"�� �и��Ǳ� �����̴�.

		// IPhone�� ��� �����ڿ� �ǿ����ڰ� ' '�� �и����� �ʾƵ� ó���� �� ����
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
            // �߿�: StudWork�� �⺻����������("Kang 22 90.1 Computer 3 3.5") ��� 
            //      ���ڿ� ���� �ʵ屸���� ���� �ϳ��� �����̽������� �� �ʵ带 �����ؾ� ��
            new StudWork("Hong 10 64 Computer 2 3.5:true:CU KangNam,Seven Eleven,GSConvenientStore Suwon:Gwangju city BongSunDong 12 BeonJi"),
            new StudWork("Chung 11 46.1 Physics 1 3.8:true:Family Mart,7 11,GS BookGu:Gwangju city NamGu 12-2"),
            new StudWork("Soon 12 88.5 Electronics 4 2.5:false:Seven Eleven:12-3 BeonGil"),
        };
       // var studWorkMng = new PersonManager<StudWork>(studWorks, scanner);
        var studWorkFact = new StudWorkFactory();
        var studWorkMng = new PersonManager<StudWork>(studWorks, scanner, studWorkFact);


        Person persons[] = {
            // �߿�: StudWork�� �⺻����������("Kang 22 90.1 Computer 3 3.5") ��� 
            //      ���ڿ� ���� �ʵ屸���� ���� �ϳ��� �����̽������� �� �ʵ带 �����ؾ� ��
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

// �� �ڵ忡�� �迭 students[]�� ���� Student ��ü �迭�� �����Ͽ� �����ϰ� �ִ�.
// �׷� �� �뷮�� [Student ��ü��]�� �����ϰ� ����, �˻�, ����, ���� ���� �� �� �ִ� 
// PersonManager<Student>�� ��ü studMng�� �����Ѵ�. 
//      var studMng = new PersonManager<Student>(students, scanner);

// �� main() �Լ������� studMng, workMng, studWorkMng, personMng �� ����
// �� ���� PersonManager ��ü�� �����Ǿ���.
// �̵��� ���� Student, Worker, StudWork, Person ��ü���� �������� �����ϴ� ������ ��ü�̴�.
// �������, studMng �����ڴ´� Student ��ü�� ����, ����, ����, ���� ���� �� �� �ְ� 
// �ٸ� Ŭ������ ��ü�� ������ �� ����.
}