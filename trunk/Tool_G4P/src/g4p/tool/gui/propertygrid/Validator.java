package g4p.tool.gui.propertygrid;

import g4p.tool.components.NameGen;



public abstract class Validator {

	private static Validator_Long defaultLong = new Validator_Long();
	private static Validator_Integer defaultInt = new Validator_Integer();
	private static Validator_Short defaultShort = new Validator_Short();
	private static Validator_Float defaultFloat = new Validator_Float();
	private static Validator_Double defaultDouble = new Validator_Double();
	private static Validator_String defaultString = new Validator_String();

	/**
	 * Make sure a validator is always returned
	 * @param c
	 * @return
	 */
	public static Validator getValidator(Class<?> c, Object ... args){
		Validator v = null;
		if(c == short.class || c == Short.class)
			v = new Validator_Short(args);
		else if(c == int.class || c == Integer.class)
			v = new Validator_Integer(args);
		else if(c == long.class || c == Long.class)
			v = new Validator_Long(args);
		else if(c == float.class || c == Float.class)
			v = new Validator_Float(args);
		else if(c == double.class || c == Double.class)
			v = new Validator_Double(args);
		else if(c == String.class)
			v = new Validator_String(args);
		else
			v = new Validator_String(args);
		return v;
	}

	/**
	 * Make sure a validator is always returned
	 * @param c
	 * @return
	 */
	public static Validator getDefaultValidator(Class<?> c){
		Validator v = defaultString;
		if(c == short.class || c == Short.class)
			v = defaultShort;
		else if(c == int.class || c == Integer.class)
			v = defaultInt;
		else if(c == long.class || c == Long.class)
			v = defaultLong;
		else if(c == float.class || c == Float.class)
			v = defaultFloat;
		else if(c == double.class || c == Double.class)
			v = defaultDouble;
		else if(c == String.class)
			v = defaultString;
		return v;
	}

	/**
	 * 
	 * @param type control string
	 * @return
	 */
	public static Validator getValidator(String type){
		if(type.equals("COMPONENT_NAME")){
			return new Validator_ControlName();
		}
		// 	Give up and return a default string	
		return defaultString;
	}
	
	// ==============================================================================
	// ==============================================================================

	// INSTANCE attributes and methods

	// Should be set to remember the original value before
	// editing should be set by the editor
	protected Object originalValue;

	// Holds the current cell value even if in valid
	protected Object cellValue;

	/**
	 * This should be overridden in the child class to cast the 
	 * return object to the type being validated.
	 * 
	 * @return the cellValue
	 */
	abstract public Object getCellValue();

	/**
	 * Validate the cell contents
	 * @param value the 'value' shown by the editor component
	 * @return
	 */
	abstract public boolean isValid(Object value);


	/**
	 * @return the originalValue
	 */
	public Object getOriginalValue() {
		return originalValue;
	}

	/**
	 * @param originalValue the originalValue to set
	 */
	public void setOriginalValue(Object originalValue) {
		this.originalValue = originalValue;
	}


	public void postEditAction(){	}
	public void preEditAction(){	}

	/**
	 * ====================================================
	 * Validator for variable and method identifiers
	 * ====================================================
	 * @author Peter Lager
	 */
	static class Validator_ControlName extends Validator {

		private long min = 1;
		private long max = 1000;

		public Validator_ControlName(Object ... args){
		}

		@Override
		public Object getCellValue() {
			return (String)cellValue;
		}

		@Override
		public boolean isValid(Object value) {
                    System.out.println("Name validator:  isValid()");
			String uv = value.toString();
			int vs = value.toString().length();
			boolean valid = true;
			if(uv.length() < min)
				valid = false;
			else if(uv.length() > max)
				valid = false;
			else if(uv.charAt(0) < 'a')
				valid = false;
			else if(uv.charAt(0) > 'z')
				valid = false;
			else if(uv.contains(" "))
				valid = false;
			else if(NameGen.instance().used(uv))
				valid = false;
			if(valid)
				cellValue = uv;
			return valid;
		}

		public void preEditAction(){
			NameGen.instance().remove((String) originalValue);
		}
		
		public void postEditAction(){
			NameGen.instance().add((String) cellValue);
		}

	}

	/**
	 * ====================================================
	 * Validator for long data type
	 * ====================================================
	 * @author Peter Lager
	 */
	static class Validator_Long extends Validator {
		private long min = Long.MIN_VALUE;
		private long max = Long.MAX_VALUE;

		/**
		 * 
		 * @param args
		 */
		public Validator_Long(Object ... args){
			if(args.length > 0){
				min = Long.parseLong(args[0].toString());
				max = Long.parseLong(args[1].toString());
			}
		}

		/**
		 * See if the value passed is valid
		 */
		@Override
		public boolean isValid(Object value) {
			boolean result = false;
			Long v;
			try {
				v = Long.parseLong(value.toString());
				cellValue = v;
				result = (v >= min && v <= max);
			}
			catch(Exception excp){
				cellValue = value;
				result = false;
			}
			return result;
		}

		public Object getCellValue() {
			return (Long)cellValue;
		}

	}

	/**
	 * ====================================================
	 * Validator for int data type
	 * ====================================================
	 * @author Peter Lager
	 */
	static class Validator_Integer extends Validator{
		int min = Integer.MIN_VALUE;
		int max = Integer.MAX_VALUE;

		/**
		 * 
		 * @param args
		 */
		public Validator_Integer(Object ... args){
			if(args.length > 1){
				min = Integer.parseInt(args[0].toString());
				max = Integer.parseInt(args[1].toString());
			}
		}

		/**
		 * See if the value passed is valid
		 */
		@Override
		public boolean isValid(Object value) {
			boolean result = false;
			Integer v = null;
			try {
				v = Integer.parseInt(value.toString());
				cellValue = v;
				result = (v >= min && v <= max);
			}
			catch(Exception excp){
				cellValue = value;
				result = false;
			}
			return result;
		}

		public Object getCellValue() {
			return (Integer)cellValue;
		}

	}

	/**
	 * ====================================================
	 * Validator for short data type
	 * ====================================================
	 * @author Peter Lager
	 */
	static class Validator_Short extends Validator{
		short min = Short.MIN_VALUE;
		short max = Short.MAX_VALUE;

		/**
		 * 
		 * @param args
		 */
		public Validator_Short(Object ... args){
			if(args.length > 1){
				min = Short.parseShort(args[0].toString());
				max = Short.parseShort(args[1].toString());
			}
		}

		/**
		 * See if the value passed is valid
		 */
		@Override
		public boolean isValid(Object value) {
			boolean result = false;
			Short v;
			try {
				v = Short.parseShort(value.toString());
				cellValue = v;
				result = (v >= min && v <= max);
			}
			catch(Exception excp){
				cellValue = value;
				result = false;
			}
			return result;
		}

		public Object getCellValue() {
			return (Short)cellValue;
		}

	}

	/**
	 * ====================================================
	 * Validator for float data type
	 * ====================================================
	 * @author Peter Lager
	 */
	static class Validator_Float extends Validator{
		float min = Float.MIN_VALUE;
		float max = Float.MAX_VALUE;

		/**
		 * 
		 * The number of arguments determine their type i.e.
		 * (1) Boolean (empty)
		 * (2) Integer (min, max)
		 * (3) Boolean (min, max, empty)
		 * @param args
		 */
		public Validator_Float(Object ... args){
			if(args.length > 1){
				min = Float.parseFloat(args[0].toString());
				max = Float.parseFloat(args[1].toString());
			}
		}

		/**
		 * See if the value passed is valid
		 */
		@Override
		public boolean isValid(Object value) {
			boolean result = false;
			Float v;
			try {
				v = Float.parseFloat(value.toString());
				cellValue = v;
				result = (v >= min && v <= max);
			}
			catch(Exception excp){
				cellValue = value;
				result = false;
			}
			return result;
		}

		public Object getCellValue() {
			return (Float)cellValue;
		}

	}

	/**
	 * ====================================================
	 * Validator for float data type
	 * ====================================================
	 * @author Peter Lager
	 */
	static class Validator_Double extends Validator{
		double min = Double.MIN_VALUE;
		double max = Double.MAX_VALUE;

		/**
		 * 
		 * The number of arguments determine their type i.e.
		 * (1) Boolean (empty)
		 * (2) Integer (min, max)
		 * (3) Boolean (min, max, empty)
		 * @param args
		 */
		public Validator_Double(Object ... args){
			if(args.length > 1){
				min = Double.parseDouble(args[0].toString());
				max = Double.parseDouble(args[1].toString());
			}
		}

		/**
		 * See if the value passed is valid
		 */
		@Override
		public boolean isValid(Object value) {
			boolean result = false;
			Double v;
			try {
				v = Double.parseDouble(value.toString());
				cellValue = v;
				result = (v >= min && v <= max);
			}
			catch(Exception excp){
				cellValue = value;
				result = false;
			}
			return result;
		}

		public Object getCellValue() {
			return (Double)cellValue;
		}

	}

	/**
	 * ====================================================
	 * Validator for String data type
	 * ====================================================
	 * @author Peter Lager
	 */
	static class Validator_String extends Validator{
		long min = 0;
		long max = 1000;

		/**
		 * 
		 * The length of args should be 2 i.e.
		 * Integer (min, max)
		 * @param args
		 */
		public Validator_String(Object ... args){
			if(args.length > 1){
				min = Integer.parseInt(args[0].toString());
				max = Integer.parseInt(args[1].toString());
			}
		}

		/**
		 * See if the value passed is valid
		 */
		@Override
		public boolean isValid(Object value) {
			int vs = value.toString().length();
			boolean valid = (vs >= min && vs <= max);
			if(valid)
				cellValue = value;
			return valid;
		}

		public Object getCellValue() {
			return (String)cellValue;
		}

	}


}
