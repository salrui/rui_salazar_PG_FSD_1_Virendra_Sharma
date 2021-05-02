package locker.me;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
	
	static class Node {
		String name;
		Node next;
	}
	
	static class NodeList {
		
		Node head;
		
		
		//node to represent all the files on the folder
		public void addNode(String name) {
			
			Node newNode = new Node();
			
			newNode.name = name;
			newNode.next = null;
			
			if(head == null) {
				head = newNode;
			}
			else {
				Node last = head;
				
				while(last.next != null) {
					last = last.next;
				}
				
				last.next = newNode;
			}			
		}
		
		//search for a node
		public boolean findNode(String name) {
						
			Node nodeList = head;
			boolean fileExist = false;
			boolean nextFile = !(nodeList == null);
						
			while(!fileExist && nextFile) {
				
				fileExist = (nodeList.name.equals(name));
				nextFile  = !(nodeList.next == null);
				
				nodeList = nodeList.next;				
			}			
			return fileExist;
		}
		
		//delete an exist node 
		public void removeNode(String name) {
			
			int depth = 0;
			Node node = head;
			Node previous = head;
						
			
			while(!node.name.equals(name) && node.next != null ) {
				node = node.next;
				depth++;			
			}

			if(node.name.equals(name)) {
			
				for(int i=0; i < depth-1; i++)
					previous = previous.next;

				if(previous == node) {
					head = previous.next;
					previous = null;
					node = null;				
				}
				else {
					previous.next = node.next;
					node = null;
				}									
			}
		}
		
		public String[] toArray() {			
			Node myNode = head;
			int nodeLength = 0;
			
			while(myNode != null) {
				nodeLength++;				
				myNode = myNode.next;
			}
			
			String[] names = new String[nodeLength];
			myNode = head;
			nodeLength = 0;
			
			while(myNode != null) {				
				names[nodeLength] = myNode.name;
				nodeLength++;				
				myNode = myNode.next;
			}			
			
			return names;			
		}
		
		public void displayNode() {
			
			Node node = head;
			
			if(node != null ) {
				System.out.print( node.name + " --> ");
				
				while(node.next != null) {
					node = node.next;
					System.out.print( node.name + " --> ");				
				}
			}
			System.out.println("");
		}
	}
	
	
	
	// **************** main class **************** // 
	private static String root = "c:\\locker.me";
	
	public static void loadFolder(NodeList list) {
		File dir = new File(root);
		
		for(File file:dir.listFiles()) {
			list.addNode(file.getName());
		}		
	}
	
	
	public static void listSortedFiles(NodeList list) {
		System.out.println("list all Filed:");
			
		//create an array with file names  
		String[] fileNames = list.toArray();
				
		//bubble sort the array
		int fileLength = fileNames.length;
		for(int i =0; i < fileLength; i++) {
			
			
			for(int j=0; j < fileLength -i -1; j++) {
						
				if(fileNames[j].compareTo(fileNames[j+1]) > 0) {
					String tempName = fileNames[j];
					fileNames[j] = fileNames[j+1];
					fileNames[j+1] = tempName;
				}			
			}
		}
		
		
		//display new sorted 
		for(String name: fileNames)
			System.out.println("+->" + name);
		
		System.out.println("");
	}
	
	//create a new file 
	public static void createFile(NodeList list) throws IOException {
		System.out.println("insert the file name");
		String name = insertString();
		
		//check that file exist
		if(!list.findNode(name)) {
		
			//add new node to the list
			list.addNode(name);
				
			//create physical file
			File newFile = new File(root + File.separator + name ); 
			newFile.createNewFile();
			
			System.out.println("file created");
		}
	}
	
	public static void deleteFile(NodeList list) throws IOException {
		System.out.println("delete the file name");
		String name = insertString();		
		
		//check that file exist
		if(list.findNode(name)) {
			
			//remove node from the list 
			list.removeNode(name);
		
			//delete physical file 
			File delFile = new File(root + File.separator + name );
			delFile.delete();

			System.out.println("file deleted");
		}
	}
	
	public static void searchFile(NodeList list) throws IOException {
		System.out.println("search file name");
		String name = insertString();	
		
		if(list.findNode(name)) {
			System.out.println("file exist");
		} 
		else {
			System.out.println("file don't exist");
		}
	}
	
	
	public static void menu() {
		
		String message = "*****************************************************************************\n";
		message += "******************************** LOCKER ME **********************************\n";
		message += "*****************************************************************************\n";		
		message += "* \n";		
		message += "* Select one option: \n";
		message += "* \n";
		message += "* 1. List sorted Files \n";
		message += "* 2. Create File \n";
		message += "* 3. Delete File \n";
		message += "* 4. Search File \n";	
		message += "* 5. Exit Application \n";
		message += "*****************************************************************************\n";	
		System.out.println(message);
	}
	
	public static int userOption() {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		int option = 0;
		
	
		try {
			option = Integer.parseInt(reader.readLine());
		} catch (NumberFormatException e) {
			option = 6;
		} catch (IOException e) {
			option = 7;
		}
		
		return option;
	}
	
	
	public static String insertString() throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				
		return reader.readLine();
	}
	

	public static void optionAction(int option, NodeList list) throws IOException {
		
		switch(option) {
			
			case 1:
				listSortedFiles(list);  //done
				break;
				
			case 2:
				createFile(list); // in progress
				break;
				
			case 3:
				deleteFile(list); // pending 
				break;
				
			case 4:
				searchFile(list); //pending
				break;					
		}		
	}
	
	
	//main function 
	public static void main(String[] args) throws IOException {
		
		int option = 0;
		NodeList list = new NodeList();
		
		String message = "+---------------------------------------------------------------------------+\n";
		message += "|  Application works with the assumption that folder '" + root + "' exists! | \n";
		message += "+---------------------------------------------------------------------------+\n";
		System.out.println(message);
		
		loadFolder(list);		
		
		while(option != 5) {
			menu();
			
			option = userOption();
						
			if(option != 5) {
				optionAction(option, list);
			}						
		}
				
		System.out.println("Application close!");
	}
}
