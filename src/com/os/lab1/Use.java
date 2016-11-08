package linker;

public class Use {
	private String use;
	private Integer position;
	private boolean used;
	
	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public Use(String use, Integer position){
		this.use = use;
		this.position = position;
	}
	
	public String getUse() {
		return use;
	}

	public Integer getPosition() {
		return position;
	}

}
