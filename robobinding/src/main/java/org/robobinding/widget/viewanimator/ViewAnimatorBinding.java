package org.robobinding.widget.viewanimator;

import android.widget.ViewAnimator;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class ViewAnimatorBinding implements ViewBinding<ViewAnimator> {

	@Override
	public void mapBindingAttributes(BindingAttributeMappings<ViewAnimator> mappings) {
		mappings.mapProperty(DisplayedChildAttribute.class, "displayedChild");
	}
}
