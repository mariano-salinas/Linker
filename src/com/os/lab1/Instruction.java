package linker;

public class Instruction {
	private Integer instruction;
	private Integer address;
	private Integer opcode;
	private Component type;
	
	public Instruction(String fullInstruction){
		parseInstruction(fullInstruction);
		this.type = parseType(fullInstruction);
	}
	
	private void parseInstruction(String fullInstruction){
		int length = fullInstruction.length();
		this.instruction = Integer.parseInt(fullInstruction.substring(0, length-1));
		this.address = Integer.parseInt(fullInstruction.substring(1, length-1));
		this.opcode = Character.getNumericValue(fullInstruction.charAt(0));
		
	}
	
	private Component parseType(String fullInstruction){
		int length = fullInstruction.length();
		int component = Character.getNumericValue(fullInstruction.charAt(length-1));
		
		Component type;
		if (component == 1){
			type = Component.IMMEDIATE;
		} else if (component == 2){
			type = Component.ABSOLUTE;
		} else if (component == 3){
			type = Component.RELATIVE;
		} else {
			type = Component.EXTERNAL;
		}
		
		return type;
	}
	public Integer getInstruction(){
		return opcode*1000 + address;
	}
	public Component getType(){
		return this.type;
	}
	public Integer getAddress() {
		return address;
	}
	public void setAddress(Integer address) {
		this.address = address;
	}
	
}
