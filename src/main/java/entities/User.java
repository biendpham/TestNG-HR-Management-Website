package entities;

public class User {

	private String username;
	private String password;
	private String expectOutput;
	
	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "username=" + username + ", password=" + password;
	}

	public String getExpectOutput() {
		return expectOutput;
	}

	public void setExpectOutput(String expectOutput) {
		this.expectOutput = expectOutput;
	}

	
}
