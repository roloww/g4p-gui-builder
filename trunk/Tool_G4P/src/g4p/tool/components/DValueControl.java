package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.Validator;

public class DValueControl extends DBase {

	public Float 		_0021_value = 0.5f;
	public String 		value_label = "Value (initial)";
	public String 		value_tooltip = "initial value to use";
	public Boolean 		value_edit = true;
	public Boolean 		value_show = true;
	public String 		value_updater = "validateLimits";

	public Float 		_0022_min = 0.0f;
	public String 		min_label = "Minimum";
	public String 		min_tooltip = "smallest value slider can return";
	public Boolean 		min_edit = true;
	public Boolean 		min_show = true;
	public String 		min_updater = "validateLimits";

	public Float 		_0023_max = 1.0f;
	public String 		max_label = "Maximum";
	public String 		max_tooltip = "largest value slider can return";
	public Boolean 		max_edit = true;
	public Boolean 		max_show = true;
	public String 		max_updater = "validateLimits";
	
	public String 		_0024_vtype = "DECIMAL";
	transient public EditorBase vtype_editor = new EditorJComboBox(VALUE_TYPE);
	public String 		vtype_label = "Value type to display";
	public Boolean 		vtype_edit = true;
	public Boolean 		vtype_show = true;
	public String		vtype_updater = "validateType";
	
	public Integer 		_0025_precision = 2;
	public String 		precision_label = "Numeric precision";
	public String 		precision_tooltip = "precision to display";
	public Boolean 		precision_edit = true;
	public Boolean 		precision_show = true;
	public Validator 	precision_validator = Validator.getValidator(int.class, 0, 4);
	public String		precision_updater = "validateType";

	public Integer 		_0029_nticks = 2;
	public String 		nticks_label = "Number of ticks";
	public String 		nticks_tooltip = "must be at least 2";
	public Boolean 		nticks_edit = true;
	public Boolean 		nticks_show = true;
	public Validator 	nticks_validator = Validator.getValidator(int.class, 2, 999);

	public Boolean 		_0028_stick_to_ticks  = false;
	public Boolean 		stick_to_ticks_edit = true;
	public Boolean 		stick_to_ticks_show = true;
	public String 		stick_to_ticks_label = "Stick to ticks?";
	
	public Float 		_0040_ease = 1.0f;
	public String 		ease_label = "Easing";
	public String 		ease_tooltip = "initial value to use";
	public Boolean 		ease_edit = true;
	public Boolean 		ease_show = true;
	public Validator 	ease_validator = Validator.getValidator(float.class, 1, 30);
	
	public Boolean 		_0060_show_ticks  = false;
	public Boolean 		show_ticks_edit = true;
	public Boolean 		show_ticks_show = true;
	public String 		show_ticks_label = "Show ticks?";
	
	
	public void validateLimits(){
		float t = (_0021_value - _0022_min)/(_0023_max - _0022_min);
		if(t < 0){
			_0021_value = _0022_min;
			propertyModel.hasBeenChanged();
		}
		else if (t > 1){
			_0021_value = _0023_max;
			propertyModel.hasBeenChanged();			
		}
	}
	
	public void validateType(){
		if(_0024_vtype.equals("INTEGER") && _0025_precision != 0 ){
			_0025_precision = 0;
			_0021_value = (float) Math.round(_0021_value);
			_0022_min = (float) Math.round(_0022_min);
			_0023_max = (float) Math.round(_0023_max);
			propertyModel.hasBeenChanged();
		}
	}

	protected String get_creator(DBase parent, String window){
		String s = "";
		s += Messages.build(SET_LIMITS, _0010_name, $(_0021_value), $(_0022_min), $(_0023_max));
		s += Messages.build(SET_VALUE_TYPE, _0010_name, _0024_vtype, $(_0025_precision));
		if(_0029_nticks != 2)
			s += Messages.build(SET_NBR_TICKS, _0010_name, $(_0029_nticks));
		if(_0028_stick_to_ticks)
			s += Messages.build(SET_STICK_TICKS, _0010_name, _0028_stick_to_ticks);
		if(_0060_show_ticks)
			s += Messages.build(SET_SHOW_TICKS, _0010_name, _0060_show_ticks);
		if(_0040_ease > 1)
			s += Messages.build(SET_EASING, _0010_name, $(_0040_ease));
		s += super.get_creator(parent, window);		
		return s;
	}

	protected void read(){
		super.read();
		vtype_editor = new EditorJComboBox(VALUE_TYPE);
	}
}
