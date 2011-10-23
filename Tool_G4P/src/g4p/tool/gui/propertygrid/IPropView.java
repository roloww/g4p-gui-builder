package g4p.tool.gui.propertygrid;

import g4p.tool.components.DBase;

/**
 * Interface for the property view.
 * 
 * @author Peter Lager
 *
 */
public interface IPropView {

	public abstract void showProprtiesFor(DBase comp);
	
	public abstract void modelHasBeenChanged();

}