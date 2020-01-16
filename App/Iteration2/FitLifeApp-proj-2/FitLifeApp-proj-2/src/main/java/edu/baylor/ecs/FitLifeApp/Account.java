package edu.baylor.ecs.FitLifeApp;

public class Account {
	private int age;
	private String email;
	private static int count = 0;
	private int id;
	private String name;
	private String password;
	private String username;
	private int weight;
	
	public Account(int age, String email, String name, String password, String username, int weight){
		setId(Account.count++);
		setAge(age);
		setEmail(email);
		setName(name);
		setPassword(password);
		setUsername(username);
		setWeight(weight);
	}

	public int getAge() {
		return age;
	}

	private void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	private void setUsername(String username) {
		this.username = username;
	}

	public int getWeight() {
		return weight;
	}

	private void setWeight(int weight) {
		this.weight = weight;
	}
	
	
	
}
