//============================================================================
// Name        : Assignment 1
// Author      : Branden Lee
// Date        : 4/24/2018
// Description : Automobile class for the KBB website application
//============================================================================
package model;

import java.lang.ArrayIndexOutOfBoundsException;
import java.util.*;

import exception.AutoException;

public class Automobile implements java.io.Serializable {
	private static final long serialVersionUID = 1362422403381823640L;
	private String makeName, modelName, packageName, typeName, year;
	private int key; // make-model-year
	private double basePrice; // double is not an exact decimal.
	ArrayList<OptionSet> optionSetList;
	ArrayList<Integer> optionSetOptionSelect;

	/*
	 * Constructor
	 */
	public Automobile() {
		init();
		optionSetList = new ArrayList<OptionSet>();
		optionSetOptionSelect = new ArrayList<Integer>();
	}

	public Automobile(int size) {
		init();
		optionSetList = new ArrayList<OptionSet>(20);
		optionSetOptionSelect = new ArrayList<Integer>(20);
	}

	public void init() {
		makeName = "";
		modelName = "";
		packageName = "";
		typeName = "";
		year = "";
		basePrice = 0;
	}

	/*
	 * Getter
	 */
	// Get Name of Automotive
	public String getMake() {
		return makeName;
	}

	public String getModel() {
		return modelName;
	}

	public String getYear() {
		return year;
	}

	// Get Automotive Base Price
	public double getPrice() {
		return basePrice;
	}

	// Get OptionSet (by index value)
	public OptionSet getOptionSet(int index) {
		OptionSet optionSetObject = null;
		try {
			optionSetObject = optionSetList.get(index);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return optionSetObject;
	}

	public int length() {
		return optionSetList.size();
	}

	public OptionSet.Option getOptionSetOptionChoice(String optionSetName) {
		OptionSet.Option returnValue = null;
		int optionSetIndex;
		try {
			optionSetIndex = findOptionSetIndex(optionSetName);
			returnValue = getOptionSetOption(optionSetIndex);
		} catch (exception.AutoException e) {
			// nothing
		}
		return returnValue;
	}

	public OptionSet.Option getOptionSetOption(int optionSetIndex) throws AutoException {
		OptionSet.Option returnValue = null;
		int optionIndex;
		try {
			optionIndex = optionSetOptionSelect.get(optionSetIndex).intValue();
			returnValue = getOptionSet(optionSetIndex).getOption(optionIndex);
		} catch (NullPointerException e) {
			throw new exception.AutoException(102);
		}
		return returnValue;
	}

	/*
	 * Find
	 */
	// Find OptionSet with name
	public OptionSet findOptionSet(String name) {
		OptionSet optionSetObject = null;
		for (int i = 0; i < optionSetList.size(); i++) {
			if (optionSetList.get(i).getName() == name) {
				optionSetObject = optionSetList.get(i);
			}
		}
		return optionSetObject;
	}

	public OptionSet.Option findOptionSetOption(String optionSetName, String optionName) {
		OptionSet.Option optionObject = null;
		OptionSet optionSetObject = findOptionSet(optionSetName);
		if (optionSetObject != null) {
			optionObject = optionSetObject.findOptionSet(optionName);
		}
		return optionObject;
	}

	public int findOptionSetIndex(String name) throws AutoException {
		int i, n;
		n = optionSetList.size();
		for (i = 0; i < n; i++) {
			if (optionSetList.get(i).getName() == name) {
				break;
			}
		}
		if (i == n) {
			throw new exception.AutoException(102);
		}
		return i;
	}

	public int findOptionSetOptionIndex(String optionSetName, String optionName) {
		int returnIndex = -1;
		OptionSet optionSetObject = findOptionSet(optionSetName);
		if (optionSetObject != null) {
			returnIndex = optionSetObject.findOptionSetIndex(optionName);
		}
		return returnIndex;
	}

	/*
	 * add new optionSet
	 * 
	 * @param OptionSetName optionSet name
	 */
	public int addOptionSet(String OptionSetName) {
		optionSetList.add(new OptionSet(OptionSetName, 10));
		return length();
	}

	/*
	 * Add option by optionSet Index
	 * 
	 * @post option added
	 * 
	 * @param optionSetIndex optionSet index
	 * 
	 * @param optionName name of added option
	 * 
	 * @param optionPrice price of added option
	 */
	public int addOptionSetOption(int optionSetIndex, String optionName, double optionPrice) {
		int indexReturn = -1;
		OptionSet optionSetObject = getOptionSet(optionSetIndex);
		indexReturn = optionSetObject.addOption(optionName, optionPrice);
		// we set the
		if (optionSetObject.getName().equals("Make")) {
			setMake(optionName);
		} else if (optionSetObject.getName().equals("Model")) {
			setModel(optionName);
		} else if (optionSetObject.getName().equals("Year")) {
			setYear(optionName);
		} else if (optionSetObject.getName().equals("Retail Price")) {
			/*
			 * Price may be set by either Retail Price: 18445 or Retail Price: /18445
			 */
			if (optionName.equals("")) {
				setPrice(optionPrice);
			} else {
				setPrice(Double.parseDouble(optionName));
			}
		}
		return indexReturn;
	}

	/*
	 * Setter
	 */
	// SetName
	public void setMake(String name) {
		makeName = name;
	}

	public void setModel(String name) {
		modelName = name;
	}

	public void setYear(String name) {
		year = name;
	}

	// Set Base Price
	public void setPrice(double price_) {
		basePrice = price_;
	}

	public boolean setOptionSetName(String optionSetName, String nameNew) {
		boolean returnValue = false;
		OptionSet optionSetObject = findOptionSet(optionSetName);
		if (optionSetObject != null) {
			optionSetObject.setName(nameNew);
			returnValue = true;
		}
		return returnValue;
	}

	/*
	 * Set option set option name
	 */
	public boolean setOptionSetOptionName(String optionSetName, String optionName, String nameNew) {
		boolean returnValue = false;
		OptionSet.Option optionObject = findOptionSetOption(optionSetName, optionName);
		if (optionObject != null) {
			optionObject.setName(optionName);
			returnValue = true;
		}
		return returnValue;
	}

	public boolean setOptionSetOptionPrice(String optionSetName, String optionName, double priceNew) {
		boolean returnValue = false;
		OptionSet.Option optionObject = findOptionSetOption(optionSetName, optionName);
		if (optionObject != null) {
			optionObject.setPrice(priceNew);
			returnValue = true;
		}
		return returnValue;
	}

	public boolean setOptionSetOption(int optionSetIndex, int optionIndex) throws AutoException {
		boolean returnValue = false;
		try {
			optionSetOptionSelect.set(optionSetIndex, optionIndex);
		} catch (IndexOutOfBoundsException e) {
			throw new exception.AutoException(100);
		}
		return returnValue;
	}

	public boolean setOptionSetOptionChoice(String optionSetName, String optionName) {
		boolean returnValue = false;
		int optionSetIndex, optionIndex;
		try {
			optionSetIndex = findOptionSetIndex(optionSetName);
			optionIndex = findOptionSetOptionIndex(optionSetName, optionName);
			setOptionSetOption(optionSetIndex, optionIndex);
			returnValue = true;
		} catch (exception.AutoException e) {
			// nothing
		}
		return returnValue;
	}

	/*
	 * print() and toString()
	 */
	public void print() {
		System.out.print(toString());
	}

	public String toString() {
		StringBuffer stringBufferObject;
		int i, n;
		n = length();
		stringBufferObject = new StringBuffer("");
		stringBufferObject.append("Year, Make, Model: ").append(getYear()).append(", ").append(getMake()).append(", ")
				.append(getModel()).append(" and Base Price: $").append(getPrice());
		stringBufferObject.append(System.getProperty("line.separator"));
		for (i = 0; i < n; i++) {
			stringBufferObject.append(optionSetList.get(i).toString()).append(System.getProperty("line.separator"));
		}
		return stringBufferObject.toString();
	}

	protected class OptionSet implements java.io.Serializable {
		private static final long serialVersionUID = 5846223453457830887L;
		ArrayList<Option> optionList;
		private String optionSetName;

		/*
		 * Constructor
		 */
		protected OptionSet() {
			init();
			optionList = new ArrayList<Option>();
		}

		protected OptionSet(String name, int size) {
			init();
			optionSetName = name;
			optionList = new ArrayList<Option>(12);
		}

		protected void init() {
			optionSetName = "";
		}

		/*
		 * Getter
		 */
		protected String getName() {
			return optionSetName;
		}

		/*
		 * get option by option index
		 */
		protected Option getOption(int optionIndex) throws AutoException {
			Option optionObject = null;
			try {
				optionObject = optionList.get(optionIndex);
			} catch (IndexOutOfBoundsException e) {
				throw new exception.AutoException(100);
			}
			return optionObject;
		}

		protected int length() {
			return optionList.size();
		}

		/*
		 * Find
		 */
		// Find Option with name (in context of OptionSet)
		protected Option findOptionSet(String name) {
			Option optionObject = null;
			for (int i = 0; i < optionList.size(); i++) {
				try {
					if (optionList.get(i).getName() == name) {
						optionObject = optionList.get(i);
					}
				} catch (NullPointerException e) {
					/*
					 * According to Carnegie Mellon University Software Engineering Institute You
					 * should not catch a null pointer exception. BUT WE WILL FOR THE SAKE OF THE
					 * ASSIGNMENT!
					 */
					break;
				}
			}
			return optionObject;
		}

		protected int findOptionSetIndex(String name) {
			int i, n;
			n = optionList.size();
			for (i = 0; i < n; i++) {
				try {
					if (optionList.get(i).getName() == name) {
						break;
					}
				} catch (NullPointerException e) {
					/*
					 * According to Carnegie Mellon University Software Engineering Institute You
					 * should not catch a null pointer exception. BUT WE WILL FOR THE SAKE OF THE
					 * ASSIGNMENT!
					 */
					break;
				}
			}
			return i;
		}

		/*
		 * Setter
		 */
		protected void setName(String name) {
			optionSetName = name;
		}

		/*
		 * add an option
		 * 
		 * @param optionName option name
		 * 
		 * @param optionPrice option price
		 */
		protected int addOption(String optionName, double optionPrice) {
			optionList.add(new Option(optionName, optionPrice));
			return optionList.size();
		}

		/*
		 * print() and toString()
		 */
		public void print() {
			System.out.print(toString());
		}

		public String toString() {
			StringBuffer stringBufferObject;
			int i, n;
			n = length();
			stringBufferObject = new StringBuffer("");
			stringBufferObject.append(getName()).append(": ");
			for (i = 0; i < n; i++) {
				try {
					stringBufferObject.append(getOption(i).toString());
				} catch (AutoException e) {
					// nothing
				}
				if (i < n - 1) {
					stringBufferObject.append(", ");
				}
			}
			return stringBufferObject.toString();
		}

		protected class Option implements java.io.Serializable {
			private static final long serialVersionUID = 2272307185575003314L;
			private String optionName;
			private double price;

			/*
			 * Constructor
			 */
			protected Option() {
				optionName = "";
				price = 0;
			}

			protected Option(String name, double price_) {
				optionName = name;
				price = price_;
			}

			/*
			 * Getter
			 */
			protected String getName() {
				return optionName;
			}

			protected double getPrice() {
				return price;
			}

			/*
			 * Setter
			 */
			protected void setName(String name) {
				optionName = name;
			}

			protected void setPrice(double price_) {
				price = price_;
			}

			/*
			 * print() and toString()
			 */
			public void print() {
				System.out.print(toString());
			}

			public String toString() {
				StringBuffer stringBufferObject;
				stringBufferObject = new StringBuffer("");
				stringBufferObject.append(getName()).append(" for $").append(getPrice());
				return stringBufferObject.toString();
			}
		}
	}
}
