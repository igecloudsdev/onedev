package io.onedev.server.web.component.tabbable;

import io.onedev.server.web.util.WicketUtils;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PageTab extends Tab {
	
	private static final long serialVersionUID = 1L;

	private final IModel<String> titleModel;
	
	private final IModel<String> iconModel;
	
	private final Class<? extends Page> mainPageClass;
	
	private final List<Class<? extends Page>> additionalPageClasses;
	
	public PageTab(IModel<String> titleModel, IModel<String> iconModel, Class<? extends Page> mainPageClass, 
			List<Class<? extends Page>> additionalPageClasses) {
		this.titleModel = titleModel;
		this.iconModel = iconModel;
		this.mainPageClass = mainPageClass;
		this.additionalPageClasses = additionalPageClasses;
	}

	public PageTab(IModel<String> titleModel, IModel<String> iconModel, Class<? extends Page> mainPageClass) {
		this(titleModel, iconModel, mainPageClass, new ArrayList<>());
	}

	public PageTab(IModel<String> titleModel, IModel<String> iconModel, Class<? extends Page> mainPageClass, 
			Class<? extends Page> additionalPageClass) {
		this(titleModel, iconModel, mainPageClass, asList(additionalPageClass));
	}

	public PageTab(IModel<String> titleModel, IModel<String> iconModel, Class<? extends Page> mainPageClass, 
			Class<? extends Page> additionalPageClass1, Class<? extends Page> additionalPageClass2) {
		this(titleModel, iconModel, mainPageClass, asList(additionalPageClass1, additionalPageClass2));
	}

	public PageTab(IModel<String> titleModel, IModel<String> iconModel, Class<? extends Page> mainPageClass, 
			Class<? extends Page> additionalPageClass1, 
			Class<? extends Page> additionalPageClass2,
			Class<? extends Page> additionalPageClass3) {
		this(titleModel, iconModel, mainPageClass, asList(additionalPageClass1, 
				additionalPageClass2, additionalPageClass3));
	}

	public PageTab(IModel<String> titleModel, Class<? extends Page> mainPageClass,
				   List<Class<? extends Page>> additionalPageClasses) {
		this(titleModel, null, mainPageClass, additionalPageClasses);
	}

	public PageTab(IModel<String> titleModel, Class<? extends Page> mainPageClass) {
		this(titleModel, null, mainPageClass, new ArrayList<Class<? extends Page>>());
	}

	public PageTab(IModel<String> titleModel, Class<? extends Page> mainPageClass,
				   Class<? extends Page> additionalPageClass) {
		this(titleModel, null, mainPageClass, asList(additionalPageClass));
	}

	public PageTab(IModel<String> titleModel, Class<? extends Page> mainPageClass,
				   Class<? extends Page> additionalPageClass1, Class<? extends Page> additionalPageClass2) {
		this(titleModel, null, mainPageClass, asList(additionalPageClass1, additionalPageClass2));
	}

	public PageTab(IModel<String> titleModel, Class<? extends Page> mainPageClass,
				   Class<? extends Page> additionalPageClass1,
				   Class<? extends Page> additionalPageClass2,
				   Class<? extends Page> additionalPageClass3) {
		this(titleModel, null, mainPageClass, asList(additionalPageClass1,
				additionalPageClass2, additionalPageClass3));
	}
	
	private static List<Class<? extends Page>> asList(Class<? extends Page> pageClass) {
		List<Class<? extends Page>> pageClasses = new ArrayList<Class<? extends Page>>();
		pageClasses.add(pageClass);
		return pageClasses;
	}
	
	private static List<Class<? extends Page>> asList(Class<? extends Page> pageClass1, 
			Class<? extends Page> pageClass2) {
		List<Class<? extends Page>> pageClasses = new ArrayList<Class<? extends Page>>();
		pageClasses.add(pageClass1);
		pageClasses.add(pageClass2);
		return pageClasses;
	}

	private static List<Class<? extends Page>> asList(Class<? extends Page> pageClass1, 
			Class<? extends Page> pageClass2, Class<? extends Page> pageClass3) {
		List<Class<? extends Page>> pageClasses = new ArrayList<Class<? extends Page>>();
		pageClasses.add(pageClass1);
		pageClasses.add(pageClass2);
		pageClasses.add(pageClass3);
		return pageClasses;
	}

	private static List<Class<? extends Page>> asList(
			Class<? extends Page> pageClass1, Class<? extends Page> pageClass2, 
			Class<? extends Page> pageClass3, Class<? extends Page> pageClass4) {
		List<Class<? extends Page>> pageClasses = new ArrayList<Class<? extends Page>>();
		pageClasses.add(pageClass1);
		pageClasses.add(pageClass2);
		pageClasses.add(pageClass3);
		pageClasses.add(pageClass4);
		return pageClasses;
	}
	
	private static List<Class<? extends Page>> asList(
			Class<? extends Page> pageClass1, Class<? extends Page> pageClass2, 
			Class<? extends Page> pageClass3, Class<? extends Page> pageClass4, 
			Class<? extends Page> pageClass5) {
		List<Class<? extends Page>> pageClasses = new ArrayList<Class<? extends Page>>();
		pageClasses.add(pageClass1);
		pageClasses.add(pageClass2);
		pageClasses.add(pageClass3);
		pageClasses.add(pageClass4);
		pageClasses.add(pageClass5);
		return pageClasses;
	}
	
	public final IModel<String> getTitleModel() {
		return titleModel;
	}

	@Nullable
	public IModel<String> getIconModel() {
		return iconModel;
	}

	public final List<Class<? extends Page>> getAdditionalPageClasses() {
		return additionalPageClasses;
	}
	
	public final Class<? extends Page> getMainPageClass() {
		return mainPageClass;
	}
	
	/**
	 * Override this to provide your own logic of populating tab item (the &lt;li&gt; element).
	 * 
	 * @param componentId
	 * 			Id of the component to add to the item. 
	 */
	@Override
	public Component render(String componentId) {
		return new PageTabHead(componentId, this);
	}
	
	@Override
	public final boolean isSelected() {
		return isActive(WicketUtils.getPage());
	}

	public boolean isActive(Page currentPage) {
		if (mainPageClass.isAssignableFrom(currentPage.getClass()))
			return true;
		
		for (Class<?> pageClass: additionalPageClasses) {
			if (pageClass.isAssignableFrom(currentPage.getClass())) 
				return true;
		}
		return false;
	}

	@Override
	public String getTitle() {
		return titleModel.getObject();
	}

}
