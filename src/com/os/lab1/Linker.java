package linker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Linker {
	
	public static void main(String[] args) {
		String fileName = args[0];
		Scanner input = createScanner(fileName);
		
		ArrayList<Module> modules = new ArrayList<Module>();
		HashMap<String, Location> symbolTable = new HashMap<String, Location>();
		
		System.out.println("Symbol table");
		firstPass(input, symbolTable, modules);
		
		System.out.println("Memory Map");
		secondPass(modules, symbolTable);		
	}
	
	public static Scanner createScanner(String fileName){
		BufferedReader br;
		Scanner input = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			input = new Scanner(br);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return input;
		
	}
	
	public static void firstPass(Scanner input, HashMap<String, Location> symbolTable, ArrayList<Module> modules){
		int numModules = input.nextInt();
		int base = 0;
		String error = "";

		for (int i = 0; i < numModules; i++){
			Module currModule = new Module();

			int numDefinitions = input.nextInt();
			for (int j = 0; j < numDefinitions; j++){
				String symbol = input.next();
				int location = input.nextInt();
				Location currLocation = new Location(i, j, location + base);
				if (!symbolTable.containsKey(symbol)){
					Definition currDefinition = new Definition(symbol, currLocation.getLocation());
					symbolTable.put(symbol, currLocation);
					currModule.addDefinition(currDefinition);
		            System.out.println(currDefinition);
				} else {
					error = "Error: " + symbol + " is multiply defined; first value used.";
		            System.out.println(error);
				}

			}
			
			int numUses = input.nextInt();
			for (int j = 0; j < numUses; j++){
				String symbol = input.next();
				currModule.addUse(new Use(symbol, j));
			}
			
			int numInstructions = input.nextInt();
			for (int j = 0; j < numInstructions; j++){
				String instruction = input.next();
				currModule.addInstruction(new Instruction(instruction));
			}
			
			currModule.setBase(base);
			modules.add(currModule);
			
			base += currModule.getNumInstructions(); 
		}
	}
	
	public static void secondPass(ArrayList<Module> modules, HashMap<String, Location> symbolTable){
		int instructionIndex = 0;
		ArrayList<String> warnings = new ArrayList<String>();
		
		for (int i = 0; i < modules.size(); i++){
			Module currModule = modules.get(i);

			for (int j = 0; j < currModule.getNumInstructions(); j++){
				String error = "";
				Instruction currInstruction = currModule.getInstructions().get(j);
				
				if (currInstruction.getType() == Component.ABSOLUTE){
					if (currInstruction.getAddress() > 600){
						error = "Error:\t Absolute address exceeds machine size; zero used.";
						currInstruction.setAddress(0);
					}
				}

				if (currInstruction.getType() == Component.RELATIVE){
					if (currInstruction.getAddress() > currModule.getNumInstructions()){
						error = "Error:\t Relative address exceeds module size; zero used.";
					}
					convertRelativeToAbsolute(currModule, currInstruction);

				} else if (currInstruction.getType() == Component.EXTERNAL){
					int index = Integer.parseInt(Integer.toString(currInstruction.getInstruction()).substring(1));
					if (index > currModule.getUses().size()){
						error = "Error:\t External address exceeds length of use list; treated as immediate.";
					} else {
						String key = currModule.getUses().get(index).getUse();
						if (symbolTable.containsKey(key)){
							currModule.getUses().get(index).setUsed(true);
							Location location = symbolTable.get(key);
							modules.get(location.getModule()).getDefinitions().get(location.getModuleIndex()).setUsed(true);
							currInstruction.setAddress(currInstruction.getAddress()- currInstruction.getAddress()%10 + location.getLocation());
						} else {
							error = "Error:\t" + key + " is not defined; zero used.";
							currInstruction.setAddress(0);
						}
					}
					
				}
				
				System.out.println(instructionIndex + ":\t" + currInstruction.getInstruction() + "\t" + error);
				instructionIndex++;
			}
			
			for (int p = 0; p < currModule.getUses().size(); p++){
				if (!currModule.getUses().get(p).isUsed()){
					String warning = "Warning:\t In module " + i + " " + currModule.getUses().get(p).getUse() + " is on use list but isn't used.";
					warnings.add(warning);
				}
			}
			
			if (i == modules.size()-1){
				checkLastModule(modules);
			}
		}
		
		for (int s = 0; s < warnings.size(); s++){
			System.out.println(warnings.get(s));
		}
	}
	
	public static void checkLastModule(ArrayList<Module> modules){
		for (int i = 0; i < modules.size(); i++){
			Module currModule = modules.get(i);
			for (int j = 0; j < currModule.getDefinitions().size(); j++){
				Definition currDefinition = currModule.getDefinitions().get(j);
				if (!currDefinition.isUsed()){
					System.out.println("Warning:\t" + currDefinition.getSymbol() +  " was defined in module " + i + " but not used");
				}
			}
		}
		
	}
	
	public static void convertRelativeToAbsolute(Module currModule, Instruction currInstruction){
		int absoluteAddress = currInstruction.getAddress() + currModule.getBase();
		currInstruction.setAddress(absoluteAddress);
	}
}
