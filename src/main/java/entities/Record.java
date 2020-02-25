package entities;


public class Record {
    private Integer id;
    private Boolean type;
    private String reason;
    private String date;
	private String expectOutput;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExpectOutput() {
		return expectOutput;
	}

	public void setExpectOutput(String expectOutput) {
		this.expectOutput = expectOutput;
	}

}
