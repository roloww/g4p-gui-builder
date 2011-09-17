package g4p.tool.components;

import processing.core.PApplet;


@SuppressWarnings("serial")
public class DSliderFloat extends DCore {

	public Float 		_0040_value = 50.0f;
	public String 		value_label = "Value (initial)";
	public String 		value_tooltip = "initial value to use";
	public Boolean 		value_edit = true;
	public Boolean 		value_show = true;
	public String 		value_updater = "validateLimits";

	public Float 		_0041_min = 0.0f;
	public String 		min_label = "Minimum";
	public String 		min_tooltip = "smallest value slider can return";
	public Boolean 		min_edit = true;
	public Boolean 		min_show = true;
	public String 		min_updater = "validateLimits";

	public Float 		_0042_max = 100.0f;
	public String 		max_label = "Maximum";
	public String 		max_tooltip = "largest value slider can return";
	public Boolean 		max_edit = true;
	public Boolean 		max_show = true;
	public String 		max_updater = "validateLimits";


	public void validateLimits(){
		if(_0041_min > _0042_max){
			float temp = _0042_max;
			_0042_max = _0041_min;
			_0041_min = temp;
		}
		_0040_value = PApplet.constrain(_0040_value, _0041_min, _0042_max);
	}
}
