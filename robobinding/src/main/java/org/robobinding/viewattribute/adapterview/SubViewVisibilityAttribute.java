/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.attributevalue.PropertyAttributeValueParser;
import org.robobinding.attributevalue.ValueModelAttributeValue;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.ChildAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;
import org.robobinding.viewattribute.ViewAttributeValidation;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewVisibilityAttribute implements ChildAttribute
{
	private AbstractSubViewVisibility visibility;
	private ValueModelAttributeValue attributeValue;
	
	public SubViewVisibilityAttribute(AbstractSubViewVisibility subViewVisibility)
	{
		this.visibility = subViewVisibility;
	}
	
	@Override
	public void setAttributeValue(String value)
	{
		this.attributeValue = PropertyAttributeValueParser.parseAsValueModelAttributeValue(value);		
	}
	
	@Override
	public void bindTo(BindingContext bindingContext)
	{
		AbstractPropertyViewAttribute<View, ?> propertyViewAttribute = createPropertyViewAttribute(bindingContext);
		propertyViewAttribute.bindTo(bindingContext);
	}
	
	private AbstractPropertyViewAttribute<View, ?> createPropertyViewAttribute(BindingContext bindingContext)
	{
		PresentationModelAdapter presentationModelAdapter = bindingContext.getPresentationModelAdapter();
		Class<?> propertyType = presentationModelAdapter.getPropertyType(attributeValue.getPropertyName());
		
		if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType))
		{
			return new IntegerSubViewVisibilityAttribute();
		}
		else if (PrimitiveTypeUtils.booleanIsAssignableFrom(propertyType))
		{
			return new BooleanSubViewVisibilityAttribute();
		}
		
		throw new RuntimeException("Could not find a suitable attribute in " + getClass().getName() + " for property type: " + propertyType);
	}
	
	class BooleanSubViewVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Boolean>
	{
		@Override
		protected void valueModelUpdated(Boolean newValue)
		{
			if(newValue)
			{
				visibility.makeVisible();
			}else
			{
				visibility.makeGone();
			}
		}
		
		@Override
		protected void validate(ViewAttributeValidation validation)
		{
			//No validation.
		}
	}
	
	class IntegerSubViewVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Integer>
	{
		@Override
		protected void valueModelUpdated(Integer newValue)
		{
			visibility.setVisibility(newValue);
		}
		
		@Override
		protected void validate(ViewAttributeValidation validation)
		{
			//No validation.
		}
	}

}
