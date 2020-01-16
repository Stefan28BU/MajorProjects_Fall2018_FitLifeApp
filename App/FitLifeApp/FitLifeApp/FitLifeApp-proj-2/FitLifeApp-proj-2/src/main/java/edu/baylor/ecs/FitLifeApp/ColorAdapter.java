package edu.baylor.ecs.FitLifeApp;

import java.awt.Color;
import javax.xml.bind.annotation.adapters.XmlAdapter;


public final class ColorAdapter extends XmlAdapter<String, Color> {

	
	// String/XML to color
	@Override
	public Color unmarshal(String v) throws Exception {

		return new Color(Integer.parseInt(v));
	}

	
	//Color to string/xml
	@Override
	public String marshal(Color v) throws Exception {
		return Integer.toString(v.getRGB());
	}

}
