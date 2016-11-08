package linker;

public class Location {
	private int module;
	private int moduleIndex;
	private int location;
	
	public Location(int module, int moduleIndex, int location){
		this.module = module;
		this.moduleIndex = moduleIndex;
		this.location = location;
	}

	public int getModuleIndex() {
		return moduleIndex;
	}
	
	public int getModule() {
		return module;
	}

	public int getLocation() {
		return location;
	}
	
	

}
