package entities;

public class Staff{
    private Integer id;
    private String fullName;
    private Boolean gender;
    private String birthday;
    private String email;
    private String phone;
    private String salary;
    private String departId;
    private String notes;
    private String image;
	private String expectOutput;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getExpectOutput() {
		return expectOutput;
	}

	public void setExpectOutput(String expectOutput) {
		this.expectOutput = expectOutput;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "id=" + id + ", fullName=" + fullName + ", gender=" + gender + ", birthday=" + birthday
				+ ", email=" + email + ", phone=" + phone + ", salary=" + salary + ", departId="
				+ departId + ", notes=" + notes + ", image=" + image;
	}

}
