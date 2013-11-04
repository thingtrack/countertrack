package com.thingtrack.konekti.countertrack.workbench.view;

import java.util.Locale;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.I18NComponent;
import com.github.peholmst.i18n4vaadin.I18NListener;
import com.github.peholmst.i18n4vaadin.support.I18NComponentSupport;

import com.vaadin.ui.CustomComponent;

@SuppressWarnings("serial")
public abstract class LocaleComponent extends CustomComponent implements I18NComponent, I18NListener {
	private final I18NComponentSupport support = new I18NComponentSupport(this);
	
	@Override
	public void setI18N(I18N i18n) {
		support.setI18N(i18n);
	}

	@Override
	public I18N getI18N() {
		return support.getI18N();
	}
	
	@Override
	public void attach() {
		super.attach();
		getI18N().addListener(this);
		updateLabels();
	}

	@Override
	public void detach() {
		getI18N().removeListener(this);
		super.detach();
	}

	@Override
	public void localeChanged(I18N sender, Locale oldLocale, Locale newLocale)  {
		updateLabels();
	}
	
	protected abstract void updateLabels();
}
