package entities;

public class Depart{

	private Integer id;
    private String code;
    private String name;
    private String expectOutput;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getExpectOutput() {
		return expectOutput;
	}

	public void setExpectOutput(String expectOutput) {
		this.expectOutput = expectOutput;
	}

	@Override
	public String toString() {
		if (id!=null) {
			return "id=" + id +", code=" + code + ", name=" + name;
		}
		return "code=" + code + ", name=" + name;
	}

}
