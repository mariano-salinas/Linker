package linker;

import java.util.ArrayList;

public class Module {
	private int base;
	private ArrayList<Definition> definitions;
	private ArrayList<Use> uses;
	private ArrayList<Instruction> instructions;
	
	public Module(){
		definitions = new ArrayList<Definition>();
		uses = new ArrayList<Use>();
		instructions = new ArrayList<Instruction>();
	}
	
	public ArrayList<Definition> getDefinitions() {
		return definitions;
	}

	public ArrayList<Use> getUses() {
		return uses;
	}

	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}

	public void addDefinition(Definition definition) {
		this.definitions.add(definition);
	}

	public void addUse(Use use) {
		this.uses.add(use);
	}

	public void addInstruction(Instruction instruction) {
		this.instructions.add(instruction);
	}
	
	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}

	public int getNumInstructions(){
		return this.instructions.size();
	}
}
